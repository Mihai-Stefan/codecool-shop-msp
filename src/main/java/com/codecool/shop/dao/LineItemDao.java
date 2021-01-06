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

    /**
     * Method looks a LineItem having the product in the cart and returns it if it is found.
     * If not found, a new LineItem is instantiated and returned.
     * @param cart cart to look in
     * @param product product to look for
     * @return line item from the cart having the product
     */
    LineItem getByCartAndProduct(Cart cart, Product product);
}
