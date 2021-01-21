package com.codecool.shop.controller.cart;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.db.CartDaoJdbc;
import com.codecool.shop.dao.db.UserDaoJdbc;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.User;
import com.codecool.shop.model.cart.Cart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/update-quantity"})

public class UpdateQuantityInCartController extends HttpServlet {

    String dataStoreType = "db";

    CartDao cartDataStore;
    UserDao userDataStore;

    User user;

    public void init() {
        switch (dataStoreType) {
            case "mem":
                cartDataStore = CartDaoMem.getInstance();
                userDataStore = UserDaoMem.getInstance();
                break;
            case "db":
                try {
                    cartDataStore = CartDaoJdbc.getInstance();
                    userDataStore = UserDaoJdbc.getInstance();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
                break;
        }
        user = userDataStore.find(1);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String newQuantity = req.getParameter("new-quantity");
        String lineItemId = req.getParameter("item-id");

        Cart cart = cartDataStore.getActiveCartForUser(user);

        try{

            cart.updateItem(Integer.parseInt(lineItemId), Integer.parseInt(newQuantity));
        } catch (NumberFormatException ignored) {}

        cartDataStore.update(cart);

        resp.sendRedirect(req.getContextPath() + "/review-cart");
    }
}
