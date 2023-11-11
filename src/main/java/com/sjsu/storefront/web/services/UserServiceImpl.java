package com.sjsu.storefront.web.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjsu.storefront.common.DuplicateResourceException;
import com.sjsu.storefront.common.NotAuthenticated;
import com.sjsu.storefront.data.model.Address;
import com.sjsu.storefront.data.model.ShoppingCart;
import com.sjsu.storefront.data.model.User;
import com.sjsu.storefront.data.respository.ShoppingCartRepository;
import com.sjsu.storefront.data.respository.UserRepository;
import com.sjsu.storefront.web.UserSession;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	  
	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	  

	@Transactional
	@Override
	public void createUser(User user) throws DuplicateResourceException {
		
		Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
		if (existingUser.isPresent()) {
			throw new DuplicateResourceException("User with email already Exists");
		}
		
		ShoppingCart cart = new ShoppingCart();
		shoppingCartRepository.save(cart);
		userRepository.save(user);
	}

	@Override
	public UserSession login(String email, String password) throws NotAuthenticated {
	      Optional<User> existingUser = userRepository.findByEmail(email);
	      if (existingUser.isPresent() && existingUser.get().getPassword().equals(password)) {
	    	// Create a UserSession object
	          UserSession userSession = new UserSession();
	          userSession.setUserId(existingUser.get().getId());
	          userSession.setEmail(existingUser.get().getEmail());
	          userSession.setUserType(existingUser.get().getUserType());
	          return userSession;
	      }
	      else {
	    	  throw new NotAuthenticated("Login Failed");
	      }
	}

	@Override
	public User findUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public List<User> getAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	@Transactional
	@Override
	public void deleteUser(Long userId) {
		User existingUser = userRepository.findById(userId).orElse(null);
		if(existingUser == null) {
		   throw new EntityNotFoundException("User Not found");
		}
		ShoppingCart cart = existingUser.getCart();
		shoppingCartRepository.delete(cart);
		userRepository.deleteById(userId);
	}

	@Override
	public ShoppingCart getUserCart(Long userId) {
	   User user = userRepository.findById(userId).orElse(null);
	   if (user == null) {
		   throw new EntityNotFoundException("User Not found");
	   }
	   return user.getCart();
	}

	@Override
	public User updateUser(Long userId, User user) {
	      User existingUser = userRepository.findById(userId).orElse(null);
	      if (existingUser == null) {
	    	  throw new EntityNotFoundException("User Not found");
	      }
	      existingUser.set(user);
	      return userRepository.save(existingUser);
	}

	@Override
	public void updateAddress(Long userId, Address address) {
		User existingUser = userRepository.findById(userId).orElse(null);
		if (existingUser == null) {
			throw new EntityNotFoundException("User Not found");
		}
		existingUser.setAddress(address);
		userRepository.save(existingUser);
	}

}
