package com.syqu.shop.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syqu.shop.object.Order;
import com.syqu.shop.object.OrderProduct;
import com.syqu.shop.repository.OrderProductRepository;
import com.syqu.shop.service.OrderProductService;


@Service
@Transactional
public class OrderProductServiceImpl implements OrderProductService {

	private final OrderProductRepository orderProductRepository;

	@Autowired
	public OrderProductServiceImpl(OrderProductRepository orderProductRepository) {
	        this.orderProductRepository = orderProductRepository;
	}	    
	
	@Override
    public Iterable<OrderProduct> findByOrder(Order order) {
        return this.orderProductRepository.findByOrder(order);
    }
	
    @Override
    public OrderProduct create(OrderProduct orderProduct) {
        return this.orderProductRepository.save(orderProduct);
    }

    @Override
    public void update(OrderProduct orderProduct) {
        this.orderProductRepository.save(orderProduct);
    }

	@Override
	public OrderProduct findById(long id) {
		return this.orderProductRepository.findById(id);
	}

	@Override
	public OrderProduct getOrderProduct() {
		return this.orderProductRepository.getOrderProduct();
	}

	@Override
	public long isOrderCompleted(long order_id) {
		return this.orderProductRepository.isOrderCompleted(order_id);
	}
}