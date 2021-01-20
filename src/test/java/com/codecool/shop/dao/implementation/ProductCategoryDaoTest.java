package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import com.sun.jdi.Value;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoTest {

    @Test
    void testAddProductCategoryMethodSuccess() {
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
    void testFindProductCategoryMethodSuccess() {
        ProductCategoryDao categoryDao = ProductCategoryDaoMem.getInstance();
        ProductCategory smartphone = new ProductCategory("Smartphone", "Phone", "A phone ");

        categoryDao.add(smartphone);

        List<ProductCategory> expectedCategories = new ArrayList<>();
        expectedCategories.add(smartphone);
        ProductCategory expectedValue = categoryDao.getAll().get(0);
        int productCategoryId = smartphone.getId();
        ProductCategory actualValue = categoryDao.find(productCategoryId);

        assertEquals(expectedValue, actualValue);

        categoryDao.remove(smartphone.getId());
    }

    @Test
    void remove() {
        ProductCategoryDao categoryDao = ProductCategoryDaoMem.getInstance();
        ProductCategory smartphone = new ProductCategory("Smartphone", "Phone", "A phone ");
        categoryDao.add(smartphone);
        int productCategoryId = smartphone.getId();

        categoryDao.remove(productCategoryId);

        List<ProductCategory> expectedValue = new ArrayList<>();
        List<ProductCategory> actualValue = categoryDao.getAll();

        assertEquals(expectedValue, actualValue);

        categoryDao.remove(smartphone.getId());
    }

    @Test
    void getAll() {
        ProductCategoryDao categoryDao = ProductCategoryDaoMem.getInstance();
        ProductCategory smartphone = new ProductCategory("Smartphone", "Phone", "A phone ");

        categoryDao.add(smartphone);

        List<ProductCategory> expectedValue = categoryDao.getAll();
        List<ProductCategory> actualValue = new ArrayList<>();
        actualValue.add(smartphone);

        assertEquals(expectedValue, actualValue);

        categoryDao.remove(smartphone.getId());
    }
}