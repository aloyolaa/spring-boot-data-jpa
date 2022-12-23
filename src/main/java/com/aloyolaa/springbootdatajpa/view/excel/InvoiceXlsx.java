package com.aloyolaa.springbootdatajpa.view.excel;

import com.aloyolaa.springbootdatajpa.entity.Invoice;
import com.aloyolaa.springbootdatajpa.entity.InvoiceItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import java.util.Map;

@Component("invoice/detail.xlsx")
public class InvoiceXlsx extends AbstractXlsxView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Invoice invoice = (Invoice) model.get("invoice");
        Sheet sheet = workbook.createSheet("Invoice Detail");

        response.setHeader("Content-Disposition", "attachment; filename=\"Invoice_" + invoice.getId() + "_Detail.xlsx\"");

        sheet.createRow(0).createCell(0).setCellValue("Customer Data");
        sheet.createRow(1).createCell(0).setCellValue("First Name" + invoice.getCustomer().getFirstName());
        sheet.createRow(2).createCell(0).setCellValue("Last Name" + invoice.getCustomer().getLastName());
        sheet.createRow(3).createCell(0).setCellValue("Email: " + invoice.getCustomer().getEmail());

        sheet.createRow(5).createCell(0).setCellValue("Invoice Data");
        sheet.createRow(6).createCell(0).setCellValue("ID: " + invoice.getId());
        sheet.createRow(7).createCell(0).setCellValue("Description: " + invoice.getDescription());
        sheet.createRow(8).createCell(0).setCellValue("Create Date: " + invoice.getCreateDate());

        CellStyle invoiceDetailStyle = workbook.createCellStyle();
        invoiceDetailStyle.setBorderBottom(BorderStyle.MEDIUM);
        invoiceDetailStyle.setBorderTop(BorderStyle.MEDIUM);
        invoiceDetailStyle.setBorderRight(BorderStyle.MEDIUM);
        invoiceDetailStyle.setBorderLeft(BorderStyle.MEDIUM);
        invoiceDetailStyle.setFillForegroundColor(IndexedColors.GOLD.index);
        invoiceDetailStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle invoiceItemsStyle = workbook.createCellStyle();
        invoiceItemsStyle.setBorderBottom(BorderStyle.THIN);
        invoiceItemsStyle.setBorderTop(BorderStyle.THIN);
        invoiceItemsStyle.setBorderRight(BorderStyle.THIN);
        invoiceItemsStyle.setBorderLeft(BorderStyle.THIN);

        Row invoiceDetail = sheet.createRow(10);
        invoiceDetail.createCell(0).setCellValue("Product");
        invoiceDetail.createCell(1).setCellValue("Price");
        invoiceDetail.createCell(2).setCellValue("Quantity");
        invoiceDetail.createCell(3).setCellValue("Amount");
        invoiceDetail.getCell(0).setCellStyle(invoiceDetailStyle);
        invoiceDetail.getCell(1).setCellStyle(invoiceDetailStyle);
        invoiceDetail.getCell(2).setCellStyle(invoiceDetailStyle);
        invoiceDetail.getCell(3).setCellStyle(invoiceDetailStyle);

        Cell cell = null;
        int rowNum = 11;
        for (InvoiceItem invoiceItem : invoice.getInvoiceItems()) {
            Row row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(invoiceItem.getProduct().getName());
            cell.setCellStyle(invoiceItemsStyle);
            cell = row.createCell(1);
            cell.setCellValue(invoiceItem.getProduct().getPrice());
            cell.setCellStyle(invoiceItemsStyle);
            cell = row.createCell(2);
            cell.setCellValue(invoiceItem.getQuantity());
            cell.setCellStyle(invoiceItemsStyle);
            cell = row.createCell(3);
            cell.setCellValue(invoiceItem.calculateAmount());
            cell.setCellStyle(invoiceItemsStyle);
        }

        Row invoiceTotal = sheet.createRow(rowNum);
        cell = invoiceTotal.createCell(2);
        cell.setCellValue("Total");
        cell.setCellStyle(invoiceItemsStyle);
        cell = invoiceTotal.createCell(3);
        cell.setCellValue(invoice.calculateTotalAmount());
        cell.setCellStyle(invoiceItemsStyle);
    }
}
