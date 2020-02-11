package com.scizzor.scizzorio.service;

import com.scizzor.scizzorio.entity.VerificationToken;
import com.scizzor.scizzorio.exceptions.EmailExistsException;
import com.scizzor.scizzorio.model.UserAccount;
import com.scizzor.scizzorio.model.dto.UserAccountDto;
import java.util.Optional;
import org.springframework.validation.BindingResult;

public interface UserService {
  Optional<UserAccount> registerNewUserAccount(UserAccountDto userAccountDto, BindingResult bindingResult)
      throws EmailExistsException;
  Optional<UserAccount> getUser(String verificationToken);
  UserAccount saveRegisteredUser(UserAccount user);
  void createVerificationToken(UserAccount user, String token);
  Optional<VerificationToken> generateNewVerificationToken(String token);
  Optional<VerificationToken> getVerificationToken(String verificationToken);
  String getAllBrands();
}
