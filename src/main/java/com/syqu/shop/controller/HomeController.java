package com.syqu.shop.controller;

import com.syqu.shop.object.Customer;
import com.syqu.shop.object.Product;
import com.syqu.shop.service.CustomerService;
import com.syqu.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {
    private final ProductService productService;
    private final CustomerService customerService;

    @Autowired
    public HomeController(ProductService productService, CustomerService customerService) {
        this.productService = productService;
        this.customerService = customerService;
    }

    @GetMapping(value = {"/","/index","/home"})
    public String home(Model model){
        model.addAttribute("products", getAllProducts());
        return "home";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }
    
    @GetMapping("/registration/{email}")
    public String registration(@PathVariable("email") String email, Model model)
    {
    	Customer customer = customerService.findByEmail(email);
    	if( customer != null )
    	{
    		model.addAttribute("user", customer);
    	}
        return "registration";
    }
    
     private List<Product> getAllProducts(){
        return productService.findAllByOrderByIdAsc();
    }
}
