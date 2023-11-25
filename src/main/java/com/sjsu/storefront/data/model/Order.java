package com.sjsu.storefront.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.sjsu.storefront.common.OrderStatus;
import com.sjsu.storefront.common.UUIDGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "user_order") // Specify a different table name
public class Order {
	
	@Id
	private UUID userOrderId = UUIDGenerator.generateUUID(); 
	
	private double totalCost;
	private double totalShipping; 
	private double totalWeight; 
	private double totalProductCost;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	
 // Define the relationship with Items
    @OneToMany(mappedBy = "order", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();
        
 // Define the relationship with User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
	
 // Define the relationship with ShippingAddress
    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;
	
	// Define the relationship with PaymentInfo
    @ManyToOne
    @JoinColumn(name = "payment_info_id")
    private PaymentInfo paymentInfo;
    
    @PrePersist
    protected void onCreate() {
    	orderDate = new Date();
    }
	
	public Order() {
		
	}

	public Order(UUID orderId, double totalCost, double totalShipping, double totalWeight, double totalProductCost,
			Date orderDate, OrderStatus orderStatus, List<OrderItem> items, User user, Address shippingAddress,
			PaymentInfo paymentInfo) {
		super();
		this.userOrderId = orderId;
		this.totalCost = totalCost;
		this.totalShipping = totalShipping;
		this.totalWeight = totalWeight;
		this.totalProductCost = totalProductCost;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.items = items;
		this.user = user;
		this.shippingAddress = shippingAddress;
		this.paymentInfo = paymentInfo;
	}

	
	public UUID getOrderId() {
		return userOrderId;
	}

	public void setOrderId(UUID orderId) {
		this.userOrderId = orderId;
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

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		for(CartItem item : items) {
			OrderItem oItem = new OrderItem(item, this);
			oItem.setOrder(this); //set the order to the item
			if(this.items == null) {
				this.items = new ArrayList<OrderItem>();
			}
			this.items.add(oItem);
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
	

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public OrderStatus getStatus() {
		return orderStatus;
	}

	public void setStatus(OrderStatus status) {
		this.orderStatus = status;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + userOrderId + ", totalCost=" + totalCost + ", totalShipping=" + totalShipping
				+ ", totalWeight=" + totalWeight + ", totalProductCost=" + totalProductCost + ", orderDate=" + orderDate
				+ ", orderStatus=" + orderStatus + ", items=" + items + ", user=" + user + ", shippingAddress="
				+ shippingAddress + ", paymentInfo=" + paymentInfo + "]";
	}

}
