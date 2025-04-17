package com.semicolon.africa.configuration;

import com.semicolon.africa.data.repositories.StudentRepository;
import com.semicolon.africa.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StudentDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        return studentRepository.findByUserName(userName)
                .orElseThrow(() -> new StudentNotFoundException("Student Not Found"));
    }
}
