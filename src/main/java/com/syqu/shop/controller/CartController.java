package com.syqu.shop.controller;

import com.syqu.shop.service.ShoppingCartService;
import com.syqu.shop.object.Product;
import com.syqu.shop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final ShoppingCartService shoppingCartService;
    @Autowired
    public CartController(ShoppingCartService shoppingCartService, ProductService productService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/cart")
    public String cart(Model model){
        model.addAttribute("products", shoppingCartService.productsInCart());
        model.addAttribute("totalPrice", shoppingCartService.totalPrice());

        return "cart";
    }

    @PostMapping("/cart/add")
    public String addProductToCart(
    		@ModelAttribute("serialnumber") String serialnumber,
    		@ModelAttribute("quantity") Integer quantity)
    {
       shoppingCartService.addProduct(serialnumber, quantity);

        return "redirect:/home";
    }

    @GetMapping("/cart/remove/{serialnumber}")
    public String removeProductFromCart(
    		@PathVariable("serialnumber") String serialnumber){
    
    	shoppingCartService.removeProduct(serialnumber);

        return "redirect:/cart";
    }

    @GetMapping("/cart/clear")
    public String clearProductsInCart(){
        shoppingCartService.clearProducts();

        return "redirect:/cart";
    }

    @GetMapping("/cart/checkout")
    public String cartCheckout(){
        shoppingCartService.cartCheckout();

        return "redirect:/cart";
    }
}
