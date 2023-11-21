package com.sjsu.storefront.web.services;

import java.util.List;

import com.sjsu.storefront.common.DuplicateResourceException;
import com.sjsu.storefront.common.NotAuthenticated;
import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.common.WorkflowException;
import com.sjsu.storefront.data.model.ShoppingCart;
import com.sjsu.storefront.data.model.DTO.AddressDTO;
import com.sjsu.storefront.data.model.DTO.CartItemDTO;
import com.sjsu.storefront.data.model.DTO.MeDTO;
import com.sjsu.storefront.data.model.DTO.OrderDTO;
import com.sjsu.storefront.data.model.DTO.PaymentInfoDTO;
import com.sjsu.storefront.data.model.DTO.UserDTO;
import com.sjsu.storefront.web.UserSession;

public interface UserService {
	
	public UserSession login(String email, String password) throws NotAuthenticated;
	public UserDTO findUserById(Long id);
	public MeDTO findMe(Long id);
	public List<UserDTO> getAllUsers();
	public void deleteUser(Long userId);
	
	public ShoppingCart getUserCart(Long userId);
	public OrderDTO checkOut(Long userId) throws WorkflowException, ResourceNotFoundException;
	
	public ShoppingCart addItemToUserCart(Long userId, CartItemDTO item) throws ResourceNotFoundException, WorkflowException;

	public void createUser(UserDTO userDTO) throws DuplicateResourceException;
	public UserDTO updateUser(Long userId, UserDTO user);
	public void updateAddress(Long userId, AddressDTO address);
	public AddressDTO addAddress(Long userId, AddressDTO address) throws WorkflowException, ResourceNotFoundException;
	public PaymentInfoDTO addPaymentInfo(Long userId, PaymentInfoDTO paymentInfo)
			throws WorkflowException, ResourceNotFoundException;

}
