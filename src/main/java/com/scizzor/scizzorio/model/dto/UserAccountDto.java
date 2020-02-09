package com.scizzor.scizzorio.model.dto;

import com.scizzor.scizzorio.enums.UserRole;

public interface UserAccountDto {
  public String getFirstName();
  public String getLastName();
  public String getPassword();
  public String getMatchingPassword();
  public String getEmail();
  public String getTelephoneNumber();
  public UserRole getRole();
}
