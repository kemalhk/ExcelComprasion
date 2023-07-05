package com.ExcelComprasion.ExcelComprasion.repository;

import com.ExcelComprasion.ExcelComprasion.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,String> {

}
