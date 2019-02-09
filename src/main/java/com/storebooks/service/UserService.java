package com.storebooks.service;

import java.util.Set;

import com.storebooks.domain.security.UserRole;
import com.storebooks.model.User;
import com.storebooks.model.UserBilling;
import com.storebooks.model.UserPayment;
import com.storebooks.model.UserShipping;

public interface UserService  {
	User createUser(User user, Set<UserRole> userRoles);
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	User save(User user);
	
	User findById(Long id);
	
    void updateShipping(UserShipping userShipping, User user);
    
    void setDefaultShipping(Long shippingId, User user);
    
    void updateUserPayment(UserPayment userPayment, UserBilling userBilling, User user);
    
    void setDefaultUserPayment(Long userPaymentId, User user);
}
