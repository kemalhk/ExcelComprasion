package com.ExcelComprasion.ExcelComprasion.service.impl;

import com.ExcelComprasion.ExcelComprasion.dto.CustomerDto;
import com.ExcelComprasion.ExcelComprasion.entity.Customer;
import com.ExcelComprasion.ExcelComprasion.repository.CustomerRepository;
import com.ExcelComprasion.ExcelComprasion.util.ExcelUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ExcelUtils excelUtils;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() {
//        CustomerDto customerDto = new CustomerDto();
//        customerDto.setName("Test-Name");
//        customerDto.setPhone("Test-Phone");
//        customerDto.setEmail("Test-Email");
//        customerDto.setInvoiceId("Test-Id1");
//        customerDto.setInstallment(1);
//        customerDto.setInvoiceAmount(1.1);
//        Customer customerMock = mock(Customer.class);
//
//        when(customerMock.getInvoiceId()).thenReturn("Test-Id1");
//        when(customerRepository.save(ArgumentMatchers.any(Customer.class))).thenReturn(customerMock);
//        CustomerDto result = customerService.(customerDto);
//
//        assertEquals(result.getAdi(), kisiDto.getAdi());
//        assertEquals(result.getId(), 1L);
    }

    @Test
    public void testReadCustomerFromExcel() throws Exception {
        // Gerçek veri dosyasının yolu
        String filePath = "C:\\Users\\kemal\\Downloads\\testData.xlsx";

        // Dosya yolunu InputStream'e dönüştürme
        FileInputStream fileInputStream = new FileInputStream(filePath);

        // Perform the method call
        List<CustomerDto> result = customerService.readCustomerFromExcel(fileInputStream);

        // Assert the result
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

        // Her bir müşteri için kaydedilen verilerin kontrolü
        for (CustomerDto customerDto : result) {
            Optional<Customer> optionalCustomer = customerRepository.findByInvoiceId(customerDto.getInvoiceId());
            System.out.println(optionalCustomer);
            assertTrue(optionalCustomer.isPresent());

            Customer savedCustomer = optionalCustomer.get();

            assertEquals(customerDto.getName(), savedCustomer.getName());
            assertEquals(customerDto.getEmail(), savedCustomer.getEmail());
            assertEquals(customerDto.getPhone(), savedCustomer.getPhone());
            assertEquals(customerDto.getInstallment(), savedCustomer.getInstallment());
            assertEquals(customerDto.getInvoiceAmount(), savedCustomer.getInvoiceAmount());
        }
    }


//    @Test
//    public void testCompareCustomers() throws Exception {
//        // Mock data
//        InputStream inputStream = mock(InputStream.class);
//        List<Customer> existingCustomers = new ArrayList<>();
//        existingCustomers.add(new Customer("JVL11HYP3KB", "Geraldine Dunn", "(773) 104-7556", "condimentum.eget.volutpat@hotmail.org", 90.54, 1));
//        existingCustomers.add(new Customer("ISB15JQG1NI", "Eleanor Hensley", "1-448-681-5069", "ac.mattis.velit@yahoo.couk", 38.55, 7));
//
//        List<CustomerDto> uploadedCustomers = new ArrayList<>();
//        uploadedCustomers.add(new CustomerDto("JVL11HYP3KB", "Geraldine Dunn", "(773) 104-7556", "condimentum.eget.volutpat@hotmail.org", 90.54, 1));
//        uploadedCustomers.add(new CustomerDto("ISB15JQG1NI", "Eleanor Hensley", "1-448-681-5069", "ac.mattis.velit@yahoo.couk", 38.55, 7));
//
//        // Mock behavior
//        when(customerRepository.findAll()).thenReturn(existingCustomers);
//        when(excelUtils.readCustomerFromExcel(inputStream)).thenReturn(uploadedCustomers);
//
//        // Perform the method call
//        List<CustomerDto> result = customerService.compareCustomers(inputStream);
//
//        // Verify the result
//        assertEquals(uploadedCustomers, result);
//    }

//    @Test
//    public void testSendNotificationToUsers() {
//        // Mock data
//        List<CustomerDto> incorrectInstallment = new ArrayList<>();
//        incorrectInstallment.add(new CustomerDto("JVL11HYP3KB", "Geraldine Dunn", "(773) 104-7556", "condimentum.eget.volutpat@hotmail.org", 90.54, 1));
//
//        // Perform the method call
//        customerService.sendNotificationToUsers(incorrectInstallment);
//    }
//
//    @Test
//    public void testSendNotificationToAdmin() {
//        // Mock data
//        List<CustomerDto> incorrectInvoiceAmount = new ArrayList<>();
//        incorrectInvoiceAmount.add(new CustomerDto("ISB15JQG1NI", "Eleanor Hensley", "1-448-681-5069", "ac.mattis.velit@yahoo.couk", 38.55, 7));
//
//        // Perform the method call
//        customerService.sendNotificationToAdmin(incorrectInvoiceAmount);
//    }
}
