package com.syqu.shop;

import com.syqu.shop.domain.Category;
import com.syqu.shop.domain.Product;
import com.syqu.shop.repository.CategoryRepository;
import com.syqu.shop.service.ProductService;
import com.syqu.shop.domain.User;
import com.syqu.shop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StartupData implements CommandLineRunner {
    private final UserService userService;
    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(StartupData.class);

    @Autowired
    public StartupData(UserService userService, ProductService productService, CategoryRepository categoryRepository) {
        this.userService = userService;
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        adminAccount();
        userAccount();
        category();
        exampleProducts();
    }

    private void userAccount(){
        User user = new User();

        user.setUsername("user");
        user.setPassword("user");
        user.setPasswordConfirm("user");
        user.setGender("Female");
        user.setEmail("user@example.com");

        userService.save(user);
    }

    private void adminAccount(){
        User admin = new User();

        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setPasswordConfirm("admin");
        admin.setGender("Male");
        admin.setEmail("admin@example.com");

        userService.save(admin);
    }

    private void category(){
        Category category1 = new Category();
        Category category2 = new Category();
        category1.setId(1);
        category1.setCategoryName("Adventure");

        categoryRepository.save(category1);
    }

    private void exampleProducts(){
        final String NAME = "Example Name";
        final String IMAGE_URL = "https://d2gg9evh47fn9z.cloudfront.net/800px_COLOURBOX7389458.jpg";
        final String DESCRIPTION = "Example Description";
        final BigDecimal PRICE = BigDecimal.valueOf(10);

        Product product1 = new Product();

        product1.setName(NAME);
        product1.setImageUrl(IMAGE_URL);
        product1.setDescription(DESCRIPTION);
        product1.setCategory(categoryRepository.findByCategoryName("Adventure"));
        product1.setPrice(PRICE);

        productService.save(product1);

    }
}
