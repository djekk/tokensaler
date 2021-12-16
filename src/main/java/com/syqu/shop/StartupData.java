package com.syqu.shop;

import com.syqu.shop.domain.Product;
import com.syqu.shop.service.ProductService;
import com.syqu.shop.domain.Customer;
import com.syqu.shop.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StartupData implements CommandLineRunner {
    private final CustomerService userService;
    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(StartupData.class);

    @Autowired
    public StartupData(CustomerService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) {
        adminAccount();
        userAccount();
        exampleProducts();
    }

    private void userAccount(){
        Customer user = new Customer();

        user.setUsername("user");
        user.setPassword("user");
        user.setPasswordConfirm("user");
        user.setEmail("user@example.com");

        userService.save(user);
    }

    private void adminAccount(){
        Customer admin = new Customer();

        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setPasswordConfirm("admin");
        admin.setEmail("admin@example.com");

        userService.save(admin);
    }

    private void exampleProducts(){
        final String NAME = "Token";
        final String IMAGE_URL = "images/token.png";
        final String DESCRIPTION = "";
        final BigDecimal PRICE = BigDecimal.valueOf(1);

        Product product1 = new Product();

        product1.setName(NAME);
        product1.setImageUrl(IMAGE_URL);
        product1.setDescription(DESCRIPTION);
        product1.setPrice(PRICE);

        productService.save(product1);

    }
}
