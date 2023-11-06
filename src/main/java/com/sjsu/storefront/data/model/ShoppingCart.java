package com.sjsu.storefront.data.model;

import java.util.List;

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

@Entity
@Table(name = "Shopping Cart")
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
	
	
	//Deletes a cartItem from the Shopping cart
	public void deleteItem(Long id) throws Exception {
		
		//find the Product in cart
		for( CartItem ci : items) {
			if(ci.getId() == id) { //If product is already in the Cart
				items.remove(ci);
				updateInventory(ci.getProduct(), ci.getQuantity());
				updateShippingAndTotalCost();
				break;
			}
		}
		
	}



	//update a products quantity in the Shopping Cart
	//it should update the inventory accordingly
	//it should update the Total Weight, Total Cost and Shipping Cost accordingly
	public void updateCart(CartItem item) throws Exception {
		
		Product product = item.getProduct();
		int qty = item.getQuantity();
		
		CartItem theItem = null;
		
		//find the Product in cart
		for( CartItem ci : items) {
			if(ci.getProduct().equals(product)) { //If product is already in the Cart
				theItem = ci;
				break;
			}
		}

		int origQty = 0;
		int diffInQuantity = 0;
		
		if(theItem != null) {
			origQty = theItem.getQuantity();
			
			if(qty > 0) {
				theItem.setQuantity(qty); //update the quantity
				diffInQuantity = qty - origQty; // + means new quantity is more and the diff. needs to be subtracted from inventory -ve means add the diff to the  inventory
				updateInventory(product, diffInQuantity);
			}
			else { //delete the product from the cart
				updateInventory(product,theItem.getQuantity()); //give back the num items in the cart
				items.remove(theItem);
			}
		}
		
		else { //if the Product is not in the cart
			if(qty > 0) {
				updateInventory(product, qty);
				
				//if no exception is thrown from update inventory, then the item is added to the cart
				CartItem ci = new CartItem();
				ci.setProduct(product);
				ci.setQuantity(qty);
				items.add(ci);				
			}
		}
		
		updateShippingAndTotalCost();
		
	}
	
	private void updateInventory(Product product, int quantity) throws Exception {
		
		int ineventoryCount = product.getQuantityInStore() + quantity;
		if(ineventoryCount > 0) {
			product.setQuantityInStore(ineventoryCount); //update inventory
		}
		else {
			throw new Exception("Not enough Inventory to Update the Cart");
		}
		
	}
	
	private void updateShippingAndTotalCost() {
		
		for( CartItem ci : items) {
			this.totalWeight += ci.getProduct().getWeight() * ci.getQuantity();		
			this.totalProductCost += ci.getProduct().getPrice() * ci.getQuantity();
		}
		this.setTotalWeight(totalWeight);
		
		if(this.totalWeight < 20.0) {
			this.totalShipping = 0.0;
		}
		else {
			this.totalShipping = 5.0;
		}
		
		this.totalProductCost = this.totalProductCost + this.totalShipping;
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



	@Override
	public String toString() {
		return "ShoppingCart [id=" + id + ", totalCost=" + totalCost + ", totalShipping=" + totalShipping
				+ ", totalWeight=" + totalWeight + ", totalProductCost=" + totalProductCost + ", items=" + items
				+ ", user=" + user + "]";
	}
	
}
