package com.syqu.shop.service.impl;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syqu.shop.object.Order;
import com.syqu.shop.repository.OrderRepository;
import com.syqu.shop.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository) {
	        this.orderRepository = orderRepository;
	}	    

	@Override
    public Iterable<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }
	
    @Override
    public Order create(Order order) {
        order.setDateCreated(LocalDate.now());
        order.setStatus("NEW");
        return this.orderRepository.save(order);
    }

    @Override
    public void update(Order order) {
        this.orderRepository.save(order);
    }
}