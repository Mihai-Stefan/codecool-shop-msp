package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoMemTest {

    @Test
    void testAdd() {
        ProductCategoryDao categoryDao = ProductCategoryDaoMem.getInstance();
        ProductCategory smartphone = new ProductCategory("Smartphone", "Phone", "A phone ");

        categoryDao.add(smartphone);

        List<ProductCategory> expectedValue = new ArrayList<>();
        expectedValue.add(smartphone);
        List<ProductCategory> actualValue = categoryDao.getAll();

        assertEquals(expectedValue, actualValue);

        categoryDao.remove(smartphone.getId());
    }

    @Test
    void find() {
    }

    @Test
    void remove() {
    }

    @Test
    void getAll() {
    }
}