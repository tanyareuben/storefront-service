package com.sjsu.storefront.data.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sjsu.storefront.data.model.CartItem;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}