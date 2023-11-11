package com.sjsu.storefront.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.sjsu.storefront.data.model.CartItem;
import com.sjsu.storefront.data.model.ShoppingCart;
import com.sjsu.storefront.data.respository.ShoppingCartRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/carts")
public class ShoppingCartController {

	  @Autowired
	  ShoppingCartRepository shoppingCartRepository;
	  	  
	  @Autowired
	  private ObjectMapper objectMapper;
	  
	  @Operation(summary = "Get a shopping cart by id")
	  @GetMapping("/{id}")
	  public ResponseEntity<ShoppingCart> getShoppingCartById(@PathVariable Long id) {
		  //TODO add auth check SameUser
	      ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElse(null);
	      if (shoppingCart == null) {
	          return ResponseEntity.notFound().build();
	      }
	      return ResponseEntity.ok(shoppingCart);
	  }
	  
	  @Operation(summary = "Update a cart Items given Cart's id, the List of CartItems needs to be passed in the request")
	  @PutMapping("/{id}/items/{itemId}")
	  public ResponseEntity<String> updateCartItem(@PathVariable Long id, @RequestBody CartItem item) {
		//TODO add auth check SameUser
	      ShoppingCart existingCart = shoppingCartRepository.findById(id).orElse(null);
	      if (existingCart == null) {
	          return ResponseEntity.notFound().build();
	      }
	      try {
				existingCart.updateCart(item);
				shoppingCartRepository.save(existingCart);
				return ResponseEntity.ok("Cart Updated Successfully");
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().build();
			}

	  }
	  
	  //TODO add auth check SameUser
	  @Operation(summary = "Update a cart Items given Cart's id, the List of CartItems needs to be passed in the request")
	  @DeleteMapping("/{id}/items/{itemId}")
	  public ResponseEntity<String> deleteCartItem(@PathVariable Long id, @RequestBody Long itemId) {
	      ShoppingCart existingCart = shoppingCartRepository.findById(id).orElse(null);
	      if (existingCart == null) {
	          return ResponseEntity.notFound().build();
	      }
	      try {
				existingCart.deleteItem(itemId);
				shoppingCartRepository.save(existingCart);
				return ResponseEntity.ok("Cart Updated Successfully");
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().build();
			}

	  }
}
