package com.aloyolaa.springbootdatajpa.controller;

import com.aloyolaa.springbootdatajpa.exception.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CustomerNotFoundException.class)
    public String customerNotFound(CustomerNotFoundException e, Model model) {
        model.addAttribute("error", "Customer Not Found");
        model.addAttribute("message", e.getMessage());
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("timestamp", LocalDateTime.now());
        return "error/customer-not-found";
    }

}
