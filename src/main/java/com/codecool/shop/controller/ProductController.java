package com.codecool.shop.controller;

<<<<<<< HEAD
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
=======
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.User;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
>>>>>>> project-a/development
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
<<<<<<< HEAD
import java.util.HashMap;
import java.util.Map;
=======
>>>>>>> project-a/development

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

<<<<<<< HEAD
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("category", productCategoryDataStore.find(1));
        context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
        engine.process("product/index.html", context, resp.getWriter());
    }

}
=======
    ProductDao productDataStore = ProductDaoMem.getInstance();
    ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
    CartDao cartDataStore = CartDaoMem.getInstance();
    UserDao userDataStore = UserDaoMem.getInstance();
    SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

    User user = userDataStore.find(1);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("cart", cartDataStore.getActiveCartForUser(user));
        context.setVariable("products", productDataStore.getAll());
        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());
        context.setVariable("category", "All categories");
        context.setVariable("supplier", "All suppliers");

        engine.process("product/index.html", context, resp.getWriter());
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());
        context.setVariable("cart", cartDataStore.getActiveCartForUser(user));

        String categoryId = req.getParameter("category-id");
        String supplierId = req.getParameter("supplier-id");

        int selectedCat = 0;
        int selectedSupl = 0;

        if(categoryId != null)
            selectedCat = Integer.parseInt(categoryId);
        if(supplierId != null)
            selectedSupl = Integer.parseInt(supplierId);

        if(selectedCat == 0 && selectedSupl == 0) {
            context.setVariable("products", productDataStore.getAll());
        }
        else if (selectedSupl == 0) {
            context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(selectedCat)));
        }
        else if (selectedCat == 0)  {
            context.setVariable("products", productDataStore.getBy(supplierDataStore.find(selectedSupl)));
        }
        else{
            context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(selectedCat), supplierDataStore.find(selectedSupl)));
        }

        if(selectedCat == 0){
            context.setVariable("category", "All categories");
        }
        else
            context.setVariable("category", productCategoryDataStore.find(selectedCat).getName());

        if(selectedSupl == 0){
            context.setVariable("supplier", "All suppliers");
        }
        else
            context.setVariable("supplier", supplierDataStore.find(selectedSupl).getName());


        engine.process("product/index.html", context, resp.getWriter());
    }



}
>>>>>>> project-a/development
