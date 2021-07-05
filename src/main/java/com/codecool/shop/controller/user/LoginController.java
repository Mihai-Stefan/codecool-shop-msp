package com.codecool.shop.controller.user;

import com.codecool.shop.Utils.Utils;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.db.UserDaoJdbc;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.User;
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

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    String emailPlaceholder = "Email address";
    String passwordPlaceholder = "Password";
    String message = "";



    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = req.getSession(false);

            context.setVariable("emailPlaceholder", emailPlaceholder);
            context.setVariable("passwordPlaceholder", passwordPlaceholder);

        engine.process("user/login.html", context, resp.getWriter());

    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        UserDao userDao = null;
        try {
            userDao = UserDaoJdbc.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String userEmail = req.getParameter("inputEmail");
        String password = req.getParameter("inputPassword");

        // verify email and password
        if(userDao.find(userEmail)!=null && Utils.checkPassword(password, userDao.find(userEmail).getPassword())){
            User user = userDao.find(userEmail);
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            User currentUser = (User)session.getAttribute("user");
            logger.info("User {} logged in",currentUser.getUsername());

            resp.sendRedirect("/");
        }
        else {
            context.setVariable("emailPlaceholder", userEmail);
            context.setVariable("passwordPlaceholder", password);
            message = "Wrong Username or password, please try again!";
            context.setVariable("message", message);
            engine.process("user/login.html", context, resp.getWriter());
        }


    }
}