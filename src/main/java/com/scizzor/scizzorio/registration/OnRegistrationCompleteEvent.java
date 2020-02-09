package com.scizzor.scizzorio.registration;

import com.scizzor.scizzorio.model.UserAccount;
import java.util.Locale;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
  private final UserAccount user;
  private final Locale locale;
  private final String appUrl;
  
  public OnRegistrationCompleteEvent(
      final UserAccount user,
      final Locale locale,
      final String appUrl
  ) {
    super(user);
    this.user = user;
    this.locale = locale;
    this.appUrl = appUrl;
  }
}
