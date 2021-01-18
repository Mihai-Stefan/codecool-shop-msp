package com.codecool.shop.model.cart;

import com.codecool.shop.model.Product;

import java.util.Currency;

public class LineItem {
    private int id;
    private final Product product;
    private int quantity;
    private float unitPrice;
    private Currency currency;
    private Cart cart;

    public LineItem(Product product, Cart cart) {
        this.id = -1;
        this.product = product;
        this.unitPrice = product.getDefaultPrice();
        this.currency = product.getDefaultCurrency();
        this.quantity = 1;
        this.cart = cart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getPrice() {
        String price = String.format("%.02f", this.quantity * this.unitPrice);
        return (price + " " + this.currency);
    }

    public Cart getCart() {
        return cart;
    }

}
