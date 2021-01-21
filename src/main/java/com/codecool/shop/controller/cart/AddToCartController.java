package com.codecool.shop.controller.cart;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.db.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.User;
import com.codecool.shop.model.cart.Cart;
import com.codecool.shop.model.cart.LineItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/add-to-cart"})

public class AddToCartController extends HttpServlet {

    String dataStoreType = "db";

    ProductDao productDataStore;
    CartDao cartDataStore;
    LineItemDao lineItemDataStore;
    UserDao userDataStore;

    User user;

    public void init() {
        switch (dataStoreType) {
            case "mem":
                productDataStore = ProductDaoMem.getInstance();
                cartDataStore = CartDaoMem.getInstance();
                lineItemDataStore = LineItemDaoMem.getInstance();
                userDataStore = UserDaoMem.getInstance();
                break;
            case "db":
                try {
                    productDataStore = ProductDaoJdbc.getInstance();
                    cartDataStore = CartDaoJdbc.getInstance();
                    lineItemDataStore = LineItemDaoJdbc.getInstance();
                    userDataStore = UserDaoJdbc.getInstance();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
                break;
        }
        user = userDataStore.find(1);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int productId = Integer.parseInt(req.getParameter("productId"));

        Cart cart = cartDataStore.getActiveCartForUser(user);
        LineItem lineItem = lineItemDataStore.getByCartAndProduct(cart, productDataStore.find(productId));

        // Add method adds the object to data store if it is not already there (if object is not stored, id == -1)
        cartDataStore.add(cart);
        lineItemDataStore.add(lineItem);

        cart.addToCart(lineItem);

        cartDataStore.update(cart);

        resp.sendRedirect(req.getContextPath() + "/");
    }
}
