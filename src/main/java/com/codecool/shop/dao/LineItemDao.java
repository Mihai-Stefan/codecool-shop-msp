package com.codecool.shop.dao;

import com.codecool.shop.model.*;
import com.codecool.shop.model.cart.Cart;
import com.codecool.shop.model.cart.LineItem;

import java.util.List;

public interface LineItemDao {

    void add(LineItem item);
    LineItem find(int id);
    void remove(int id);

    List<LineItem> getByCart(Cart cart);
}
