package com.ExcelComprasion.ExcelComprasion.service.impl;

import com.ExcelComprasion.ExcelComprasion.dto.CustomerDto;
import com.ExcelComprasion.ExcelComprasion.entity.Customer;
import com.ExcelComprasion.ExcelComprasion.repository.CustomerRepository;
import com.ExcelComprasion.ExcelComprasion.util.ExcelUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    @DisplayName("Should throw an exception when the provided InputStream is null")
    void readCustomerFromExcelWhenInputStreamIsNullThenThrowException() {
        assertThrows(Exception.class, () -> {
            customerService.readCustomerFromExcel(null);
        });
    }

    @Test
    @DisplayName("Should return a list of CustomerDto objects after reading from the Excel InputStream")
    void returnListOfCustomerDtoAfterReadingFromExcel() throws Exception {
        // Prepare test data
        List<CustomerDto> expectedCustomers = new ArrayList<>();
        CustomerDto customer1 = new CustomerDto("INV001", "John Doe", "1234567890", "john.doe@example.com", 1000.0, 12);
        CustomerDto customer2 = new CustomerDto("INV002", "Jane Smith", "0987654321", "jane.smith@example.com", 2000.0, 6);
        expectedCustomers.add(customer1);
        expectedCustomers.add(customer2);

        // Prepare mock behavior
        InputStream inputStream = new ByteArrayInputStream("test".getBytes());
        when(customerService.readCustomerFromExcel(inputStream)).thenReturn(expectedCustomers);

        // Perform the test
        List<CustomerDto> actualCustomers = null;
        try {
            actualCustomers = customerService.readCustomerFromExcel(inputStream);
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }

        // Verify the result
        assertNotNull(actualCustomers);
        assertEquals(expectedCustomers.size(), actualCustomers.size());
        assertEquals(expectedCustomers.get(0), actualCustomers.get(0));
        assertEquals(expectedCustomers.get(1), actualCustomers.get(1));
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


    //    @Test
//    @DisplayName("Should read and save all customers from the provided Excel InputStream")
//    void readAndSaveAllCustomersFromExcelInputStream() throws Exception {
//        // Prepare test data
//        List<CustomerDto> customers = new ArrayList<>();
//        customers.add(new CustomerDto("INV001", "John Doe", "1234567890", "john.doe@example.com", 1000.0, 12));
//        customers.add(new CustomerDto("INV002", "Jane Smith", "0987654321", "jane.smith@example.com", 2000.0, 6));
//
//        InputStream inputStream = Mockito.mock(InputStream.class);
//
//        // Mock the behavior of ExcelUtils.readCustomerFromExcel() method
//        Mockito.when(customerService.readCustomerFromExcel(Mockito.any(InputStream.class))).thenReturn(customers);
//
//
//        // Mock the behavior of customerRepository.save() method
//        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(new Customer());
//
//        // Call the method under test
//        List<CustomerDto> result = customerService.readCustomerFromExcel(inputStream);
//
//        // Verify the results
//        assertNotNull(result);
//        assertEquals(customers.size(), result.size());
//        for (int i = 0; i < customers.size(); i++) {
//            CustomerDto expectedCustomer = customers.get(i);
//            CustomerDto actualCustomer = result.get(i);
//            assertEquals(expectedCustomer.getInvoiceId(), actualCustomer.getInvoiceId());
//            assertEquals(expectedCustomer.getName(), actualCustomer.getName());
//            assertEquals(expectedCustomer.getPhone(), actualCustomer.getPhone());
//            assertEquals(expectedCustomer.getEmail(), actualCustomer.getEmail());
//            assertEquals(expectedCustomer.getInvoiceAmount(), actualCustomer.getInvoiceAmount());
//            assertEquals(expectedCustomer.getInstallment(), actualCustomer.getInstallment());
//        }
//
//        // Verify the interactions with customerRepository.save()
//        Mockito.verify(customerRepository, Mockito.times(customers.size())).save(Mockito.any(Customer.class));
//    }


//    @Test
//    public void testSave() {
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
//        CustomerDto result = customerService.readCustomerFromExcel();
//
//        assertEquals(result.getName(), customerDto.getName());
//        assertEquals(result.getInvoiceId(), "Test-Id1");
//    }


//    @Test
//    @DisplayName("Should correctly identify customers with incorrect installment")
//    void compareCustomersWithIncorrectInstallment() {        // Prepare test data
//        List<Customer> existingCustomers = new ArrayList<>();
//        existingCustomers.add(new Customer("INV001", "John Doe", "1234567890", "john.doe@example.com", 1000.0, 12));
//        existingCustomers.add(new Customer("INV002", "Jane Smith", "0987654321", "jane.smith@example.com", 2000.0, 6));
//
//        List<CustomerDto> uploadedCustomers = new ArrayList<>();
//        uploadedCustomers.add(new CustomerDto("INV001", "John Doe", "1234567890", "john.doe@example.com", 1000.0, 10));
//        uploadedCustomers.add(new CustomerDto("INV002", "Jane Smith", "0987654321", "jane.smith@example.com", 2000.0, 6));
//
//        // Mock the behavior of customerRepository
//        when(customerRepository.findAll()).thenReturn(existingCustomers);
//
//        // Call the method under test
//        List<CustomerDto> incorrectInstallmentCustomers = customerService.compareCustomers(null);
//
//        // Verify the results
//        assertEquals(1, incorrectInstallmentCustomers.size());
//        assertEquals("John Doe", incorrectInstallmentCustomers.get(0).getName());
//        assertEquals("john.doe@example.com", incorrectInstallmentCustomers.get(0).getEmail());
//        assertEquals(10, incorrectInstallmentCustomers.get(0).getInstallment());
//    }

//    @Test
//    @DisplayName("Should return null after comparing customers")
//    void compareCustomersReturnNull() {        // Prepare test data
//        List<Customer> existingCustomers = new ArrayList<>();
//        existingCustomers.add(new Customer("INV001", "John Doe", "1234567890", "john.doe@example.com", 1000.0, 12));
//        existingCustomers.add(new Customer("INV002", "Jane Smith", "0987654321", "jane.smith@example.com", 2000.0, 6));
//
//        List<CustomerDto> uploadedCustomers = new ArrayList<>();
//        uploadedCustomers.add(new CustomerDto("INV001", "John Doe", "1234567890", "john.doe@example.com", 1000.0, 12));
//        uploadedCustomers.add(new CustomerDto("INV002", "Jane Smith", "0987654321", "jane.smith@example.com", 2000.0, 6));
//
//        // Mock the behavior of customerRepository
//        when(customerRepository.findAll()).thenReturn(existingCustomers);
//
//        // Call the method under test
//        List<CustomerDto> result = customerService.compareCustomers(new ByteArrayInputStream(new byte[0]));
//
//        // Assertions
//        assertNull(result);
//    }

//    @Test
//    @DisplayName("Should correctly identify customers with incorrect invoice amount")
//    void compareCustomersWithIncorrectInvoiceAmount() {        // Prepare test data
//        List<Customer> existingCustomers = new ArrayList<>();
//        existingCustomers.add(new Customer("INV001", "John Doe", "1234567890", "john.doe@example.com", 1000.0, 12));
//        existingCustomers.add(new Customer("INV002", "Jane Smith", "9876543210", "jane.smith@example.com", 2000.0, 6));
//
//        List<CustomerDto> uploadedCustomers = new ArrayList<>();
//        uploadedCustomers.add(new CustomerDto("INV001", "John Doe", "1234567890", "john.doe@example.com", 1500.0, 12));
//        uploadedCustomers.add(new CustomerDto("INV002", "Jane Smith", "9876543210", "jane.smith@example.com", 2000.0, 6));
//
//        // Mock the behavior of customerRepository
//        when(customerRepository.findAll()).thenReturn(existingCustomers);
//
//        // Call the method under test
//        List<CustomerDto> incorrectInvoiceAmountCustomers = customerService.compareCustomers(null);
//
//        // Verify the results
//        assertEquals(1, incorrectInvoiceAmountCustomers.size());
//        assertEquals("INV001", incorrectInvoiceAmountCustomers.get(0).getInvoiceId());
//        assertEquals("John Doe", incorrectInvoiceAmountCustomers.get(0).getName());
//        assertEquals("1234567890", incorrectInvoiceAmountCustomers.get(0).getPhone());
//        assertEquals("john.doe@example.com", incorrectInvoiceAmountCustomers.get(0).getEmail());
//        assertEquals(1500.0, incorrectInvoiceAmountCustomers.get(0).getInvoiceAmount());
//        assertEquals(12, incorrectInvoiceAmountCustomers.get(0).getInstallment());
//    }

//    @Test
//    @DisplayName("Should send notification to users for customers with incorrect installment")
//    void sendNotificationToUsersForIncorrectInstallment() {        // Create a list of existing customers
//        List<Customer> existingCustomers = new ArrayList<>();
//        existingCustomers.add(new Customer("INV001", "John Doe", "1234567890", "john.doe@example.com", 1000.0, 12));
//        existingCustomers.add(new Customer("INV002", "Jane Smith", "0987654321", "jane.smith@example.com", 2000.0, 6));
//
//        // Create a list of uploaded customers
//        List<CustomerDto> uploadedCustomers = new ArrayList<>();
//        uploadedCustomers.add(new CustomerDto("INV001", "John Doe", "1234567890", "john.doe@example.com", 1500.0, 12));
//        uploadedCustomers.add(new CustomerDto("INV002", "Jane Smith", "0987654321", "jane.smith@example.com", 2000.0, 6));
//
//        // Mock the behavior of the customerRepository
//        when(customerRepository.findAll()).thenReturn(existingCustomers);
//
//        // Call the compareCustomers method
//        List<CustomerDto> incorrectInstallmentCustomers = customerService.compareCustomers(new ByteArrayInputStream(new byte[0]));
//
//        // Verify that the sendNotificationToUsers method is called with the correct customers
//        ArgumentCaptor<List<CustomerDto>> captor = ArgumentCaptor.forClass(List.class);
//        Mockito.verify(customerService).sendNotificationToUsers(captor.capture());
//        List<CustomerDto> capturedCustomers = captor.getValue();
//        assertEquals(1, capturedCustomers.size());
//        assertEquals("John Doe", capturedCustomers.get(0).getName());
//        assertEquals("1234567890", capturedCustomers.get(0).getPhone());
//        assertEquals("john.doe@example.com", capturedCustomers.get(0).getEmail());
//        assertEquals(1500.0, capturedCustomers.get(0).getInvoiceAmount());
//        assertEquals(12, capturedCustomers.get(0).getInstallment());
//    }

//    @Test
//    @DisplayName("Should send notification to admin for customers with incorrect invoice amount")
//    void sendNotificationToAdminForIncorrectInvoiceAmount() {        // Create a list of existing customers
//        List<Customer> existingCustomers = new ArrayList<>();
//        existingCustomers.add(new Customer("INV001", "John Doe", "1234567890", "john.doe@example.com", 1000.0, 12));
//        existingCustomers.add(new Customer("INV002", "Jane Smith", "0987654321", "jane.smith@example.com", 2000.0, 6));
//
//        // Create a list of customers from Excel
//        List<CustomerDto> customersFromExcel = new ArrayList<>();
//        customersFromExcel.add(new CustomerDto("INV001", "John Doe", "1234567890", "john.doe@example.com", 1500.0, 12));
//        customersFromExcel.add(new CustomerDto("INV002", "Jane Smith", "0987654321", "jane.smith@example.com", 2000.0, 6));
//        customersFromExcel.add(new CustomerDto("INV003", "Bob Johnson", "9876543210", "bob.johnson@example.com", 3000.0, 9));
//
//        // Mock the behavior of the customerRepository
//        when(customerRepository.findAll()).thenReturn(existingCustomers);
//
//        // Mock the behavior of the ExcelUtils
//        try {
//            InputStream inputStream = new ByteArrayInputStream("test".getBytes());
//            when(excelUtils.readCustomerFromExcel(inputStream)).thenReturn(customersFromExcel);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Call the method under test
//        List<CustomerDto> result = customerService.compareCustomers(new ByteArrayInputStream("test".getBytes()));
//
//        // Verify the behavior
//        ArgumentCaptor<List<CustomerDto>> incorrectInvoiceAmountCaptor = ArgumentCaptor.forClass(List.class);
//        Mockito.verify(customerService).sendNotificationToAdmin(incorrectInvoiceAmountCaptor.capture());
//
//        List<CustomerDto> incorrectInvoiceAmount = incorrectInvoiceAmountCaptor.getValue();
//        assertEquals(1, incorrectInvoiceAmount.size());
//        assertEquals("INV001", incorrectInvoiceAmount.get(0).getInvoiceId());
//        assertEquals("John Doe", incorrectInvoiceAmount.get(0).getName());
//        assertEquals("1234567890", incorrectInvoiceAmount.get(0).getPhone());
//        assertEquals("john.doe@example.com", incorrectInvoiceAmount.get(0).getEmail());
//        assertEquals(1500.0, incorrectInvoiceAmount.get(0).getInvoiceAmount());
//        assertEquals(12, incorrectInvoiceAmount.get(0).getInstallment());
//    }


}
