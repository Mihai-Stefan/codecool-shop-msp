package com.codecool.shop.controller.order;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.User;
import com.codecool.shop.model.cart.Cart;
import com.codecool.shop.model.cart.CartStatus;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;

@WebServlet(urlPatterns = {"/payment/card"})

public class CardPaymentController extends HttpServlet {

    CartDao cartDataStorage = CartDaoMem.getInstance();
    UserDao userDataStorage = UserDaoMem.getInstance();

    User user = userDataStorage.find(1);
    Cart cart = cartDataStorage.getActiveCartForUser(user);

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
        cartDataStorage.update(cart);
        resp.sendRedirect(req.getContextPath() + "/order-confirmation");
    }
}
