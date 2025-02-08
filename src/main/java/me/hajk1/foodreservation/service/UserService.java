package me.hajk1.foodreservation.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import me.hajk1.foodreservation.dto.PasswordResetRequest;
import me.hajk1.foodreservation.dto.RegisterRequest;
import me.hajk1.foodreservation.dto.UpdateProfileRequest;
import me.hajk1.foodreservation.dto.UserProfileResponse;
import me.hajk1.foodreservation.exception.ResourceNotFoundException;
import me.hajk1.foodreservation.model.User;
import me.hajk1.foodreservation.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUser(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    user.setFirstName(registerRequest.getFirstName());
    user.setLastName(registerRequest.getLastName());
        user.setEnabled(true);

        List<String> roles = registerRequest.getRoles();
        if (roles == null || roles.isEmpty()) {
            roles = new ArrayList<>();
            roles.add("USER");
        }
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public User updateProfile(String username, UpdateProfileRequest updateRequest) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Verify current password
        if (!passwordEncoder.matches(updateRequest.getCurrentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Current password is incorrect");
        }

        // Update password if provided
        if (updateRequest.getNewPassword() != null && !updateRequest.getNewPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateRequest.getNewPassword()));
        }

        return userRepository.save(user);
    }

    public UserProfileResponse getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        UserProfileResponse response = new UserProfileResponse();
        response.setUsername(user.getUsername());
    response.setFirstName(user.getFirstName());
    response.setLastName(user.getLastName());
        response.setRoles(user.getRoles());
        response.setEnabled(user.getEnabled());

        return response;
    }

  @PreAuthorize("hasRole('ADMIN')")
  @Transactional
  public void disableUser(String username) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

    // Prevent disabling the last admin
    if (isLastAdmin(user)) {
      throw new IllegalStateException("Cannot disable the last admin user");
    }

    int updated = userRepository.updateUserEnabled(username, false);
    if (updated == 0) {
      throw new ResourceNotFoundException("User not found: " + username);
    }
  }

  @PreAuthorize("hasRole('ADMIN')")
  @Transactional
  public void enableUser(String username) {
    int updated = userRepository.updateUserEnabled(username, true);
    if (updated == 0) {
      throw new ResourceNotFoundException("User not found: " + username);
    }
  }

  @PreAuthorize("hasRole('ADMIN')")
  @Transactional
  public void resetPassword(String username, PasswordResetRequest request) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

    String encodedPassword = passwordEncoder.encode(request.getNewPassword());
    int updated = userRepository.updateUserPassword(username, encodedPassword);
    if (updated == 0) {
      throw new ResourceNotFoundException("User not found: " + username);
    }
  }

  private boolean isLastAdmin(User user) {
    return user.getRoles().contains("ADMIN")
        && userRepository.findAll().stream()
            .filter(u -> u.getEnabled() && !u.getUsername().equals(user.getUsername()))
            .noneMatch(u -> u.getRoles().contains("ADMIN"));
  }
}