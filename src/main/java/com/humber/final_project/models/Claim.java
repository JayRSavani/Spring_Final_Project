package com.humber.final_project.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Claim {

    enum ClaimStatus {
        PENDING,
        APPROVED,
        REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date claimDate;
    private ClaimStatus claimStatus;
    private String claimDescription;

    @ManyToOne
    private ProductRegistration productRegistration;
}