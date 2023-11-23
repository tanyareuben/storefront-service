package com.sjsu.storefront.web.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<Product> findProductsByName(String searchTerm) {
        return productRepository.findByProductNameContaining(searchTerm);
    }
    
    @Transactional
    @Override
    public List<Product> findProductsByCategory(ProductCategory category) {
        return productRepository.findByproductCategory(category);
    }

	@Transactional
	@Override
	public Product updateProduct(Long id, Product product) throws ResourceNotFoundException {
		Product existingItem = productRepository.findById(id).orElse(null);
	    if (existingItem == null) {
	    	throw new ResourceNotFoundException("Product not found");
	    }
	    existingItem.set(product);
	    return productRepository.save(existingItem);
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
	public Product createProduct(Product product) throws DuplicateResourceException {
		Optional<Product> existingProduct = productRepository.findByName(product.getName());
		if (existingProduct.isPresent()) {
			throw new DuplicateResourceException("Product already Exists");
		}
		List<Image> images = product.getImages();
		for(Image img : images) {
			img.setProduct(product);
		}
	    return productRepository.save(product);
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
}
