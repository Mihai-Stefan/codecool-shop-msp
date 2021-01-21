package com.codecool.shop.dao.db;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {

    private static ProductDaoJdbc instance = null;
    private final DataSource dataSource;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductDaoJdbc() throws SQLException {
        this.dataSource = DBConnection.getInstance().getDataSource();
    }

    public static ProductDaoJdbc getInstance() throws SQLException {
        if (instance == null) {
            instance = new ProductDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Product product) {

        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO " +
                    "products (name, description, default_price, default_currency, category_id, supplier_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)" +
                    "RETURNING id";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getDefaultPrice());
            statement.setString(4, product.getDefaultCurrency().toString());
            statement.setInt(5, product.getProductCategory().getId());
            statement.setInt(6, product.getSupplier().getId());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            product.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product find(int id) {

        try (Connection connection = dataSource.getConnection()) {

            String query = "SELECT name,description, default_price, default_currency, category_id, supplier_id FROM products WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return new Product(
                    id,
                    resultSet.getString(1),
                    resultSet.getFloat(3),
                    resultSet.getString(4),
                    resultSet.getString(2),
                    ProductCategoryDaoJdbc
                            .getInstance()
                            .find(resultSet.getInt(5)),
                    SupplierDaoJdbc
                            .getInstance()
                            .find(resultSet.getInt(6))
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {

        try (Connection connection = dataSource.getConnection()) {

            String query = "DELETE FROM products WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAll() {

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT name,description, default_price, default_currency, category_id, supplier_id, id FROM products";
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            return generateProductList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier) {

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT name,description, default_price, default_currency, category_id, supplier_id, id FROM products WHERE supplier_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, supplier.getId());
            ResultSet resultSet = statement.executeQuery();
            return generateProductList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT name,description, default_price, default_currency, category_id, supplier_id, id FROM products WHERE category_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productCategory.getId());
            ResultSet resultSet = statement.executeQuery();
            return generateProductList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Product> getBy(ProductCategory productCategory, Supplier supplier) {

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT name,description, default_price, default_currency, category_id, supplier_id, id FROM products WHERE category_id = ? AND supplier_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productCategory.getId());
            statement.setInt(2, supplier.getId());
            ResultSet resultSet = statement.executeQuery();
            return generateProductList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private List<Product> generateProductList(ResultSet resultSet) throws SQLException {

        List<Product> result = new ArrayList<>();

        while (resultSet.next()) {
            result.add(
                    new Product(
                            resultSet.getInt(7),
                            resultSet.getString(1),
                            resultSet.getFloat(3),
                            resultSet.getString(4),
                            resultSet.getString(2),
                            ProductCategoryDaoJdbc
                                    .getInstance()
                                    .find(resultSet.getInt(5)),
                            SupplierDaoJdbc
                                    .getInstance()
                                    .find(resultSet.getInt(6))
                    )
            );
        }
        return result;
    }
}

