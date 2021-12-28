package com.syqu.shop.service.impl;

import com.syqu.shop.object.OrderProduct;
import com.syqu.shop.service.ShoppingCartService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/*
Thanks to Dusan!
www.github.com/reljicd/spring-boot-shopping-cart
 */

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private Map<String, Integer> cart = new LinkedHashMap<>();

    @Override
    public void addProduct(String serialnumber, Integer quantity) {
        if (cart.containsKey(serialnumber)){
        	if(cart.get(serialnumber) + quantity > 50)
        		cart.replace(serialnumber, 50);
        	else
        		cart.replace(serialnumber, cart.get(serialnumber) + quantity);
        }else{
            cart.put(serialnumber, quantity);
        }
    }

    @Override
    public void removeOneQuantity(String serialnumber) {
        if (cart.containsKey(serialnumber)) {
            if (cart.get(serialnumber) > 1)
                cart.replace(serialnumber, cart.get(serialnumber) - 1);
            else if (cart.get(serialnumber) == 1) {
                cart.remove(serialnumber);
            }
        }
    }
    
    @Override
    public void addOneQuantity(String serialnumber) {
    	 if (cart.containsKey(serialnumber))
    	 {
         	if(cart.get(serialnumber) == 50)
         		cart.replace(serialnumber, 50);
         	else
         		cart.replace(serialnumber, cart.get(serialnumber) + 1);
    	 }
    }

    @Override
    public void clearProducts() {
        cart.clear();
    }

    @Override
    public Map<String, Integer> productsInCart() {
        return Collections.unmodifiableMap(cart);
    }
    
    @Override
    public BigDecimal totalPrice(BigDecimal pricePerToken) {
    	
    	Integer totalQuantity = 0; 
    	for (Integer quantity : productsInCart().values())
    	{
    		totalQuantity += quantity;    		
    	}
    	
    	return pricePerToken.multiply(new BigDecimal(totalQuantity));
    }

    @Override
    public void cartCheckout() {
        cart.clear();
        // Normally there would be payment etc.
    }
}
