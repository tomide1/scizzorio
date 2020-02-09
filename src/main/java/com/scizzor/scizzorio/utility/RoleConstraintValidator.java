package com.scizzor.scizzorio.utility;

import static com.scizzor.scizzorio.enums.UserRole.BRAND;
import static com.scizzor.scizzorio.enums.UserRole.CUSTOMER;

import com.scizzor.scizzorio.enums.UserRole;
import com.scizzor.scizzorio.annotations.ValidRole;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoleConstraintValidator implements ConstraintValidator<ValidRole, UserRole> {
  
  @Override
  public void initialize(ValidRole constraintAnnotation) {}
  
  @Override
  public boolean isValid(UserRole role, ConstraintValidatorContext context) {
    return validateRole(role);
  }
  
  private boolean validateRole(UserRole role) {
    return role.equals(BRAND) || role.equals(CUSTOMER);
  }
}
