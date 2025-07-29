package com.example.CodeArena.repository;

import com.example.CodeArena.model.entity.User;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
//    Optional<User> findByUsername(String login);
    Optional<User> findByEmailOrUsername(String email, String username);

}
