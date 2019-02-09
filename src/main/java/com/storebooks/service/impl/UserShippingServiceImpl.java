package com.storebooks.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storebooks.model.UserShipping;
import com.storebooks.repository.UserShippingRepository;
import com.storebooks.service.UserShippingService;

@Service
public class UserShippingServiceImpl implements UserShippingService {

    @Autowired
    private UserShippingRepository userShippingRepository;

    @Override
    public UserShipping findById(Long id) {
        return userShippingRepository.findOne(id);
    }

    @Override
    public void remove(Long id) {
        userShippingRepository.delete(id);
    }
}