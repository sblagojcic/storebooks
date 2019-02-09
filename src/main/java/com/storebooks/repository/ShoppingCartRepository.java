package com.storebooks.repository;

import org.springframework.data.repository.CrudRepository;

import com.storebooks.model.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {
}