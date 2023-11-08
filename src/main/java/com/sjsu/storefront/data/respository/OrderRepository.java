package com.sjsu.storefront.data.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sjsu.storefront.common.OrderStatus;
import com.sjsu.storefront.data.model.Order;
import com.sjsu.storefront.data.model.User;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	List<Order> findByUser(User user);
	List<Order> findByUserId(Long userId);
	
	@Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :orderId")
    void updateStatus(@Param("orderId") Long orderId, @Param("status") OrderStatus status);
}
