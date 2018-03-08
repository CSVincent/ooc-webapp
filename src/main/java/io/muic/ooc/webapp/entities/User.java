package io.muic.ooc.webapp.entities;

public class User {

    private int id;

    private String user_name;

    private String hashedpassword;

    private String email;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getHashedPassword() {
        return hashedpassword;
    }

    public void setHashedPassword(String password) {
        this.hashedpassword = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return "USER( id: " + this.getId() + " ,username : " + this.getUser_name() + " ,email_address : " + this.getEmail() + " )";
    }
}
