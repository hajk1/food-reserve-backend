package me.hajk1.foodreservation.security;

import lombok.extern.slf4j.Slf4j;
import me.hajk1.foodreservation.model.User;
import me.hajk1.foodreservation.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.debug("Loading user details for username: {}", username);

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> {
          log.debug("User not found with username: {}", username);
          return new UsernameNotFoundException("User not found with username: " + username);
        });

    log.debug("Found user: {}, enabled: {}, authorities: {}",
        user.getUsername(),
        user.getEnabled(),
        user.getAuthorities());

    return org.springframework.security.core.userdetails.User
        .withUsername(user.getUsername())
        .password(user.getPassword())
        .authorities(user.getAuthorities())
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(!user.getEnabled())
        .build();
  }
}