package com.scizzor.scizzorio.model;

import com.scizzor.scizzorio.enums.Gender;
import com.scizzor.scizzorio.enums.UserRole;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserAccount {
  private String brandName;
  private String firstName;
  private String lastName;
  private String password;
  private String email;
  private String telephoneNumber;
  private UserRole role;
  private Gender gender;
  
  @Column
  private boolean enabled;
  
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;
  
  protected UserAccount() {
    super();
    this.enabled = false;
  }
  
  public UserAccount(
      final String brandName,
      final String firstName,
      final String lastName,
      final String password,
      final String email,
      final String telephoneNUmber,
      final UserRole role
  ) {
    super();
    this.brandName = brandName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.email = email;
    this.telephoneNumber = telephoneNUmber;
    this.role = role;
    this.enabled = false;
  }
  
  public UserAccount(
      final String firstName,
      final String lastName,
      final String password,
      final String email,
      final String telephoneNUmber,
      final UserRole role,
      final Gender gender
  ) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.email = email;
    this.telephoneNumber = telephoneNUmber;
    this.role = role;
    this.gender = gender;
    this.enabled = false;
  }
  
  public String toString() {
    return "Firstname: " + firstName + ", Lastname: " + lastName + ", Role: " + role;
  }
}
