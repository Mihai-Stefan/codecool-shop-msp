package com.codecool.shop.config;

import com.codecool.shop.model.*;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        UserDao userDataStore = UserDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier apple = new Supplier("Apple Computers", "Smartphones, laptops and computers");
        supplierDataStore.add(apple);
        Supplier samsung = new Supplier("Samsung", "Almost everything");
        supplierDataStore.add(samsung);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        ProductCategory smartphone = new ProductCategory("Smartphone", "Phone", "A phone ");
        productCategoryDataStore.add(smartphone);
        ProductCategory laptop = new ProductCategory("Laptop", "Hardware", "A laptop or laptop computer, is a small, portable personal computer (PC) with a \"clamshell\" form factor.");
        productCategoryDataStore.add(laptop);
        ProductCategory tv = new ProductCategory("TV Set", "Home Entertainment", "A TV Set, is a device that combines a tuner, display, and loudspeakers, for the purpose of viewing and hearing television.");
        productCategoryDataStore.add(tv);

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product("TV", 200, "USD", "Televizor fain", tv, samsung));
        productDataStore.add(new Product("Apple iPhone 12 Pro Max 128GB", 800, "USD", "Silver, Graphite, Gold, Pacific Blue\n" +
                "\n" +
                "Ceramic Shield front\n" +
                "Textured matte glass back and\n" +
                "stainless steel design", smartphone, apple));
        productDataStore.add(new Product("Samsung 65-inch Class QLED Q800T Series - Real 8K Resolution", 3899, "USD", "This bundle includes the Samsung 65-inch Class QLED Q800T Series Smart TV with Alexa Built-In and the Amazon Smart Plug.", tv, samsung));
        productDataStore.add(new Product("Samsung Galaxy Note 20", 470, "USD", "The Dynamic AMOLED 2X Infinity-O Display on Galaxy Note20 Ultra 5G delivers 1500 nits for a colorful, glare-free view, even in bright daylight.1 And it reduces blue light significantly to lessen eye strain.\n" +
                "\n", smartphone, samsung));
        productDataStore.add(new Product("iPad Pro", 800, "USD", "Apple tablet", tablet, apple));
        productDataStore.add(new Product("MacBook Pro", 1500, "USD", "Apple M1 Chip with 8-Core CPU and 8-Core GPU\n" +
                "512GB Storage", laptop, apple));

        // setting up a new user
        userDataStore.add(new User("Sphinx", "ruptilian@codecool.com", "ruptix2021"));
    }
}