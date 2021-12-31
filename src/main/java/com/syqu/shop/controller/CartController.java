package com.syqu.shop.controller;

import com.syqu.shop.service.ShoppingCartService;
import com.syqu.shop.mail.MailSender;
import com.syqu.shop.object.Customer;
import com.syqu.shop.object.Distributor;
import com.syqu.shop.object.Order;
import com.syqu.shop.object.OrderProduct;
import com.syqu.shop.service.CustomerService;
import com.syqu.shop.service.OrderProductService;
import com.syqu.shop.service.OrderService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CartController {
    
	private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    
    private final ShoppingCartService shoppingCartService;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final OrderProductService orderProductService;
        
    @Autowired
    public CartController(
    		ShoppingCartService shoppingCartService, 
    		CustomerService customerService,
    		OrderService orderService,
    		OrderProductService orderProductService) {
        this.shoppingCartService = shoppingCartService;
        this.customerService = customerService; 
        this.orderService = orderService;
        this.orderProductService = orderProductService;
    }

    @GetMapping("/cart")
    public String cart(Principal principal, Model model){
    	
    	if (principal != null) 
    	{	    		
	    	Customer customer = customerService.findByEmail(principal.getName());
	        if (customer != null) 
	        {            
	        	Distributor distributor = customer.getDistributor();
	            if(distributor != null)
	            {
	            	model.addAttribute("products", shoppingCartService.productsInCart());
	                model.addAttribute("totalPrice", getTotalOrderPrice(distributor));
	                return "cart";
	            }
	        }
    	}
        return "login";
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
    
    private BigDecimal getTotalOrderPrice(Distributor distributor)
    {
    	if(distributor != null)
        {
    		return shoppingCartService.totalPrice(distributor.getPrice());
        }
    	
    	return BigDecimal.ZERO;
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
            	order.setTotalPrice(getTotalOrderPrice(distributor));
            	orderService.create(order);
            	
            	for (Map.Entry<String, Integer> entry : shoppingCartService.productsInCart().entrySet())
            	{
            		OrderProduct op = new OrderProduct(order, entry.getKey(), entry.getValue());
            		op = orderProductService.create(op); 
            	}
           	 	           	 	           	 	
                shoppingCartService.cartCheckout();            	
            }         
        }
        
        //TODO  thank you message

        return "redirect:/cart";
    }
    
    
    @GetMapping("/getData")
    public ResponseEntity<OrderProduct> getData(){	      
	 try {			
		 	OrderProduct op = orderProductService.getOrderProduct();
				
		 	return new ResponseEntity<OrderProduct>(op, HttpStatus.OK);
		 	
		} catch (NoSuchElementException e) {
			return new ResponseEntity<OrderProduct>(HttpStatus.NOT_FOUND);
		}
    }
        
    /*
	c++ kidaet proshitanij result
	http://localhost:8080/setResult
	PUT
	Body zaporosa:  {"id":1,"data":"3dfghfhfgh"}
	*/
	// RESTful API method for Update operation
	@PostMapping("/setData")
	public ResponseEntity<OrderProduct> setData(@RequestBody OrderProduct task) {
		try {
			
			OrderProduct op = orderProductService.findById(task.getId());
			Order order = op.getOrder();
			if(order != null)
			{
				op.setData(task.getData());
				orderProductService.update(op);
								
				if(orderProductService.isOrderCompleted(order.getId()) == 0)
				{
					order.setStatus("COMPLETED");
					orderService.update(order);
					
					String body = ""; 
					for(OrderProduct orp : orderProductService.findByOrder(order))
	        		{
						body += orp.getData() + "\r\n\r\n";                		
	            	} 
					
			        MailSender.sendSimpleEmail(order.getCustomer().getEmail(), 
			        		"Token(s) were completeted", 
			        		body);				
				}
			}
			return new ResponseEntity<OrderProduct>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<OrderProduct>(HttpStatus.NOT_FOUND);
		}
	}
    
}
