package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.FavoritesDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.FavoritesDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.Favorites;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/favorites"})
public class FavoritesController extends HttpServlet {

    FavoritesDao favoritesDataStore = FavoritesDaoMem.getInstance();
    ProductDao productDataStore = ProductDaoMem.getInstance();
    UserDao userDataStore = UserDaoMem.getInstance();
    CartDao cartDataStore = CartDaoMem.getInstance();

    User user = userDataStore.find(1);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String FavoriteProductId = req.getParameter("addToFavorites");
        var favoriteProduct =productDataStore.find(Integer.parseInt(FavoriteProductId));
        Favorites favorites = favoritesDataStore.getBy(user);
        if (favorites == null) {
            favorites = new Favorites(user);
            favoritesDataStore.add(favorites);
        }

        favorites.addToFavorites(favoriteProduct);
        favoritesDataStore.update(favorites);

        resp.sendRedirect(req.getContextPath() + "/");
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("cart", cartDataStore.getActiveCartForUser(user));
        context.setVariable("favorites", favoritesDataStore.getBy(user));

        engine.process("product/favorites.html", context, resp.getWriter());

    }


}
