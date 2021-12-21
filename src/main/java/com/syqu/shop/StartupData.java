package com.syqu.shop;

import com.syqu.shop.service.ProductService;
import com.syqu.shop.object.Customer;
import com.syqu.shop.object.Distributor;
import com.syqu.shop.object.Product;
import com.syqu.shop.service.CustomerService;
import com.syqu.shop.service.DistributorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupData implements CommandLineRunner {
    private final CustomerService userService;
    private final ProductService productService;
    private final DistributorService distributorService;
   // private static final Logger logger = LoggerFactory.getLogger(StartupData.class);

    @Autowired
    public StartupData(CustomerService userService, ProductService productService, DistributorService distributorService) {
        this.userService = userService;
        this.productService = productService;
        this.distributorService = distributorService;
    }

    @Override
    public void run(String... args) {
    	distributor();
        adminAccount();
        userAccount();
        exampleProducts();
    }

    private void userAccount(){
        Customer customer = new Customer();

        customer.setEmail("user@example.com");
        customer.setPassword("user");        
        customer.setPasswordConfirm("user");
        customer.setEnabled(true);
      //  customer.setDistributor(distributorService.findByUsername("distributor"));
        userService.save(customer);
    }

    private void adminAccount(){
        Customer admin = new Customer();

        admin.setEmail("admin@example.com");
        admin.setPassword("admin");
        admin.setPasswordConfirm("admin");
        admin.setEnabled(true);

        userService.save(admin);
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
    
    private void distributor(){
    	Distributor distributor = new Distributor();

    	distributor.setUsername("distributor");
        distributor.setEmail("distributor@example.com");

        distributorService.save(distributor);
    }
}
