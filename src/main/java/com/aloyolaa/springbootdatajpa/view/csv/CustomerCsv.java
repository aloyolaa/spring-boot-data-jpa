package com.aloyolaa.springbootdatajpa.view.csv;

import com.aloyolaa.springbootdatajpa.entity.Customer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.util.Map;

@Component("customers")
public class CustomerCsv extends AbstractView {
    public CustomerCsv() {
        setContentType("text/csv");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=\"customers.csv\"");
        response.setContentType(getContentType());
        Page<Customer> customers = (Page<Customer>) model.get("customers");
        ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] headers = {"ID", "firstName", "lastName", "email", "birthDate", "createDate"};
        beanWriter.writeHeader(headers);
        for (Customer customer : customers) {
            beanWriter.write(customer, headers);
        }
        beanWriter.close();
    }
}
