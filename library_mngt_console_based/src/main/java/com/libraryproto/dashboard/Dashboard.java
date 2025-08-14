package com.libraryproto.dashboard;

import java.sql.Connection;
import java.util.Scanner;

import com.libraryproto.bookservice.book_S;
import com.libraryproto.session.session;

public class Dashboard {

    private Scanner sc;
    private Connection con;

    public Dashboard(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void startApp() {
        session ses = new session(con, sc);
        book_S bs = new book_S(con, sc);

        while (true) {
            System.out.println("\n====== LIBRARY MANAGEMENT SYSTEM ======");
            System.out.println("1. Login as User");
            System.out.println("2. Login as Admin");
            System.out.println("3. Register for new User");
            System.out.println("4. Register for new Admin");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    ses.login_user();
                    userMenu(bs);
                }
                case 2 -> {
                    ses.login_admin();
                    adminMenu(bs);
                }
                case 3 -> {
                    ses.create_new_user();
                }
                case 4 -> {
                    ses.create_new_admin();
                }
                case 5 -> {
                    System.out.println("Exiting... Thank you!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void userMenu(book_S bs) {
        while (true) {
            System.out.println("\n--- USER DASHBOARD ---");
            System.out.println("1. View Available Books");
            System.out.println("2. Search Book by Author");
            System.out.println("3. Search Book by Genre");
            System.out.println("4. View Description by Book ID");
            System.out.println("5. View My Issued Books");
            System.out.println("6. Issue Book");
            System.out.println("7. Return Book");
            System.out.println("8. Logout");

            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> bs.view_all_available_b();
                case 2 -> bs.search_by_author();
                case 3 -> bs.search_by_genre();
                case 4 -> bs.search_by_id_and_view_desc();
                case 5 -> bs.view_all_issued_b_by_user();
                case 6 -> {
                    bs.view_all_available_b();
                    System.out.print("Enter Book ID to issue: ");
                    int bookId = sc.nextInt();
                    bs.issue_book(bookId);
                }
                case 7 -> {
                    System.out.print("Enter Book ID to return: ");
                    int bookId = sc.nextInt();
                    bs.return_book(bookId);
                }
                case 8 -> {
                    session.loggedInUserId = -1;
                    System.out.println("Logged out successfully.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void adminMenu(book_S bs) {
        while (true) {
            System.out.println("\n--- ADMIN DASHBOARD ---");
            System.out.println("1. View All Books");
            System.out.println("2. Add Book");
            System.out.println("3. Delete Book");
            System.out.println("4. View All Issued Books");
            System.out.println("5. View Return Dates of Issued Books");
            System.out.println("6. Search Book by Author");
            System.out.println("7. Search Book by Genre");
            System.out.println("8. Check Book Issued to Which User");
            System.out.println("9. View All Books Issued to a User");
            System.out.println("10. Logout");

            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> bs.view_all_b();
                case 2 -> bs.add_b();
                case 3 -> bs.del_b();
                case 4 -> bs.view_all_issued_b();
                case 5 -> bs.view_all_issued_b_return_date();
                case 6 -> bs.search_by_author();
                case 7 -> bs.search_by_genre();
                case 8 -> bs.search_book_issued_to_which_user();
                case 9 -> bs.search_all_book_issued_to_user();
                case 10 -> {
                    session.loggedInUserId = -1;
                    System.out.println("Logged out successfully.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
