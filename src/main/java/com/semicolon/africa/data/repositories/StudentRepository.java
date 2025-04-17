package com.semicolon.africa.data.repositories;

import com.semicolon.africa.data.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    boolean existsByEmail(String email);

    Optional<Student> findById(Long id);

    Optional <Student> findByEmail(String email);

    Optional <Student> findByUserName(String userName);
}
