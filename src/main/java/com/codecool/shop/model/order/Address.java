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
        this.id = -1;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return "Name: " + firstName + " " + lastName;
    }

    public String getEmail() {
        return "Email: " + email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return "Address: " + address + ", " + country + ", " + city + " " + zipCode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return "PhoneNumber: " + phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
