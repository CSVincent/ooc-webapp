/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp.service;

import io.muic.ooc.webapp.database.MySQL;
import io.muic.ooc.webapp.entities.User;
import io.muic.ooc.webapp.helpers.HashTool;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author gigadot
 */
public class SecurityService {

    public boolean isAuthorized(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        // do checking
       return (username != null && MySQL.checkUserAlreadyExist(username));
    }

    public boolean authenticate(String username, String password, HttpServletRequest request) {
        String hashed = "";
        try{
            User requested_user = UserService.getUser(username);
            hashed = requested_user.getHashedPassword();
        }catch (NullPointerException e){
            return false;
        }
        boolean isMatched = HashTool.verifyHash(password,hashed);
        if (isMatched) {
            request.getSession().setAttribute("username", username);
            return true;
        } else {
            return false;
        }
    }

    public void logout(HttpServletRequest request) {
        request.getSession().setAttribute("username",null);
        request.getSession().invalidate();
    }
    
}
