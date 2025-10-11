package com.bfwg.config;

import com.bfwg.model.User;
import com.bfwg.security.auth.*;
import com.bfwg.service.impl.CustomUserDetailsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by fan.jin on 2016-10-19.
 * Updated for Spring Security 6 / Spring Boot 3
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

  protected final Log LOGGER = LogFactory.getLog(getClass());

  private final CustomUserDetailsService jwtUserDetailsService;
  private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
  private final LogoutSuccess logoutSuccess;
  private final AuthenticationSuccessHandler authenticationSuccessHandler;
  private final AuthenticationFailureHandler authenticationFailureHandler;
  
  @Value("${jwt.cookie}")
  private String TOKEN_COOKIE;

  @Autowired
  public WebSecurityConfig(CustomUserDetailsService jwtUserDetailsService, RestAuthenticationEntryPoint restAuthenticationEntryPoint, LogoutSuccess logoutSuccess, AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler) {
    this.jwtUserDetailsService = jwtUserDetailsService;
    this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    this.logoutSuccess = logoutSuccess;
    this.authenticationSuccessHandler = authenticationSuccessHandler;
    this.authenticationFailureHandler = authenticationFailureHandler;
  }

  @Bean
  public TokenAuthenticationFilter jwtAuthenticationTokenFilter() {
    return new TokenAuthenticationFilter();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(jwtUserDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .ignoringRequestMatchers("/api/login", "/api/signup")
      )
      .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .exceptionHandling(exception -> exception
        .authenticationEntryPoint(restAuthenticationEntryPoint)
      )
      .authorizeHttpRequests(auth -> auth
        .anyRequest().authenticated()
      )
      .authenticationProvider(authenticationProvider())
      .addFilterBefore(jwtAuthenticationTokenFilter(), BasicAuthenticationFilter.class)
      .formLogin(form -> form
        .loginPage("/api/login")
        .successHandler(authenticationSuccessHandler)
        .failureHandler(authenticationFailureHandler)
      )
      .logout(logout -> logout
        .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
        .logoutSuccessHandler(logoutSuccess)
        .deleteCookies(TOKEN_COOKIE)
      );

    return http.build();
  }

  public void changePassword(String oldPassword, String newPassword) throws Exception {

    Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
    String username = currentUser.getName();

    AuthenticationConfiguration authConfig = new AuthenticationConfiguration();
    AuthenticationManager authManager = authenticationManager(authConfig);

    if (authManager != null) {
      LOGGER.debug("Re-authenticating user '" + username + "' for password change request.");

      authManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
    } else {
      LOGGER.debug("No authentication manager set. can't change Password!");

      return;
    }

    LOGGER.debug("Changing password for user '" + username + "'");

    User user = jwtUserDetailsService.loadUserByUsername(username);

    user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
    jwtUserDetailsService.save(user);
  }
}
