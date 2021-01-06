package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.Supplier;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDao = SupplierDaoMem.getInstance();
        UserDao userDataStore = UserDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        //read from form
        String cattegoryId = req.getParameter("category-id");
//        String supplierId = req.getParameter("supplier-id");
        int selectedCat = 0;

        if(cattegoryId != null)
            selectedCat = Integer.parseInt(cattegoryId);

//        context.setVariable("category", productCategoryDataStore.find(4));
//        context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(4)));
//        context.setVariable("user", userDataStore.find(1));



         // Alternative setting of the template context
        Map<String, Object> params = new HashMap<>();

        //== Case List All ==
        if( cattegoryId == null || selectedCat == 0){
            params.put("category", "All");
            params.put("products", productDataStore.getAll());
        }
        else{
            params.put("category", productCategoryDataStore.find(selectedCat).getName());

//            params.put("products", productDataStore.getBy(productCategoryDataStore.find(selectedCat)));
            params.put("products", productDataStore.getBy(productCategoryDataStore.find(selectedCat), supplierDao.find(3)));

        }



        context.setVariables(params);
        engine.process("product/index.html", context, resp.getWriter());
        }

}
