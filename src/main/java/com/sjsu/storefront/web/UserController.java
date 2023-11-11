package com.sjsu.storefront.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjsu.storefront.common.AuthNCheck;
import com.sjsu.storefront.common.AuthZCheck;
import com.sjsu.storefront.common.DuplicateResourceException;
import com.sjsu.storefront.common.NotAuthenticated;
import com.sjsu.storefront.common.OrderStatus;
import com.sjsu.storefront.data.model.Address;
import com.sjsu.storefront.data.model.Order;
import com.sjsu.storefront.data.model.ShoppingCart;
import com.sjsu.storefront.data.model.User;
import com.sjsu.storefront.web.services.OrderService;
import com.sjsu.storefront.web.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController {
	
  @Autowired
  private UserService userService;
  
  @Autowired
  private OrderService orderService;
  
  @Autowired
  private HttpSession httpSession;
  
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
 
  @Operation(summary = "Get all users in the system")
  @AuthZCheck // Apply the AuthAspect to this method
  @GetMapping
  public List<User> getAllUsers() {
      return userService.getAllUsers();
  }
  
  @Operation(summary = "Get a User by id")
  @AuthZCheck // Apply the AuthAspect to this method
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
      User user = userService.findUserById(id);
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
      
      User user = userService.findUserById(userId);
      if (user == null) {
          return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(user);
  }
  
  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody User user) {
	  
	  logger.info("Received a registration request with User: {}", user);
	  try {
		  userService.createUser(user);
		  logger.info("Registration request SUCCESS");
	      return ResponseEntity.ok("User registered successfully");
	  }
	  catch(DuplicateResourceException de) {
		  logger.info("{} already exists", user.getEmail());
          return ResponseEntity.badRequest().body("Username already exists");
	  }  
  }
  
  @PostMapping("/login")
  public ResponseEntity<String> loginUser(@RequestBody User user) {
	  
	  try {
		  UserSession userSession = userService.login(user.getEmail(), user.getPassword());

		  // Store UserSession in HttpSession
          httpSession.setAttribute("userSession", userSession);

          return ResponseEntity.ok("Login successful");
	  }
	  catch(NotAuthenticated ne) {
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
	  
	  try {
		  userService.deleteUser(id);
	      return ResponseEntity.noContent().build();
	  }
	  catch(EntityNotFoundException enf){
		  return ResponseEntity.notFound().build();
	  }  
  }
  
  //TODO SAMEUSER Auth
  @Operation(summary = "Update a user given User's id, the whole User object needs to be passed in the request")
  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
	  
	  try {
		  User updatedUser = userService.updateUser(id, user);
	      return ResponseEntity.ok(updatedUser);
	  }
      catch(EntityNotFoundException enf) {
    	  return ResponseEntity.notFound().build();
      }
  }
  
  //TODO SameUser Auth
  @Operation(summary = "Update a user's Address given User's id, the whole Address object needs to be passed in the request")
  @PutMapping("/{id}/address")
  public ResponseEntity<String> updateUserAddress(@PathVariable Long id, @RequestBody Address address) {
	  try {
		  userService.updateAddress(id, address);
	      return ResponseEntity.ok("Address Updated Successfully");
	  }
      catch(EntityNotFoundException enf) {
    	  return ResponseEntity.notFound().build();
      }
  }
  
  @Operation(summary = "Get the Current User's Shopping Cart")
  @AuthNCheck // Apply the AuthAspect to this method
  @GetMapping("/cart")
  public ResponseEntity<ShoppingCart> getUserShoppingCart() {
      try {
		  UserSession userSession = (UserSession) httpSession.getAttribute("userSession");
	      Long userId = userSession.getUserId();
	      ShoppingCart cart = userService.getUserCart(userId);
	      return ResponseEntity.ok(cart);
      }
      catch(EntityNotFoundException enf) {
    	  return ResponseEntity.notFound().build();
      }
  }
  
  //TODO give an end point for Open Orders, Shipped Orders and Delivered orders with SORT option
  @Operation(summary = "Get the Logged in User's All Orders")
  @AuthNCheck // Apply the AuthAspect to this method
  //TODO SAMEUSER auth check
  @GetMapping("/{userId}/orders/status/{status}")
  public List<Order> getOrdersByStatusForUser(@PathVariable Long userId, @PathVariable OrderStatus status) {
      // Retrieve the user (you might need a UserService for this)
      User user = userService.findUserById(userId);
      return orderService.getOrdersByStatusForUser(user, status);
  }
  
  @PostMapping("/{userId}/orders")
  public Order addOrderForUser(@PathVariable Long userId, @RequestBody Order order) {
      // Retrieve the user (you might need a UserService for this)
      User user = userService.findUserById(userId);

      // Add the order for the user
      return orderService.addOrder(user, order);
  }
  
  //TODO get user by user email (user name)
  //TODO get user base data or include other related entities like Address, Shopping Cart etc.
  //TODO get user by First name or Last Name - Make it Paginated and Fuzzy search
  //TODO add/update a PaymentInfo for a user  users/{id}/payment-info  -- need same user check
  //TODO or do an add or Update Payment info on ME/payment-info (Create and Update). 
}
