package com.sjsu.storefront.data.model.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sjsu.storefront.common.OrderStatus;
import com.sjsu.storefront.data.model.Order;
import com.sjsu.storefront.data.model.OrderItem;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class OrderDTO {
	
	private long orderId;
	private double totalCost;
	private double totalShipping; 
	private double totalWeight; 
	private double totalProductCost;
	private Date orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	
    private List<OrderItemDTO> items = new ArrayList<>();
    
    private Long userId;
	
    private AddressDTO shippingAddress;
	
    private PaymentInfoDTO paymentInfo;
	
	public OrderDTO() {
		
	}
	
	public OrderDTO(Order order) {
		this.userId = order.getUser().getId();
		this.orderId = order.getId();
		this.orderDate = order.getOrderDate();
		this.orderStatus = order.getOrderStatus();
		this.totalCost = order.getTotalCost();
		this.totalProductCost = order.getTotalProductCost();
		this.totalShipping = order.getTotalShipping();
		this.totalWeight = order.getTotalWeight();
		this.shippingAddress = new AddressDTO(order.getShippingAddress());
		this.paymentInfo = new PaymentInfoDTO(order.getPaymentInfo());
		this.items = getDTOItems(order.getItems());
	}
	
	private List<OrderItemDTO>getDTOItems(List<OrderItem> items) {
		
		List<OrderItemDTO> dtoItems = new ArrayList<OrderItemDTO>();
		for(OrderItem item : items) {
			dtoItems.add(new OrderItemDTO(item));
		}
		return dtoItems;
	}

	public OrderDTO(long id, double totalCost, double totalShipping, double totalWeight, double totalProductCost,
			Date orderDate, OrderStatus orderStatus, List<OrderItemDTO> items, Long userId, AddressDTO shippingAddress,
			PaymentInfoDTO paymentInfo) {
		super();
		this.orderId = id;
		this.totalCost = totalCost;
		this.totalShipping = totalShipping;
		this.totalWeight = totalWeight;
		this.totalProductCost = totalProductCost;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.items = items;
		this.userId = userId;
		this.shippingAddress = shippingAddress;
		this.paymentInfo = paymentInfo;
	}

	public long getId() {
		return orderId;
	}

	public void setId(long id) {
		this.orderId = id;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public double getTotalShipping() {
		return totalShipping;
	}

	public void setTotalShipping(double totalShipping) {
		this.totalShipping = totalShipping;
	}

	public double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public double getTotalProductCost() {
		return totalProductCost;
	}

	public void setTotalProductCost(double totalProductCost) {
		this.totalProductCost = totalProductCost;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<OrderItemDTO> getItems() {
		return items;
	}

	public void setItems(List<OrderItemDTO> items) {
		this.items = items;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public AddressDTO getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(AddressDTO shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public PaymentInfoDTO getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(PaymentInfoDTO paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	@Override
	public String toString() {
		return "OrderDTO [id=" + orderId + ", totalCost=" + totalCost + ", totalShipping=" + totalShipping + ", totalWeight="
				+ totalWeight + ", totalProductCost=" + totalProductCost + ", orderDate=" + orderDate + ", orderStatus="
				+ orderStatus + ", items=" + items + ", userId=" + userId + ", shippingAddress=" + shippingAddress
				+ ", paymentInfo=" + paymentInfo + "]";
	}
}
