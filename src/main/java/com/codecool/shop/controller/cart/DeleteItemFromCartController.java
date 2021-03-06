package com.codecool.shop.controller.cart;

import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.db.CartDaoJdbc;
import com.codecool.shop.dao.db.LineItemDaoJdbc;
import com.codecool.shop.dao.db.ProductDaoJdbc;
import com.codecool.shop.dao.db.UserDaoJdbc;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.LineItemDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.User;
import com.codecool.shop.model.cart.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.sampled.Line;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/delete-item"})

public class DeleteItemFromCartController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DeleteItemFromCartController.class);

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

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        if ( null == session.getAttribute("user") ) {
            resp.sendRedirect(req.getContextPath() + "/login");
        }

        user = (User) session.getAttribute("user");

        String itemId = req.getParameter("item-id");

        Cart cart = cartDataStore.find((Integer) session.getAttribute("cart_id"));

        try {
            cart.removeFromCart(Integer.parseInt(itemId));
        } catch (NumberFormatException ignored){}
        logger.info("An item was deleted from cart");
                cartDataStore.update(cart);

        resp.sendRedirect(req.getContextPath() + "/review-cart");
    }
}
