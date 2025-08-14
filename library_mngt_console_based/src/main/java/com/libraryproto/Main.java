package com.libraryproto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.libraryproto.dashboard.Dashboard;

public class Main {
        
        private static final String url = "jdbc:mysql://localhost:3306/library_mngt";
        private static final String username = "root";
        private static final String password = "NewStrongPassword@123";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Connection cn = DriverManager.getConnection(url, username, password);

            Dashboard dashboard = new Dashboard(cn, sc);
            dashboard.startApp();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        
        }
}