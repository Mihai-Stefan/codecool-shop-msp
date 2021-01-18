package com.codecool.shop.model.cart;

import com.codecool.shop.model.User;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Cart {
    private int id;
    private final User user;
    private final List<LineItem> items;
    private int numberOfItems;
    private float subtotal;
    private Currency currency = Currency.getInstance("USD");
    private CartStatus status;

    public Cart(User user) {
        this.id = -1;
        this.user = user;
        this.items = new ArrayList<>();
        this.numberOfItems = 0;
        this.subtotal = 0;
        this.status = CartStatus.ACTIVE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public String getSubtotal() {
        return String.format("%.02f", this.subtotal);
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    public void addToCart(LineItem itemToAdd) {

        LineItem targetItem = null;

        for (LineItem item: this.items) {
            if (item.getProduct() == itemToAdd.getProduct()) {
                targetItem = item;
                break;
            }
        }

        if (targetItem != null) {
            targetItem.incrementQuantity();
        } else {
            targetItem = itemToAdd;
            this.items.add(targetItem);
        }

        this.numberOfItems++;
        this.subtotal += targetItem.getUnitPrice();
    }

    public void updateItem(int itemId, int newQuantity) {

        for (LineItem item: this.items) {
            if (item.getId() == itemId) {

                int oldQuantity = item.getQuantity();

                if (newQuantity > 0) {
                    this.numberOfItems += newQuantity - oldQuantity;
                    this.subtotal += (newQuantity - oldQuantity) * item.getUnitPrice();
                    item.setQuantity(newQuantity);
                } else {
                    this.numberOfItems -= oldQuantity;
                    this.subtotal -= oldQuantity * item.getUnitPrice();
                    this.items.remove(item);
                }

                break;
            }
        }
    }

    public void removeFromCart(int itemId) {
        for (LineItem item: this.items) {
            if (item.getId() == itemId) {
                this.items.remove(item);
                this.numberOfItems -= item.getQuantity();
                this.subtotal -= item.getQuantity() * item.getUnitPrice();
                break;
            }
        }
    }
}
