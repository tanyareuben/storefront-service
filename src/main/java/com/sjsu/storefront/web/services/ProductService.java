package com.sjsu.storefront.web.services;

import java.util.List;

import com.sjsu.storefront.common.ProductCategory;
import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.data.model.Image;
import com.sjsu.storefront.data.model.Product;

public interface ProductService {
	
	public List<Product> findProductsByName(String searchTerm);
	
	public List<Product> findProductsByCategory(ProductCategory category);
	
	public Product updateProduct(Long id, Product product) throws ResourceNotFoundException;

	public void deleteProduct(Long id);

	public void deleteImage(Long id, Long imgId) throws ResourceNotFoundException;

	public void addImage(Long id, Image image) throws Exception;

	public Product createProduct(Product item);

	public List<Product> getAllProducts();

	public Product getProduct(Long id) throws ResourceNotFoundException;
}
