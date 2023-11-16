package com.sjsu.storefront.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sjsu.storefront.common.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@JsonIgnoreProperties({"user"})
@Entity
@Table(name = "order_table")
public class Order {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private double totalCost;
	private double totalShipping; 
	private double totalWeight; 
	private double totalProductCost;
	private Date orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	
 // Define the relationship with Items
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<CartItem> items = new ArrayList<>();
    
 // Define the relationship with User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
	
 // Define the relationship with ShippingAddress
    @OneToOne
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;
	
	// Define the relationship with PaymentInfo
    @OneToOne
    @JoinColumn(name = "payment_info_id")
    private PaymentInfo paymentInfo;
	
	public Order() {
		
	}

	public Order(long id, double totalCost, double totalShipping, double totalWeight, double totalProductCost,
			Date orderDate, OrderStatus status, List<CartItem> items, User user, Address shippingAddress,
			PaymentInfo paymentInfo) {
		super();
		this.id = id;
		this.totalCost = totalCost;
		this.totalShipping = totalShipping;
		this.totalWeight = totalWeight;
		this.totalProductCost = totalProductCost;
		this.orderDate = orderDate;
		this.orderStatus = status;
		this.items = items;
		this.user = user;
		this.shippingAddress = shippingAddress;
		this.paymentInfo = paymentInfo;
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

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
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

	public long getId() {
		return id;
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

	@Override
	public String toString() {
		return "Order [id=" + id + ", totalCost=" + totalCost + ", totalShipping=" + totalShipping + ", totalWeight="
				+ totalWeight + ", totalProductCost=" + totalProductCost + ", orderDate=" + orderDate + ", status="
				+ orderStatus + ", items=" + items + ", user=" + user + ", shippingAddress=" + shippingAddress
				+ ", paymentInfo=" + paymentInfo + "]";
	}

}
