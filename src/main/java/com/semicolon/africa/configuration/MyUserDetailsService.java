package com.semicolon.africa.configuration;

import com.semicolon.africa.data.model.Student;
import com.semicolon.africa.data.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Student student = studentRepository.findByUsername(username);
       if (student == null) {
           System.out.println("Student not found");
           throw new UsernameNotFoundException("Student not found");
       }

        return new Student(student);
    }
}
