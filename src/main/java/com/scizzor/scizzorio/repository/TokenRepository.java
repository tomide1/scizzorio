package com.scizzor.scizzorio.repository;

import com.scizzor.scizzorio.entity.VerificationToken;
import com.scizzor.scizzorio.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<VerificationToken, Long> {
  public VerificationToken findByToken(String token);
  public VerificationToken findByUser(UserAccount user);
}
