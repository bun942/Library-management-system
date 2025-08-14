package com.libraryproto.bookservice;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Scanner;

import com.libraryproto.session.session;


public class book_S {

    private Scanner sc;
    private Connection con;

    public book_S(Connection con, Scanner sc){
        this.sc = sc;
        this.con = con;
    }


//extra method for description wraping
    public static String wrapText(String text, int lineLength) {
        StringBuilder wrapped = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            int end = Math.min(i + lineLength, text.length());
            wrapped.append(text, i, end).append("\n");
            i = end;
        }
        return wrapped.toString();
    }

    //add the books method()
    public void add_b(){
        
        System.out.print("Enter the title of the book: ");
        String title = sc.nextLine();
        System.out.print("Enter the author of the book: ");
        String author = sc.nextLine();
        System.out.print("Enter the genre of the book: ");
        String genre = sc.nextLine();

        System.out.print("Enter the description of the book: ");
        String description = sc.nextLine();
        String query = "INSERT INTO BOOKS (title,author,genre,description ) VALUES(?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, genre);
            ps.setString(4, description);
            int affectedrow = ps.executeUpdate();
            if(affectedrow>0){
                System.out.println("************************");
                System.out.println("BOOK ADDED SUCCESSFULLY");
                System.out.print("************************");
            }else{
                System.out.println("************************");
                System.out.println("BOOK NOT ADDED");
                System.out.print("************************");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //DELETE the books method()
    public void del_b(){
        view_all_available_b();
        System.out.println("**********************************");
        System.out.print("Enter the Id of the book to delete: ");
        int id = sc.nextInt();
        System.out.println("**********************************");


        String query = "DELETE FROM books WHERE book_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int affectedrow = ps.executeUpdate();
            if(affectedrow>0){
                System.out.println("*************************");
                System.out.println("BOOK REMOVED SUCCESSFULLY");
                System.out.println("*************************");
            }else{
                System.out.println("*************************");
                System.out.println("BOOK NOT REMOVED");
                System.out.println("*************************");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //SEARCH METHODS()
    //search the books by author method()
    public void search_by_author(){

        // view_all_available_b();
        System.out.println("**********************************");
        System.out.print("Enter the name of the author: ");
        String author_name = sc.next();
        System.out.println("**********************************");

        String query = "SELECT * FROM BOOKS WHERE author LIKE ?";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, '%' + author_name + '%');
            ResultSet rs = ps.executeQuery();
            System.out.println("LIST OF ALL BOOKS BY AUTHOR:");
            System.out.println("+---------+-------------------------+-----------------+-----------+---------------+");
            System.out.println("| book_id | title                   | author          | status    | genre         |");
            System.out.println("+---------+-------------------------+-----------------+-----------+---------------+");
            while(rs.next()){
                int book_id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String status = rs.getString("status");
                String genre = rs.getString("genre");
                System.out.printf("|%-9s|%-25s|%-17s|%-11s|%-15s|\n",book_id, title, author, status,genre);
                System.out.print("+---------+-------------------------+-----------------+-----------+---------------+");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //search the books by GENRE method()
    public void search_by_genre(){

        view_all_available_b();
        System.out.println("**********************************");
        System.out.print("Enter the genre of the book: ");
        String genre_name = sc.next();
        System.out.println("**********************************");

        String query = "SELECT * FROM BOOKS WHERE genre = ?";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, genre_name);
            ResultSet rs = ps.executeQuery();
            System.err.println("LIST OF ALL BOOKS BY GENRE:");
            System.err.println("+---------+-------------------------+------------+-----------+---------------+");
            System.err.println("| book_id | title                   | author     | status    | genre         |");
            System.err.println("+---------+-------------------------+------------+-----------+---------------+");
            while(rs.next()){
                int book_id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String status = rs.getString("status");
                String genre = rs.getString("genre");
                System.err.printf("|%-9s|%-25s|%-12s|%-11s|%-15s|\n",book_id, title, author, status,genre);
                System.err.print("+---------+-------------------------+------------+-----------+---------------+");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //search the book by id and view the book's description method()
    public void search_by_id_and_view_desc(){
        view_all_b();
        System.out.println("**********************************");
        System.err.print("Enter the book id: ");
        int book_id_n = sc.nextInt();
        System.out.println("**********************************");

        String query ="SELECT * FROM books WHERE book_id = ?";
         try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, book_id_n);
            ResultSet rs = ps.executeQuery();
            System.err.println("DESCRIPTION OF THE BOOK: ");
            while(rs.next()){
                int book_id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String status = rs.getString("status");
                String genre = rs.getString("genre");
                String description = rs.getString("description");
                System.out.println("book_id:" +book_id);
                System.out.println("title: " +title);
                System.out.println("author: " +author);
                System.out.println("status: " +status);
                System.out.println("genre: " +genre);
                System.out.println("description: ");
                System.out.println(wrapText(description, 80));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //search_book_issued_to_which_user() 
    public void search_book_issued_to_which_user() {
        //
        System.out.print("Enter the ID of the book: ");
        int bookId = sc.nextInt();

        String query = "SELECT t.user_id, b.title, b.author FROM transactions t INNER JOIN books b ON t.book_id = b.book_id WHERE t.book_id = ? AND t.returned_status = 'No'";
    
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String title = rs.getString("title");
                String author = rs.getString("author");

                System.out.println("Book is currently issued to:");
                System.out.println("User ID: " + userId);
                System.out.println("Title  : " + title);
                System.out.println("Author : " + author);
            } else {
                System.out.println("This book is not currently issued.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
}

    //search_all_book_issued_to_user() 
    public void search_all_book_issued_to_user() {
        System.out.println("Enter the id of the user: ");
        int user = sc.nextInt();

        String query = "SELECT t.user_id, b.title, b.author FROM transactions t INNER JOIN books b ON t.book_id = b.book_id WHERE t.user_id = ? AND t.returned_status = 'No'";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, user);
            ResultSet rs = ps.executeQuery();

        // System.err.println("LIST OF BOOKS ISSUED TO USER ID: " + Session.loggedInUserId);
            System.err.println("+---------+-------------------------+------------+");
            System.err.println("| user_id | title                   | author     |");
            System.err.println("+---------+-------------------------+------------+");

            boolean found = false;
            while (rs.next()) {
                found = true;
                int userId = rs.getInt("user_id");
                String title = rs.getString("title");
                String author = rs.getString("author");

                System.err.printf("|%-9s|%-25s|%-12s|\n", userId, title, author);
                System.err.println("+---------+-------------------------+------------+");
            }

            if (!found) {
                System.out.println("No books currently issued to this user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
}

    //methods for view books all and view book isseued to user
    //view all books method()
    public void view_all_b(){
        String query = "SELECT * FROM books";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.err.println("LIST OF ALL BOOKS");
            System.err.println("+---------+-------------------------+------------+-----------+---------------+");
            System.err.println("| book_id | title                   | author     | status    | genre         |");
            System.err.println("+---------+-------------------------+------------+-----------+---------------+");
            while(rs.next()){
                int book_id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String status = rs.getString("status");
                String genre = rs.getString("genre");
                System.err.printf("|%-9s|%-25s|%-12s|%-11s|%-15s|\n",book_id, title, author, status,genre);
                System.err.println("+---------+-------------------------+------------+-----------+---------------+");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //view all the available books method()
    public void view_all_available_b(){
        String query = "SELECT * FROM books WHERE status='Available'";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.err.println("LIST OF ALL BOOKS THAT ARE AVAILABLE");
            System.err.println("+---------+-------------------------+------------+-----------+---------------+");
            System.err.println("| book_id | title                   | author     | status    | genre         |");
            System.err.println("+---------+-------------------------+------------+-----------+---------------+");
            while(rs.next()){
                int book_id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String status = rs.getString("status");
                String genre = rs.getString("genre");
                System.err.printf("|%-9s|%-25s|%-12s|%-11s|%-15s|\n",book_id, title, author, status,genre);
                System.err.println("+---------+-------------------------+------------+-----------+---------------+");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //view all the NOT available books method()
    public void view_all_not_available_b(){
        String query = "SELECT * FROM books WHERE status='NOT Available'";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.err.println("LIST OF ALL BOOKS THAT ARE NOT AVAILABLE");
            System.err.println("+---------+-------------------------+------------+-----------+---------------+");
            System.err.println("| book_id | title                   | author     | status    | genre         |");
            System.err.println("+---------+-------------------------+------------+-----------+---------------+");
            while(rs.next()){
                int book_id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String status = rs.getString("status");
                String genre = rs.getString("genre");
                System.err.printf("|%-9s|%-25s|%-12s|%-11s|%-15s|\n",book_id, title, author, status,genre);
                System.err.println("+---------+-------------------------+------------+-----------+---------------+");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //view all issued books method()
    public void view_all_issued_b(){
        String query = "SELECT t.book_id,t.user_id,b.title,b.author,b.genre FROM books as b INNER JOIN transactions as t ON b.book_id = t.book_id WHERE t.returned_status='No'";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.err.println("LIST OF ALL ISSUED BOOKS");
            System.err.println("+---------+---------+-------------------------+------------+---------------+");
            System.err.println("| user_id | book_id | title                   | author     | genre         |");
            System.err.println("+---------+---------+-------------------------+------------+---------------+");
            while(rs.next()){
                int user_id = rs.getInt("user_id");
                int book_id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                System.err.printf("|%-9s|%-9s|%-25s|%-12s|%-15s|\n",user_id,book_id, title, author, genre);
                System.err.println("+---------+---------+-------------------------+------------+---------------+");
    }
}
catch (SQLException e){
    e.printStackTrace();
}
    }

    //view all issued books by usermethod()
    public void view_all_issued_b_by_user(){
        String query = "SELECT t.book_id,b.title,b.author,b.genre FROM books as b INNER JOIN transactions as t ON b.book_id = t.book_id WHERE t.returned_status='No' AND t.user_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, session.loggedInUserId);
            ResultSet rs = ps.executeQuery();
            System.err.println("LIST OF ALL ISSUED BOOKS");
            System.err.println("+---------+-------------------------+------------+---------------+");
            System.err.println("| book_id | title                   | author     | genre         |");
            System.err.println("+---------+-------------------------+------------+---------------+");
            while(rs.next()){
                int book_id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                System.err.printf("|%-9s|%-25s|%-12s|%-15s|\n",book_id, title, author, genre);
                System.err.println("+---------+-------------------------+------------+---------------+");
    }
}
catch (SQLException e){
    e.printStackTrace();
}
    }

    //view all the book return date
    public void view_all_issued_b_return_date() {
    String query = "SELECT t.return_date, t.book_id, t.user_id, b.title, b.author, b.genre FROM books AS b INNER JOIN transactions AS t ON b.book_id = t.book_id WHERE t.returned_status = 'No'";
    try {
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        System.err.println("LIST OF ALL ISSUED BOOKS WITH RETURN DATES");
        System.err.println("+---------+---------+-------------------------+------------+---------------+--------------+");
        System.err.println("| user_id | book_id | title                   | author     | genre         | return_date  |");
        System.err.println("+---------+---------+-------------------------+------------+---------------+--------------+");

        while (rs.next()) {
            int user_id = rs.getInt("user_id");
            int book_id = rs.getInt("book_id");
            String title = rs.getString("title");
            String author = rs.getString("author");
            String genre = rs.getString("genre");
            Date return_date = rs.getDate("return_date");

            System.err.printf("|%-9d|%-9d|%-25s|%-12s|%-15s|%-14s|\n",
                    user_id, book_id, title, author, genre, return_date.toString());
            System.err.println("+---------+---------+-------------------------+------------+---------------+--------------+");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    //issue and return book methods
    //issue mothod by user 
    public void issue_book(int bookId) {
    String query = "INSERT INTO transactions (user_id, book_id, issue_date, return_date) VALUES (?,?,?,?)";
    try {
        PreparedStatement ps = con.prepareStatement(query);

        System.out.println("**********************************");
        // current user from session
        ps.setInt(1, session.loggedInUserId);
        ps.setInt(2, bookId);
        Date issueDate = new Date(System.currentTimeMillis());
        ps.setDate(3, issueDate);

        // return date = 30 days from today
        Calendar cal = Calendar.getInstance();
        cal.setTime(issueDate);
        cal.add(Calendar.DAY_OF_MONTH, 30);
        Date returnDate = new Date(cal.getTimeInMillis());
        ps.setDate(4, returnDate);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("**********************************");
            System.out.println("Book issued. Return by: " + returnDate);
            System.out.println("**********************************");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

 //return issue mothod by user 
    public void return_book(int bookId) {
    String query = "DELETE FROM transactions WHERE user_id = ? AND book_id = ?";
    try {
        PreparedStatement ps = con.prepareStatement(query);

        // current user from session
        ps.setInt(1, session.loggedInUserId);
        ps.setInt(2, bookId);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("No matching book found for return.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
    
