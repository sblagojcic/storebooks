package com.storebooks.service;

import com.storebooks.model.UserShipping;

public interface UserShippingService {

    UserShipping findById(Long id);
    void remove(Long id);

}
