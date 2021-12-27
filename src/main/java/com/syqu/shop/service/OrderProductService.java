package com.syqu.shop.service;

import com.syqu.shop.object.Order;
import com.syqu.shop.object.OrderProduct;

public interface OrderProductService {

	Iterable<OrderProduct> findByOrder(Order order);

	OrderProduct create(OrderProduct orderProduct);

	void update(OrderProduct orderProduct);

}
