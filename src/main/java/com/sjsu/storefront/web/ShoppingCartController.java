package com.sjsu.storefront.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.sjsu.storefront.data.model.Item;
import com.sjsu.storefront.data.model.ShoppingCart;
import com.sjsu.storefront.data.respository.ItemRepository;
import com.sjsu.storefront.data.respository.ShoppingCartRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/carts")
public class ShoppingCartController {

	  @Autowired
	  ShoppingCartRepository shoppingCartRepository;
	  
	  @Autowired
	  private ItemRepository itemRepository;
	  
	  @Autowired
	  private ObjectMapper objectMapper;
	  
	  @Operation(summary = "Get a shopping cart by id")
	  @GetMapping("/{id}")
	  public ResponseEntity<ShoppingCart> getShoppingCartById(@PathVariable Long id) {
	      ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElse(null);
	      if (shoppingCart == null) {
	          return ResponseEntity.notFound().build();
	      }
	      return ResponseEntity.ok(shoppingCart);
	  }
	  
	  @Operation(summary = "Create a NEW Shopping Cart in the system")
	  @PostMapping
	  public ResponseEntity<ShoppingCart> createCart(@RequestBody ShoppingCart shoppingCart) {
		  //log.info("Creating item : " + item);
		  
	      shoppingCartRepository.save(shoppingCart);
	      return ResponseEntity.created(null).body(shoppingCart);
	  }
	  
	  @Operation(summary = "Delete a cart in the system given Shopping Cart's id")
	  @DeleteMapping("/{id}")
	  public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
		  shoppingCartRepository.deleteById(id);
	      return ResponseEntity.noContent().build();
	  }
	  
	  @Operation(summary = "Update a cart given Cart's id, the whole Cart object needs to be passed in the request")
	  @PutMapping("/{id}")
	  public ResponseEntity<ShoppingCart> updateCart(@PathVariable Long id, @RequestBody ShoppingCart shoppingCart) {
	      ShoppingCart existingCart = shoppingCartRepository.findById(id).orElse(null);
	      if (existingCart == null) {
	          return ResponseEntity.notFound().build();
	      }
	      existingCart.set(shoppingCart);
	      shoppingCartRepository.save(existingCart);
	      return ResponseEntity.ok(existingCart);
	  }
	  
	  @Operation(summary = "Upadate an Cart, given partial data in the Request")
	  @PatchMapping("/{id}")
	  public ResponseEntity<ShoppingCart> patchUser(@PathVariable Long id, @RequestBody JsonPatch patch) {
	      ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElse(null);
	      if (shoppingCart == null) {
	          return ResponseEntity.notFound().build();
	      }

	   // Apply the JSON patch to the user object
	  	try {
	  		
	      JsonNode cartNode = objectMapper.valueToTree(shoppingCart);
	      JsonNode patchedNode = patch.apply(cartNode);
	      ShoppingCart patchedShoppingCart;
	      patchedShoppingCart = objectMapper.treeToValue(patchedNode, ShoppingCart.class);

	      // Save the updated user object to the database
	      shoppingCartRepository.save(patchedShoppingCart);

	      // Return the updated user object
	      return ResponseEntity.ok(patchedShoppingCart);
	      
	  	} catch (JsonProcessingException | IllegalArgumentException e) {
	  		e.printStackTrace();
	  		return ResponseEntity.badRequest().build();
		} catch (JsonPatchException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		} 
	  }
	
	
}
