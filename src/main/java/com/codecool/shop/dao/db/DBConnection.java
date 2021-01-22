package com.codecool.shop.dao.db;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance = null;
    private DataSource dataSource = null;

    private DBConnection() {}

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public DataSource getDataSource() throws SQLException {
        if (dataSource == null) {
            dataSource = connect();
        }
        return dataSource;
    }

    private DataSource connect() throws SQLException {

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = "codecool-shop";
        String user = "cristina"; // my postgres username
        String password = " "; // my postgres password

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }
}
