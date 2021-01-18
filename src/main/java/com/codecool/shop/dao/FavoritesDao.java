package com.codecool.shop.dao;

import com.codecool.shop.model.Favorites;
import com.codecool.shop.model.User;

import java.util.List;

public interface FavoritesDao {

    void add(Favorites favorites);
    Favorites find(int id);
    void update(Favorites favorites);
    void remove(int id);

    Favorites getBy(User user);



/**
 *
 */
}
