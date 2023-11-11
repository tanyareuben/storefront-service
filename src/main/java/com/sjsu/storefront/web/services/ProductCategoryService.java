package com.sjsu.storefront.web.services;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.sjsu.storefront.data.model.ProductCategory;

//ProductCategoryService.java
public interface ProductCategoryService {
	ProductCategory findById(Long id) throws NotFoundException;
}
