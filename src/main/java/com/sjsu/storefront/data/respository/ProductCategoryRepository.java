package com.sjsu.storefront.data.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sjsu.storefront.data.model.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    // Additional custom queries if needed
}
