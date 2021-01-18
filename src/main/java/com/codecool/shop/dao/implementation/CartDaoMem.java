package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.User;
import com.codecool.shop.model.cart.Cart;
import com.codecool.shop.model.cart.CartStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartDaoMem implements CartDao {

    private List<Cart> data = new ArrayList<>();
    private static CartDaoMem instance = null;

    private CartDaoMem() {}

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Cart cart) {
        if (cart.getId() == -1) {
            cart.setId(data.size() + 1);
            data.add(cart);
        }
    }

    @Override
    public void update(Cart cart) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId()== cart.getId()) {
                data.set(i, cart);
                break;
            }
        }
    }

    @Override
    public Cart find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<Cart> getBy(User user) {
        return data.stream().filter(t -> t.getUser().equals(user)).collect(Collectors.toList());
    }

    @Override
    public Cart getActiveCartForUser(User user) {

        List<Cart> cartsForUser = this.getBy(user);

        if (cartsForUser.size() != 0) {
            for (Cart cart: cartsForUser) {
                if (cart.getStatus().equals(CartStatus.ACTIVE)) {
                    return cart;
                }
            }
        }

        return new Cart(user);
    }
}
