package com.storebooks.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.storebooks.model.Order;
import com.storebooks.model.User;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    List<Order> findByUser(User user);
}
