package com.scizzor.scizzorio.controller;

import com.scizzor.scizzorio.exceptions.EmailExistsException;
import com.scizzor.scizzorio.model.dto.BrandAccountDto;
import com.scizzor.scizzorio.model.UserAccount;
import com.scizzor.scizzorio.model.dto.CustomerAccountDto;
import com.scizzor.scizzorio.model.dto.UserAccountDto;
import com.scizzor.scizzorio.model.dto.AuthenticationRequest;
import com.scizzor.scizzorio.model.dto.AuthenticationResponse;
import com.scizzor.scizzorio.registration.OnRegistrationCompleteEvent;
import com.scizzor.scizzorio.service.AppUserDetailsService;
import com.scizzor.scizzorio.service.JwtUtil;
import com.scizzor.scizzorio.service.UserService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
  private AuthenticationManager authenticationManager;
  private JwtUtil jwtUtil;
  private AppUserDetailsService userDetailsService;
  private UserService userService;
  private ApplicationEventPublisher applicationEventPublisher;
  
  @Autowired
  public AuthenticationController(
      final AuthenticationManager authenticationManager,
      final JwtUtil jwtUtil,
      final AppUserDetailsService userDetailsService,
      final UserService userService,
      final ApplicationEventPublisher applicationEventPublisher
  ) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil =jwtUtil;
    this.userDetailsService = userDetailsService;
    this.userService = userService;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @GetMapping("/")
  public String home() {
    return ("<h1>Hello World</h1>");
  }

  @GetMapping("/hello")
  public String hello() {
    return ("<h1>Hello World</h1>");
  }

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authRequest)
      throws Exception {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              authRequest.getUsername(),
              authRequest.getPassword()
          )
      );
    } catch (BadCredentialsException e) {
      throw new Exception("Bad credentials exception", e);
    }
    try {
      final UserDetails userDetails =
          userDetailsService.loadUserByUsername(authRequest.getUsername());
      final String jwt = getJwt(userDetails);
      if (!userDetails.isEnabled()) {
        return new ResponseEntity("user is not authorised", HttpStatus.NOT_ACCEPTABLE);
      }
      return ResponseEntity.ok(new AuthenticationResponse(jwt));
    } catch (Exception ex) {
      return new ResponseEntity(ex, HttpStatus.NOT_ACCEPTABLE);
    }
  }
  
  @RequestMapping(value = "/register-brand", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<?> registerBrandUser(
      @Valid @RequestBody BrandAccountDto brandAccount,
      BindingResult bindingResult,
      HttpServletRequest request) throws Exception
  {
    return registerUser(brandAccount, bindingResult, request);
  }
  
  @RequestMapping(value = "/register-customer", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<?> registerCustomerUser(
      @Valid @RequestBody CustomerAccountDto customerAccount,
      BindingResult bindingResult,
      HttpServletRequest request) throws Exception
  {
    return registerUser(customerAccount, bindingResult, request);
  }
  
  private ResponseEntity<?> registerUser(
      UserAccountDto userAccount, BindingResult bindingResult, HttpServletRequest request)
      throws Exception
  {
    Optional<UserAccount> registeredUser = Optional.empty();
    if (!bindingResult.hasErrors()) {
      registeredUser = createUserAccount(userAccount, bindingResult);
    }
  
    if (bindingResult.hasErrors()) {
      return new ResponseEntity(
          bindingResult.getAllErrors().toString(),
          HttpStatus.CONFLICT);
    }
  
    if (registeredUser.isPresent()) {
      try {
        String appUrl = request.getContextPath();
        applicationEventPublisher.publishEvent(
            new OnRegistrationCompleteEvent(
                registeredUser.get(), request.getLocale(), appUrl
            )
        );
      } catch (Exception ex) {
        return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return ResponseEntity.ok(registeredUser.get());
    }
  
    return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
  }
  
  private Optional<UserAccount> createUserAccount(UserAccountDto userAccountDto, BindingResult bindingResult) {
    try {
      return userService.registerNewUserAccount(userAccountDto, bindingResult);
    } catch (EmailExistsException e) {
      return Optional.empty();
    }
  }

  private String getJwt(UserDetails userDetails) {
    return jwtUtil.generateToken(userDetails);
  }

}
