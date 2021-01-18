package com.codecool.shop.controller.cart;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.LineItemDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.User;
import com.codecool.shop.model.cart.Cart;
import com.codecool.shop.model.cart.LineItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/add-to-cart"})

public class AddToCartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int productId = Integer.parseInt(req.getParameter("productId"));

        CartDao cartDataStore = CartDaoMem.getInstance();
        LineItemDao lineItemDataStore = LineItemDaoMem.getInstance();
        UserDao userDataStore = UserDaoMem.getInstance();
        ProductDao productDataStore = ProductDaoMem.getInstance();

        User user = userDataStore.find(1);
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
