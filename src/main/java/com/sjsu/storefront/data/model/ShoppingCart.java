package com.sjsu.storefront.data.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Shopping Cart")
public class ShoppingCart {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private double totalCost;
	private double totalShipping; 
	private double totalWeight; 
	
	
	 // Define the one-to-many relationship
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY) //is this line correct with the mappedBy = "item" or should it be something else
    private List<Item> items;

    public ShoppingCart()
    {
    	
    }
    
	public ShoppingCart(long id, double totalCost, double totalShipping, double totalWeight, List<Item> items) {
		super();
		this.id = id;
		this.totalCost = totalCost;
		this.totalShipping = totalShipping;
		this.totalWeight = totalWeight;
		this.items = items;
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

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public long getId() {
		return id;
	}
	
	public void set(ShoppingCart shoppingCart) {
		this.items = shoppingCart.items;
		this.totalCost = shoppingCart.totalCost;
		this.totalShipping = shoppingCart.totalShipping;
		this.totalWeight = shoppingCart.totalWeight;
	}

	@Override
	public String toString() {
		return "ShoppingCart [id=" + id + ", totalCost=" + totalCost + ", totalShipping=" + totalShipping
				+ ", totalWeight=" + totalWeight + ", items=" + items + "]";
	}


	
}
