package com.sjsu.storefront.data.model.DTO;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.storefront.data.model.CartItem;
import com.sjsu.storefront.data.model.ShoppingCart;


public class ShoppingCartDTO {
	
	private long shoppingCartId;
	private double totalCost;
	private double totalShipping; 
	private double totalWeight; 
	private double totalProductCost;
	
	
    private List<CartItemDetailDTO> items = new ArrayList<CartItemDetailDTO>();
    
    private Long user_id;

    public ShoppingCartDTO()
    {
    	
    }
    
	
	public ShoppingCartDTO(long id, double totalCost, double totalShipping, double totalWeight, double totalProductCost,
			List<CartItemDetailDTO> items, Long user_id) {
		super();
		this.shoppingCartId = id;
		this.totalCost = totalCost;
		this.totalShipping = totalShipping;
		this.totalWeight = totalWeight;
		this.totalProductCost = totalProductCost;
		this.items = items;
		this.user_id = user_id;
	}
	
	public ShoppingCartDTO(ShoppingCart updatedCart) {
		this.shoppingCartId = updatedCart.getId();
		this.totalCost = updatedCart.getTotalCost();
		this.totalShipping = updatedCart.getTotalShipping();
		this.totalWeight = updatedCart.getTotalWeight();
		this.totalProductCost = updatedCart.getTotalProductCost();
		for(CartItem cItem : updatedCart.getItems()) {
			this.items.add(new CartItemDetailDTO(cItem));
		}
		this.user_id = updatedCart.getUser().getId();
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

	public List<CartItemDetailDTO> getItems() {
		return items;
	}

	public void setItems(List<CartItemDetailDTO> items) {
		this.items = items;
	}

	public long getId() {
		return shoppingCartId;
	}
	
	
	public double getTotalProductCost() {
		return totalProductCost;
	}

	public void setTotalProductCost(double totalProductCost) {
		this.totalProductCost = totalProductCost;
	}

	public void set(ShoppingCartDTO shoppingCart) {
		this.items = shoppingCart.items;
		this.totalCost = shoppingCart.totalCost;
		this.totalShipping = shoppingCart.totalShipping;
		this.totalWeight = shoppingCart.totalWeight;
		this.totalProductCost = shoppingCart.totalProductCost;
	}

	public Long getUser_Id() {
		return user_id;
	}


	public void setUser(Long user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "ShoppingCart [id=" + shoppingCartId + ", totalCost=" + totalCost + ", totalShipping=" + totalShipping
				+ ", totalWeight=" + totalWeight + ", totalProductCost=" + totalProductCost + ", items=" + items
				+ ", user_id=" + user_id + "]";
	}
	
}
