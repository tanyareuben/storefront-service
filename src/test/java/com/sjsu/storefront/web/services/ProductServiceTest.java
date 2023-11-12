package com.sjsu.storefront.web.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.sjsu.storefront.common.ProductCategory;
import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.data.model.Product;
import com.sjsu.storefront.data.respository.ProductRepository;

@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;
   
    @AfterEach
    public void cleanup() {
        // Clean up the database after each test
        productRepository.deleteAll();
    }

    @Test
    public void testFindProductsByName() {
        // Save a product to the database
        Product product = new Product();
        product.setName("Apple");
        product.setDescription("Organic Apple");
        product.setPrice(10);
        product.setWeight(9.8);
        product.setQuantityInStock(100);
        product.setProductCategory(ProductCategory.FRUIT);
        productRepository.save(product);

        // Actual service method invocation
        List<Product> result = productService.findProductsByName("Apple");

        // Assertions
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testFindProductsByCategory() {
        // Create two products in the same category
        Product apple = createProduct("Apple", "Organic Apple", 10, 9.8, 100, ProductCategory.FRUIT);
        Product orange = createProduct("Orange", "Organic Orange", 8, 6.5, 100, ProductCategory.FRUIT);

        // Actual service method invocation
        List<Product> productsInCategory = productService.findProductsByCategory(ProductCategory.FRUIT);

        // Assertions
        assertNotNull(productsInCategory);
        assertEquals(2, productsInCategory.size());
        assertTrue(productsInCategory.contains(apple));
        assertTrue(productsInCategory.contains(orange));
    }

    // Other test methods...

    private Product createProduct(String name, String description, double price, double weight, int quantityInStock, ProductCategory category) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setWeight(weight);
        product.setQuantityInStock(quantityInStock);
        product.setProductCategory(category);
        productRepository.save(product);
        return product;
    }
 

    @Test
    public void testUpdateProduct() {
        // Save a product to the database
        Product product = new Product();
        product.setName("Apple");
        product.setDescription("Organic Apple");
        product.setPrice(10);
        product.setWeight(9.8);
        product.setQuantityInStock(100);
        product.setProductCategory(ProductCategory.FRUIT);
        productRepository.save(product);

        // Modify some properties
        product.setName("Updated Apple");

        // Actual service method invocation
        Product updatedProduct = null;
        try {
            updatedProduct = productService.updateProduct(product.getId(), product);
        } catch (ResourceNotFoundException e) {
            // Handle the exception if needed
            fail("Unexpected exception: " + e.getMessage());
        }

        // Assertions
        assertNotNull(updatedProduct);
        assertEquals("Updated Apple", updatedProduct.getName());
    }
    
    @Test
    public void testGetAllProducts() {
        // Create products in different categories
        Product apple = createProduct("Apple", "Organic Apple", 10, 9.8, 100, ProductCategory.FRUIT);
        Product orange = createProduct("Orange", "Organic Orange", 8, 6.5, 100, ProductCategory.FRUIT);
        Product spinach = createProduct("Spinach", "Fresh Spinach", 3, 0.5, 50, ProductCategory.VEGITABLE);
        Product milk = createProduct("Milk", "Whole Milk", 4, 1, 200, ProductCategory.DIARY);

        // Actual service method invocation
        List<Product> allProducts = productService.getAllProducts();

        // Assertions
        assertNotNull(allProducts);
        assertEquals(4, allProducts.size()); // Adjust the count based on the number of products added
        assertTrue(allProducts.contains(apple));
        assertTrue(allProducts.contains(orange));
        assertTrue(allProducts.contains(spinach));
        assertTrue(allProducts.contains(milk));
    }

    
}

