package com.semicolon.africa.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table()
public class LoanApplication {

    @Id
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LOAN_STATUS status;

    private BigDecimal loanAmount;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToMany(mappedBy = "loanApplication", cascade = CascadeType.ALL)
    private List<Document> documents;

    @OneToMany(mappedBy = "loanApplication", cascade = CascadeType.ALL)
    private Verification verification;

    @ManyToOne
    private LoanPolicy loanPolicy;


}
