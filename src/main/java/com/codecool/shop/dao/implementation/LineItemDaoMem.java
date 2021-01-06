package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.cart.Cart;
import com.codecool.shop.model.cart.LineItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LineItemDaoMem implements LineItemDao {

    private List<LineItem> data = new ArrayList<>();
    private static LineItemDaoMem instance = null;

    private LineItemDaoMem() {}

    public static LineItemDaoMem getInstance() {
        if (instance == null) {
            instance = new LineItemDaoMem();
        }
        return instance;
    }

    @Override
    public void add(LineItem item) {
        if (item.getId() == -1) {
            item.setId(data.size() + 1);
            data.add(item);
        }
    }

    @Override
    public LineItem find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<LineItem> getByCart(Cart cart) {
        return data.stream().filter(t -> t.getCart().equals(cart)).collect(Collectors.toList());
    }

    @Override
    public LineItem getByCartAndProduct(Cart cart, Product product) {
        List<LineItem> itemsInCart = this.getByCart(cart);
        return itemsInCart.stream().filter(t -> t.getProduct() == product).findFirst().orElse(new LineItem(product, cart));
    }
}
