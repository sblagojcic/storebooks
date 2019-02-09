package com.storebooks.service;

import com.storebooks.model.BillingAddress;
import com.storebooks.model.Order;
import com.storebooks.model.Payment;
import com.storebooks.model.ShippingAddress;
import com.storebooks.model.ShoppingCart;
import com.storebooks.model.User;

public interface OrderService {

    Order createOrder(ShoppingCart shoppingCart,
                      ShippingAddress shippingAddress,
                      BillingAddress billingAddress,
                      Payment payment,
                      String shippingMethod,
                      User user);

    Order findOne(int id);
}
