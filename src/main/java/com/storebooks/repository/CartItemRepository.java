package com.storebooks.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.storebooks.model.CartItem;
import com.storebooks.model.Order;
import com.storebooks.model.ShoppingCart;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {

    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
    List<CartItem> findByOrder(Order order);
}