package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoTest {


    @Test
    void testAddSupplierMethodSuccess() {
        SupplierDao supplierDao = SupplierDaoMem.getInstance();
        Supplier amazon = new Supplier("Amazon", "Digital content and services");

        supplierDao.add(amazon);

        List<Supplier> expectedValue = new ArrayList<>();
        expectedValue.add(amazon);
        List<Supplier> actualValue = supplierDao.getAll();

        assertEquals(expectedValue, actualValue);

        supplierDao.remove(amazon.getId());
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