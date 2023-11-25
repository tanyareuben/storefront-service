package com.sjsu.storefront.web.services;

import java.util.List;
import java.util.UUID;

import com.sjsu.storefront.common.OrderStatus;
import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.common.WorkflowException;
import com.sjsu.storefront.data.model.Order;
import com.sjsu.storefront.data.model.ShoppingCart;
import com.sjsu.storefront.data.model.User;
import com.sjsu.storefront.data.model.DTO.OrderDTO;
import com.sjsu.storefront.data.model.DTO.UserDTO;

public interface OrderService {
	public List<Order> getAllOrdersForUser( User user);
	public List<OrderDTO> getAllOrdersForUser(Long userId);
	public OrderDTO getOderById(Long id) throws ResourceNotFoundException;
	public UUID createOder(User user, ShoppingCart cart);
	public UUID cancelOrder(Long id) throws ResourceNotFoundException, WorkflowException;
	public void updateOrderStatus(Long orderId, OrderStatus status);
	public Order addOrder(User user, Order order);
	public List<OrderDTO> getOrdersForUser(UserDTO user);
	public List<OrderDTO> getOrdersByStatusForUser(UserDTO user, OrderStatus orderStatus);
}
