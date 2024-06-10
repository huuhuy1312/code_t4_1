package com.example.web_spring_security_demo.repository;

import com.example.web_spring_security_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Page<User> findAll(Pageable pageable);
    @Query("SELECT u.username FROM User u WHERE u.username IN:usernames")
    List<String> findUsernameIn(List<String> usernames);
    @Query("SELECT u.email FROM User u WHERE u.email IN:emails")
    List<String> findEmailIn(List<String> emails);

    @Override
    void deleteById(Long id);
}
