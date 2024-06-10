package com.example.web_spring_security_demo.repository;

import com.example.web_spring_security_demo.model.ERole;
import com.example.web_spring_security_demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}
