package com.p2j.MohamedApp.Utils;

import com.p2j.MohamedApp.Model.User;

public class Session {

private static Session instance;
private User user;


    public Session() {

    }

public static Session getInstance(){

        if (instance==null){
            instance = new Session();
        }
        return  instance;
}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
