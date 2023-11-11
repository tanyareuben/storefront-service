package com.sjsu.storefront.data.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sjsu.storefront.data.model.Product;
import com.sjsu.storefront.data.model.ProductCategory;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    public List<Product> findByProductNameContaining(@Param("searchTerm") String searchTerm);
    
    
    public List<Product> findByCategory(ProductCategory category);
}
