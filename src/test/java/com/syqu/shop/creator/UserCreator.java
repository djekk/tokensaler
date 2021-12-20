package com.syqu.shop.creator;


import com.syqu.shop.object.Customer;

public class UserCreator {
    public static final String USERNAME = "Test";
    public static final String PASSWORD = "longpassword123";
    public static final String EMAIL = "randomemail@gmail.test";

    public static Customer createTestUser() {
        Customer testObject = new Customer();

        testObject.setPassword(PASSWORD);
        testObject.setPasswordConfirm(PASSWORD);
        testObject.setEmail(EMAIL);

        return testObject;
    }
}
