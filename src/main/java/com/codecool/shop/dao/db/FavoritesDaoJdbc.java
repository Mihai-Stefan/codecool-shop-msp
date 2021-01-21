package com.codecool.shop.dao.db;

import com.codecool.shop.dao.FavoritesDao;
import com.codecool.shop.model.Favorites;
import com.codecool.shop.model.User;

import java.util.ArrayList;
import java.util.List;

public class FavoritesDaoJdbc implements FavoritesDao {

    private List<Favorites> data = new ArrayList<>();
    private static FavoritesDaoJdbc instance = null;

    private FavoritesDaoJdbc() {}

    public static FavoritesDaoJdbc getInstance() {
        if (instance == null) {
            instance = new FavoritesDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Favorites favorites) {

    }

    @Override
    public Favorites find(int id) {
        return null;
    }

    @Override
    public void update(Favorites favorites) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Favorites getBy(User user) {
        return null;
    }
}
