package com.syqu.shop.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingCartServiceTests {

    @MockBean
    private ShoppingCartService shoppingCartService;

    @Test
    public void checkIfShoppingCartServiceIsNotNull() {
        initMocks(this);

        assertThat(shoppingCartService).isNotNull();
    }

    @Test
    public void addProductToCartTests(){
        Map<String, Integer> cart = new LinkedHashMap<>();
        
        String serial1 = "Not Bad Trainers";

        when(shoppingCartService.productsInCart()).thenReturn(cart);

        cart.put(serial1, 1);

        assertThat(shoppingCartService.productsInCart()).containsKey(serial1);
    }

    @Test
    public void addTwoTheSameProductsToCartTests(){
        Map<String, Integer> cart = new LinkedHashMap<>();

        when(shoppingCartService.productsInCart()).thenReturn(cart);

        String serial1 = ("Not Bad Trainers");
        String serial2 = ("Nice Shoes");

        cart.put(serial1, 1);
        cart.put(serial2, 1);

        assertThat(shoppingCartService.productsInCart()).containsKey(serial1);
        assertThat(shoppingCartService.productsInCart()).containsKey(serial2);
    }
}
