package com.codecool.shop.controller.cart;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.UserDao;
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

@WebServlet(urlPatterns = {"/delete-item"})

public class DeleteItemFromCartController extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String itemId = req.getParameter("item-id");

        CartDao cartDataStore = CartDaoMem.getInstance();
        UserDao userDataStore = UserDaoMem.getInstance();

        User user = userDataStore.find(1);
        Cart cart = cartDataStore.getActiveCartForUser(user);

        try {
            cart.removeFromCart(Integer.parseInt(itemId));
        } catch (NumberFormatException ignored){}

        cartDataStore.update(cart);

        resp.sendRedirect(req.getContextPath() + "/review-cart");
    }
}
