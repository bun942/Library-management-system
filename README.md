# Library Management System

## 📌 Overview
The **Library Management System** is a console-based Java application that manages books, users, and book issuance using **Java + JDBC** connected to a **MySQL database**.  
It supports secure user authentication with **passwords** and implements database-level automation with **SQL triggers**.

---

## 🛠 Tech Stack
- **Java** (Core Java, JDBC for database connectivity)
- **MySQL** (Relational database for storing books, users, and issued books data)
- **SQL Triggers** (For automatic updates in issued books and availability counts)
- **BCrypt** (for password encryption)

---

## 🎯 Features
- **Add New Books** with details like title, author, genre, and quantity
- **View Books** (all books or by specific criteria)
- **Issue Books** to registered users
- **Return Books** and update availability
- **Secure Login System** for users and admins
- **Encrypted Password Storage** in MySQL
- **Database Triggers** for auto-updating book counts when issued/returned

---

## ⚡ SQL Triggers

### 1. **Decrease Quantity When Issued**
```sql
CREATE TRIGGER decrease_quantity
AFTER INSERT ON issued_books
FOR EACH ROW
UPDATE books
SET quantity = quantity - 1
WHERE book_id = NEW.book_id;
