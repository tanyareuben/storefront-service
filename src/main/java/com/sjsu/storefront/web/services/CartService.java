package com.sjsu.storefront.web.services;

import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.data.model.CartItem;
import com.sjsu.storefront.data.model.ShoppingCart;

public interface CartService {

	public ShoppingCart addItemIntoCart(Long cartId, CartItem item) throws ResourceNotFoundException;
	public ShoppingCart updateItemInCart(Long cartId, Long cartItemId, CartItem item) throws ResourceNotFoundException, Exception;
	public ShoppingCart deleteItemFromCart(Long cartId, Long cartItemId) throws ResourceNotFoundException, Exception;
	public ShoppingCart getShoppingCartById(Long id);

}
