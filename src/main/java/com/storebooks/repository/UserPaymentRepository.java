package com.storebooks.repository;

import org.springframework.data.repository.CrudRepository;

import com.storebooks.model.UserPayment;

public interface UserPaymentRepository extends CrudRepository<UserPayment,	Long> {
	
}
