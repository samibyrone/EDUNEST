    package com.semicolon.africa.data.model;

    import jakarta.persistence.*;
    import jakarta.persistence.GeneratedValue;
    import lombok.*;
    import org.springframework.data.annotation.Id;


    @Data
    @Entity
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Table
    public class Admin {

        @Id
        @jakarta.persistence.Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long Id;

        @Column(unique = true, nullable = false)
        private String userName;

        @Column(nullable = false)
        private String password;

        @Column(unique = true, nullable = false)
        private String email;

        @Column(unique = true, nullable = false)
        private String phoneNumber;

        @Column(nullable = false)
        private boolean isLoggedIn;
    }
