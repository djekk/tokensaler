package com.syqu.shop;

import com.syqu.shop.service.ProductService;
import com.syqu.shop.object.Customer;
import com.syqu.shop.object.Distributor;
import com.syqu.shop.object.Order;
import com.syqu.shop.object.OrderProduct;
import com.syqu.shop.object.Product;
import com.syqu.shop.service.CustomerService;
import com.syqu.shop.service.DistributorService;
import com.syqu.shop.service.OrderProductService;
import com.syqu.shop.service.OrderService;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupData implements CommandLineRunner {
    private final CustomerService customerService;
    private final ProductService productService;
    private final DistributorService distributorService;
    private final OrderService orderService;
    private final OrderProductService orderProductService;

   // private static final Logger logger = LoggerFactory.getLogger(StartupData.class);

    @Autowired
    public StartupData(
    		CustomerService customerService, 
    		ProductService productService, 
    		DistributorService distributorService,
    		OrderService orderService,
    		OrderProductService orderProductService) {
        this.customerService = customerService;
        this.productService = productService;
        this.distributorService = distributorService;
        this.orderService = orderService;
        this.orderProductService = orderProductService;
    }

    @Override
    public void run(String... args) {
    	distributor();
        adminAccount();
        userAccount();
        exampleProducts();
        cart();
    }

    private void userAccount(){
        Customer customer = new Customer();

        customer.setEmail("user@example.com");
        customer.setPassword("user");        
        customer.setPasswordConfirm("user");
        customer.setEnabled(true);
        customer.setDistributor(
        		distributorService.findByUsername("distributor"));
        customerService.save(customer);
    }

    private void adminAccount(){
        Customer admin = new Customer();

        admin.setEmail("admin@example.com");
        admin.setPassword("admin");
        admin.setPasswordConfirm("admin");
        admin.setEnabled(true);

        customerService.save(admin);
    }

    private void exampleProducts(){
        final String NAME = "Token";
        final String IMAGE_URL = "images/token.png";
        final String DESCRIPTION = "";

        Product product = new Product();

        product.setName(NAME);
        product.setImageUrl(IMAGE_URL);
        product.setDescription(DESCRIPTION);


        productService.save(product);

    }
    
    private void cart()
    {
    	Customer customer = customerService.findByEmail("user@example.com");

        if (customer != null) 
        {            
        	Distributor distributor = customer.getDistributor();
            if(distributor != null)
            {
            	Order order = new Order();
            	order.setCustomer(customer);
            	order.setDistributor(distributor);
            	order.setTotalPrice(BigDecimal.valueOf(8));
            	orderService.create(order); 
    
               	System.out.println("order.getDistributor="+order.getDistributor().toString());
            	
            	{
            		OrderProduct op = new OrderProduct(order, 
            				"gdfgdfg", 1);
            		
            		op.setData("fghfghfghfg");
            		
            		op = orderProductService.create(op); 
            		
            		op = new OrderProduct(order, 
            				"23423423", 3);
            		
            		op.setData("fghfghfghfg");
            		
            		op = orderProductService.create(op); 
            	}
            	           
            	
            	Order order2 = new Order();
            	order2.setCustomer(customer);
            	order2.setDistributor(distributor);
            	order2.setTotalPrice(BigDecimal.valueOf(17));
            	orderService.create(order2); 
    
               	System.out.println("order.getDistributor="+order.getDistributor().toString());
            	
            	{
            		OrderProduct op = new OrderProduct(order2, 
            				"tyutkhjk", 10);
            		
            		op.setData("uuuu");
            		
            		op = orderProductService.create(op); 
            		
            		op = new OrderProduct(order2, 
            				"yutyuyuyt", 2);
            		
            		op.setData("dddddddd");
            		
            		op = orderProductService.create(op); 
            	}
              
        		for(OrderProduct orp : orderProductService.findByOrder(order2))
        		{
        			System.out.println("**************orp="+orp.toString());                		
            	} 
            }         
        }

    }
    
    private void distributor(){
    	Distributor distributor = new Distributor();

    	distributor.setUsername("distributor");
        distributor.setEmail("distributor@example.com");
        distributor.setPrice(BigDecimal.valueOf(2));

        distributorService.save(distributor);
    }
}
