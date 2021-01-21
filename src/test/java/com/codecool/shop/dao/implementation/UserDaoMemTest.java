package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;
import org.junit.Test;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class UserDaoMemTest {
    User newUser1 = new User("Sphinx", "ruptilian@codecool.com", "ruptix2021");
    User newUser2 = new User("User_02", "user2@codecool.com", "unudoi");

    @Test
    void addUserTest() throws IOException {
        // arrange
        UserDao userDao = UserDaoMem.getInstance();
//        User user = newUser;
        userDao.add(newUser1);
        userDao.add(newUser2);

    User readUser = userDao.find("user2@codecool.com");
        System.out.println(readUser.getId() + " / " +  readUser.getUsername() + " / " +  readUser.getEmail());
    readUser = userDao.find("ruptilian@codecool.com");
        System.out.println(readUser.getId() + " / " +  readUser.getUsername() + " / " +  readUser.getEmail());
    }




}