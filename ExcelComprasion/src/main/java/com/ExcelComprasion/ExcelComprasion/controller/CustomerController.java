package com.ExcelComprasion.ExcelComprasion.controller;

import com.ExcelComprasion.ExcelComprasion.dto.CustomerDto;
import com.ExcelComprasion.ExcelComprasion.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/upload")
    public ResponseEntity<List<CustomerDto>> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        try {
            List<CustomerDto> customers = customerService.readCustomerFromExcel(file.getInputStream());
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/compare")
    public ResponseEntity<List<CustomerDto>> compareExcelFile(@RequestParam("file") MultipartFile file) {
        try {
            List<CustomerDto> comparedCustomers = customerService.compareCustomers(file.getInputStream());
            return ResponseEntity.ok(comparedCustomers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
