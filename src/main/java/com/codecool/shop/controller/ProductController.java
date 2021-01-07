package com.codecool.shop.controller;

import  com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("products", productDataStore.getAll());
        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());

        context.setVariable("category", "All");

        engine.process("product/index.html", context, resp.getWriter());
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());

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
            context.setVariable("category", "All");
        }
        else
            context.setVariable("category", productCategoryDataStore.find(selectedCat).getName());

        engine.process("product/index.html", context, resp.getWriter());
    }



}