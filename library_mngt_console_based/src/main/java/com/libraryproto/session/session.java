package com.libraryproto.session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class session {
    public static int loggedInUserId =-1;;
    public static String role_f = "";

    private Scanner sc;
    private Connection con;

    public session(Connection con, Scanner sc){
        this.sc = sc;
        this.con = con;
    }

    public void login_user() {
    String role = "user";

    while (true) {
        System.out.print("Enter the user-id: ");
        int input_user_id = sc.nextInt();
        System.out.print("Enter the password: ");
        String input_password = sc.next();

        String query = "SELECT * FROM users WHERE user_id = ? AND password_hash = ?";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, input_user_id);
            ps.setString(2, input_password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Successful login
                int user_id = rs.getInt("user_id");
                String name = rs.getString("name");

                loggedInUserId = user_id;
                role_f = role;

                System.out.println("Login successful. Welcome, " + name + "!");
                System.out.println("User ID: " + user_id);
                System.out.println("Name: " + name);
                break;
            } else {
                // No such user/pass
                System.out.println("Invalid user-id or password. Please try again.\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            break; // exit loop on error
        }
    }
}

    public void logout_user(){
        loggedInUserId = -1;
    }

    public void login_admin(){
        String role ="Adimn";

        while(true){
             System.out.print("Enter the admin-id: ");
             int input_admin_id = sc.nextInt();
             System.out.print("Enter the password: ");
             String input_password = sc.next();

             String query = "SELECT * FROM admins WHERE admin_id = ? AND password_hash = ?";
             try{
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, input_admin_id);
                ps.setString(2, input_password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                // Successful login
                int admin_id = rs.getInt("admin_id");
                String name = rs.getString("name");

                loggedInUserId = admin_id;
                role_f = role;

                System.out.println("Login successful. Welcome, " + name + "!");
                System.out.println("User ID: " + admin_id);
                System.out.println("Name: " + name);
                break;
            } else {
                // No such user/pass
                System.out.println("Invalid admin-id or password. Please try again.\n");
            }
            } catch (SQLException e) {
            e.printStackTrace();
            break; // exit loop on error
            }
        }
    } 


    public void logout_admin(){
        loggedInUserId = -1;
    }


    public void create_new_user(){
        String query = "INSERT INTO users (name, email, password_hash) VALUES ( ?, ?, ?)";
        System.out.println("Enter the name: ");
        String name = sc.next();
        System.out.println("Enter the email: ");
        String email = sc.next();
        System.out.println("Enter the password_hash: ");
        String password_hash = sc.next();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password_hash);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
            // Fetch the generated keys (usually just one key: user_id)
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int newId = rs.getInt(1); // First column of the generated keys
                System.out.println("New admin created successfully.");
                System.out.println("Your admin ID is: " + newId);
            } else {
                System.out.println("Admin created but couldn't retrieve admin ID.");
            }
        } else {
            System.out.println("Admin creation failed.");
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void create_new_admin(){
        String query = "INSERT INTO admins (name, email, password_hash) VALUES ( ?, ?, ?)";
        System.out.println("Enter the name: ");
        String name = sc.next();
        System.out.println("Enter the email: ");
        String email = sc.next();
        System.out.println("Enter the password_hash: ");
        String password_hash = sc.next();
        
        try {
            PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password_hash);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
            // Fetch the generated keys (usually just one key: user_id)
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int newId = rs.getInt(1); // First column of the generated keys
                System.out.println("New admin created successfully.");
                System.out.println("Your admin ID is: " + newId);
            } else {
                System.out.println("Admin created but couldn't retrieve admin ID.");
            }
        } else {
            System.out.println("Admin creation failed.");
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
