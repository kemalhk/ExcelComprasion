package com.ExcelComprasion.ExcelComprasion.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private String invoiceId;
    private String name;
    private String phone;
    private String email;
    private Double invoiceAmount;
    private Integer installment;

}
