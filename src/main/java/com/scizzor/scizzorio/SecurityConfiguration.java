package com.scizzor.scizzorio;

import com.scizzor.scizzorio.filter.JwtRequestFilter;
import com.scizzor.scizzorio.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Autowired
  private AppUserDetailsService appUserDetailsService;

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(appUserDetailsService);
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception{
    httpSecurity.csrf().disable()
        .authorizeRequests()
        .antMatchers("/authenticate").permitAll()
        .antMatchers("/register-brand").permitAll()
        .anyRequest().authenticated()
        .and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  //  @Override
//  protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
//      throws Exception {
//    authenticationManagerBuilder.inMemoryAuthentication()
//        .withUser("user")
//        .password("pwd")
//        .roles("USER")
//        .and()
//        .withUser("tomide")
//        .password("pwd")
//        .roles("ADMIN");
//  }
//
//  @Override
//  protected void configure(HttpSecurity httpSecurity) throws Exception{
//    httpSecurity.authorizeRequests()
//        .antMatchers("/admin").hasRole("ADMIN")
//        .antMatchers("/user").hasAnyRole("USER", "ADMIN")
//        .antMatchers("/", "statics/css", "statics/js").permitAll()
//        .and().formLogin();
//  }
//
}
