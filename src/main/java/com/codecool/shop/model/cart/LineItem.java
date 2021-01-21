package com.codecool.shop.model.cart;

import com.codecool.shop.model.Product;

import java.util.Currency;

public class LineItem {
    private int id;
    private final Product product;
    private int quantity;
    private final Cart cart;

    public LineItem(int id, int quantity, Product product, Cart cart) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
        this.cart = cart;
    }

    public LineItem(Product product, Cart cart) {
        this.id = -1;
        this.product = product;
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

    public String getPrice() {
        String price = String.format("%.02f", this.quantity * this.product.getDefaultPrice());
        return (price + " " + this.product.getDefaultCurrency());
    }

    public Cart getCart() {
        return cart;
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "id=" + id +
                ", product=" + product.toString() +
                ", quantity=" + quantity +
                ", cart=" + "CEVA" +
                '}';
    }
}
