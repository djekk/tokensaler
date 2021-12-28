package com.syqu.shop.controller;

import com.syqu.shop.object.Customer;
import com.syqu.shop.object.Order;
import com.syqu.shop.service.CustomerService;
import com.syqu.shop.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class CustomerController {
    private final CustomerService customerService;
    private final OrderService orderService;

    @Autowired
    public CustomerController(CustomerService customerService,
    		OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @GetMapping("/user")
    public String userPanel(Principal principal, Model model){
        Customer customer = customerService.findByEmail(principal.getName());

        if (customer != null) 
        {
            model.addAttribute("user", customer);
            
            Iterable<Order> orders = orderService.getOrdersByCustomer(customer);
            model.addAttribute("orders", orders);
            
            String distributorUsername = "";
            if(customer.getDistributor() != null)
            {
            	distributorUsername = customer.getDistributor().getUsername();
            }
            model.addAttribute("distributorUsername", distributorUsername);
        }else {
            return "error/404";
        }

        return "user";
    }
}
