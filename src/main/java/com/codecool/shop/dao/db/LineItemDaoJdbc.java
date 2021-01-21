package com.codecool.shop.dao.db;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.cart.Cart;
import com.codecool.shop.model.cart.LineItem;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LineItemDaoJdbc implements LineItemDao {

    private static LineItemDaoJdbc instance = null;
    private final DataSource dataSource;

    private LineItemDaoJdbc() throws SQLException {
        this.dataSource = DBConnection.getInstance().getDataSource();
    }

    public static LineItemDaoJdbc getInstance() throws SQLException {
        if (instance == null) {
            instance = new LineItemDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(LineItem item) {

        System.out.println(item.toString());

        if (item.getId() != -1) {
            return;
        }

        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO " +
                    "line_items (quantity, product_id, cart_id) " +
                    "VALUES (?, ?, ?)" +
                    "RETURNING id";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, item.getQuantity());
            statement.setInt(2, item.getProduct().getId());
            statement.setInt(3, item.getCart().getId());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            item.setId(resultSet.getInt(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LineItem find(int id) {
        return null;
    }

    public void update(LineItem item) {

        try (Connection connection = dataSource.getConnection()) {
            String query = "UPDATE line_items SET quantity = ?, product_id = ?, cart_id = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, item.getQuantity());
            statement.setInt(2, item.getProduct().getId());
            statement.setInt(3, item.getCart().getId());
            statement.setInt(4, item.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {
        try (Connection connection = dataSource.getConnection()) {

            String query = "DELETE FROM line_items WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<LineItem> getByCart(Cart cart) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT id, quantity, product_id, cart_id FROM line_items WHERE cart_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, cart.getId());
            ResultSet resultSet = statement.executeQuery();

            List<LineItem> result = new ArrayList<>();

            while (resultSet.next()) {
                result.add(
                        new LineItem(
                                resultSet.getInt(1),
                                resultSet.getInt(2),
                                ProductDaoJdbc
                                        .getInstance()
                                        .find(resultSet.getInt(3)),
                                cart
                        )
                );
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LineItem getByCartAndProduct(Cart cart, Product product) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT id, quantity FROM line_items WHERE cart_id = ? AND product_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, cart.getId());
            statement.setInt(2, product.getId());

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return new LineItem(product, cart);
            }

            return new LineItem(
                    resultSet.getInt(1),
                    resultSet.getInt(2),
                    product,
                    cart
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
