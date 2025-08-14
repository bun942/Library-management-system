# Library Management System

## 📌 Overview
The **Library Management System** is a console-based Java application that manages books, users, and book issuance using **Java + JDBC** connected to a **MySQL database**.  
It supports secure user authentication with **encrypted passwords** and implements database-level automation with **SQL triggers**.

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

## 🗄 Database Structure

### 1. **books**
| Column Name | Data Type | Description |
|-------------|-----------|-------------|
| `book_id`   | INT (PK, AUTO_INCREMENT) | Unique ID for each book |
| `title`     | VARCHAR(255) | Book title |
| `author`    | VARCHAR(255) | Author name |
| `genre`     | VARCHAR(100) | Genre/category |
| `quantity`  | INT | Available quantity |

---

### 2. **users**
| Column Name | Data Type | Description |
|-------------|-----------|-------------|
| `user_id`   | INT (PK, AUTO_INCREMENT) | Unique ID for each user |
| `username`  | VARCHAR(100) | User's name |
| `password`  | VARCHAR(255) | Encrypted password |
| `role`      | ENUM('admin','user') | Role type |

---

### 3. **issued_books**
| Column Name | Data Type | Description |
|-------------|-----------|-------------|
| `issue_id`  | INT (PK, AUTO_INCREMENT) | Unique ID for each issue record |
| `book_id`   | INT (FK) | Book ID from `books` |
| `user_id`   | INT (FK) | User ID from `users` |
| `issue_date`| DATE | Date of book issue |
| `return_date` | DATE | Date of return (NULL if not returned) |

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
