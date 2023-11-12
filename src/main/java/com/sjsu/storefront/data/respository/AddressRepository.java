package com.sjsu.storefront.data.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sjsu.storefront.data.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
