package com.example.tutorappfx;

public class UserSession {
    private static UserSession instance;

    private String userName;
    // You can have more details like user roles, preferences, etc.

    private UserSession(String userName) {
        this.userName = userName;
    }

    public static UserSession getInstance(String userName) {
        if (instance == null) {
            instance = new UserSession(userName);
        }
        return instance;
    }

    public static UserSession getInstance() {
        // This method can be called after the session is established
        return instance;
    }

    public String getUserName() {
        return userName;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
