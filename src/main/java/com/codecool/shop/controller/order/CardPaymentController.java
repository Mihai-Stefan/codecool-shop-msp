package com.codecool.shop.controller.order;

import com.codecool.shop.config.TemplateEngineUtil;
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
import com.codecool.shop.model.cart.CartStatus;
import com.codecool.shop.model.order.Order;
import com.codecool.shop.model.order.OrderStatus;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/payment/card"})

public class CardPaymentController extends HttpServlet {

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
        user = userDataStore.find(1);
        cart = cartDataStore.getActiveCartForUser(user);
        order = orderDataStore.getActiveOrderForUser(user);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("cart", cart);

        engine.process("order/card-payment.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException {
        cart.setStatus(CartStatus.ORDERED);
        cartDataStore.update(cart);
        order.setOrderStatus(OrderStatus.PROCESSING);
        orderDataStore.update(order);
        resp.sendRedirect(req.getContextPath() + "/order-confirmation");
    }
}
