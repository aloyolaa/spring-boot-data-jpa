package com.aloyolaa.springbootdatajpa.view.json;

import com.aloyolaa.springbootdatajpa.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

@Component("customers.json")
public class CustomerJson extends MappingJackson2JsonView {
    @Override
    protected Object filterModel(Map<String, Object> model) {
        model.remove("title");
        model.remove("page");
        Page<Customer> customers = (Page<Customer>) model.get("customers");
        model.remove("customers");
        model.put("customers", customers.getContent());
        return super.filterModel(model);
    }
}
