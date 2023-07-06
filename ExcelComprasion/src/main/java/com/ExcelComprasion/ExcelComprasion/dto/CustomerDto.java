package com.ExcelComprasion.ExcelComprasion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private String invoiceId;
    private String name;
    private String phone;
    private String email;
    private Double invoiceAmount;
    private Integer installment;

}
