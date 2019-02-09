package com.storebooks.service;

import java.util.List;

import com.storebooks.model.Book;
import com.storebooks.model.CartItem;
import com.storebooks.model.Order;
import com.storebooks.model.ShoppingCart;
import com.storebooks.model.User;

public interface CartItemService {

    CartItem addBookToCartItem(Book book, User user, int quantity);
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
    List<CartItem> findByOrder(Order order);
    CartItem updateCartItem(CartItem cartItem);
    void removeCartItem(CartItem cartItem);
    CartItem findById(Long id);
    CartItem save(CartItem  cartItem);
}
