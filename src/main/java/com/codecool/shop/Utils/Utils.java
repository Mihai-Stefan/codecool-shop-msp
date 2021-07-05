package com.codecool.shop.Utils;

import org.mindrot.jbcrypt.BCrypt;

public class Utils {

    public static String encrypt(String password) {
        final int saltRounds = 12;
        String saltHashPassword = BCrypt.hashpw(password, BCrypt.gensalt(saltRounds));
        return saltHashPassword;

    }

    public static boolean checkPassword(String plainPassword, String storedPassword) {
        if (storedPassword == null){
            throw  new IllegalArgumentException("Invalid Hash String");
        }
        System.out.println("pass ver=================");
        return BCrypt.checkpw(plainPassword, storedPassword);
    }


}
