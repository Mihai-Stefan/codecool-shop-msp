package com.codecool.shop.dao.db;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJdbc implements SupplierDao {

    private static SupplierDaoJdbc instance = null;
    private final DataSource dataSource;

    /* A private Constructor prevents any other class from instantiating.
     */
    private SupplierDaoJdbc() throws SQLException {
        this.dataSource = DBConnection.getInstance().getDataSource();
    }

    public static SupplierDaoJdbc getInstance() throws SQLException {
        if (instance == null) {
            instance = new SupplierDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {

    }

    @Override
    public Supplier find(int id) {
        try (Connection connection = dataSource.getConnection()) {

            String query = "SELECT name, description FROM suppliers WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return new Supplier(
                    id,
                    resultSet.getString(1),
                    resultSet.getString(2)
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Supplier> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT id, name,description FROM suppliers";
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            List<Supplier> result = new ArrayList<>();

            while (resultSet.next()) {
                result.add(
                        new Supplier(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3)
                        )
                );
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
