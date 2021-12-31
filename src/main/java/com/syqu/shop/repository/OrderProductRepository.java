package com.syqu.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.syqu.shop.object.Order;
import com.syqu.shop.object.OrderProduct;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

	Iterable<OrderProduct> findByOrder(Order order);
	
	OrderProduct findById(long id);
	
	@Query(value="select * from order_product op where op.data is null and op.order_id in (SELECT id FROM ORDERS where status = 'PAID' ) limit 1", nativeQuery = true)
	OrderProduct getOrderProduct();
	
	
	@Query(value="select count(*) from order_product op where op.data is null and op.order_id = ?1", nativeQuery = true)
	long isOrderCompleted(long order_id);
}
