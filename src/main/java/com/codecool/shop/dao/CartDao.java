package com.codecool.shop.dao;

import com.codecool.shop.model.cart.Cart;
import com.codecool.shop.model.User;

import java.util.List;

public interface CartDao {

    void add(Cart cart);
    Cart find(int id);
    void update(Cart cart);
    void remove(int id);

    List<Cart> getBy(User user);

    /**
     * Method checks if data storage contains an active cart and returns it.
     * If there is no active cart in the data storage, the method instantiates and returns a new active cart.
     * @param user user to check in data storage
     * @return an active cart
     */
    Cart getActiveCartForUser(User user);
}
