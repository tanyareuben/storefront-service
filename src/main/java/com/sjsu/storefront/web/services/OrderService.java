package com.sjsu.storefront.web.services;

import java.util.List;

import com.sjsu.storefront.common.OrderStatus;
import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.common.WorkflowException;
import com.sjsu.storefront.data.model.Order;
import com.sjsu.storefront.data.model.ShoppingCart;
import com.sjsu.storefront.data.model.User;

public interface OrderService {
	public List<Order> getAllOrdersForUser( User user);
	public List<Order> getAllOrdersForUser(Long userId);
	public Order getOderById(Long id) throws ResourceNotFoundException;
	public Long createOder(User user, ShoppingCart cart);
	public Long cancelOrder(Long id) throws ResourceNotFoundException, WorkflowException;
	public void updateOrderStatus(Long orderId, OrderStatus status);
}
