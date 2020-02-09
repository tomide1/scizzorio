package com.scizzor.scizzorio.utility;

import com.google.common.base.Joiner;
import com.scizzor.scizzorio.model.dto.UserAccountDto;
import com.scizzor.scizzorio.annotations.ValidPassword;
import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.LowercaseCharacterRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.SpecialCharacterRule;
import org.passay.UppercaseCharacterRule;
import org.passay.WhitespaceRule;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, Object> {

  @Override
  public void initialize(ValidPassword constraintAnnotation) {}

  @Override
  public boolean isValid(Object user, ConstraintValidatorContext context) {
    PasswordValidator validator = new PasswordValidator(Arrays.asList(
        new LengthRule(8, 30),
        new UppercaseCharacterRule(1),
        new LowercaseCharacterRule(1),
        new DigitCharacterRule(1),
        new SpecialCharacterRule(1),
        new WhitespaceRule()
    ));
    UserAccountDto userAccountDto = (UserAccountDto) user;
    RuleResult result = validator.validate(new PasswordData(userAccountDto.getPassword()));
    if (result.isValid() && userAccountDto.getPassword().equals(userAccountDto.getMatchingPassword())) {
      return true;
    }
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(
            Joiner.on(',').join(validator.getMessages(result))).addConstraintViolation();
    return false;
  }
}
