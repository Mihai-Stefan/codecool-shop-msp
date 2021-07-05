package com.codecool.shop.controller.order;


import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.db.CartDaoJdbc;
import com.codecool.shop.dao.db.OrderDaoJdbc;
import com.codecool.shop.dao.db.UserDaoJdbc;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.User;
import com.codecool.shop.model.cart.Cart;
import com.codecool.shop.model.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@WebServlet(urlPatterns = {"/order-confirmation"})

public class OrderConfirmationController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(OrderConfirmationController.class);

    String dataStoreType = "db";

    CartDao cartDataStore;
    UserDao userDataStore;
    OrderDao orderDataStore;

    User user;
    Cart cart;
    Order order;


    public void init() {
        switch (dataStoreType) {
            case "mem":
                cartDataStore = CartDaoMem.getInstance();
                userDataStore = UserDaoMem.getInstance();
                orderDataStore = OrderDaoMem.getInstance();
                break;
            case "db":
                try {
                    cartDataStore = CartDaoJdbc.getInstance();
                    userDataStore = UserDaoJdbc.getInstance();
                    orderDataStore = OrderDaoJdbc.getInstance();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");
        int orderId = (Integer) session.getAttribute("order_id");

        Order order = orderDataStore.find(orderId);

        Cart cart = cartDataStore.find((Integer) session.getAttribute("cart_id"));

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("order", order);
        context.setVariable("cart", cart);

        logger.info("Order: {}  confirmed", order.getId());

                engine.process("order/order-confirmation.html", context, resp.getWriter());


    }
}
