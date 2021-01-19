package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {



    @Test
    void testAddProductMethodSuccess() {
//        preparation
        ProductDao productDao = ProductDaoMem.getInstance();
        var apple = new Supplier("Apple Computers", "Smartphones, laptops and computers");
        var laptop = new ProductCategory("Laptop", "Hardware", "A laptop or laptop computer, is a small, portable personal computer (PC) with a \"clamshell\" form factor.");
        var productTest = new Product("MacBook Pro", 1500, "USD", "Apple M1 Chip with 8-Core CPU and 8-Core GPU\n" +
                "512GB Storage", laptop, apple);

//        execution
        productDao.add(productTest);

//        assertion
        List<Product> expectedValue = new ArrayList<>();
        expectedValue.add(productTest);
        System.out.println(expectedValue);
        List<Product> actualValue = productDao.getAll();
        System.out.println(actualValue);

        assertEquals(expectedValue, actualValue);

//         clean up
        productDao.remove(productTest.getId());
    }


    @Test
    void testFindProductMethodSuccess() {
    ProductDao productDao = ProductDaoMem.getInstance();
    var apple = new Supplier("Apple Computers", "Smartphones, laptops and computers");
    var laptop = new ProductCategory("Laptop", "Hardware", "A laptop or laptop computer, is a small, portable personal computer (PC) with a \"clamshell\" form factor.");
    var productTest = new Product("MacBook Pro", 1500, "USD", "Apple M1 Chip with 8-Core CPU and 8-Core GPU\n" +
            "512GB Storage", laptop, apple);

    productDao.add(productTest);

    int productId = productTest.getId();
    Product actualValue = productDao.find(productId);
    Product expectedValue = productDao.getAll().get(0);
    assertEquals(expectedValue, actualValue);

    productDao.remove(productTest.getId());
    }

    @Test
    void testRemoveProductMethodSuccess() {
        ProductDao productDao = ProductDaoMem.getInstance();
        var apple = new Supplier("Apple Computers", "Smartphones, laptops and computers");
        var laptop = new ProductCategory("Laptop", "Hardware", "A laptop or laptop computer, is a small, portable personal computer (PC) with a \"clamshell\" form factor.");
        var productTest = new Product("MacBook Pro", 1500, "USD", "Apple M1 Chip with 8-Core CPU and 8-Core GPU\n" +
                "512GB Storage", laptop, apple);
        productDao.add(productTest);
        int productId = productTest.getId();

        productDao.remove(productId);

        List<Product> expectedValue = new ArrayList<>();
        List<Product> actualValue = productDao.getAll();

        assertEquals(expectedValue, actualValue);

        productDao.remove(productTest.getId());
    }

    @Test
    void getAll() {
    }

    @Test
    void getBy() {
    }

    @Test
    void testGetBy() {
    }

    @Test
    void testGetBy1() {
    }
}