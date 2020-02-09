package com.scizzor.scizzorio.repository;

import com.scizzor.scizzorio.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserAccount, Long> {
  public UserAccount findByEmail(String email);
  public UserAccount findById(long Id);
}
