package com.storebooks.service;

import com.storebooks.model.UserPayment;

public interface UserPaymentService {
	
	UserPayment findById(Long id);
	
	void removeById(Long id);
}
