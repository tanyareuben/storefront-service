package com.sjsu.storefront.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.sjsu.storefront.data.model.ProductCategory;
import com.sjsu.storefront.data.respository.ProductCategoryRepository;

//ProductCategoryServiceImpl.java
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

 @Autowired
 ProductCategoryRepository productCategoryRepository;

 @Override
 public ProductCategory findById(Long id) throws NotFoundException{
     return productCategoryRepository.findById(id)
             .orElseThrow(() -> new NotFoundException());
 }
}
