package com.ExcelComprasion.ExcelComprasion.service;

import com.ExcelComprasion.ExcelComprasion.dto.CustomerDto;
import com.ExcelComprasion.ExcelComprasion.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.io.InputStream;
import java.util.List;

public interface CustomerService {

    List<CustomerDto> readCustomerFromExcel(InputStream inputStream) throws Exception;

    List<CustomerDto> compareCustomers(InputStream inputStream) throws Exception;

    Page<CustomerDto> getCustomers(Pageable pageable);

    Slice<Customer> getCustomersSlice(Pageable pageable);

}
