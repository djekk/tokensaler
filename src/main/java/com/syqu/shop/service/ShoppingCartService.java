package com.syqu.shop.service;

import org.springframework.stereotype.Service;

import com.syqu.shop.object.Product;

import java.math.BigDecimal;
import java.util.Map;

@Service
public interface ShoppingCartService {
    void addProduct(String serialnumber, Integer quantity);
    void removeOneQuantity(String serialnumber);
    void addOneQuantity(String serialnumber);
    void clearProducts();
    Map<String, Integer> productsInCart();
    BigDecimal totalPrice();
    void cartCheckout();
}
