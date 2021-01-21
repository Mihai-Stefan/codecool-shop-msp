package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Favorites {

    private int id;
    private final User user;
    private final List<Product> favoriteProducts;

    public Favorites(User user) {
        this.id = -1;
        this.user = user;
        this.favoriteProducts = new ArrayList<>();
    }

    public Favorites(int id, User user) {
        this.id = id;
        this.user = user;
        this.favoriteProducts = new ArrayList<>();
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
