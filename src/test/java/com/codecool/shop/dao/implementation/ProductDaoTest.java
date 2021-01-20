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
        List<Product> actualValue = productDao.getAll();

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

    List<Product> expectedList = new ArrayList<>();
    expectedList.add(productTest);
    Product expectedValue = productDao.getAll().get(0);

    int productId = productTest.getId();
    Product actualValue = productDao.find(productId);

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
    void testGetAllProductsMethodSuccess() {

        ProductDao productDao = ProductDaoMem.getInstance();
        var apple = new Supplier("Apple Computers", "Smartphones, laptops and computers");
        var laptop = new ProductCategory("Laptop", "Hardware", "A laptop or laptop computer, is a small, portable personal computer (PC) with a \"clamshell\" form factor.");
        var productTest = new Product("MacBook Pro", 1500, "USD", "Apple M1 Chip with 8-Core CPU and 8-Core GPU\n" +
                "512GB Storage", laptop, apple);

        productDao.add(productTest);

        List<Product> expectedValue = productDao.getAll();
        List<Product> actualValue = new ArrayList<>();
        actualValue.add(productTest);

        assertEquals(expectedValue, actualValue);

        productDao.remove(productTest.getId());

    }

    @Test
    void testGetBySupplierMethodSuccess() {
        ProductDao productDao = ProductDaoMem.getInstance();
        var apple = new Supplier("Apple Computers", "Smartphones, laptops and computers");
        var laptop = new ProductCategory("Laptop", "Hardware", "A laptop or laptop computer, is a small, portable personal computer (PC) with a \"clamshell\" form factor.");
        var productTest = new Product("MacBook Pro", 1500, "USD", "Apple M1 Chip with 8-Core CPU and 8-Core GPU\n" +
                "512GB Storage", laptop, apple);

        productDao.add(productTest);

        List<Product> expectedValue = new ArrayList<>();
        expectedValue.add(productTest);
        List<Product> actualValue = productDao.getBy(apple);

        assertEquals(expectedValue, actualValue);

        productDao.remove(productTest.getId());
    }

    @Test
    void testGetByCategoryMethodSuccess() {
        ProductDao productDao = ProductDaoMem.getInstance();
        var apple = new Supplier("Apple Computers", "Smartphones, laptops and computers");
        var laptop = new ProductCategory("Laptop", "Hardware", "A laptop or laptop computer, is a small, portable personal computer (PC) with a \"clamshell\" form factor.");
        var productTest = new Product("MacBook Pro", 1500, "USD", "Apple M1 Chip with 8-Core CPU and 8-Core GPU\n" +
                "512GB Storage", laptop, apple);

        productDao.add(productTest);

        List<Product> expectedValue = new ArrayList<>();
        expectedValue.add(productTest);
        List<Product> actualValue = productDao.getBy(laptop);

        assertEquals(expectedValue, actualValue);

        productDao.remove(productTest.getId());

    }

    @Test
    void testGetByProductCategoryAndSupplierMethodSuccess() {
        ProductDao productDao = ProductDaoMem.getInstance();
        var apple = new Supplier("Apple Computers", "Smartphones, laptops and computers");
        var laptop = new ProductCategory("Laptop", "Hardware", "A laptop or laptop computer, is a small, portable personal computer (PC) with a \"clamshell\" form factor.");
        var productTest1 = new Product("MacBook Pro", 1500, "USD", "Apple M1 Chip with 8-Core CPU and 8-Core GPU\n" +
                "512GB Storage", laptop, apple);
        var amazon = new Supplier("Amazon", "Digital content and services");
        var tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        var productTest2 = new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon);
        productDao.add(productTest1);
        productDao.add(productTest2);

        List<Product> expectedValue = new ArrayList<>();
        expectedValue.add(productTest2);

        List<Product> actualValue = productDao.getBy(tablet, amazon);

        assertEquals(expectedValue, actualValue);

        productDao.remove(productTest1.getId());
        productDao.remove(productTest2.getId());
    }
}