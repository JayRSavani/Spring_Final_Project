package com.humber.final_project.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimForm {
    private Integer claimId;
    private Integer registrationId;
    private Date claimDate;
    private String description;
    private String status;
}
