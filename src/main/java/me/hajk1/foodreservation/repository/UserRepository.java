package me.hajk1.foodreservation.repository;

import java.util.Optional;
import me.hajk1.foodreservation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

  @Modifying
  @Query("UPDATE User u SET u.enabled = :enabled WHERE u.username = :username")
  int updateUserEnabled(@Param("username") String username, @Param("enabled") boolean enabled);

  @Modifying
  @Query("UPDATE User u SET u.password = :password WHERE u.username = :username")
  int updateUserPassword(@Param("username") String username, @Param("password") String password);
}