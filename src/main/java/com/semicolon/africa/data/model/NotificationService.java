package com.semicolon.africa.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationService {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String message;

    @ManyToOne
    private Student student;

}
