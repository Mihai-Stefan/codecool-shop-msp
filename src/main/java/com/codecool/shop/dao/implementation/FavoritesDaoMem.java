package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.FavoritesDao;
import com.codecool.shop.model.Favorites;
import com.codecool.shop.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FavoritesDaoMem implements FavoritesDao {

    private List<Favorites> data = new ArrayList<>();
    private static FavoritesDaoMem instance = null;

    private FavoritesDaoMem() {}

    public static FavoritesDaoMem getInstance() {
        if (instance == null) {
            instance = new FavoritesDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Favorites favorites) {
        if (favorites.getId() == -1) {
            favorites.setId(data.size() + 1);
            data.add(favorites);
        }
    }

    @Override
    public Favorites find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void update(Favorites favorites) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() ==favorites.getId()) {
                data.set(i, favorites);
                break;
            }
        }
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public Favorites getBy(User user) {
        return data.stream().filter(t -> t.getUser().equals(user)).findFirst().orElse(null);
    }
}
