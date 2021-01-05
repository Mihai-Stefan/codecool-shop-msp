package com.codecool.shop.dao;

import com.codecool.shop.model.cart.Cart;
import com.codecool.shop.model.User;

import java.util.List;

public interface CartDao {

    void add(Cart cart);
    Cart find(int id);
    void remove(int id);

    List<Cart> getBy(User user);
}
