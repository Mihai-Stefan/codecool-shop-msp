package com.codecool.shop.dao.db;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJdbc implements ProductCategoryDao {

    private static ProductCategoryDaoJdbc instance = null;
    private final DataSource dataSource;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductCategoryDaoJdbc() throws SQLException {
        this.dataSource = DBConnection.getInstance().getDataSource();
    }

    public static ProductCategoryDaoJdbc getInstance() throws SQLException {
        if (instance == null) {
            instance = new ProductCategoryDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {

    }

    @Override
    public ProductCategory find(int id) {

        try (Connection connection = dataSource.getConnection()) {

            String query = "SELECT name,description, department FROM product_categories WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return new ProductCategory(
                    id,
                    resultSet.getString(1),
                    resultSet.getString(3),
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
    public List<ProductCategory> getAll() {

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT id, name, department, description FROM product_categories";
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            List<ProductCategory> result = new ArrayList<>();

            while (resultSet.next()) {
                result.add(
                        new ProductCategory(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4)
                        )
                );
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
