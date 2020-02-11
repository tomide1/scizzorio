package com.scizzor.scizzorio.controller;

import com.scizzor.scizzorio.entity.VerificationToken;
import com.scizzor.scizzorio.model.UserAccount;
import com.scizzor.scizzorio.service.UserService;
import java.util.Calendar;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConfirmRegistrationController {
  private UserService userService;
  private JavaMailSender mailSender;
  
  @Autowired
  public ConfirmRegistrationController(
      final UserService userService,
      final JavaMailSender mailSender) {
    this.userService = userService;
    this.mailSender = mailSender;
  }
  
  @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
  public ResponseEntity<?> confirmRegistration(
      HttpServletRequest request,
      @RequestParam("token") String token)
  {
    Optional<VerificationToken> verificationToken = userService.getVerificationToken(token);
    if (verificationToken.isEmpty()) {
      //: TODO add redirect to react app
      return new ResponseEntity<>("invalid token", HttpStatus.NOT_ACCEPTABLE);
    }
  
    UserAccount user = verificationToken.get().getUser();
    Calendar calendar = Calendar.getInstance();
    long timeLeft = verificationToken.get().getExpiryDate().getTime()
        - calendar.getTime().getTime();
    if (timeLeft <= 0 ) {
      //: TODO add redirect to react app
      return new ResponseEntity<>("token expired", HttpStatus.NOT_ACCEPTABLE);
    }
    
    user.setEnabled(true);
    UserAccount updatedUser = userService.saveRegisteredUser(user);
    //: TODO add redirect to react app
    return ResponseEntity.ok("user is registered");
  }
  
  @RequestMapping(value = "/resendVerificationEmail", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<?> resendVerificationEmail(
      HttpServletRequest request,
      @RequestParam("token") String existingToken
  ) {
    Optional<VerificationToken> newToken = userService.generateNewVerificationToken(existingToken);
    if (newToken.isPresent()) {
      Optional<UserAccount> user = userService.getUser(newToken.get().getToken());
      if (user.isEmpty()) {
        return getErrorResponse();
      }
      SimpleMailMessage email = constructResendVerificationEmail(request, newToken.get(), user.get());
      mailSender.send(email);
    } else {
      return getErrorResponse();
    }
    return ResponseEntity.ok("resent token");
  }
  
  private SimpleMailMessage constructResendVerificationEmail(
      HttpServletRequest request,
      VerificationToken token,
      UserAccount user
  ) {
    String recipientAddress = user.getEmail();
    String appUrl =
        "http://" + request.getServerName() +
            ":" + request.getServerPort() +
            request.getContextPath();
    String confirmationUrl = appUrl + "/registrationConfirm.html?token=" + token.getToken();
    String subject = "Resend Registration Token";
    String message = "Click to complete email verification.";
    
    SimpleMailMessage email = new SimpleMailMessage();
    email.setSubject(subject);
    email.setText(message + "\r\n" + confirmationUrl);
    email.setTo(recipientAddress);
    return email;
  }
  
  private ResponseEntity<?> getErrorResponse() {
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
