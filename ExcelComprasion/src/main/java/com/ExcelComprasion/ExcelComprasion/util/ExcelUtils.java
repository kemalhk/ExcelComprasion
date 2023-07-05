package com.ExcelComprasion.ExcelComprasion.util;

import com.ExcelComprasion.ExcelComprasion.dto.CustomerDto;

import com.ExcelComprasion.ExcelComprasion.entity.Customer;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtils {
    public static List<CustomerDto> readCustomerFromExcel(InputStream inputStream) throws Exception {
        List<CustomerDto> customers = new ArrayList<>();
        // Excel dosyasını oku
        Workbook workbook = new XSSFWorkbook(inputStream);

        // İlk sayfayı al
        Sheet sheet = workbook.getSheetAt(0);

        //satırları oku
        Iterator<Row>rowIterator=sheet.rowIterator();
        //başlıkları atla
        rowIterator.next();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            //sütunları oku
            Iterator<Cell>cellIterator = row.cellIterator();

            // customer dto oluştur
            CustomerDto customerDto = new CustomerDto();

            // İlk sütundaki veriyi al (name)
            Cell nameCell = cellIterator.next();
            customerDto.setName(nameCell.getStringCellValue());

            // İkinci sütundaki veriyi al (phone)
            Cell phoneCell = cellIterator.next();
            customerDto.setPhone(phoneCell.getStringCellValue());

            // Üçüncü sütundaki veriyi al (mail)
            Cell mailCell = cellIterator.next();
            customerDto.setEmail(mailCell.getStringCellValue());

            // Dördüncü sütundaki veriyi al (invoiceId)
            Cell invoiceIdCell = cellIterator.next();
            customerDto.setInvoiceId(invoiceIdCell.getStringCellValue());


          // Beşinci sütundaki veriyi al (invoiceAmount)
            Cell invoiceAmountCell = cellIterator.next();
            double invoiceAmountValue;
             if (invoiceAmountCell.getCellType() == CellType.STRING) {
                String invoiceAmountString = invoiceAmountCell.getStringCellValue().trim();
                if (invoiceAmountString.startsWith("$")) {
                    invoiceAmountString = invoiceAmountString.substring(1);
                }
                invoiceAmountValue = Double.parseDouble(invoiceAmountString);
            }
            else {
                throw new IllegalArgumentException("Invalid cell type for invoiceAmount");
            }
            customerDto.setInvoiceAmount(invoiceAmountValue);


            // Altıncı sütundaki veriyi al (installment)
            Cell installmentCell = cellIterator.next();
            //convert işlemi
            int installment = (int) installmentCell.getNumericCellValue();
            customerDto.setInstallment(installment);

            // CustomerDto'yu listeye ekle
            customers.add(customerDto);

        }
        // Workbook'i kapat
        workbook.close();
        return customers;
    }
}
