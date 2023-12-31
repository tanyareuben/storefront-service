package com.sjsu.storefront.web.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjsu.storefront.common.DuplicateResourceException;
import com.sjsu.storefront.common.ProductCategory;
import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.data.model.Image;
import com.sjsu.storefront.data.model.Product;
import com.sjsu.storefront.data.model.DTO.ImageDTO;
import com.sjsu.storefront.data.model.DTO.ProductDTO;
import com.sjsu.storefront.data.respository.ImageRepository;
import com.sjsu.storefront.data.respository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
		
	@Autowired
    ImageRepository imageRepository;
	
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);


    @Transactional
    @Override
    public List<ProductDTO> findProductsByName(String searchTerm) {
        List<Product> prdts = productRepository.findByProductNameContaining(searchTerm);
        List<ProductDTO> products = new ArrayList<ProductDTO>();
        for(Product prod : prdts) {
        	products.add(new ProductDTO(prod));
        }
        return products;
    }
    
    @Transactional
    @Override
    public List<ProductDTO> findProductsByCategory(ProductCategory category) {
        List<Product> prdts = productRepository.findByproductCategory(category);
        List<ProductDTO> products = new ArrayList<>();
        for(Product product : prdts) {
        	products.add(new ProductDTO(product));
        }
        return products;
    }

	@Transactional
	@Override
	public ProductDTO updateProduct(Long id, ProductDTO product) throws ResourceNotFoundException {
		Product existingItem = productRepository.findById(id).orElse(null);
	    if (existingItem == null) {
	    	throw new ResourceNotFoundException("Product not found");
	    }
	    existingItem.set(product);
	    return new ProductDTO(productRepository.save(existingItem));
	}

	@Transactional
	@Override
	public void deleteProduct(Long id) {
	    productRepository.deleteById(id);
	}

	@Transactional
	@Override
	public void deleteImage(Long id, Long imgId) throws ResourceNotFoundException {
	  Product existingProduct = productRepository.findById(id).orElse(null);
      if (existingProduct == null) {
         throw new ResourceNotFoundException("Product not found");
      }
      existingProduct.deleteImage(imgId);
      productRepository.save(existingProduct);
	}

	@Transactional
	@Override
	public void addImage(Long id, ImageDTO image) throws Exception {
	  Product existingProduct = productRepository.findById(id).orElse(null);
      if (existingProduct == null) {
          throw new ResourceNotFoundException("Product not found");
      }
      Image img = new Image(image,existingProduct);
      existingProduct.addImage(img);
      productRepository.save(existingProduct);
	}

	@Transactional
	@Override
	public ProductDTO createProduct(ProductDTO product) throws DuplicateResourceException {
		Optional<Product> existingProduct = productRepository.findByName(product.getName());
		if (existingProduct.isPresent()) {
			throw new DuplicateResourceException("Product already Exists");
		}
		Product prod = new Product(product);
	    return new ProductDTO(productRepository.save(prod));
	}

	@Transactional
	@Override
	public List<ProductDTO> getAllProducts() {
	   
	   List<Product> products = productRepository.findAll();
	   List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
	   for(Product product : products) {
		   ProductDTO pDTO = new ProductDTO(product);
		   productDTOs.add(pDTO);
	   }
	   return productDTOs;
	}

	@Transactional
	@Override
	public ProductDTO getProduct(Long id) throws ResourceNotFoundException {
	    Product prod = productRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
	    return new ProductDTO(prod);
	}

	@Override
	public List<String> getProductCategories() {
		List<String> categoryStrings = Arrays.stream(ProductCategory.values())
                .map(Enum::name) // Convert each enum constant to its string representation
                .collect(Collectors.toList());
        return categoryStrings;
	}
}
