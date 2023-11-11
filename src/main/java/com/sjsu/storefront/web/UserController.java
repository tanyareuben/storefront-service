package com.sjsu.storefront.web;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.sjsu.storefront.common.AuthNCheck;
import com.sjsu.storefront.common.AuthZCheck;
import com.sjsu.storefront.data.model.Address;
import com.sjsu.storefront.data.model.Order;
import com.sjsu.storefront.data.model.ShoppingCart;
import com.sjsu.storefront.data.model.User;
import com.sjsu.storefront.data.respository.ShoppingCartRepository;
import com.sjsu.storefront.data.respository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  UserRepository userRepository;
  
  
  @Autowired
  ShoppingCartRepository shoppingCartRepository;
  
  @Autowired
  private ObjectMapper objectMapper;
  
  @Autowired
  private HttpSession httpSession;
  
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
 
  @Operation(summary = "Get all users in the system")
  @AuthZCheck // Apply the AuthAspect to this method
  @GetMapping
  public List<User> getAllUsers() {
      return (List<User>) userRepository.findAll();
  }
  
  @Operation(summary = "Get a User by id")
  @AuthZCheck // Apply the AuthAspect to this method
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
      User user = userRepository.findById(id).orElse(null);
      if (user == null) {
          return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(user);
  }
  
  @Operation(summary = "ME - Get currenly logged in users Info")
  @AuthNCheck // Apply the AuthAspect to this method
  @GetMapping("/me")
  public ResponseEntity<User> getUserME() {
      UserSession userSession = (UserSession) httpSession.getAttribute("userSession");

      Long userId = userSession.getUserId();
      
      User user = userRepository.findById(userId).orElse(null);
      if (user == null) {
          return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(user);
  }
  
  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody User user) {
	  
	  logger.info("Received a registration request with User: {}", user);
      Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
      if (existingUser.isPresent()) {
          return ResponseEntity.badRequest().body("Username already exists");
      }

      // You might want to perform password hashing here before saving
      //TODO do the saves in the same transaction
      ShoppingCart cart = new ShoppingCart();
      shoppingCartRepository.save(cart);
      userRepository.save(user);

	  logger.info("Registration request SUCCESS");
      return ResponseEntity.ok("User registered successfully");
  }
  
  @PostMapping("/login")
  public ResponseEntity<String> loginUser(@RequestBody User user) {
	  
      Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
      if (existingUser.isPresent() && existingUser.get().getPassword().equals(user.getPassword())) {
    	// Create a UserSession object
          UserSession userSession = new UserSession();
          userSession.setUserId(existingUser.get().getId());
          userSession.setEmail(existingUser.get().getEmail());
          userSession.setUserType(existingUser.get().getUserType());

          // Store UserSession in HttpSession
          httpSession.setAttribute("userSession", userSession);

          return ResponseEntity.ok("Login successful");
      } else {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
      }
  }
  
  @PostMapping("/logout")
  public ResponseEntity<String> logoutUser() {
      httpSession.invalidate();
      return ResponseEntity.ok("Logout successful");
  }
  
  @Operation(summary = "Delete a user in the system given User's id")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
	  //TODO do everything in a Transaction
	  User existingUser = userRepository.findById(id).orElse(null);
	  if(existingUser == null) {
		  return ResponseEntity.notFound().build();
	  }
	  ShoppingCart cart = existingUser.getCart();
	  shoppingCartRepository.delete(cart);
      userRepository.deleteById(id);
      return ResponseEntity.noContent().build();
  }
  
  @Operation(summary = "Update a user given User's id, the whole User object needs to be passed in the request")
  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
	  
	  
      User existingUser = userRepository.findById(id).orElse(null);
      if (existingUser == null) {
          return ResponseEntity.notFound().build();
      }
      existingUser.set(user);
      userRepository.save(existingUser);
      return ResponseEntity.ok(existingUser);
  }
  
  @Operation(summary = "Update a user's Address given User's id, the whole Address object needs to be passed in the request")
  @PutMapping("/{id}/address")
  public ResponseEntity<String> updateUserAddress(@PathVariable Long id, @RequestBody Address address) {
	  //TODO SameUser Auth
      User existingUser = userRepository.findById(id).orElse(null);
      if (existingUser == null) {
          return ResponseEntity.notFound().build();
      }
      existingUser.setAddress(address);
      userRepository.save(existingUser);
      return ResponseEntity.ok("Address Updated Successfully");
  }
  
  @Operation(summary = "Upadate a User, given partial data in the Request")
  //TODO AuthChecks
  @PatchMapping("/{id}")
  public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody JsonPatch patch) {
      User user = userRepository.findById(id).orElse(null);
      if (user == null) {
          return ResponseEntity.notFound().build();
      }

   // Apply the JSON patch to the user object
  	try {
  		
      JsonNode userNode = objectMapper.valueToTree(user);
      JsonNode patchedNode = patch.apply(userNode);
      User patchedUser;
      patchedUser = objectMapper.treeToValue(patchedNode, User.class);

      // Save the updated user object to the database
      userRepository.save(patchedUser);

      // Return the updated user object
      return ResponseEntity.ok(patchedUser);
      
  	} catch (JsonProcessingException | IllegalArgumentException e) {
  		e.printStackTrace();
  		return ResponseEntity.badRequest().build();
	} catch (JsonPatchException e) {
		e.printStackTrace();
		return ResponseEntity.badRequest().build();
	} 
  }
  
  @Operation(summary = "Get the Logged in User's Shopping Cart")
  @AuthNCheck // Apply the AuthAspect to this method
  @GetMapping("/cart")
  public ResponseEntity<ShoppingCart> getUserShoppingCart() {
      UserSession userSession = (UserSession) httpSession.getAttribute("userSession");

      Long userId = userSession.getUserId();
      
      User user = userRepository.findById(userId).orElse(null);
      if (user == null) {
          return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(user.getCart());
  }
  
  //TODO give an end point for Open Orders, Shipped Orders and Delivered orders with SORT option
  @Operation(summary = "Get the Logged in User's All Orders")
  @AuthNCheck // Apply the AuthAspect to this method
  @GetMapping("/orders")
  public ResponseEntity<List<Order>> getUserOrders() {
      UserSession userSession = (UserSession) httpSession.getAttribute("userSession");

      Long userId = userSession.getUserId();
      
      User user = userRepository.findById(userId).orElse(null);
      if (user == null) {
          return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(user.getOrders());
  }
  
  //TODO get user by user email (user name)
  //TODO get user base data or include other related entities like Address, Shopping Cart etc.
  //TODO get user by First name or Last Name - Make it Paginated and Fuzzy search
  //TODO add/update a PaymentInfo for a user  users/{id}/payment-info  -- need same user check
  //TODO or do an add or Update Payment info on ME/payment-info (Create and Update). 
}
