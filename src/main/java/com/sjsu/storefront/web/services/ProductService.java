package com.sjsu.storefront.web.services;

import java.util.List;

import com.sjsu.storefront.common.DuplicateResourceException;
import com.sjsu.storefront.common.ProductCategory;
import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.data.model.Product;
import com.sjsu.storefront.data.model.DTO.ImageDTO;
import com.sjsu.storefront.data.model.DTO.ProductDTO;

public interface ProductService {
	
	public List<ProductDTO> findProductsByName(String searchTerm);
	
	public List<ProductDTO> findProductsByCategory(ProductCategory category);
	
	public void deleteProduct(Long id);

	public void deleteImage(Long id, Long imgId) throws ResourceNotFoundException;


	public List<ProductDTO> getAllProducts();

	public ProductDTO getProduct(Long id) throws ResourceNotFoundException;

	void addImage(Long id, ImageDTO image) throws Exception;

	public List<String> getProductCategories();

	ProductDTO createProduct(ProductDTO product) throws DuplicateResourceException;

	ProductDTO updateProduct(Long id, ProductDTO product) throws ResourceNotFoundException;
}
