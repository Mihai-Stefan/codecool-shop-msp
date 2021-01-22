package com.codecool.shop.dao.db;

import com.codecool.shop.dao.AddressDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.User;
import com.codecool.shop.model.cart.Cart;
import com.codecool.shop.model.cart.CartStatus;
import com.codecool.shop.model.cart.LineItem;
import com.codecool.shop.model.order.Address;
import com.codecool.shop.model.order.Order;
import com.codecool.shop.model.order.OrderStatus;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDaoJdbc implements OrderDao {

    private static OrderDaoJdbc instance = null;
    private final DataSource dataSource;

    private OrderDaoJdbc() throws SQLException {
        this.dataSource = DBConnection.getInstance().getDataSource();

    }

    public static OrderDaoJdbc getInstance() throws SQLException {
        if (instance == null) {
            instance = new OrderDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Order order) {

        if (order.getId() != -1) {
            return;
        }

        try (Connection connection = dataSource.getConnection()) {

            Address shippingAddress = order.getShippingAddress();
            Address billingAddress = order.getBillingAddress();

            AddressDaoJdbc.getInstance().add(shippingAddress);
            AddressDaoJdbc.getInstance().add(billingAddress);

            order.setShippingAddress(shippingAddress);
            order.setBillingAddress(billingAddress);

            String query = "INSERT INTO " +
                    "orders (cart_id, user_id, billing_address_id, shipping_address_id, status_id) " +
                    "VALUES (?, ?, ?, ?, ?)" +
                    "RETURNING id";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, order.getCart().getId());
            statement.setInt(2, order.getUser().getId());
            statement.setInt(3, order.getBillingAddress().getId());
            statement.setInt(4, order.getShippingAddress().getId());
            statement.setInt(5, order.getOrderStatus().getId());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            order.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order find(int id) {

        try (Connection connection = dataSource.getConnection()) {

            String query = "SELECT cart_id, user_id, billing_address_id, shipping_address_id, status_id FROM orders WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return new Order(
                    id,
                    CartDaoJdbc.getInstance().find(resultSet.getInt(1)),
                    UserDaoJdbc.getInstance().find(resultSet.getInt(2)),
                    AddressDaoJdbc.getInstance().find(resultSet.getInt(3)),
                    AddressDaoJdbc.getInstance().find(resultSet.getInt(4)),
                    OrderStatus.getStatusForId(resultSet.getInt(5))
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void update(Order order) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "UPDATE orders SET status_id = ? WHERE id = ? AND user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, order.getOrderStatus().getId());
            statement.setInt(2, order.getId());
            statement.setInt(3, order.getUser().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> getBy(User user) {
        return null;
    }

    @Override
    public Order getProcessingOrderForUser(User user) {
        try (Connection connection = dataSource.getConnection()) {

            String query = "SELECT id, cart_id, billing_address_id, shipping_address_id FROM orders WHERE user_id = ? AND status_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.getId());
            statement.setInt(2, OrderStatus.PROCESSING.getId());
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return new Order(
                    resultSet.getInt(1),
                    CartDaoJdbc.getInstance().find(resultSet.getInt(2)),
                    user,
                    AddressDaoJdbc.getInstance().find(resultSet.getInt(3)),
                    AddressDaoJdbc.getInstance().find(resultSet.getInt(4)),
                    OrderStatus.PENDING_PAYMENT
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public Order getActiveOrderForUser(User user) {
        try (Connection connection = dataSource.getConnection()) {

            String query = "SELECT id, cart_id, billing_address_id, shipping_address_id FROM orders WHERE user_id = ? AND status_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.getId());
            statement.setInt(2, OrderStatus.PENDING_PAYMENT.getId());
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return new Order(
                    resultSet.getInt(1),
                    CartDaoJdbc.getInstance().find(resultSet.getInt(2)),
                    user,
                    AddressDaoJdbc.getInstance().find(resultSet.getInt(3)),
                    AddressDaoJdbc.getInstance().find(resultSet.getInt(4)),
                    OrderStatus.PENDING_PAYMENT
                    );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
