package com.syqu.shop.controller;

import com.syqu.shop.service.ShoppingCartService;
import com.syqu.shop.object.Customer;
import com.syqu.shop.object.Distributor;
import com.syqu.shop.object.Order;
import com.syqu.shop.object.OrderProduct;
import com.syqu.shop.object.Product;
import com.syqu.shop.service.CustomerService;
import com.syqu.shop.service.OrderService;
import com.syqu.shop.service.ProductService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private final CustomerService customerService;
    private final OrderService orderService;
        
    @Autowired
    public CartController(
    		ShoppingCartService shoppingCartService, 
    		CustomerService customerService,
    		OrderService orderService) {
        this.shoppingCartService = shoppingCartService;
        this.customerService = customerService; 
        this.orderService = orderService; 
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

    @GetMapping("/cart/removeOneQuantity/{serialnumber}")
    public String removeOneQuantity(
    		@PathVariable("serialnumber") String serialnumber){
    
    	shoppingCartService.removeOneQuantity(serialnumber);

        return "redirect:/cart";
    }
    
    @GetMapping("/cart/addOneQuantity/{serialnumber}")
    public String addOneQuantity(
    		@PathVariable("serialnumber") String serialnumber){
    
    	shoppingCartService.addOneQuantity(serialnumber);

        return "redirect:/cart";
    }

    @GetMapping("/cart/clear")
    public String clearProductsInCart(){
        shoppingCartService.clearProducts();

        return "redirect:/cart";
    }

    @GetMapping("/cart/checkout")
    public String cartCheckout(Principal principal){
    	
		Customer customer = customerService.findByEmail(principal.getName());

        if (customer != null) 
        {            
        	Distributor distributor = customer.getDistributor();
            if(distributor != null)
            {
            	Order order = new Order();
            	order.setCustomer(customer);
            	order.setDistributor(distributor);
           // 	orderService.create(order);
           	
            	List<OrderProduct> orderProducts = new ArrayList<OrderProduct>();
            			
            	Map<String, Integer> mapOrderProducts = shoppingCartService.productsInCart();
            	
            	for (Map.Entry<String, Integer> entry : mapOrderProducts.entrySet())
            	{
            		OrderProduct op = new OrderProduct(order, entry.getKey(), entry.getValue());
            		orderProducts.add(op);
            	}
            	            	
           	 	order.setOrderProducts(orderProducts);
           	 	orderService.create(order);
           	 	           	 	           	 	
                shoppingCartService.cartCheckout();            	
            }         
        }

        return "redirect:/cart";
    }
}
