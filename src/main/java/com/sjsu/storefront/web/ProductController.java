package com.sjsu.storefront.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjsu.storefront.common.AuthZCheck;
import com.sjsu.storefront.common.DuplicateResourceException;
import com.sjsu.storefront.common.ProductCategory;
import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.data.model.Image;
import com.sjsu.storefront.data.model.DTO.ImageDTO;
import com.sjsu.storefront.data.model.DTO.ProductDTO;
import com.sjsu.storefront.web.services.ProductService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	ProductService productService;
  	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	    
	@Operation(summary = "Get all Products in the system")
	@GetMapping
	public ResponseEntity<List<ProductDTO>> getAllProducts() {

        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
	}


	@Operation(summary = "Get a Product Details by id")
	@GetMapping("/{id}")
	public ProductDTO getItemById(@PathVariable Long id) throws ResourceNotFoundException {
	    return productService.getProduct(id);
	}
	
	@Operation(summary = "Create a NEW Product in the system")
	//@AuthZCheck // Apply the AuthAspect to this method //TODO Uncomment ME
	@PostMapping("/bulkadd")
	public ResponseEntity<String> bulkAddProducts(@RequestBody List<ProductDTO> products) {
		logger.info("Going to add a bunch of Products");
		for(ProductDTO product : products) {
		    try {
				productService.createProduct(product);
			} catch (DuplicateResourceException e) {
				  logger.info(e.getMessage());
			}
		}
	    return ResponseEntity.created(null).body("Products created successfully");			
	}
	  
	@Operation(summary = "Create a NEW Product in the system")
	//@AuthZCheck // Apply the AuthAspect to this method //TODO Uncomment ME
	@PostMapping
	public ResponseEntity<String> createProduct(@RequestBody ProductDTO product) throws DuplicateResourceException {
	    productService.createProduct(product);
	    return ResponseEntity.created(null).body("Product created successfully");
	}

	
	@Operation(summary = "Add a new Image to the Product")
	//@AuthZCheck // Apply the AuthAspect to this method //TODO Uncomment ME
	@PostMapping("{id}/images")
	public ResponseEntity<String> addImage(@PathVariable Long id, @RequestBody ImageDTO image) throws Exception {
	    productService.addImage(id, image);
	    return ResponseEntity.ok("Images Successfully added");
	}

	  
	  @Operation(summary = "Delete an Image from the Product, given image ID")
	  @AuthZCheck // Apply the AuthAspect to this method
	  @DeleteMapping("{id}/images/{imgId}")
	  public ResponseEntity<String> deleteImage(@PathVariable Long id,@PathVariable Long imgId, @RequestBody Image image) throws ResourceNotFoundException {

		  productService.deleteImage(id,imgId);
	      return ResponseEntity.ok("Images Successfully DELETED");
	      
	  }
	  
	  @Operation(summary = "Delete an item in the system given Item's id")
	  @AuthZCheck // Apply the AuthAspect to this method
	  @DeleteMapping("/{id}")
	  public ResponseEntity<String> deleteProduct(@PathVariable Long id) {

		  productService.deleteProduct(id);
	      return ResponseEntity.noContent().build();
	  }
	  
	  @Operation(summary = "Update a Product given Product id, the whole Product object needs to be passed in the request")
	  @AuthZCheck // Apply the AuthAspect to this method
	  @PutMapping("/{id}")
	  public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO item) {
	      
		  try {
			  ProductDTO updatedProduct = productService.updateProduct(id, item);
			  return ResponseEntity.ok(updatedProduct);
		  }
		  catch(ResourceNotFoundException nfe) {
			  return ResponseEntity.notFound().build();
		  }
	  }
	  
	  @Operation(summary = "Eg: Serach for a Product 'computer' like /products/search/computer")
	  @GetMapping("/search/{searchTerm}")
	  public List<ProductDTO> getProductsByName(@PathVariable String searchTerm) {
	      return productService.findProductsByName(searchTerm);
	  }

	  @Operation(summary = "Eg: Serach for a Product 'computer' like /products/search?searchTerm=computer")
	  @GetMapping("/search")
	  public List<ProductDTO> getProductsByNameQueryParam(@RequestParam String searchTerm) {
	      return productService.findProductsByName(searchTerm);
	  }
	  
	  @Operation(summary = "Returns all the Products under the Categoy give")
	  @GetMapping("/byCategory/{category}")
	  public List<ProductDTO> getProductsByCategory(@PathVariable ProductCategory category) throws NotFoundException {
	      return productService.findProductsByCategory(category);
	  }
	  
	  @Operation(summary = "Returns a List of all Product Categories")
	  @GetMapping("/categories")
	  public List<String> getProductCategories(){
	      return productService.getProductCategories();
	  }	  
	  
	  	  
	  //TODO get all products - Paginate
	  
	  //TODO get all products in a category - Paginate
	  
	  //TODO search by product name - support Fuzzy search - Paginate
}
