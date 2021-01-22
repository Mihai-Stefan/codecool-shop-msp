package com.codecool.shop.controller;


import com.codecool.shop.dao.*;
import com.codecool.shop.dao.db.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.User;
import com.codecool.shop.model.cart.Cart;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    String dataStoreType = "db";

    ProductDao productDataStore;
    ProductCategoryDao productCategoryDataStore;
    CartDao cartDataStore;
    UserDao userDataStore;
    SupplierDao supplierDataStore;

    User user;

    @Override
    public void init() {
        switch (dataStoreType) {
            case "mem":
                productDataStore = ProductDaoMem.getInstance();
                productCategoryDataStore = ProductCategoryDaoMem.getInstance();
                cartDataStore = CartDaoMem.getInstance();
                userDataStore = UserDaoMem.getInstance();
                supplierDataStore = SupplierDaoMem.getInstance();
                break;
            case "db":
                try {
                    productDataStore = ProductDaoJdbc.getInstance();
                    productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();
                    cartDataStore = CartDaoJdbc.getInstance();
                    userDataStore = UserDaoJdbc.getInstance();
                    supplierDataStore = SupplierDaoJdbc.getInstance();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
                break;
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        user = (User) session.getAttribute("user");

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());


        if ( session.getAttribute("user") != null ) {
            Cart cart = cartDataStore.getActiveCartForUser(user);
            context.setVariable("cart", cart);
            session.setAttribute("cart_id", cart.getId());
        }

        context.setVariable("products", productDataStore.getAll());
        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());

        context.setVariable("category", "All categories");
        context.setVariable("supplier", "All suppliers");

        engine.process("product/index.html", context, resp.getWriter());
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        user = (User) session.getAttribute("user");

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());


        if ( session.getAttribute("user") != null ) {
            context.setVariable("cart", cartDataStore.find((Integer) session.getAttribute("cart_id")));
        }

        String categoryId = req.getParameter("category-id");
        String supplierId = req.getParameter("supplier-id");

        int selectedCat = 0;
        int selectedSupl = 0;

        if(categoryId != null)
            selectedCat = Integer.parseInt(categoryId);
        if(supplierId != null)
            selectedSupl = Integer.parseInt(supplierId);

        if(selectedCat == 0 && selectedSupl == 0) {
            context.setVariable("products", productDataStore.getAll());
        }
        else if (selectedSupl == 0) {
            context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(selectedCat)));
        }
        else if (selectedCat == 0)  {
            context.setVariable("products", productDataStore.getBy(supplierDataStore.find(selectedSupl)));
        }
        else{
            context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(selectedCat), supplierDataStore.find(selectedSupl)));
        }

        if(selectedCat == 0){
            context.setVariable("category", "All categories");
        }
        else {
            context.setVariable("category", productCategoryDataStore.find(selectedCat).getName());
        }

        if(selectedSupl == 0){
            context.setVariable("supplier", "All suppliers");
        }
        else {
            context.setVariable("supplier", supplierDataStore.find(selectedSupl).getName());
        }

        engine.process("product/index.html", context, resp.getWriter());
    }
}
