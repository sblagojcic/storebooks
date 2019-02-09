package com.storebooks.service;

import com.storebooks.model.ShoppingCart;

public interface ShoppingCartService {

    ShoppingCart update(ShoppingCart shoppingCart);
	void clearShoppingCart(ShoppingCart shoppingCart);
}