package com.scizzor.scizzorio.entity;

import com.scizzor.scizzorio.model.UserAccount;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class VerificationToken {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  
  private String token;
  
  private Date expiryDate;
  
  @OneToOne(targetEntity = UserAccount.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "userId")
  private UserAccount user;
  
  protected VerificationToken() {}
  
  public VerificationToken(final String token, final UserAccount user) {
    this.token = token;
    this.user = user;
  }

  private Date calculateExpiryDate(int expiryTimeInMinutes) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Timestamp(calendar.getTime().getTime()));
    calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
    return new Date(calendar.getTime().getTime());
  }
}
