package com.storebooks.repository;

import org.springframework.data.repository.CrudRepository;

import com.storebooks.model.UserBilling;

public interface UserBillingRepository extends CrudRepository<UserBilling, Long> {

}
