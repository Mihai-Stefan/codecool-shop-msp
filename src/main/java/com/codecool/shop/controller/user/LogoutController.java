package com.codecool.shop.controller.user;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.controller.ProductController;
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


@WebServlet(urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = req.getSession(false);
        logger.info("User logout");
        if (session != null) {
            session.invalidate();
            resp.sendRedirect("/");
            return;
        }
        resp.sendRedirect("/");
    }

    // doPost seems unnecessary

}
