package com.scizzor.scizzorio.model.dto;

import com.scizzor.scizzorio.enums.UserRole;
import com.scizzor.scizzorio.annotations.ValidEmail;
import com.scizzor.scizzorio.annotations.ValidPassword;
import com.scizzor.scizzorio.annotations.ValidRole;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ValidPassword
public class BrandAccountDto implements UserAccountDto {
  @NotNull
  @NotEmpty
  private String firstName;

  @NotNull
  @NotEmpty
  private String lastName;

  @NotNull
  @NotEmpty
  private String brandName;
  
  private String password;
  private String matchingPassword;

  @ValidEmail
  private String email;

  @NotNull
  @NotEmpty
  private String telephoneNumber;
  
  @ValidRole
  private UserRole role;

  protected BrandAccountDto() {}

  public BrandAccountDto(
      String firstName,
      String lastName,
      String brandName,
      String password,
      String matchingPassword,
      String email,
      String telephoneNumber,
      UserRole role
  ) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.brandName = brandName;
    this.password = password;
    this.matchingPassword = matchingPassword;
    this.email = email;
    this.telephoneNumber = telephoneNumber;
    this.role = role;
  }
  
  @Override
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  @Override
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }
  
  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  
  @Override
  public String getMatchingPassword() {
    return matchingPassword;
  }

  public void setMatchingPassword(String matchingPassword) {
    this.matchingPassword = matchingPassword;
  }
  
  @Override
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelephoneNumber() {
    return telephoneNumber;
  }

  public void setTelephoneNumber(String telephoneNumber) {
    this.telephoneNumber = telephoneNumber;
  }
  
  public UserRole getRole() {
    return role;
  }
  
  public void setRole(UserRole role) {
    this.role = role;
  }
}
