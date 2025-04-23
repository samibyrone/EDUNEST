package com.semicolon.africa.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Verification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VERIFICATION_STATUS status;

    private LocalDate verifiedDate;

    @OneToOne
    @JoinColumn(name = "admin_id")
    private LoanApplication loanApplication;

}
