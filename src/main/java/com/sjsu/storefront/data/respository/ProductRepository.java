package com.sjsu.storefront.data.respository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sjsu.storefront.data.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
