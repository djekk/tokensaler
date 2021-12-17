package com.syqu.shop.controller;

import com.syqu.shop.domain.Customer;
import com.syqu.shop.service.CustomerService;
import com.syqu.shop.validator.CustomerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private final CustomerService customerService;
    private final CustomerValidator customerValidator;

    @Autowired
    public RegisterController(CustomerService customerService, CustomerValidator customerValidator) {
        this.customerService = customerService;
        this.customerValidator = customerValidator;
    }

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("customerForm", new Customer());

        System.out.println("get register");
        return "register";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("customerForm") Customer customerForm, BindingResult bindingResult) {
        customerValidator.validate(customerForm, bindingResult);

    	System.out.println("post register");
    	
        if (bindingResult.hasErrors()) {
            logger.error(String.valueOf(bindingResult.getFieldError()));
            return "register";
        }

        customerService.save(customerForm);
        customerService.login(customerForm.getUsername(), customerForm.getPasswordConfirm());

        return "redirect:/home";
    }
}
