package com.codecool.shop.dao.db;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.User;
import com.codecool.shop.model.cart.Cart;
import com.codecool.shop.model.cart.CartStatus;
import com.codecool.shop.model.cart.LineItem;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartDaoJdbc implements CartDao {

    private static CartDaoJdbc instance = null;
    private final DataSource dataSource;

    private CartDaoJdbc() throws SQLException {
        this.dataSource = DBConnection.getInstance().getDataSource();
    }

    public static CartDaoJdbc getInstance() throws SQLException {
        if (instance == null) {
            instance = new CartDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Cart cart) {

        if (cart.getId() != -1) {
            return;
        }

        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO " +
                    "carts (user_id, status_id) " +
                    "VALUES (?, ?)" +
                    "RETURNING id";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, cart.getUser().getId());
            statement.setInt(2, cart.getStatus().getId());

            statement.executeUpdate();
//
//            List<LineItem> itemsInCart = cart.getItems();
//
//            for (LineItem item: itemsInCart) {
//                LineItemDaoJdbc.getInstance().add(item);
//            }

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            cart.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Cart cart) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "UPDATE carts SET status_id = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, cart.getStatus().getId());
            statement.setInt(2, cart.getId());

            statement.executeUpdate();

            List<LineItem> itemsInCart = cart.getItems();
            List<LineItem> itemsInCartDB = LineItemDaoJdbc.getInstance().getByCart(cart);

            for (LineItem item: itemsInCart) {

                if (item.getId() == -1) {
                    LineItemDaoJdbc.getInstance().add(item);
                } else {
                    LineItemDaoJdbc.getInstance().update(item);
                }
            }

            for (LineItem item: getRemovedItems(itemsInCart, itemsInCartDB)) {
                LineItemDaoJdbc.getInstance().remove(item.getId());
                System.out.println("A AJUNS");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cart find(int id) {
        try (Connection connection = dataSource.getConnection()) {

            String query = "SELECT user_id, status_id FROM carts WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            Cart cart = new Cart(UserDaoJdbc.getInstance().find(resultSet.getInt(1)));
            cart.setId(resultSet.getInt(1));
            cart.setStatus(CartStatus.getStatusForId(resultSet.getInt(2)));

            List<LineItem> itemsInCart = LineItemDaoJdbc.getInstance().getByCart(cart);

            for (LineItem item: itemsInCart) {
                cart.addToCart(item);
            }

            return cart;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Cart> getBy(User user) {
        return null;
    }

    @Override
    public Cart getActiveCartForUser(User user) {
        try (Connection connection = dataSource.getConnection()) {

            String query = "SELECT id FROM carts WHERE user_id = ? AND status_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.getId());
            statement.setInt(2, CartStatus.ACTIVE.getId());
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return new Cart(user);
            }

            Cart cart = new Cart(user);
            cart.setId(resultSet.getInt(1));

            List<LineItem> itemsInCart = LineItemDaoJdbc.getInstance().getByCart(cart);
            for (LineItem item: itemsInCart) {
                cart.addToCart(item);
            }

            return cart;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<LineItem> getRemovedItems(List<LineItem> cartItems, List<LineItem> DbCartItems) {

        List<Integer> cartItemsIds = new ArrayList<>();
        List<LineItem> removedItems = new ArrayList<>();

        for (LineItem cartItem: cartItems) {
            cartItemsIds.add(cartItem.getId());
        }

        for (LineItem DbItem: DbCartItems) {
            if (!cartItemsIds.contains(DbItem.getId())) {
                removedItems.add(DbItem);
            }
        }

        return removedItems;
    }
}
