package com.sjsu.storefront.data.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sjsu.storefront.common.WorkflowException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@JsonIgnoreProperties({"user"})
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private double totalCost;
	private double totalShipping; 
	private double totalWeight; 
	private double totalProductCost;
	
	
	 // Define the one-to-many relationship
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY) //is this line correct with the mappedBy = "item" or should it be something else
    private List<CartItem> items;
    
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public ShoppingCart()
    {
    	
    }
    
	
	public ShoppingCart(long id, double totalCost, double totalShipping, double totalWeight, double totalProductCost,
			List<CartItem> items, User user) {
		super();
		this.id = id;
		this.totalCost = totalCost;
		this.totalShipping = totalShipping;
		this.totalWeight = totalWeight;
		this.totalProductCost = totalProductCost;
		this.items = items;
		this.user = user;
	}
	
	public void emptyCart() {
		this.totalCost = 0.0;
		this.totalProductCost = 0.0;
		this.totalShipping = 0.0;
		this.totalWeight = 0.0;
		
		 if (items != null) {
	       items.clear();
	     }
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

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	public long getId() {
		return id;
	}
	
	
	public double getTotalProductCost() {
		return totalProductCost;
	}

	public void setTotalProductCost(double totalProductCost) {
		this.totalProductCost = totalProductCost;
	}

	public void set(ShoppingCart shoppingCart) {
		this.items = shoppingCart.items;
		this.totalCost = shoppingCart.totalCost;
		this.totalShipping = shoppingCart.totalShipping;
		this.totalWeight = shoppingCart.totalWeight;
		this.totalProductCost = shoppingCart.totalProductCost;
	}

	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	public void addItem(CartItem cartItem) throws WorkflowException {
		boolean bFoundProduct = false;
		for(CartItem item : this.items) {
			if(item.getProduct().getName().compareToIgnoreCase(cartItem.getProduct().getName()) == 0) {
				bFoundProduct = true;
				//just increment the count of the CartItem for that product
				int newQty = item.getQuantity()+ cartItem.getQuantity();
				if(newQty <= item.getProduct().getQuantityInStock()) { //if there is still enough in inventory
					item.setQuantity(newQty);
				}
				else {
					throw new WorkflowException("Not enough Inventory for " + item.getProduct().getName());
				}
			}
		}
		if(!bFoundProduct) {
			this.items.add(cartItem);
		}
		
	}
	
	private boolean doesProductAlreadyExistInCart(CartItem cartItem) {
		

		return true;
	}

	@Override
	public String toString() {
		return "ShoppingCart [id=" + id + ", totalCost=" + totalCost + ", totalShipping=" + totalShipping
				+ ", totalWeight=" + totalWeight + ", totalProductCost=" + totalProductCost + ", items=" + items
				+ ", user=" + user + "]";
	}
	
}
