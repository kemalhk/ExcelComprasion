package com.ExcelComprasion.ExcelComprasion.service;

import com.ExcelComprasion.ExcelComprasion.dto.CustomerDto;

import java.io.InputStream;
import java.util.List;

public interface CustomerService {

    List<CustomerDto> readCustomerFromExcel(InputStream inputStream) throws Exception;

    List<CustomerDto> compareCustomers(InputStream inputStream) throws Exception;

}
