package com.sjsu.storefront.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.data.model.CartItem;
import com.sjsu.storefront.data.model.ShoppingCart;
import com.sjsu.storefront.data.model.DTO.CartItemDTO;
import com.sjsu.storefront.data.model.DTO.ShoppingCartDTO;
import com.sjsu.storefront.web.services.CartService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/carts")
public class ShoppingCartController {

	  @Autowired
	  CartService cartService;
	  	  
	  
	  //TODO add auth check SameUser
	  @Operation(summary = "Get a shopping cart by id")
	  @GetMapping("/{cartId}")
	  public ResponseEntity<ShoppingCartDTO> getShoppingCartById(@PathVariable Long cartId) {
		  ShoppingCart shoppingCart = cartService.getShoppingCartById(cartId);
	      if (shoppingCart == null) {
	          return ResponseEntity.notFound().build();
	      }
	      return ResponseEntity.ok(new ShoppingCartDTO(shoppingCart));
	  }
	  
	  //TODO add the SameUser
	  @Operation(summary = "Add a CartItem to the Cart given Cart's id")
	  @PostMapping("/{cartId}/items")
	  public ResponseEntity<ShoppingCartDTO> addItemToCart(@PathVariable Long cartId, @RequestBody CartItemDTO item) {

		  try {
			ShoppingCart cart =  cartService.addItemIntoCart(cartId, item);
			return ResponseEntity.ok(new ShoppingCartDTO(cart));
		  } catch (ResourceNotFoundException e) {
			  return ResponseEntity.notFound().build();
		  } catch (Exception e) {
			  return ResponseEntity.badRequest().build();
		  }
	  }	  
	  
	  //TODO add the SameUser
	  @Operation(summary = "Update a cart Items given Cart's id, and the CartItem that need to be updated in the request")
	  @PutMapping("/{cartId}/items/{cartItemId}")
	  public ResponseEntity<ShoppingCart> updateCartItem(@PathVariable Long cartId, @PathVariable Long cartItemId, @RequestBody CartItem item) {

		  try {
			ShoppingCart cart =  cartService.updateItemInCart(cartId, cartItemId, item);
			return ResponseEntity.ok(cart);
		  } catch (ResourceNotFoundException e) {
			  return ResponseEntity.notFound().build();
		  } catch (Exception e) {
			  return ResponseEntity.badRequest().build();
		  }
	  }
	  
	  //TODO add auth check SameUser
	  @Operation(summary = "Update a cart Items given Cart's id, the List of CartItems needs to be passed in the request")
	  @DeleteMapping("/{cartId}/items/{cartItemId}")
	  public ResponseEntity<ShoppingCart> deleteCartItem(@PathVariable Long cartId, @PathVariable Long cartItemId) {
		  try {
			ShoppingCart cart =  cartService.deleteItemFromCart(cartId, cartItemId);
			return ResponseEntity.ok(cart);
		  } catch (ResourceNotFoundException e) {
			  return ResponseEntity.notFound().build();
		  } catch (Exception e) {
			  return ResponseEntity.badRequest().build();
		  }
	  }
}
