    package com.semicolon.africa.data.model;

    import jakarta.persistence.*;
    import jakarta.persistence.GeneratedValue;
    import lombok.*;
    import lombok.experimental.SuperBuilder;
    import org.springframework.data.annotation.Id;


    @EqualsAndHashCode(callSuper = true)
    @Data
    @Entity
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @Table
    public class Admin extends Student{

        @Id
        @jakarta.persistence.Id
        @GeneratedValue
        private Long Id;
        private String firstName;
        private String lastName;
        private String userName;
        private String password;
        private String email;
        private String phoneNumber;
        private String address;
        private boolean isLoggedIn;
    }
