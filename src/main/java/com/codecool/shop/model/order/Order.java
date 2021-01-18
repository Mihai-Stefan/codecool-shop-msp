package com.codecool.shop.model.order;

import com.codecool.shop.model.User;
import com.codecool.shop.model.cart.Cart;

public class Order {
   private int id;
   private Cart cart;
   private User user;
   private Address billingAddress;
   private Address shippingAddress;
   private OrderStatus orderStatus;

   public Order(Cart cart, User user, Address billingAddress, Address shippingAddress) {
      this.id = -1;
      this.cart = cart;
      this.user = user;
      this.billingAddress = billingAddress;
      this.shippingAddress = shippingAddress;
      this.orderStatus = OrderStatus.PENDING_PAYMENT;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public Cart getCart() {
      return cart;
   }

   public void setCart(Cart cart) {
      this.cart = cart;
   }

   public User getUser() {
      return user;
   }
   public Address getBillingAddress() {
      return billingAddress;
   }

   public void setBillingAddress(Address billingAddress) {
      this.billingAddress = billingAddress;
   }

   public Address getShippingAddress() {
      return shippingAddress;
   }

   public void setShippingAddress(Address shippingAddress) {
      this.shippingAddress = shippingAddress;
   }

   public OrderStatus getOrderStatus() {
      return orderStatus;
   }

   public void setOrderStatus(OrderStatus orderStatus) {
      this.orderStatus = orderStatus;
   }
}
