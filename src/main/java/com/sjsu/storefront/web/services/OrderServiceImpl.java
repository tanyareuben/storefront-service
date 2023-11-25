package com.sjsu.storefront.web.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjsu.storefront.common.OrderStatus;
import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.common.WorkflowException;
import com.sjsu.storefront.data.model.Order;
import com.sjsu.storefront.data.model.ShoppingCart;
import com.sjsu.storefront.data.model.User;
import com.sjsu.storefront.data.model.DTO.OrderDTO;
import com.sjsu.storefront.data.model.DTO.UserDTO;
import com.sjsu.storefront.data.respository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrdersForUser( User user) {
        return orderRepository.findByUser(user);
    }
    
    @Override
    public List<OrderDTO> getAllOrdersForUser(Long userId) {
        List<OrderDTO> orders = new ArrayList<OrderDTO>();
        for(Order order : orderRepository.findByUserId(userId)) {
        	OrderDTO orderDTO = new OrderDTO(order);
        	orders.add(orderDTO);
        }
        return orders;
    }

    @Override
    public OrderDTO getOderById(Long id) throws ResourceNotFoundException {
        Order ordr =  orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
        return new OrderDTO(ordr);
    }

    @Override
    public UUID createOder(User user, ShoppingCart cart) {
    	Order order = new Order();
    	order.setItems(cart.getItems());
    	order.setPaymentInfo(user.getPayment_info());
    	order.setShippingAddress(user.getAddress());
    	order.setTotalCost(cart.getTotalCost());
    	order.setTotalProductCost(cart.getTotalProductCost());
    	order.setTotalShipping(cart.getTotalShipping());
    	order.setTotalWeight(cart.getTotalWeight());
    	order.setUser(user);
    	
    	Order newOrder = orderRepository.save(order);
    	return newOrder.getOrderId();
    	
    }

    @Override
    public UUID cancelOrder(Long orderId) throws ResourceNotFoundException, WorkflowException {
    	Optional<Order> orderTemp = orderRepository.findById(orderId);
    	if(orderTemp.isPresent()) {
    		Order order = orderTemp.get();
    		//Order can be cancelled only if its still in RECIEVED status, means not yet SHIPPED
    		if(order.getStatus() == OrderStatus.RECIEVED) {
	            order.setStatus(OrderStatus.CANCELLED); 
	            orderRepository.save(order);
	            return order.getOrderId();
    		}
    		else {
    			throw new WorkflowException("Order can not be Cancelled, already shipped " + orderId);
    		}
    	}
    	throw new ResourceNotFoundException("Order Not found for {Id} " + orderId);
    }
    
    @Override
    public void updateOrderStatus(Long orderId, OrderStatus status) {
        orderRepository.updateStatus(orderId, status);
    }

	@Override
	public List<OrderDTO> getOrdersByStatusForUser(UserDTO user, OrderStatus orderStatus) {
		User usr = new User(user);
        List<Order> orders = orderRepository.findByUserAndOrderStatus(usr, orderStatus);
        List<OrderDTO> ordrs = new ArrayList<OrderDTO>();
        for(Order order : orders) {
        	ordrs.add(new OrderDTO(order));
        }
        return ordrs;
    }

	@Override
	public List<OrderDTO> getOrdersForUser(UserDTO user) {
		User usr = new User(user);
        List<Order> orders = orderRepository.findByUser(usr);
        List<OrderDTO> ordrs = new ArrayList<OrderDTO>();
        for(Order order : orders) {
        	ordrs.add(new OrderDTO(order));
        }
        return ordrs;
        
    }

	@Override
    public Order addOrder(User user, Order order) {
        order.setUser(user);
        return orderRepository.save(order);
    }

}
