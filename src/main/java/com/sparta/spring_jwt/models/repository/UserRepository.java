package com.sparta.spring_jwt.models.repository;


import com.sparta.spring_jwt.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndAndPassword(String username, String password);
}
