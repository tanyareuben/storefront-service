package com.sjsu.storefront.data.respository;

import org.springframework.data.repository.CrudRepository;

import com.sjsu.storefront.data.model.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {

}
