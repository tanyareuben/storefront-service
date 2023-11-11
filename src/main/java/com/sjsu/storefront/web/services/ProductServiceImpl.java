package com.sjsu.storefront.web.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sjsu.storefront.common.ResourceNotFoundException;
import com.sjsu.storefront.data.model.Image;
import com.sjsu.storefront.data.model.Product;
import com.sjsu.storefront.data.model.ProductCategory;
import com.sjsu.storefront.data.respository.ImageRepository;
import com.sjsu.storefront.data.respository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
    ProductRepository productRepository;
	
	@Autowired
    ImageRepository imageRepository;

    @Override
    public List<Product> findProductsByName(String searchTerm) {
        return productRepository.findByProductNameContaining(searchTerm);
    }
    
    @Override
    public List<Product> findProductsByCategory(ProductCategory category) {
        return productRepository.findByCategory(category);
    }

	@Override
	public Product updateProduct(Long id, Product product) throws ResourceNotFoundException {
		Product existingItem = productRepository.findById(id).orElse(null);
	    if (existingItem == null) {
	    	throw new ResourceNotFoundException("Product not found");
	    }
	    existingItem.set(product);
	    return productRepository.save(existingItem);
	}

	@Override
	public void deleteProduct(Long id) {
	    productRepository.deleteById(id);
	}

	@Override
	public void deleteImage(Long id, Long imgId) throws ResourceNotFoundException {
	  Product existingItem = productRepository.findById(id).orElse(null);
      if (existingItem == null) {
         throw new ResourceNotFoundException("Product not found");
      }
      imageRepository.deleteById(imgId);
	}

	@Override
	public void addImage(Long id, Image image) throws Exception {
	  Product existingItem = productRepository.findById(id).orElse(null);
      if (existingItem == null) {
          throw new ResourceNotFoundException("Product not found");
      }
      image.setItem(existingItem);
      imageRepository.save(image);
	}

	@Override
	public void createProduct(Product item) {
//  	List<Image> images = item.getImages();
//      imageRepository.saveAll(images);
//      item.setImages(images);
      productRepository.save(item);

	}

	@Override
	public List<Product> getAllProducts() {
	   return (List<Product>) productRepository.findAll();
	}

	@Override
	public Product getProduct(Long id) throws ResourceNotFoundException {
	      return productRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
	}
}
