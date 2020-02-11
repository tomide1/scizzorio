package com.scizzor.scizzorio.registration;

import com.scizzor.scizzorio.model.UserAccount;
import com.scizzor.scizzorio.service.UserService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener
    implements ApplicationListener<OnRegistrationCompleteEvent> {
  private UserService userService;
  private JavaMailSender mailSender;
  
  @Autowired
  public RegistrationListener(
      final UserService userService,
      final JavaMailSender mailSender
  ) {
    this.userService = userService;
    this.mailSender = mailSender;
  }
  
  @Override
  public void onApplicationEvent(OnRegistrationCompleteEvent event) {
    this.confirmRegistration(event);
  }
  
  private void confirmRegistration(OnRegistrationCompleteEvent event) {
    UserAccount user = event.getUser();
    String token = UUID.randomUUID().toString();
    userService.createVerificationToken(user, token);
    SimpleMailMessage email = constructEmailMessage(user, token, event);
    mailSender.send(email);
  }
  
  private SimpleMailMessage constructEmailMessage(
      UserAccount user,
      String token,
      OnRegistrationCompleteEvent event
  ) {
    String recipientAddress = user.getEmail();
    String subject = "Registration Confirmation";
    String confirmationUrl = event.getAppUrl()
        + "/registrationConfirm.html?token="
        + token;
    String message = "Click to complete email verification.";
  
    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(recipientAddress);
    email.setSubject(subject);
    email.setText(message + "\r\n" + "http://localhost:8081" + confirmationUrl);
    return email;
  }
}
