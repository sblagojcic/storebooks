package com.storebooks.repository;

import org.springframework.data.repository.CrudRepository;

import com.storebooks.model.BillingAddress;

public interface BillingAddressRepository extends CrudRepository<BillingAddress, Integer> {
}