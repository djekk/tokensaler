package com.syqu.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.syqu.shop.object.Customer;
import com.syqu.shop.object.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	Order findByToken(String token);
		
	Iterable<Order> findByCustomer(Customer customer);

}
