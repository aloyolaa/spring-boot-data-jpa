package com.aloyolaa.springbootdatajpa.view.pdf;

import com.aloyolaa.springbootdatajpa.entity.Invoice;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.awt.*;
import java.util.Map;

@Component("invoice/detail")
public class InvoiceView extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Invoice invoice = (Invoice) model.get("invoice");

        PdfPTable table1 = new PdfPTable(1);
        table1.setSpacingAfter(20);

        PdfPCell cell = null;
        cell = new PdfPCell(new Phrase("Customer Data"));
        cell.setBackgroundColor(new Color(184, 218, 255));
        cell.setPadding(8f);

        table1.addCell(cell);
        table1.addCell("First Name: " + invoice.getCustomer().getFirstName());
        table1.addCell("Last Name: " + invoice.getCustomer().getLastName());
        table1.addCell("Email: " + invoice.getCustomer().getEmail());

        PdfPTable table2 = new PdfPTable(1);
        table2.setSpacingAfter(20);

        cell = new PdfPCell(new Phrase("Invoice Data"));
        cell.setBackgroundColor(new Color(195, 230, 203));
        cell.setPadding(8f);

        table2.addCell(cell);
        table2.addCell("ID: " + invoice.getId().toString());
        table2.addCell("Description: " + invoice.getDescription());
        table2.addCell("Create Date: " + invoice.getCreateDate().toString());

        PdfPTable table3 = new PdfPTable(4);
        table3.setWidths(new float[]{3.5f, 1, 1, 1});
        table3.addCell("Product");
        table3.addCell("Price");
        table3.addCell("Quantity");
        table3.addCell("Amount");

        invoice.getInvoiceItems().forEach(invoiceItem -> {
            table3.addCell(invoiceItem.getProduct().getName());
            table3.addCell(invoiceItem.getProduct().getPrice().toString());
            table3.addCell(invoiceItem.getQuantity().toString());
            table3.addCell(invoiceItem.calculateAmount().toString());
        });

        cell = new PdfPCell(new Phrase("Total: "));
        cell.setColspan(3);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table3.addCell(cell);
        table3.addCell(invoice.calculateTotalAmount().toString());

        document.add(table1);
        document.add(table2);
        document.add(table3);
    }
}
