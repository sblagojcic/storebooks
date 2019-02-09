package com.storebooks.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storebooks.domain.security.UserRole;
import com.storebooks.model.ShoppingCart;
import com.storebooks.model.User;
import com.storebooks.model.UserBilling;
import com.storebooks.model.UserPayment;
import com.storebooks.model.UserShipping;
import com.storebooks.repository.RoleRepository;
import com.storebooks.repository.UserBillingRepository;
import com.storebooks.repository.UserPaymentRepository;
import com.storebooks.repository.UserRepository;
import com.storebooks.repository.UserShippingRepository;
import com.storebooks.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
 
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
    @Autowired
    private UserShippingRepository userShippingRepository;
    
    @Autowired
    private UserBillingRepository userBillingRepository;
    
    @Autowired
    private UserPaymentRepository userPaymentRepository;
    

    public User createUser(User user, Set<UserRole> userRoles) {
        User localUser = userRepository.findByUsername(user.getUsername());
        if (localUser != null){
            LOG.info("User with username {} already exist. Nothing will be done", user.getUsername());
        } else {
            for (UserRole ur: userRoles) {
                roleRepository.save(ur.getRole());
            }
            user.getUserRoles().addAll(userRoles);
            
            user.setUserShippingList(new ArrayList<UserShipping>());
            user.setUserPaymentList(new ArrayList<UserPayment>());
            
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            user.setShoppingCart(shoppingCart); 
            localUser = userRepository.save(user);
        }

        return localUser;
    }
    
    public User save(User user){
    	return userRepository.save(user);
    }
    
    public User findById(Long id){
    	return userRepository.findOne(id);
    }
    
    public User findByUsername(String username){
    	return userRepository.findByUsername(username);
    }
    
    public User findByEmail(String email){
    	return userRepository.findByEmail(email);
    }
    
    @Override
    public void updateShipping(UserShipping userShipping, User user) {
        userShipping.setUser(user);
        userShipping.setDefaultShipping(true);
        user.getUserShippingList().add(userShipping);
        userRepository.save(user);
    }

    @Override
    public void setDefaultShipping(Long shippingId, User user) {
        List<UserShipping> userShippingList = (List<UserShipping>) userShippingRepository.findAll();

        for (UserShipping userShipping : userShippingList) {
            if (userShipping.getId() == shippingId){
                userShipping.setDefaultShipping(true);
                userShippingRepository.save(userShipping);
            }
            else {
                userShipping.setDefaultShipping(false);
                userShippingRepository.save(userShipping);
            }
        }
    }


    @Override
    public void updateUserPayment(UserPayment userPayment, UserBilling userBilling, User user) {
        userPayment.setUser(user);
        userPayment.setUserBilling(userBilling);
        userPayment.setDefaultPayment(true);
        System.out.println("UserBilling: " +userBilling);
        System.out.println(userPayment);
        userBilling.setUserPayment(userPayment);
        user.getUserPaymentList().add(userPayment);
        userBillingRepository.save(userBilling);
        userPaymentRepository.save(userPayment);
        userRepository.save(user);

    }

    @Override
    public void setDefaultUserPayment(Long userPaymentId, User user) {
        List<UserPayment> userPaymentList = (List<UserPayment>) userPaymentRepository.findAll();

        for (UserPayment userPayment: userPaymentList) {
            if (userPayment.getId() == userPaymentId){
                userPayment.setDefaultPayment(true);
                userPaymentRepository.save(userPayment);
            }
            else {
                userPayment.setDefaultPayment(false);
                userPaymentRepository.save(userPayment);
            }
        }
    }


    
	
	
}
