package com.codecool.shop.controller.user;

import com.codecool.shop.Utils.JavaMailUtil;
import com.codecool.shop.Utils.Utils;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.db.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

//import javax.mail.MessagingException;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {
    String usernamePlaceholder = "Your name";
    String emailPlaceholder = "Email address";
    String passwordPlaceholder = "Password";
    String message = "";

    String dataStoreType = "db";

    UserDao userDataStore;

    @Override
    public void init() {
        switch (dataStoreType) {
            case "mem":
                userDataStore = UserDaoMem.getInstance();
                break;
            case "db":
                try {
                    userDataStore = UserDaoJdbc.getInstance();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
                break;
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("usernamePlaceholder", usernamePlaceholder);
        context.setVariable("emailPlaceholder", emailPlaceholder);
        context.setVariable("passwordPlaceholder", passwordPlaceholder);

        engine.process("user/register.html", context, resp.getWriter());

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        String userName = req.getParameter("inputUserName");
        String userEmail = req.getParameter("inputEmail");
        String password = req.getParameter("inputPassword");

        User user = new User(userName, userEmail, Utils.encrypt(password));

        // verify existing user
        if (userDataStore.find(userEmail) == null) {
            userDataStore.add(user);
            resp.sendRedirect("/login");
//
//            try {
//                JavaMailUtil.sendMail(userEmail);
//            } catch (MessagingException e) {
//                e.printStackTrace();

//            }
        } else
            message = "This email is in database\nPlease try another one or Login";
            context.setVariable("usernamePlaceholder", userName);
            context.setVariable("emailPlaceholder", userEmail);
            context.setVariable("passwordPlaceholder", password);
            context.setVariable("message", message);
            engine.process("user/register.html", context, resp.getWriter());











    }

}
