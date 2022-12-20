package com.aloyolaa.springbootdatajpa.controller;

import com.aloyolaa.springbootdatajpa.entity.Customer;
import com.aloyolaa.springbootdatajpa.entity.Invoice;
import com.aloyolaa.springbootdatajpa.entity.InvoiceItem;
import com.aloyolaa.springbootdatajpa.entity.Product;
import com.aloyolaa.springbootdatajpa.exception.CustomerNotFoundException;
import com.aloyolaa.springbootdatajpa.service.CustomerService;
import com.aloyolaa.springbootdatajpa.service.InvoiceService;
import com.aloyolaa.springbootdatajpa.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/invoices")
@SessionAttributes("invoice")
@Secured("ADMIN")
public class InvoiceController {

    private final CustomerService customerService;

    private final ProductService productService;

    private final InvoiceService invoiceService;

    private final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @GetMapping("/form/{customerId}")
    public String create(@PathVariable Long customerId, Model model) {
        Customer customer = customerService.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("User with ID " + customerId + " does not exist in the system"));
        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        model.addAttribute("title", "Invoice Form");
        model.addAttribute("invoice", invoice);
        return "invoice/form";
    }

    @GetMapping(value = "/charge-products/{name}", produces = {"application/json"})
    public @ResponseBody List<Product> chargeProduct(@PathVariable String name) {
        return productService.findByNameContainsIgnoreCase(name);
    }

    @PostMapping("/form")
    public String save(@Valid Invoice invoice,
                       BindingResult result,
                       Model model,
                       @RequestParam(name = "item_[]", required = false) Long[] itemId,
                       @RequestParam(name = "quantity[]", required = false) Integer[] quantity,
                       RedirectAttributes flash,
                       SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("title", "Invoice Form");
            return "invoice/form";
        }
        if (itemId == null || itemId.length == 0) {
            model.addAttribute("title", "Invoice Form");
            model.addAttribute("error", "Invoice must contain items");
            return "invoice/form";
        }
        for (int i = 0; i < itemId.length; i++) {
            Product product = productService.findById(itemId[i]).orElseThrow();
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setQuantity(quantity[i]);
            invoiceItem.setProduct(product);
            invoice.addInvoiceItem(invoiceItem);
            logger.info("ID: {0}, quantity: {1}", itemId[i], quantity[i]);
        }
        invoiceService.save(invoice);
        status.setComplete();
        flash.addFlashAttribute("success", "Invoice saved successfully");
        return "redirect:/customers/detail/" + invoice.getCustomer().getId();
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Invoice invoice = invoiceService.fetchByIdWithCustomerWithInvoiceItemWithProduct(id).orElseThrow();
        model.addAttribute("title", "Invoice Detail");
        model.addAttribute("invoice", invoice);
        return "invoice/detail";
    }

    @GetMapping("/delete/{id}")
    public String delele(@PathVariable Long id, RedirectAttributes flash) {
        Invoice invoice = invoiceService.findById(id).orElseThrow();
        invoiceService.delete(invoice.getId());
        flash.addFlashAttribute("success", "Invoice deleted successfully");
        return "redirect:/customers/detail/" + invoice.getCustomer().getId();
    }

}
