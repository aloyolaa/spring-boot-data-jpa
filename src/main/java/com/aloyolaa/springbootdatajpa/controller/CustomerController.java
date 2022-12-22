package com.aloyolaa.springbootdatajpa.controller;

import com.aloyolaa.springbootdatajpa.controller.util.PageRender;
import com.aloyolaa.springbootdatajpa.entity.Customer;
import com.aloyolaa.springbootdatajpa.exception.CustomerNotFoundException;
import com.aloyolaa.springbootdatajpa.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@AllArgsConstructor
@Controller
@RequestMapping("/customers")
@SessionAttributes("customer")
public class CustomerController {

    private final CustomerService customerService;
    private MessageSource messageSource;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/form")
    public String form(Model model) {
        Customer customer = new Customer();
        model.addAttribute("title", "Customer Form");
        model.addAttribute("customer", customer);
        return "form";
    }

    @GetMapping({"/find-all", "/"})
    public String findAll(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Locale locale) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Customer> customers = customerService.findAll(pageable);
        PageRender<Customer> pageRender = new PageRender<>("/customers/find-all", customers);
        model.addAttribute("title", messageSource.getMessage("text.customer.findAll.title", null, locale));
        model.addAttribute("customers", customers);
        model.addAttribute("page", pageRender);
        return "customers";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Customer customer = customerService.fetchByIdWithInvoice(id).orElseThrow(() -> new CustomerNotFoundException("User with ID " + id + " does not exist in the system"));
        model.addAttribute("customer", customer);
        model.addAttribute("title", "Customer Detail");
        return "detail";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/form/{id}")
    public String findById(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id).orElseThrow(() -> new CustomerNotFoundException("User with ID " + id + " does not exist in the system"));
        model.addAttribute("title", "Customer Form");
        model.addAttribute("customer", customer);
        return "form";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public String save(@Valid Customer customer, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("title", "Customer Form");
            return "form";
        }
        customerService.save(customer);
        status.setComplete();
        flash.addFlashAttribute("success", "Customer saved successfully");
        return "redirect:/customers/find-all";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {
        Customer customer = customerService.findById(id).orElseThrow(() -> new CustomerNotFoundException("User with ID " + id + " does not exist in the system"));
        customerService.delete(customer.getId());
        flash.addFlashAttribute("success", "Customer deleted successfully");
        return "redirect:/customers/find-all";
    }

}
