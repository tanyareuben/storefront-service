package com.sjsu.storefront.web.services;

import java.util.List;

import com.sjsu.storefront.common.DuplicateResourceException;
import com.sjsu.storefront.common.NotAuthenticated;
import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.common.WorkflowException;
import com.sjsu.storefront.data.model.Address;
import com.sjsu.storefront.data.model.Order;
import com.sjsu.storefront.data.model.ShoppingCart;
import com.sjsu.storefront.data.model.User;
import com.sjsu.storefront.web.UserSession;

public interface UserService {
	
	public void createUser(User user) throws DuplicateResourceException;
	
	public UserSession login(String email, String password) throws NotAuthenticated;
	public User findUserById(Long id);
	public List<User> getAllUsers();
	public void deleteUser(Long userId);
	
	public ShoppingCart getUserCart(Long userId);
	public Order checkOut(Long userId) throws WorkflowException, ResourceNotFoundException;
	
	public User updateUser(Long userId, User user);
	public void updateAddress(Long userId, Address address);

}
