package com.ExcelComprasion.ExcelComprasion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Person")
public class Customer {
    @Id
    @Column(length = 50,name = "invoiceId")
    private String invoiceId;
    @Column(length = 50,name = "name")
    private String name;
    @Column(length = 50,name = "phone")
    private String phone;
    @Column(length = 50,name = "email")
    private String email;
    @Column(name = "invoiceAmount")
    private Double invoiceAmount;
    @Column(name = "installment")
    private Integer installment;

}
