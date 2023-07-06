package com.ExcelComprasion.ExcelComprasion.repository;

import com.ExcelComprasion.ExcelComprasion.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer,String> {
    Optional<Customer> findByInvoiceId(String invoiceId);

}
