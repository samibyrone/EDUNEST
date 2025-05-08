package com.semicolon.africa.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "documents")
public class Document {

    @Id
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documentName;
    private Boolean isDocumentValid;
    private String filePath;

    @Enumerated(EnumType.STRING)
    private DOCUMENT_TYPE docType;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public boolean validateFormat() {
    }
}
