package com.codecool.shop.dao.db;

import com.codecool.shop.dao.AddressDao;
import com.codecool.shop.model.User;
import com.codecool.shop.model.order.Address;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDaoJdbc implements AddressDao {

    private static AddressDaoJdbc instance = null;
    private final DataSource dataSource;

    private AddressDaoJdbc() throws SQLException {
        this.dataSource = DBConnection.getInstance().getDataSource();
    }

    public static AddressDaoJdbc getInstance() throws SQLException {
        if (instance == null) {
            instance = new AddressDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Address address) {

        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO " +
                    "addresses (first_name, last_name, email, address, country, city, zip, phone, user_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                    "RETURNING id";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, address.getFirstName());
            statement.setString(2, address.getLastName());
            statement.setString(3, address.getEmail());
            statement.setString(4, address.getAddress());
            statement.setString(5, address.getCountry());
            statement.setString(6, address.getCity());
            statement.setInt(7, Integer.parseInt(address.getZipCode()));
            statement.setString(8, address.getPhoneNumber());
            statement.setInt(9, address.getUser().getId());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            address.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Address find(int id) {
        try (Connection connection = dataSource.getConnection()) {

            String query = "SELECT first_name, last_name, email, address, user_id, country, city, zip, phone FROM addresses WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return new Address(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    UserDaoJdbc.getInstance().find(resultSet.getInt(5)),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)
                    );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

    }
}
