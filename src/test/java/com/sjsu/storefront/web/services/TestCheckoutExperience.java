package com.sjsu.storefront.web.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.common.WorkflowException;
import com.sjsu.storefront.data.model.DTO.CartItemDTO;
import com.sjsu.storefront.data.model.DTO.OrderDTO;
import com.sjsu.storefront.data.model.DTO.ProductDTO;
import com.sjsu.storefront.data.model.DTO.UserDTO;

@SpringBootTest
@Transactional
public class TestCheckoutExperience {
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private OrderService orderService;
    
    @Test
    public void testCheckout() {
    	
    	//get a user
    	List<UserDTO> users = userService.getAllUsers();
    	
    	//select a user
    	UserDTO user = users.get(0);
    	
    	//add items to cart and checkout
    	addItemsToUserCart(user);
    	
    	//checkout cart for user
    	try {
			userService.checkOut(user.getId());
		} catch (WorkflowException | ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//add items to cart and checkout
    	addItemsToUserCart(user);
    	
    	//checkout cart for user
    	try {
			userService.checkOut(user.getId());
		} catch (WorkflowException | ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//get the orders for the user
    	List<OrderDTO> orders = orderService.getAllOrdersForUser(user.getId());
    	assertEquals(2, orders.size());
    }
    
    private void addItemsToUserCart(UserDTO user) {
    	//get all available products
    	List<ProductDTO> products = productService.getAllProducts();
    	
    	//create couple of CartItemDTOs
    	CartItemDTO cartItem1 = createRandomCartItem(products,3);
    	CartItemDTO cartItem2 = createRandomCartItem(products,2);
    	
    	try {
			userService.addItemToUserCart(user.getId(), cartItem1);
		} catch (ResourceNotFoundException | WorkflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			userService.addItemToUserCart(user.getId(), cartItem2);
		} catch (ResourceNotFoundException | WorkflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private CartItemDTO createRandomCartItem(List<ProductDTO> products, int quantity) {
    	
    	int numProducts = products.size();
    	
    	//create a random number between 0 and numProducts
    	// Generate a random integer between 0 (inclusive) and maxValue (exclusive)

        Random random = new Random();
        int productIndex = random.nextInt(numProducts);
        
    	//pick a random Product from the list

    	ProductDTO prod = products.get(productIndex);
    	
    	CartItemDTO cartItem = new CartItemDTO();
    	cartItem.setProductId(prod.getId());
    	cartItem.setQuantity(quantity);
    	
    	return cartItem;
    }
}
