package com.sjsu.storefront.data.initialization;

import java.io.InputStream;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjsu.storefront.common.DuplicateResourceException;
import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.common.WorkflowException;
import com.sjsu.storefront.data.model.Product;
import com.sjsu.storefront.data.model.DTO.AddressDTO;
import com.sjsu.storefront.data.model.DTO.PaymentInfoDTO;
import com.sjsu.storefront.data.model.DTO.ProductDTO;
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
    	addProductsToStore();
    }
    
    private void addAddressfoForAllusers() {
		
    	// Specify the path to your products JSON file
        String filePath = "address.json";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
         
            // Use ClassLoader to load the resource from the classpath
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);

            // Use TypeReference to specify the type of the list (List<ProductDTO>)
            List<AddressDTO> addressList = objectMapper.readValue(inputStream, new TypeReference<List<AddressDTO>>() {});

        	//get all existing users
        	List<UserDTO> users = userService.getAllUsers();
        	
        	//add address to all users
        	for(UserDTO user : users) {
        		Random random = new Random();
                int randomNumber = random.nextInt(addressList.size());
                try {
                	userService.addAddress(user.getId(),addressList.get(randomNumber));
                }
                catch (WorkflowException we) {
                }
                catch (ResourceNotFoundException e) {
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    
    private void addPaymentInfoForAllusers() {
    	
    	// Specify the path to your products JSON file
        String filePath = "paymentinfo.json";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
         
            // Use ClassLoader to load the resource from the classpath
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);

            // Use TypeReference to specify the type of the list (List<ProductDTO>)
            List<PaymentInfoDTO> paymentsList = objectMapper.readValue(inputStream, new TypeReference<List<PaymentInfoDTO>>() {});

        	//get all existing users
        	List<UserDTO> users = userService.getAllUsers();
        	
        	//add address to all users
        	for(UserDTO user : users) {
        		Random random = new Random();
                int randomNumber = random.nextInt(paymentsList.size());
                try {
                	userService.addPaymentInfo(user.getId(),paymentsList.get(randomNumber));
                }
                catch (WorkflowException we) {
                }
                catch (ResourceNotFoundException e) {
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void createSomeUsers() {
    	// Specify the path to your products JSON file
        String filePath = "users.json";
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // Use ClassLoader to load the resource from the classpath
            //InputStream inputStream = DataInitialization.class.getClassLoader().getResourceAsStream(filePath);
         
            // Use ClassLoader to load the resource from the classpath
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);

            // Use TypeReference to specify the type of the list (List<ProductDTO>)
            List<UserDTO> userList = objectMapper.readValue(inputStream, new TypeReference<List<UserDTO>>() {});

            // Iterate over the list of ProductDTO objects
            for (UserDTO userDTO : userList) {
                
                try {
                	userService.createUser(userDTO);
                    System.out.println("Added user" + userDTO);
                }
                catch (DuplicateResourceException e) {
                	System.out.println("Skipped DUPLICATE user" + userDTO);
        		}
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	
    private void addProductsToStore() {
    	// Specify the path to your products JSON file
        String filePath = "products.json";
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // Use ClassLoader to load the resource from the classpath
            //InputStream inputStream = DataInitialization.class.getClassLoader().getResourceAsStream(filePath);
         
            // Use ClassLoader to load the resource from the classpath
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);

            // Use TypeReference to specify the type of the list (List<ProductDTO>)
            List<ProductDTO> productList = objectMapper.readValue(inputStream, new TypeReference<List<ProductDTO>>() {});

            // Iterate over the list of ProductDTO objects
            for (ProductDTO productDTO : productList) {
                
                try {
                	productService.createProduct(productDTO);
                    System.out.println("Added Product" + productDTO);
                }
                catch (DuplicateResourceException e) {
                    System.out.println("Skipped DUPLICATE Product" + productDTO);
        		}
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

