package com.aloyolaa.springbootdatajpa.controller;

import com.aloyolaa.springbootdatajpa.entity.Customer;
import com.aloyolaa.springbootdatajpa.exception.CustomerNotFoundException;
import com.aloyolaa.springbootdatajpa.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@AllArgsConstructor
@Controller
@RequestMapping("/customers")
@SessionAttributes("customer")
public class CustomerController {

    private CustomerService customerService;

    @GetMapping("/form")
    public String form(Model model) {
        Customer customer = new Customer();
        model.addAttribute("title", "Customer Form");
        model.addAttribute("customer", customer);
        return "form";
    }

    @GetMapping("/find-all")
    public String findAll(Model model) {
        model.addAttribute("title", "Customer List");
        model.addAttribute("customers", customerService.findAll());
        return "customers";
    }

    @GetMapping("/form/{id}")
    public String findById(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id).orElseThrow(() -> new CustomerNotFoundException("User with ID " + id + " does not exist in the system"));
        model.addAttribute("title", "Customer Form");
        model.addAttribute("customer", customer);
        return "form";
    }

    @PostMapping("/save")
    public String save(@Valid Customer customer, BindingResult result, Model model, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("title", "Customer Form");
            return "form";
        }
        customerService.save(customer);
        status.setComplete();
        return "redirect:/customers/find-all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Customer customer = customerService.findById(id).orElseThrow(() -> new CustomerNotFoundException("User with ID " + id + " does not exist in the system"));
        customerService.delete(customer.getId());
        return "redirect:/customers/find-all";
    }

}
