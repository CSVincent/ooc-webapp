package io.muic.ooc.webapp.database;

import io.muic.ooc.webapp.entities.User;
import io.muic.ooc.webapp.helpers.HashTool;
import io.muic.ooc.webapp.service.*;

import java.sql.*;
import java.util.ArrayList;

public class MySQL  {
//    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/new_database";

    static final String DB_User = "root";
    static final String DB_Pass = "chin18945";
    static final String DB_Table = "user_table";

    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    private static Connection connection = null;

    private static Connection getConnection(){
        try{
//            Class.forName("com.mysql.jdbc.Driver"); //register the dirver
            if(connection ==  null || connection.isClosed()){
                connection = DriverManager.getConnection(DB_URL,DB_User,DB_Pass); //get the connection
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        if(connection == null){
            System.out.println("Connection is still null");
        }
        return connection;
    }

    public static void Create_Table(){
        try{
            String query = "create table if  not exists " + DB_Table + " (id INT(11) AUTO_INCREMENT PRIMARY KEY"
                    +", username VARCHAR(60) NOT NULL"
                    +", hashedpassword VARCHAR(60) NOT NULL"
                    +", email VARCHAR(60) NOT NULL);";
            PreparedStatement prepareStatement = getConnection().prepareStatement(query);
            prepareStatement.executeUpdate();
//            String hash = HashTool.hash("1234");
//            boolean createdFirstUser = createNewUser("win","1234","chaninac127@gmail.com");
//            if(createdFirstUser){
//                System.out.println("Successfully created first user.");
//            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static boolean checkUserAlreadyExist(String username){
        try{
            String query = "SELECT username FROM " + DB_Table + " WHERE username=?;";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.first()){
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createNewUser(String username,String password, String email){
        boolean result = false;
        try{
            if(!(checkUserAlreadyExist(username)) && !(checkEmailAlreadyExist(email))){
                String hashedpassword = HashTool.hash(password);
                String query = "INSERT INTO " + DB_Table + " (username,hashedpassword,email) VALUES(?,?,?);";
                preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setString(1,username);
                preparedStatement.setString(2,hashedpassword);
                preparedStatement.setString(3,email);
                preparedStatement.executeUpdate();
                result = true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public static boolean modifyUserById(String id,String newUsername, String newPassword, String newEmail){
        boolean modified = false;
        System.out.println("Editing User id : " + id);
        User modifying = UserService.getUserById(id);
        String current_username = modifying.getUser_name();
        String current_email = modifying.getEmail();
        if(checkUserAlreadyExist(current_username)){
            try{
                String query = "UPDATE " + DB_Table + " SET username=?, hashedpassword=?, email=? WHERE id=?;";
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                if(!current_username.equals(newUsername)){
                    if(!checkUserAlreadyExist(newUsername)){
                        preparedStatement.setString(1,newUsername);
                        System.out.println("Username changed");
                    }else{
                        return false;
                    }
                }else{
                    preparedStatement.setString(1,current_username);
                    System.out.println("Username is the same as previous one");
                }
                String newHash = HashTool.hash(newPassword);
                preparedStatement.setString(2,newHash);
                System.out.println("Changed password");
                if(!current_email.equals(newEmail)){
                    if(!checkEmailAlreadyExist(newEmail)){
                        preparedStatement.setString(3,newEmail);
                        System.out.println("Email address changed");
                    }else{
                        return false;
                    }
                }else{
                    preparedStatement.setString(3,current_email);
                    System.out.println("Email address unchange");
                }
                preparedStatement.setString(4,id);
                preparedStatement.executeUpdate();
                modified = true;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }else{
            modified = false;
        }
        return modified;
    }

    public static boolean removeUserbyUsername(String username){
        if(!checkUserAlreadyExist(username)){
            return false;
        }
        try{
            String query = "DELETE FROM " + DB_Table + " WHERE username=?;";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.executeUpdate();
//            resultSet = preparedStatement.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public static boolean checkEmailAlreadyExist(String email){
        try{
            String query = "SELECT email FROM " + DB_Table + " WHERE email=?;";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1,email);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.first()){
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static User getUserbyUsername(String username){
        try{
            String query = "SELECT * FROM " + DB_Table + " WHERE username=?;";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.first()){
                User resultUser = new User();
                resultUser.setId(resultSet.getInt("id"));
                resultUser.setUser_name(resultSet.getString("username"));
                resultUser.setHashedPassword(resultSet.getString("hashedpassword"));
                resultUser.setEmail(resultSet.getString("email"));
                return resultUser;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static User getUserbyId(String id){
        try{
            String query = "SELECT * FROM " + DB_Table + " WHERE id=?;";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1,id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.first()){
                User resultUser = new User();
                resultUser.setId(resultSet.getInt("id"));
                resultUser.setUser_name(resultSet.getString("username"));
                resultUser.setHashedPassword(resultSet.getString("hashedpassword"));
                resultUser.setEmail(resultSet.getString("email"));
                return resultUser;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<User> getAllUsers(){
        ArrayList<User> result = new ArrayList<>();
        try{
            String query = "SELECT * FROM " + DB_Table + ";";
            preparedStatement = getConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                User cur_user = new User();
                cur_user.setId(resultSet.getInt("id"));
                cur_user.setUser_name(resultSet.getString("username"));
                cur_user.setHashedPassword(resultSet.getString("hashedpassword"));
                cur_user.setEmail(resultSet.getString("email"));
                result.add(cur_user);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

   // public static void main(String[] args) {
   //     Create_Table();
   // }
}
