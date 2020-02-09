package com.scizzor.scizzorio.model.dto;

import com.scizzor.scizzorio.enums.Gender;
import com.scizzor.scizzorio.enums.UserRole;
import com.scizzor.scizzorio.annotations.ValidEmail;
import com.scizzor.scizzorio.annotations.ValidPassword;
import com.scizzor.scizzorio.annotations.ValidRole;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ValidPassword
public class CustomerAccountDto implements UserAccountDto {
  @NotNull
  @NotEmpty
  private String firstName;
  
  @NotNull
  @NotEmpty
  private String lastName;
  
  private String password;
  private String matchingPassword;
  
  @ValidEmail
  @NotNull
  @NotEmpty
  private String email;
  
  @NotNull
  @NotEmpty
  private String telephoneNumber;
  
  @NotNull
  private Gender gender;
  
  @ValidRole
  private UserRole role;
  
  public CustomerAccountDto() {}
  
  public CustomerAccountDto(
      String firstName,
      String lastName,
      String password,
      String matchingPassword,
      String email,
      String telephoneNumber,
      Gender gender,
      UserRole role
  ) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.matchingPassword = matchingPassword;
    this.email = email;
    this.telephoneNumber = telephoneNumber;
    this.gender = gender;
    this.role = role;
  }
  
  @Override
  public String getFirstName() {
    return firstName;
  }
  
  @Override
  public String getLastName() {
    return lastName;
  }
  
  @Override
  public String getPassword() {
    return password;
  }
  
  @Override
  public String getMatchingPassword() {
    return matchingPassword;
  }
  
  @Override
  public String getEmail() {
    return email;
  }
  
  @Override
  public String getTelephoneNumber() {
    return telephoneNumber;
  }
  
  public Gender getGender() {
    return gender;
  }
  
  @Override
  public UserRole getRole() {
    return role;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public void setMatchingPassword(String matchingPassword) {
    this.matchingPassword = matchingPassword;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public void setTelephoneNumber(String telephoneNumber) {
    this.telephoneNumber = telephoneNumber;
  }
  
  public void setGender(Gender gender) {
    this.gender = gender;
  }
  
  public void setRole(UserRole role) {
    this.role = role;
  }
}
