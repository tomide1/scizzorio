package com.scizzor.scizzorio.controller;

import com.scizzor.scizzorio.entity.VerificationToken;
import com.scizzor.scizzorio.model.UserAccount;
import com.scizzor.scizzorio.service.UserService;
import java.util.Calendar;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConfirmRegistrationController {
  private UserService userService;
  
  @Autowired
  public ConfirmRegistrationController(final UserService userService) {
    this.userService = userService;
  }
  
  @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
  public String confirmRegistration(
      HttpServletRequest request,
      @RequestParam("token") String token)
  {
    Optional<VerificationToken> verificationToken = userService.getVerificationToken(token);
    if (verificationToken.isEmpty()) {
      //: TODO add redirect to react app
      return "invalid token";
    }
  
    UserAccount user = verificationToken.get().getUser();
    Calendar calendar = Calendar.getInstance();
    long timeLeft = verificationToken.get().getExpiryDate().getTime()
        - calendar.getTime().getTime();
    if (timeLeft <= 0 ) {
      //: TODO add redirect to react app
      return "token expired";
    }
    
    user.setEnabled(true);
    UserAccount updatedUser = userService.saveRegisteredUser(user);
    //: TODO add redirect to react app
    return "user is registered";
  }
}
