package com.semicolon.africa.data.repositories;

import com.semicolon.africa.data.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {

    boolean existsByEmail(String username);

    Optional<Admin> findById(Long id);

    Optional<Admin> findByEmail(String email);

    Optional<Admin> findByUserName(String userName);

}
