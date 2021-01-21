package com.codecool.shop.controller.user;

import com.codecool.shop.Utils.JavaMailUtil;
import com.codecool.shop.Utils.Utils;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.UserDaoMem;
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

@WebServlet(urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {
    String usernamePlaceholder = "Your name";
    String emailPlaceholder = "Email address";
    String passwordPlaceholder = "Password";
    String message = "";


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = req.getSession(false);

        context.setVariable("usernamePlaceholder", usernamePlaceholder);
        context.setVariable("emailPlaceholder", emailPlaceholder);
        context.setVariable("passwordPlaceholder", passwordPlaceholder);


        engine.process("user/register.html", context, resp.getWriter());

    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        UserDao userDao = UserDaoMem.getInstance();

        String userName = req.getParameter("inputUserName");
        String userEmail = req.getParameter("inputEmail");
        String password = req.getParameter("inputPassword");

        User user = new User(userName, userEmail, Utils.encrypt(password));

        // verify existing user
        if (userDao.find(userEmail) == null) {
            userDao.add(user);
            System.out.println("User added to DB");
            try {
                JavaMailUtil.sendMail(userEmail);
                resp.sendRedirect("/");
            } catch (MessagingException e) {
                e.printStackTrace();

            }
        } else
            message = "This email is in database\nPlease try another one or Login";
            context.setVariable("usernamePlaceholder", userName);
            context.setVariable("emailPlaceholder", userEmail);
            context.setVariable("passwordPlaceholder", password);
            context.setVariable("message", message);
            engine.process("user/register.html", context, resp.getWriter());











    }

}
