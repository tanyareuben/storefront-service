package com.sjsu.storefront.web.services;

import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.common.WorkflowException;
import com.sjsu.storefront.data.model.CartItem;
import com.sjsu.storefront.data.model.ShoppingCart;
import com.sjsu.storefront.data.model.DTO.CartItemDTO;
import com.sjsu.storefront.data.model.DTO.ShoppingCartDTO;

public interface CartService {

	public ShoppingCart updateItemInCart(Long cartId, Long cartItemId, CartItem item) throws ResourceNotFoundException, Exception;
	public ShoppingCart deleteItemFromCart(Long cartId, Long cartItemId) throws ResourceNotFoundException, Exception;
	public ShoppingCart getShoppingCartById(Long id);
	public ShoppingCart addItemIntoCart(Long cartId, CartItemDTO item) throws ResourceNotFoundException, WorkflowException;

}
