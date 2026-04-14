# 🍔 Online Food Ordering System

A full-stack **Swiggy/Zomato clone** built with Java Servlets, JSP, JDBC, and MySQL. Features a premium dark-themed responsive UI with session-based cart management and secure user authentication.

![Java](https://img.shields.io/badge/Java-11-orange?style=for-the-badge&logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![Maven](https://img.shields.io/badge/Maven-3.x-red?style=for-the-badge&logo=apache-maven)
![Servlet](https://img.shields.io/badge/Servlet-4.0-green?style=for-the-badge)

---

## ✨ Features

- 🔐 **User Authentication** — Register/Login with SHA-256 password hashing
- 🏪 **Restaurant Browsing** — Browse restaurants with ratings and cuisine info
- 📋 **Menu Management** — View categorized menus with item details and pricing
- 🛒 **Smart Cart System** — Session-based cart with restaurant-switch logic
- 📦 **Order Placement** — Checkout with order confirmation and database transaction management
- 🎨 **Premium Dark UI** — Modern, responsive design with glassmorphism effects

---

## 🏗️ Tech Stack

| Layer       | Technology                        |
|-------------|-----------------------------------|
| **Frontend**  | JSP, JSTL, HTML5, CSS3, JavaScript |
| **Backend**   | Java Servlets (Servlet 4.0)       |
| **Database**  | MySQL 8.0 with JDBC               |
| **Build Tool**| Apache Maven                      |
| **Server**    | Apache Tomcat 9                   |
| **Security**  | SHA-256 Password Hashing          |

---

## 📁 Project Structure

```
Online Food Ordering System using Java/
├── pom.xml
├── sql/
│   └── schema.sql                    # Database schema & seed data
├── src/main/
│   ├── java/com/foodapp/
│   │   ├── dao/                      # Data Access Objects (interfaces)
│   │   │   └── impl/                 # DAO implementations
│   │   ├── model/                    # POJO models
│   │   ├── servlet/                  # Servlet controllers
│   │   └── util/                     # Utility classes (DB, Password)
│   └── webapp/
│       ├── WEB-INF/
│       ├── css/                      # Stylesheets
│       ├── js/                       # JavaScript files
│       ├── index.jsp                 # Login page
│       ├── register.jsp              # Registration page
│       ├── home.jsp                  # Restaurant listing
│       ├── menu.jsp                  # Restaurant menu
│       ├── cart.jsp                  # Shopping cart
│       ├── checkout.jsp              # Checkout page
│       ├── order-confirmation.jsp    # Order confirmation
│       └── error.jsp                 # Error page
```

---

## ⚙️ Prerequisites

- **Java JDK** 11 or higher
- **Apache Maven** 3.x
- **MySQL** 8.0
- **Apache Tomcat** 9.x

---

## 🚀 Setup & Installation

### 1. Clone the Repository

```bash
git clone https://github.com/Harikrishnan1305/Online-Food-Ordering-System.git
cd Online-Food-Ordering-System
```

### 2. Set Up the Database

```bash
mysql -u root -p < sql/schema.sql
```

### 3. Configure Database Credentials

Edit `src/main/java/com/foodapp/util/DBConnection.java` and update:

```java
private static final String URL = "jdbc:mysql://localhost:3306/food_ordering_db";
private static final String USERNAME = "root";
private static final String PASSWORD = "YOUR_MYSQL_PASSWORD";  // ← Set your password
```

### 4. Build the Project

```bash
mvn clean package
```

### 5. Deploy to Tomcat

Copy the generated `target/FoodOrderingSystem.war` to your Tomcat `webapps/` directory, then start Tomcat.

### 6. Access the Application

Open your browser and navigate to:

```
http://localhost:8080/FoodOrderingSystem/
```

---

## 📚 Technical Documentation

For detailed technical documentation including system architecture, database design, API endpoints, security implementation, and deployment guide, see:

📄 **[TECHNICAL_DOCUMENTATION.md](TECHNICAL_DOCUMENTATION.md)**

---

## 📸 Architecture

```
┌───────────┐     ┌──────────────┐     ┌──────────┐     ┌─────────┐
│   Browser  │────▶│   Servlets   │────▶│   DAO    │────▶│  MySQL  │
│  (JSP/CSS) │◀────│ (Controller) │◀────│  (JDBC)  │◀────│   DB    │
└───────────┘     └──────────────┘     └──────────┘     └─────────┘
```

---

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

## 👨‍💻 Author

**Krishnan** — Built with ❤️ using Java
