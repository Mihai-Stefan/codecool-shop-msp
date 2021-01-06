package com.codecool.shop.model.order;

import com.codecool.shop.model.User;

public class Address {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private User user;
    private String country;
    private String city;
    private String zipCode;
    private String phoneNumber;

    public Address(String firstName, String lastName, String email, String address, User user, String country, String city, String zipCode, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.user = user;
        this.country = country;
        this.city = city;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
    }

    public void setId(int id) {
        this.id = id;
    }


}
