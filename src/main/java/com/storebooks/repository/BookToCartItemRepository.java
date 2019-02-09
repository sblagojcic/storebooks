package com.storebooks.repository;

import org.springframework.data.repository.CrudRepository;

import com.storebooks.model.BookToCartItem;
import com.storebooks.model.CartItem;

public interface BookToCartItemRepository extends CrudRepository<BookToCartItem, Long> {

    void deleteByCartItem(CartItem cartItem);
}
