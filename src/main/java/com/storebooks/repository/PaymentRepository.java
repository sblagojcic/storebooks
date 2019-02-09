package com.storebooks.repository;

import org.springframework.data.repository.CrudRepository;

import com.storebooks.model.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {
}
