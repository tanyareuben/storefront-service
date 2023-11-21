package com.sjsu.storefront.data.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sjsu.storefront.common.DuplicateResourceException;
import com.sjsu.storefront.common.ProductCategory;
import com.sjsu.storefront.common.UserType;
import com.sjsu.storefront.data.model.Product;
import com.sjsu.storefront.data.model.DTO.UserDTO;
import com.sjsu.storefront.web.services.ProductService;
import com.sjsu.storefront.web.services.UserService;

import jakarta.annotation.PostConstruct;

@Component
public class UserInitialization {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    
    @PostConstruct
    public void initializeDat() {
       
    	createSuperUsers();
    	//createSomeProductsAndCategories();

    }
    
    public void createSuperUsers() {

    	UserDTO adminUser = 	new UserDTO();
        adminUser.setFirstName("Roxie");
        adminUser.setLastName("john");
        adminUser.setEmail("roxie@john.com");
        adminUser.setPassword("password1");
        adminUser.setPhone("1234567890");
        adminUser.setUserType(UserType.ADMIN);
        
    	UserDTO normalUser = 	new UserDTO();
        normalUser.setFirstName("kevin");
        normalUser.setLastName("bacon");
        normalUser.setEmail("kevin@bacon.com");
        normalUser.setPassword("password1");
        normalUser.setPhone("1234567891");
        normalUser.setUserType(UserType.SUPER);
        
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
        carrot.setProductCategory(ProductCategory.VEGITABLE);  
        

        Product cabbage = new Product();
        cabbage.setName("Cabbage");
        cabbage.setDescription("Organic Cabbage");
        cabbage.setPrice(1.98);
        cabbage.setWeight(2.5);
        cabbage.setQuantityInStock(200);
        cabbage.setProductCategory(ProductCategory.VEGITABLE); 
        
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

