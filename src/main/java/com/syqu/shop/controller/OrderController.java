package com.syqu.shop.controller;

import com.syqu.shop.object.Order;
import com.syqu.shop.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orderDetails/{token}")
    public String editProduct(@PathVariable("token") String token, Model model){
	
        Order order = orderService.findByToken(token);
        if (order != null){
            model.addAttribute("orderProducts", order.getOrderProducts());
            return "order_products";
        }
        else 
        {
            return "error/404";
        }
    }
    
}
