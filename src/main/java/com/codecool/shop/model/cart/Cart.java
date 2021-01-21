package com.codecool.shop.model.cart;

import com.codecool.shop.model.User;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Cart {
    private int id;
    private final User user;
    private final List<LineItem> items;
    private Currency currency = Currency.getInstance("USD");
    private CartStatus status;

    public Cart(User user) {
        this.id = -1;
        this.user = user;
        this.items = new ArrayList<>();
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

    public int countItems() {
        int numberOfItems = 0;
        for (LineItem item: items) {
            numberOfItems += item.getQuantity();
        }
        return numberOfItems;
    }

    public String calculateSubtotal() {
        double subtotal = 0;

        for (LineItem item: items) {
            subtotal += item.getQuantity() * item.getProduct().getDefaultPrice();
        }

        return String.format("%.02f", subtotal);
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
    }

    public void updateItem(int itemId, int newQuantity) {

        for (LineItem item: this.items) {
            if (item.getId() == itemId) {

                int oldQuantity = item.getQuantity();

                if (newQuantity > 0) {
                    item.setQuantity(newQuantity);
                } else {
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
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", user=" + user.toString() +
                ", items=" + items.size() +
                ", currency=" + currency.toString() +
                ", status=" + status.toString() +
                '}';
    }
}
