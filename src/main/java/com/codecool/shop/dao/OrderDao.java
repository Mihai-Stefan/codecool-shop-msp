package com.codecool.shop.dao;

import com.codecool.shop.model.User;
import com.codecool.shop.model.order.Order;

import java.util.List;

public interface OrderDao {

    void add(Order order);
    Order find(int id);
    void remove(int id);
    Order getActiveOrderForUser(User user);
    List<Order> getBy(User user);


}
