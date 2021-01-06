package com.codecool.shop.model.order;

import com.codecool.shop.model.User;
import com.codecool.shop.model.cart.Cart;

public class Order {
   private Cart cart;
   private User user;
   private Address billingAddress;
   private Address shippingAddress;

}
