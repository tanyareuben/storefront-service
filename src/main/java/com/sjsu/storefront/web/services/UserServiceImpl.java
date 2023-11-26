package com.sjsu.storefront.web.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjsu.storefront.common.DuplicateResourceException;
import com.sjsu.storefront.common.NotAuthenticated;
import com.sjsu.storefront.common.OrderStatus;
import com.sjsu.storefront.common.PasswordEncrypt;
import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.common.WorkflowException;
import com.sjsu.storefront.data.model.Address;
import com.sjsu.storefront.data.model.CartItem;
import com.sjsu.storefront.data.model.PaymentInfo;
import com.sjsu.storefront.data.model.ShoppingCart;
import com.sjsu.storefront.data.model.User;
import com.sjsu.storefront.data.model.Order;
import com.sjsu.storefront.data.model.DTO.AddressDTO;
import com.sjsu.storefront.data.model.DTO.CartItemDTO;
import com.sjsu.storefront.data.model.DTO.MeDTO;
import com.sjsu.storefront.data.model.DTO.OrderDTO;
import com.sjsu.storefront.data.model.DTO.PaymentInfoDTO;
import com.sjsu.storefront.data.model.DTO.ShoppingCartDTO;
import com.sjsu.storefront.data.model.DTO.UserDTO;
import com.sjsu.storefront.data.respository.ShoppingCartRepository;
import com.sjsu.storefront.data.respository.OrderRepository;
import com.sjsu.storefront.data.respository.UserRepository;
import com.sjsu.storefront.web.UserSession;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
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
	

    @PersistenceContext
    private EntityManager entityManager;
	  

	@Transactional
	@Override
	public void createUser(UserDTO userDTO) throws DuplicateResourceException {
		
		Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
		if (existingUser.isPresent()) {
			throw new DuplicateResourceException("User with email already Exists");
		}
		//encrypt user password before saving
		userDTO.setPassword(PasswordEncrypt.hashPassword(userDTO.getPassword()));
		User user = new User(userDTO);
		ShoppingCart cart = new ShoppingCart();
		cart.setUser(user);
		shoppingCartRepository.save(cart);
		userRepository.save(user);
	}

	@Override
	public UserSession login(String email, String password) throws NotAuthenticated {
	      Optional<User> existingUser = userRepository.findByEmail(email);
	      if (existingUser.isPresent()) {
	    	  //check the encrypted password is same as the one provided
	    	  if(PasswordEncrypt.checkPassword(password, existingUser.get().getPassword())) {
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
	      throw new NotAuthenticated("User password combination not found");
	}

	@Override
	public UserDTO findUserById(Long id) {
		UserDTO usr;
		User user = userRepository.findById(id).orElse(null);
		if(user != null) {
			usr = new UserDTO(user);
		}
		else {
			usr = null;
		}
		return usr;
	}
	
	@Override
	public MeDTO findMe(Long id) {
		MeDTO usr;
		User user = userRepository.findById(id).orElse(null);
		if(user != null) {
			usr = new MeDTO(user);
		}
		else {
			usr = null;
		}
		return usr;
	}


	@Override
	public List<UserDTO> getAllUsers() {
		List<UserDTO> usrs = new ArrayList<UserDTO>();
		List<User> users =  (List<User>) userRepository.findAll();
		for (User user : users) {
			UserDTO usrDTO = new UserDTO(user);
			usrs.add(usrDTO);
		}
		return usrs;
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
	public UserDTO updateUser(Long userId, UserDTO user) {
	      User existingUser = userRepository.findById(userId).orElse(null);
	      if (existingUser == null) {
	    	  throw new EntityNotFoundException("User Not found");
	      }
	      existingUser.set(user);
	      User savedUser = userRepository.save(existingUser);
	      return new UserDTO(savedUser);
	}

	@Transactional
	@Override
	public void updateAddress(Long userId, AddressDTO address) {
		User existingUser = userRepository.findById(userId).orElse(null);
		if (existingUser == null) {
			throw new EntityNotFoundException("User Not found");
		}
		Address newAddr = new Address(address);
		existingUser.setAddress(newAddr);
		userRepository.save(existingUser);
	}

	@Transactional
	@Override
	public OrderDTO checkOut(Long userId) throws WorkflowException, ResourceNotFoundException {
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
			
			order.setPaymentInfo(existingUser.getPayment_info());
			order.setShippingAddress(existingUser.getAddress());
			order.setStatus(OrderStatus.RECIEVED);
			order.setTotalCost(cart.getTotalCost());
			order.setTotalProductCost(cart.getTotalProductCost());
			order.setTotalShipping(cart.getTotalShipping());
			order.setTotalWeight(cart.getTotalWeight());
			order.setItems(itemsInCart);
			order.setUser(existingUser); //add the order to the user
			
			if(existingUser.getAddress() == null) {
				throw new WorkflowException("User do not have an address on file to ship the Orders to");
			}
			
			order.setShippingAddress(existingUser.getAddress());

			if(existingUser.getPayment_info() == null) {
				throw new WorkflowException("User do not have a Payment Info setup.");
			}
			order.setPaymentInfo(existingUser.getPayment_info());
//FIXME - probably the merge code below is not needed			
			//create the Order
			Order savedOrder =  orderRepository.save(order);
			
//			UserOrder savedOrder;
//			
//			if (entityManager.contains(order)) {
//			    // The entity is managed (not detached)
//			    savedOrder = orderRepository.save(order);
//			} else {
//			    // The entity is detached, handle accordingly (e.g., merge or reattach)
//			    UserOrder mergedOrder = entityManager.merge(order);
//		        // Now, iterate over the items and merge them as well
//		        for (OrderItem item : mergedOrder.getItems()) {
//		            if (!entityManager.contains(item)) {
//		                // The item is detached, merge it
//		                OrderItem mergedItem = entityManager.merge(item);
//		            }
//		        }
//			    savedOrder = orderRepository.save(mergedOrder);
//		        return new OrderDTO(savedOrder);
//			}
			
			//Empty user's Shopping Cart
			cart.emptyCart();

			return new OrderDTO(savedOrder);
			
		}
		else {
			throw new WorkflowException("Cart is Empty, order can't be created");
		}
	}

	@Transactional
	@Override
	public AddressDTO addAddress(Long userId, AddressDTO address) throws WorkflowException, ResourceNotFoundException {
		
		User existingUser = userRepository.findById(userId).orElse(null);
		if (existingUser == null) {
			throw new ResourceNotFoundException("User Not found");
		}
		
		if(existingUser.getAddress() != null) {
			throw new WorkflowException("User already has address.");
		}
		Address addr = new Address(address);
		addr.setUser(existingUser);
		existingUser.setAddress(addr);
		userRepository.save(existingUser);
		return new AddressDTO(existingUser.getAddress());
	}
	
	@Transactional
	@Override
	public PaymentInfoDTO addPaymentInfo(Long userId, PaymentInfoDTO paymentInfo) throws WorkflowException, ResourceNotFoundException {
		
		User existingUser = userRepository.findById(userId).orElse(null);
		if (existingUser == null) {
			throw new ResourceNotFoundException("User Not found");
		}
		
		if(existingUser.getPayment_info() != null) {
			throw new WorkflowException("User already has Payment method setup.");
		}
		PaymentInfo pInfo = new PaymentInfo(paymentInfo);
		existingUser.setPayment_info(pInfo);
		userRepository.save(existingUser);
		return(new PaymentInfoDTO(existingUser.getPayment_info()));
	}

	@Override
	public ShoppingCartDTO addItemToUserCart(Long userId, CartItemDTO item) throws ResourceNotFoundException, WorkflowException {
		ShoppingCart cart = getUserCart(userId);
		if (cart == null) {
			throw new WorkflowException("User's cart is not found, this is weird");
		}
		ShoppingCart updatedCart = cartService.addItemIntoCart(cart.getId(), item);
		return new ShoppingCartDTO(updatedCart);
	}

}
