package com.codecool.shop.controller.order;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.AddressDao;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.AddressDaoMem;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.User;
import com.codecool.shop.model.cart.Cart;
import com.codecool.shop.model.order.Address;
import com.codecool.shop.model.order.Order;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;

@WebServlet(urlPatterns = {"/billing-details"})
public class BillingDetailsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException {
        CartDao cartDataStore = CartDaoMem.getInstance();
        UserDao userDataStore = UserDaoMem.getInstance();

        User user = userDataStore.find(1);
        Cart cart = cartDataStore.getActiveCartForUser(user);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("cart", cart);

        engine.process("order/billing-details.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException {

        UserDao userDataStore = UserDaoMem.getInstance();
        CartDao cartDataStore = CartDaoMem.getInstance();
        AddressDao addressDataStore = AddressDaoMem.getInstance();
        OrderDao orderDataStore = OrderDaoMem.getInstance();

        User user = userDataStore.find(1);
        Cart cart = cartDataStore.getActiveCartForUser(user);

        String bFirstName    = req.getParameter("firstName");
        String bLastName     = req.getParameter("lastName");
        String bEmail        = req.getParameter("email");
        String bAddress      = req.getParameter("address");
        String bCountry      = req.getParameter("country");
        String bCity         = req.getParameter("city");
        String bZipCode      = req.getParameter("zipCode");
        String bPhoneNumber  = req.getParameter("phoneNumber");

        String sFirstName    = req.getParameter("sFirstName");
        String sLastName     = req.getParameter("sLastName");
        String sEmail        = req.getParameter("sEmail");
        String sAddress      = req.getParameter("sAddress");
        String sCountry      = req.getParameter("sCountry");
        String sCity         = req.getParameter("sCity");
        String sZipCode      = req.getParameter("sZipCode");
        String sPhoneNumber  = req.getParameter("sPhoneNumber");

        String paymentMethod = req.getParameter("payment");

        Address billingAddress = new Address(bFirstName, bLastName, bEmail, bAddress, user, bCountry, bCity, bZipCode, bPhoneNumber);
        Address shippingAddress = new Address(sFirstName, sLastName, sEmail, sAddress, user, sCountry, sCity, sZipCode, sPhoneNumber);

        addressDataStore.add(billingAddress);
        addressDataStore.add(shippingAddress);

        Order order = new Order(cart, user, billingAddress, shippingAddress);

        orderDataStore.add(order);

        if (paymentMethod.equals("pay-by-card")) {
            resp.sendRedirect(req.getContextPath() + "/payment/card");
        }


        if (paymentMethod.equals("pay-by-paypal")) {
            resp.sendRedirect(req.getContextPath() + "/payment/paypal");
        }
    }
}
