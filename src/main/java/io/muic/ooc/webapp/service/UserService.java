package io.muic.ooc.webapp.service;

import io.muic.ooc.webapp.database.MySQL;
import io.muic.ooc.webapp.entities.User;

public class UserService {

    public static User getUser(String username){
        return MySQL.getUserbyUsername(username);
    }

    public static User getUserById(String id){
        return MySQL.getUserbyId(id);
    }
}
