package com.humber.final_project.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class ProductRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String serialNumber;
    private Date purchaseDate;

    @ManyToOne
    private Users user;

    @ManyToOne
    private Products product;
}
