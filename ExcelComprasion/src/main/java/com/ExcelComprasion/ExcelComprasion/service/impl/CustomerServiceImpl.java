package com.ExcelComprasion.ExcelComprasion.service.impl;
import com.ExcelComprasion.ExcelComprasion.dto.CustomerDto;
import com.ExcelComprasion.ExcelComprasion.entity.Customer;
import com.ExcelComprasion.ExcelComprasion.repository.CustomerRepository;
import com.ExcelComprasion.ExcelComprasion.service.CustomerService;
import com.ExcelComprasion.ExcelComprasion.util.ExcelUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;


import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<CustomerDto>readCustomerFromExcel(InputStream inputStream) throws Exception{
        List<CustomerDto> customers = ExcelUtils.readCustomerFromExcel(inputStream);
        for(CustomerDto customerDto : customers){
            Customer customer = new Customer();
            customer.setName(customerDto.getName());
            customer.setEmail(customerDto.getEmail());
            customer.setPhone(customerDto.getPhone());
            customer.setInstallment(customerDto.getInstallment());
            customer.setInvoiceAmount(customerDto.getInvoiceAmount());
            customer.setInvoiceId(customerDto.getInvoiceId());
            customerRepository.save(customer);
        }
        return customers;
    }

    @Override
    public List<CustomerDto> compareCustomers(InputStream inputStream) throws Exception {
        List<Customer> existingCustomers = customerRepository.findAll(); // db den kayıtları al
        List<CustomerDto> customers = ExcelUtils.readCustomerFromExcel(inputStream); // exceldeki kayıtları al
        List<CustomerDto> incorrectInstallment = new ArrayList<>(); // hatalı taksit
        List<CustomerDto> incorrectInvoiceAmount = new ArrayList<>(); // hatalı tutar
            for (Customer existingCustomer : existingCustomers) {
                for (CustomerDto uploadedCustomer : customers) {
                //eğer InvoiceId eşleşirse karşılaştırma işlemi yap
                if (uploadedCustomer.getInvoiceId().equals(existingCustomer.getInvoiceId())){
                    //taksit kontrol
                    if (uploadedCustomer.getInstallment() != existingCustomer.getInstallment()) {
                        incorrectInstallment.add(uploadedCustomer); //hatalıysa listeye ekle
                    }
                    //tutar kontrol
                    if (!uploadedCustomer.getInvoiceAmount().equals(existingCustomer.getInvoiceAmount())) {
                        incorrectInvoiceAmount.add(uploadedCustomer);
                    }
                }
            }
        }
        // Bildirim işlemlerini gerçekleştir
        sendNotificationToAdmin(incorrectInvoiceAmount);
        sendNotificationToUsers(incorrectInstallment);
        return null;
    }

    //kullanıcıya bildirim gönderme
    public void sendNotificationToUsers(List<CustomerDto> incorrectInstallment) {
        // Hatalı taksite sahip kayıtların bilgilerini consola yazdır
        for (CustomerDto customer : incorrectInstallment) {
            System.out.println("Eşleşmeyen kayıt (Installment): " + customer.toString());
        }
        System.out.println("Kullanıcılara mail gönderildi: Hatalı Installment kayıtları");
    }

    //admine bildirim gönderme
    public void sendNotificationToAdmin(List<CustomerDto> incorrectInvoiceAmount) {
        // Hatalı tutara sahip kayıtların bilgilerini consola yazdır
        for (CustomerDto customer : incorrectInvoiceAmount) {
            System.out.println("Eşleşmeyen kayıt (InvoiceAmount): " + customer.toString());
        }
        System.out.println("Sistem yöneticisine mail gönderildi: Hatalı InvoiceAmount kayıtları");
        System.out.println("--------------------------------------------------------------------------");
    }


    @Override
    public Page<CustomerDto> getCustomers(Pageable pageable) {
        Page<Customer> customers = customerRepository.findAll(pageable);

        return customers.map(customer -> {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setInvoiceId(customer.getInvoiceId());
            customerDto.setName(customer.getName());
            customerDto.setEmail(customer.getEmail());
            customerDto.setPhone(customer.getPhone());
            customerDto.setInvoiceAmount(customer.getInvoiceAmount());
            customerDto.setInstallment(customer.getInstallment());

            return customerDto;
        });
    }

    @Override
    public Slice<Customer> getCustomersSlice(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }


}
