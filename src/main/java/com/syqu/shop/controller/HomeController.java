package com.syqu.shop.controller;

import com.syqu.shop.mail.MyMailSender;
import com.syqu.shop.object.Customer;
import com.syqu.shop.object.Product;
import com.syqu.shop.service.CustomerService;
import com.syqu.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {
    private final ProductService productService;
  //  private final CustomerService customerService;

    @Autowired
    public HomeController(ProductService productService/*, CustomerService customerService*/) {
        this.productService = productService;
   //     this.customerService = customerService;
    }

    @GetMapping(value = {"/","/index","/home"})
    public String home(Model model){
        model.addAttribute("products", getAllProducts());
        
   //     MyMailSender.sendEmail();
        return "home";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }
    
     private List<Product> getAllProducts(){
        return productService.findAllByOrderByIdAsc();
    }
}
