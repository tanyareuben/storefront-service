package com.sjsu.storefront.data.initialization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sjsu.storefront.common.CardType;
import com.sjsu.storefront.common.DuplicateResourceException;
import com.sjsu.storefront.common.ProductCategory;
import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.common.UserType;
import com.sjsu.storefront.common.WorkflowException;
import com.sjsu.storefront.data.model.Product;
import com.sjsu.storefront.data.model.DTO.AddressDTO;
import com.sjsu.storefront.data.model.DTO.PaymentInfoDTO;
import com.sjsu.storefront.data.model.DTO.UserDTO;
import com.sjsu.storefront.web.services.ProductService;
import com.sjsu.storefront.web.services.UserService;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitialization {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    
    @PostConstruct
    public void initializeData() {
       
    	createSomeUsers();
    	addAddressfoForAllusers();
    	addPaymentInfoForAllusers();
    	
    	createSomeProductsAndCategories();

    }
    
    private void addAddressfoForAllusers() {
		
		AddressDTO addr = new AddressDTO();
		addr.setStreet("123 abc street");
		addr.setCity("San Jose");
		addr.setState("CA");
		addr.setZipCode("95112");
		addr.setCountry("USA");
		
    	//get all existing users
    	List<UserDTO> users = userService.getAllUsers();
    	
    	//add address to all users
    	for(UserDTO user : users) {
    		try {
				userService.addAddress(user.getId(), addr);
			} catch (WorkflowException e) {
				//ignore
			} catch (ResourceNotFoundException e) {
				//ignore
			}
    	}
		
	}
    
    private void addPaymentInfoForAllusers() {
    	
    	PaymentInfoDTO paymentInfo = new PaymentInfoDTO();
    	paymentInfo.setCardNumber("1111 1111 1111 1111");
    	paymentInfo.setCardType(CardType.VISA);
    	paymentInfo.setCVV("055");
    	paymentInfo.setExpiry("07/27");
    	paymentInfo.setNickName("Chase Visa");
    	
    	//get all existing users
    	List<UserDTO> users = userService.getAllUsers();
    	
    	//add address to all users
    	for(UserDTO user : users) {
    		try {
				userService.addPaymentInfo(user.getId(), paymentInfo);
			} catch (WorkflowException e) {
				//ignore
			} catch (ResourceNotFoundException e) {
				//ignore
			}
    	}
    }

	public void createSomeUsers() {

    	UserDTO adminUser = 	new UserDTO();
        adminUser.setFirstName("Roxie");
        adminUser.setLastName("john");
        adminUser.setEmail("roxie@john.com");
        adminUser.setPassword("password1");
        adminUser.setPhone("1234567890");
        adminUser.setUserType(UserType.ADMIN);
        
    	UserDTO superUser = 	new UserDTO();
        superUser.setFirstName("kevin");
        superUser.setLastName("bacon");
        superUser.setEmail("kevin@bacon.com");
        superUser.setPassword("password1");
        superUser.setPhone("1234567891");
        superUser.setUserType(UserType.SUPER);
        
    	UserDTO normalUser = 	new UserDTO();
        normalUser.setFirstName("dua");
        normalUser.setLastName("lipa");
        normalUser.setEmail("dua@lipa.com");
        normalUser.setPassword("password1");
        normalUser.setPhone("1234567891");
        normalUser.setUserType(UserType.USER);
        
        try {
			userService.createUser(adminUser);
        } catch (DuplicateResourceException e) {
			//e.printStackTrace();
		}
	    try {
	    	userService.createUser(normalUser);
	
	    } catch (DuplicateResourceException e) {
			//e.printStackTrace();
		}

    }
    
    public void createSomeProductsAndCategories() {
    	
    	//create some fruits
        Product apple = new Product();
        apple.setName("Apple");
        apple.setDescription("Organic Apple");
        apple.setPrice(1.89);
        apple.setWeight(3.5);
        apple.setQuantityInStock(100);
        apple.setProductCategory(ProductCategory.FRUIT);
        
        Product pear = new Product();
        pear.setName("Pear");
        pear.setDescription("Organic Pear");
        pear.setPrice(1.10);
        pear.setWeight(5.5);
        pear.setQuantityInStock(100);
        pear.setProductCategory(ProductCategory.FRUIT);
        
        //create some Vegitables
        Product carrot = new Product();
        carrot.setName("Carrot");
        carrot.setDescription("Organic Carrot");
        carrot.setPrice(1.99);
        carrot.setWeight(7.5);
        carrot.setQuantityInStock(175);
        carrot.setProductCategory(ProductCategory.VEGETABLE);  
        

        Product cabbage = new Product();
        cabbage.setName("Cabbage");
        cabbage.setDescription("Organic Cabbage");
        cabbage.setPrice(1.98);
        cabbage.setWeight(2.5);
        cabbage.setQuantityInStock(200);
        cabbage.setProductCategory(ProductCategory.VEGETABLE); 
        
	    try {
	    	productService.createProduct(apple);
	
	    } catch (DuplicateResourceException e) {
			//e.printStackTrace();
		}
	    try {
	        productService.createProduct(pear);
	
	    } catch (DuplicateResourceException e) {
			//e.printStackTrace();
		}
	    try {
	        productService.createProduct(carrot);
	
	    } catch (DuplicateResourceException e) {
			//e.printStackTrace();
		}
	    try {
	        productService.createProduct(cabbage);
	
	    } catch (DuplicateResourceException e) {
			//e.printStackTrace();
		}
    }

}

