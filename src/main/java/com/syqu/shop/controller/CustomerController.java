package com.syqu.shop.controller;

import com.syqu.shop.object.Customer;
import com.syqu.shop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/user")
    public String userPanel(Principal principal, Model model){
        Customer user = customerService.findByEmail(principal.getName());

        if (user != null) 
        {
            model.addAttribute("user", user);
            
            String distributorUsername = "";
            if(user.getDistributor() != null)
            {
            	distributorUsername = user.getDistributor().getUsername();
            }
            model.addAttribute("distributorUsername", distributorUsername);
        }else {
            return "error/404";
        }

        return "user";
    }
}
