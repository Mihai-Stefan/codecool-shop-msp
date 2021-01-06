package com.codecool.shop.dao;

import com.codecool.shop.model.order.Order;

public interface OrderDao {

    void add(Order order);
    Order find(int id);
    void remove(int id);


}
