package com.sjsu.storefront.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.data.model.CartItem;
import com.sjsu.storefront.data.model.Product;
import com.sjsu.storefront.data.model.ShoppingCart;
import com.sjsu.storefront.data.respository.ShoppingCartRepository;

import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	ShoppingCartRepository shoppingCartRepository;

	@Override
	public ShoppingCart getShoppingCartById(Long id) {
	      return shoppingCartRepository.findById(id).orElse(null);
	}
	
	@Transactional
	@Override
	public ShoppingCart addItemIntoCart(Long cartId, CartItem item) throws ResourceNotFoundException {
	  ShoppingCart existingCart = shoppingCartRepository.findById(cartId).orElse(null);
      if (existingCart != null) {

		existingCart.addItem(item);
		updateShippingAndTotalCost(existingCart);
		return shoppingCartRepository.save(existingCart);
      
      }
      else {
    	throw new ResourceNotFoundException("Cart Not Foiund");
      }
	}

	@Transactional
	@Override
	public ShoppingCart updateItemInCart(Long cartId, Long cartItemId, CartItem item) throws ResourceNotFoundException, Exception {
	  ShoppingCart existingCart = shoppingCartRepository.findById(cartId).orElse(null);
      if (existingCart != null) {

		updateCartWithItem(existingCart, item);
		updateShippingAndTotalCost(existingCart);
		return shoppingCartRepository.save(existingCart);
      
      }
      else {
    	throw new ResourceNotFoundException("Cart Not Foiund");
      }
	}
	
	//Deletes a cartItem from the Shopping cart
	@Transactional
	@Override
	public ShoppingCart deleteItemFromCart(Long cartId, Long cartItemId) throws ResourceNotFoundException, Exception {
	  ShoppingCart existingCart = shoppingCartRepository.findById(cartId).orElse(null);
      if (existingCart != null) {
		
		List<CartItem> cartItems = existingCart.getItems();
		//find the Product in cart
		for( CartItem ci : cartItems) {
			if(ci.getId() == cartItemId) { //If product is already in the Cart
				cartItems.remove(ci);
				updateInventory(ci.getProduct(), ci.getQuantity());
				updateShippingAndTotalCost(existingCart);
				break;
			}
		}
		return shoppingCartRepository.save(existingCart);
      }
      else {
      	throw new ResourceNotFoundException("Cart Not Foiund");
	  }
		
	}

	//update a products quantity in the Shopping Cart
	//it should update the inventory accordingly
	//it should update the Total Weight, Total Cost and Shipping Cost accordingly
	public void updateCartWithItem(ShoppingCart existingCart, CartItem item) throws Exception {
		
		Product product = item.getProduct();
		int qty = item.getQuantity();
		
		CartItem theItem = null;
		List<CartItem> cartItems = existingCart.getItems();
		
		//find the Product in cart
		for( CartItem ci : cartItems) {
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
				cartItems.remove(theItem);
			}
		}
		
		else { //if the Product is not in the cart
			if(qty > 0) {
				updateInventory(product, qty);
				
				//if no exception is thrown from update inventory, then the item is added to the cart
				CartItem ci = new CartItem();
				ci.setProduct(product);
				ci.setQuantity(qty);
				cartItems.add(ci);				
			}
		}		
	}
	
	private void updateInventory(Product product, int quantity) throws Exception {
		
		int ineventoryCount = product.getQuantityInStock() + quantity;
		if(ineventoryCount > 0) {
			product.setQuantityInStock(ineventoryCount); //update inventory
		}
		else {
			throw new Exception("Not enough Inventory to Update the Cart");
		}
		
	}
	
	private void updateShippingAndTotalCost(ShoppingCart existingCart) {
		
		List<CartItem> cartItems = existingCart.getItems();
		
		long totalWeight = 0;
		long totalProductCost = 0;
		
		for( CartItem ci : cartItems) {
			totalWeight += ci.getProduct().getWeight() * ci.getQuantity();		
			totalProductCost += ci.getProduct().getPrice() * ci.getQuantity();
		}
		existingCart.setTotalWeight(totalWeight);
		existingCart.setTotalProductCost(totalProductCost);
		
		if(existingCart.getTotalWeight() < 20.0) {
			existingCart.setTotalShipping(0.0);
		}
		else {
			existingCart.setTotalShipping(5.0);
		}
		
		existingCart.setTotalCost(existingCart.getTotalProductCost() + existingCart.getTotalShipping());
	}
}
