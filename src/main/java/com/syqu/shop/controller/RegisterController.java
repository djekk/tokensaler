package com.syqu.shop.controller;

import com.syqu.shop.object.Customer;
import com.syqu.shop.service.CustomerService;
import com.syqu.shop.service.DistributorService;
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
    private final DistributorService distributorService;

    @Autowired
    public RegisterController(CustomerService customerService, 
    		CustomerValidator customerValidator,
    		DistributorService distributorService) {
        this.customerService = customerService;
        this.customerValidator = customerValidator;
        this.distributorService = distributorService;
    }

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("userForm", new Customer());

        return "register";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("userForm") Customer userForm, BindingResult bindingResult) {
        customerValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error(String.valueOf(bindingResult.getFieldError()));
            return "register";
        }

        customerService.save(userForm);
     //   customerService.login(userForm.getEmail(), userForm.getPasswordConfirm());
       // return "redirect:/home";
        
        return "redirect:/registration/"+userForm.getEmail();
    }
}
