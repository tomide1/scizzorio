package com.scizzor.scizzorio.service;

import com.scizzor.scizzorio.model.UserAccount;
import com.scizzor.scizzorio.repository.UserRepository;
import java.util.Collections;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
  private UserRepository userRepository;
  
  @Autowired
  public AppUserDetailsService(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    try {
      Optional<UserAccount> userAccount = Optional.ofNullable(userRepository.findByEmail(email));
      if (userAccount.isEmpty()) {
        throw new
            UsernameNotFoundException(String.format("No user found with username %s", email));
      }
      UserAccount user = userAccount.get();
      return new User(
          email,
          user.getPassword(),
          user.isEnabled(),
          true,
          true,
          true,
          Collections.singletonList(
              new SimpleGrantedAuthority(user.getRole().name())
          )
      );
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }
}
