package com.syqu.shop.service;

import com.syqu.shop.object.Customer;
import com.syqu.shop.object.Order;

public interface OrderService {
	
	Iterable<Order> getOrdersByCustomer(Customer customer);

	Order create(Order order);

	void update(Order order);

	Order findByToken(String token);

}
