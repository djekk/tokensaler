package com.syqu.shop.service;

import com.syqu.shop.object.Order;
import com.syqu.shop.object.OrderProduct;

public interface OrderProductService {

	Iterable<OrderProduct> findByOrder(Order order);
	
	OrderProduct findById(long id);

	OrderProduct create(OrderProduct orderProduct);

	void update(OrderProduct orderProduct);

	OrderProduct getOrderProduct();
	
	long isOrderCompleted(long order_id);

}
