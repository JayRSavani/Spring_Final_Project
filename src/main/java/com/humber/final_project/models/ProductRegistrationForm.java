package com.humber.final_project.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRegistrationForm {
    private Integer registrationId;
    private Integer userId;
    private Integer productId;
    private String serialNo;
    private Date purchaseDate;
    private String productName;

}
