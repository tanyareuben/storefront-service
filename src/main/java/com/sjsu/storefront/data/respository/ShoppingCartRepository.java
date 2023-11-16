package com.sjsu.storefront.data.respository;

import org.springframework.data.repository.CrudRepository;

import com.sjsu.storefront.data.model.ShoppingCart;
import com.sjsu.storefront.data.model.User;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

	ShoppingCart findByUser(User user);

}
