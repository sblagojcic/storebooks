package com.storebooks.repository;

import org.springframework.data.repository.CrudRepository;

import com.storebooks.model.ShippingAddress;

public interface ShippingAddressRepository extends CrudRepository<ShippingAddress, Integer> {
}