package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Favorites {

    private List<Product> favoriteProducts;
    private User user;

    private int id;

    public Favorites(User user) {
        this.favoriteProducts = new ArrayList<>();
        this.user = user;
        this.id = -1;
    }

    public void addToFavorites(Product product) {
        favoriteProducts.add(product);
    }

    public void removeFromFavorites(Product product) {
        favoriteProducts.remove(product);
    }

    public List<Product> getFavoriteProducts() {
        return favoriteProducts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }
}
