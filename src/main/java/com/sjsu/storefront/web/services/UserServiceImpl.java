package com.sjsu.storefront.web.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjsu.storefront.common.DuplicateResourceException;
import com.sjsu.storefront.common.NotAuthenticated;
import com.sjsu.storefront.common.OrderStatus;
import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.common.WorkflowException;
import com.sjsu.storefront.data.model.Address;
import com.sjsu.storefront.data.model.CartItem;
import com.sjsu.storefront.data.model.Order;
import com.sjsu.storefront.data.model.PaymentInfo;
import com.sjsu.storefront.data.model.ShoppingCart;
import com.sjsu.storefront.data.model.User;
import com.sjsu.storefront.data.model.DTO.CartItemDTO;
import com.sjsu.storefront.data.respository.OrderRepository;
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
	OrderRepository orderRepository;
	  
	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	CartService cartService;
	  

	@Transactional
	@Override
	public void createUser(User user) throws DuplicateResourceException {
		
		Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
		if (existingUser.isPresent()) {
			throw new DuplicateResourceException("User with email already Exists");
		}
		
		ShoppingCart cart = new ShoppingCart();
		cart.setUser(user);
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
	   ShoppingCart cart = shoppingCartRepository.findByUser(user);
	   if (cart == null) {
		   throw new EntityNotFoundException("Shopping Cart Not found");
	   }
	   return cart;
	}

	@Transactional
	@Override
	public User updateUser(Long userId, User user) {
	      User existingUser = userRepository.findById(userId).orElse(null);
	      if (existingUser == null) {
	    	  throw new EntityNotFoundException("User Not found");
	      }
	      existingUser.set(user);
	      return userRepository.save(existingUser);
	}

	@Transactional
	@Override
	public void updateAddress(Long userId, Address address) {
		User existingUser = userRepository.findById(userId).orElse(null);
		if (existingUser == null) {
			throw new EntityNotFoundException("User Not found");
		}
		existingUser.setAddress(address);
		userRepository.save(existingUser);
	}

	@Transactional
	@Override
	public Order checkOut(Long userId) throws WorkflowException, ResourceNotFoundException {
		User existingUser = userRepository.findById(userId).orElse(null);
		if (existingUser == null) {
			throw new ResourceNotFoundException("User Not found");
		}
		//get the users cart
		ShoppingCart cart = getUserCart(userId);
		
		List<CartItem> itemsInCart = cart.getItems();
		if(itemsInCart.size() > 0) { 
			//create Order from Cart
			Order order = new Order();
			
			order.setUser(existingUser);
			order.setPaymentInfo(existingUser.getPayment_info());
			order.setShippingAddress(existingUser.getAddress());
			order.setStatus(OrderStatus.RECIEVED);
			order.setTotalCost(cart.getTotalCost());
			order.setTotalProductCost(cart.getTotalProductCost());
			order.setTotalShipping(cart.getTotalShipping());
			order.setTotalWeight(cart.getTotalWeight());
			order.setItems(itemsInCart);
			order.setUser(existingUser); //add the order to the user
			
			order.setShippingAddress(existingUser.getAddress());
			order.setPaymentInfo(existingUser.getPayment_info());
			
			//create the Order
			Order savedOrder =  orderRepository.save(order);
			
			//Empty user's Shopping Cart
			cart.emptyCart();
			shoppingCartRepository.save(cart);
			
			return savedOrder;
			
		}
		else {
			throw new WorkflowException("Cart is Empty, order can't be created");
		}
	}

	@Transactional
	@Override
	public Address addAddress(Long userId, Address address) throws WorkflowException, ResourceNotFoundException {
		
		User existingUser = userRepository.findById(userId).orElse(null);
		if (existingUser == null) {
			throw new ResourceNotFoundException("User Not found");
		}
		
		if(existingUser.getAddress() != null) {
			throw new WorkflowException("User already has address.");
		}
		
		existingUser.setAddress(address);
		userRepository.save(existingUser);
		return existingUser.getAddress();
	}
	
	@Transactional
	@Override
	public PaymentInfo addPaymentInfo(Long userId, PaymentInfo paymentInfo) throws WorkflowException, ResourceNotFoundException {
		
		User existingUser = userRepository.findById(userId).orElse(null);
		if (existingUser == null) {
			throw new ResourceNotFoundException("User Not found");
		}
		
		if(existingUser.getPayment_info() != null) {
			throw new WorkflowException("User already has Payment method setup.");
		}
		
		existingUser.setPayment_info(paymentInfo);
		userRepository.save(existingUser);
		return existingUser.getPayment_info();
	}

	@Override
	public ShoppingCart addItemToUserCart(Long userId, CartItemDTO item) throws ResourceNotFoundException, WorkflowException {
		ShoppingCart cart = getUserCart(userId);
		if (cart == null) {
			throw new WorkflowException("User's cart is not found, this is weird");
		}
		return cartService.addItemIntoCart(cart.getId(), item);
	}

}
