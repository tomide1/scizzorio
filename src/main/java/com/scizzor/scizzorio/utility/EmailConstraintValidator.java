package com.scizzor.scizzorio.utility;

import com.scizzor.scizzorio.annotations.ValidEmail;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailConstraintValidator implements ConstraintValidator<ValidEmail, String> {
  private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
  
  @Override
  public void initialize(ValidEmail constraintAnnotation) {}
  
  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    return validateEmail(email);
  }
  
  private boolean validateEmail(String email) {
    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    return pattern.matcher(email).matches();
  }
}
