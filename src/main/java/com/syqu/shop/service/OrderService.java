package com.syqu.shop.service;

import com.syqu.shop.object.Order;

public interface OrderService {

	Iterable<Order> getAllOrders();

	Order create(Order order);

	void update(Order order);

}
