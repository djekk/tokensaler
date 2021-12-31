package com.syqu.shop.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syqu.shop.object.Customer;
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
    public Order findByToken(String token) {
        return orderRepository.findByToken(token);
    }
	
	@Override
    public Iterable<Order> getOrdersByCustomer(Customer customer) {
        return orderRepository.findByCustomer(customer);
    }
	
    @Override
    public Order create(Order order) {
    	order.setToken(UUID.randomUUID().toString());
        order.setDateOrdered(LocalDateTime.now());
        order.setStatus("NEW");
        return orderRepository.save(order);
    }

    @Override
    public void update(Order order) {
        orderRepository.save(order);
    }


	@Override
	public Order findById(long id) {
		return orderRepository.findById(id);
	}
}