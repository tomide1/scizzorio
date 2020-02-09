package com.scizzor.scizzorio.service;

import static com.scizzor.scizzorio.enums.UserRole.BRAND;
import static com.scizzor.scizzorio.enums.UserRole.CUSTOMER;

import com.scizzor.scizzorio.entity.VerificationToken;
import com.scizzor.scizzorio.exceptions.EmailExistsException;
import com.scizzor.scizzorio.model.dto.BrandAccountDto;
import com.scizzor.scizzorio.model.dto.CustomerAccountDto;
import com.scizzor.scizzorio.model.UserAccount;
import com.scizzor.scizzorio.model.dto.UserAccountDto;
import com.scizzor.scizzorio.enums.UserRole;
import com.scizzor.scizzorio.repository.TokenRepository;
import com.scizzor.scizzorio.repository.UserRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@Transactional
public class UserServiceImpl implements UserService {
  private UserRepository userRepository;
  private TokenRepository tokenRepository;
  
  @Autowired
  public UserServiceImpl(
      final UserRepository userRepository,
      final TokenRepository tokenRepository
  ) {
    this.userRepository = userRepository;
    this.tokenRepository = tokenRepository;
  }
  
  @Override
  public Optional<UserAccount> registerNewUserAccount(
      UserAccountDto userAccountDto,
      BindingResult bindingResult) throws EmailExistsException
  {
    String email = userAccountDto.getEmail();
    if (emailExists(email)) {
      bindingResult.rejectValue("email", "duplicate email");
      throw new EmailExistsException(
          String.format("An account exists with this email address: %s", email));
    }
    UserRole userRole = userAccountDto.getRole();
    UserAccount userAccount = null;
    if (userRole.equals(BRAND)) {
      userAccount = createNewBrandAccount(userAccountDto);
    }
    if(userRole.equals(CUSTOMER)) {
      userAccount = createNewCustomerAccount(userAccountDto);
    }
    userAccount = userAccount != null ? userRepository.save(userAccount) : null;
    return Optional.ofNullable(userAccount);
  }
  
  @Override
  public UserAccount getUser(String verificationToken) {
    return tokenRepository.findByToken(verificationToken).getUser();
  }
  
  @Override
  public UserAccount saveRegisteredUser(UserAccount user) {
    return userRepository.save(user);
  }
  
  @Override
  public void createVerificationToken(UserAccount user, String token) {
    tokenRepository.save(new VerificationToken(token, user));
  }
  
  @Override
  public Optional<VerificationToken> getVerificationToken(String verificationToken) {
    return Optional.of(tokenRepository.findByToken(verificationToken));
  }
  
  private boolean emailExists(String email) {
    UserAccount userAccount = userRepository.findByEmail(email);
    return userAccount != null;
  }
  
  private UserAccount createNewCustomerAccount(UserAccountDto userAccountDto) {
    CustomerAccountDto customerAccountDto = (CustomerAccountDto) userAccountDto;
    return new UserAccount(
        customerAccountDto.getFirstName(),
        customerAccountDto.getLastName(),
        customerAccountDto.getPassword(),
        customerAccountDto.getEmail(),
        customerAccountDto.getTelephoneNumber(),
        customerAccountDto.getRole(),
        customerAccountDto.getGender()
    );
  }
  
  private UserAccount createNewBrandAccount(UserAccountDto userAccountDto) {
    BrandAccountDto brandAccountDto = (BrandAccountDto) userAccountDto;
    return new UserAccount(
        brandAccountDto.getBrandName(),
        brandAccountDto.getFirstName(),
        brandAccountDto.getLastName(),
        brandAccountDto.getPassword(),
        brandAccountDto.getEmail(),
        brandAccountDto.getTelephoneNumber(),
        userAccountDto.getRole()
    );
  }
}
