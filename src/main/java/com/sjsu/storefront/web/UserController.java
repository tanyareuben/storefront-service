package com.sjsu.storefront.web;

import java.util.List;

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
import com.sjsu.storefront.data.model.Address;
import com.sjsu.storefront.data.model.User;
import com.sjsu.storefront.data.respository.AddressRepository;
import com.sjsu.storefront.data.respository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  UserRepository userRepository;
  
  @Autowired
  private AddressRepository addressRepository;
  
  @Autowired
  private ObjectMapper objectMapper;
 
  @Operation(summary = "Get all users in the system")
  @GetMapping
  public List<User> getAllUsers() {
      return (List<User>) userRepository.findAll();
  }
  
  @Operation(summary = "Get a User by id")
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
      User user = userRepository.findById(id).orElse(null);
      if (user == null) {
          return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(user);
  }
  
  @Operation(summary = "Create a NEW User in the system")
  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
      Address address = user.getAddress();
      addressRepository.save(address);
      user.setAddress(address);
      User createdUser = userRepository.save(user);
      return ResponseEntity.created(null).body(createdUser);
  }
  
  @Operation(summary = "Delete a user in the system given User's id")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
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
  
  @Operation(summary = "Upadate a User, given partial data in the Request")
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
  
}
