# 📋 Technical Documentation

## Online Food Ordering System

> A full-stack Swiggy/Zomato clone built with Java Servlets, JSP, JDBC, and MySQL

**Version:** 1.0-SNAPSHOT  
**Author:** Krishnan  
**Repository:** [GitHub — Online Food Ordering System](https://github.com/Harikrishnan1305/Online-Food-Ordering-System)  
**Last Updated:** April 2026

---

## Table of Contents

1. [Project Overview](#1-project-overview)
2. [System Architecture](#2-system-architecture)
3. [Technology Stack](#3-technology-stack)
4. [Project Structure](#4-project-structure)
5. [Database Design](#5-database-design)
6. [Backend Architecture](#6-backend-architecture)
7. [Frontend Architecture](#7-frontend-architecture)
8. [API / Servlet Endpoints](#8-api--servlet-endpoints)
9. [Security Implementation](#9-security-implementation)
10. [Session & Cart Management](#10-session--cart-management)
11. [Build & Deployment](#11-build--deployment)
12. [Configuration](#12-configuration)
13. [Testing](#13-testing)
14. [Future Enhancements](#14-future-enhancements)

---

## 1. Project Overview

### 1.1 Introduction

The **Online Food Ordering System** is a full-stack web application that simulates online food delivery platforms like Swiggy and Zomato. It enables users to browse restaurants, view menus, add items to a smart session-based cart, and place orders with checkout functionality. The application follows a layered MVC architecture with a clean separation of concerns.

### 1.2 Objectives

- Provide a responsive, premium dark-themed UI for browsing restaurants and ordering food
- Implement secure user authentication with SHA-256 password hashing
- Build a session-based cart system with intelligent restaurant-switch logic
- Support transactional order placement with database-level integrity
- Follow industry-standard DAO design patterns for data access

### 1.3 Key Features

| Feature | Description |
|---------|-------------|
| **User Authentication** | Register/Login with SHA-256 hashed passwords |
| **Restaurant Browsing** | View all restaurants with ratings, cuisine, and delivery time |
| **Restaurant Search** | Search by restaurant name or cuisine keyword |
| **Menu Viewing** | Browse categorized food items with price, rating, and images |
| **Smart Cart** | Session-based cart with single-restaurant enforcement |
| **Checkout & Order** | Place orders with address and payment mode selection |
| **Order Confirmation** | View order details with item breakdown post-checkout |
| **Responsive UI** | Premium glassmorphism dark theme, mobile-friendly |

---

## 2. System Architecture

### 2.1 High-Level Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                         CLIENT LAYER                            │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌───────────────┐   │
│  │ index.jsp│  │ home.jsp │  │ menu.jsp │  │   cart.jsp    │   │
│  │ (Login)  │  │(Listing) │  │ (Menu)   │  │   (Cart)      │   │
│  └────┬─────┘  └────┬─────┘  └────┬─────┘  └───────┬───────┘   │
│       │              │              │               │           │
│  ┌────┴──────────────┴──────────────┴───────────────┴────────┐  │
│  │              CSS (style.css) + JS (app.js)                │  │
│  └───────────────────────────────────────────────────────────┘  │
└─────────────────────────┬───────────────────────────────────────┘
                          │ HTTP (GET/POST)
┌─────────────────────────▼───────────────────────────────────────┐
│                      CONTROLLER LAYER                           │
│  ┌───────────┐  ┌──────────┐  ┌──────────┐  ┌──────────────┐   │
│  │LoginServlet│  │HomeServlet│  │MenuServlet│  │CartServlet  │   │
│  └─────┬─────┘  └─────┬────┘  └─────┬────┘  └──────┬───────┘   │
│        │               │              │              │          │
│  ┌─────┴───────────────┴──────────────┴──────────────┴──────┐   │
│  │           RegisterServlet  |  CheckoutServlet            │   │
│  │           LogoutServlet                                   │   │
│  └──────────────────────────────────────────────────────────┘   │
└─────────────────────────┬───────────────────────────────────────┘
                          │ Java Method Calls
┌─────────────────────────▼───────────────────────────────────────┐
│                    DATA ACCESS LAYER (DAO)                       │
│  ┌─────────────┐  ┌──────────────┐  ┌────────────┐  ┌────────┐ │
│  │  UserDAO    │  │RestaurantDAO │  │  MenuDAO   │  │OrderDAO│ │
│  │  Interface  │  │  Interface   │  │  Interface │  │Interface│ │
│  └──────┬──────┘  └──────┬───────┘  └─────┬──────┘  └───┬────┘ │
│         │                │                │              │      │
│  ┌──────▼──────┐  ┌──────▼───────┐  ┌─────▼──────┐  ┌───▼────┐ │
│  │UserDAOImpl  │  │RestaurantDAO │  │MenuDAOImpl │  │OrderDAO│ │
│  │             │  │    Impl      │  │            │  │  Impl  │ │
│  └──────┬──────┘  └──────┬───────┘  └─────┬──────┘  └───┬────┘ │
└─────────┼────────────────┼────────────────┼──────────────┼──────┘
          │                │                │              │
┌─────────▼────────────────▼────────────────▼──────────────▼──────┐
│                    UTILITY LAYER                                 │
│  ┌──────────────────┐    ┌──────────────────┐                   │
│  │  DBConnection    │    │  PasswordUtil    │                   │
│  │  (JDBC Manager)  │    │  (SHA-256 Hash)  │                   │
│  └────────┬─────────┘    └──────────────────┘                   │
└───────────┼─────────────────────────────────────────────────────┘
            │ JDBC
┌───────────▼─────────────────────────────────────────────────────┐
│                    DATABASE LAYER                                │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │               MySQL 8.0 — food_ordering_db               │   │
│  │  ┌───────┐ ┌─────────────┐ ┌──────┐ ┌───────┐ ┌───────┐ │   │
│  │  │ users │ │ restaurants │ │ menu │ │orders │ │order_ │ │   │
│  │  │       │ │             │ │      │ │       │ │ items │ │   │
│  │  └───────┘ └─────────────┘ └──────┘ └───────┘ └───────┘ │   │
│  └──────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

### 2.2 Design Pattern: MVC + DAO

The application follows the **Model-View-Controller (MVC)** architectural pattern augmented with the **Data Access Object (DAO)** pattern:

| Component | Role | Implementation |
|-----------|------|----------------|
| **Model** | Data representation (POJOs) | `com.foodapp.model.*` — User, Restaurant, Menu, Order, OrderItem, Cart, CartItem |
| **View** | User interface rendering | JSP pages with JSTL — `*.jsp` files |
| **Controller** | Request handling & business logic | `com.foodapp.servlet.*` — Servlet classes |
| **DAO** | Data access abstraction | `com.foodapp.dao.*` — Interfaces + JDBC implementations |

---

## 3. Technology Stack

### 3.1 Core Technologies

| Component | Technology | Version |
|-----------|-----------|---------|
| Programming Language | Java | 11+ |
| Web Framework | Java Servlets | 4.0.1 (Servlet API) |
| View Technology | JavaServer Pages (JSP) | 2.3.3 |
| Tag Library | JSTL (JSP Standard Tag Library) | 1.2 |
| Database | MySQL | 8.0 |
| JDBC Driver | MySQL Connector/J | 8.0.33 |
| Build Tool | Apache Maven | 3.x |
| Application Server | Apache Tomcat | 9.x |
| Packaging | WAR (Web Application Archive) | — |

### 3.2 Frontend Technologies

| Technology | Usage |
|-----------|-------|
| HTML5 | Semantic page structure |
| CSS3 | Custom dark theme with CSS variables, glassmorphism, animations |
| JavaScript | DOM manipulation, event handling, dynamic interactions |
| Google Fonts | Outfit (display), Inter (body) |

### 3.3 Maven Dependencies (`pom.xml`)

```xml
<dependencies>
    <!-- Servlet API 4.0 (provided by Tomcat) -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
        <scope>provided</scope>
    </dependency>

    <!-- JSP API (provided by Tomcat) -->
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>javax.servlet.jsp-api</artifactId>
        <version>2.3.3</version>
        <scope>provided</scope>
    </dependency>

    <!-- JSTL -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
    </dependency>

    <!-- MySQL JDBC Connector -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
</dependencies>
```

---

## 4. Project Structure

```
Online Food Ordering System using Java/
│
├── pom.xml                                 # Maven build configuration
├── README.md                               # Project documentation
├── TECHNICAL_DOCUMENTATION.md              # This file
├── .gitignore                              # Git ignore rules
│
├── sql/
│   └── schema.sql                          # Database DDL + seed data
│
└── src/main/
    ├── java/com/foodapp/
    │   │
    │   ├── model/                          # POJO Models (Data Entities)
    │   │   ├── User.java                   # User entity
    │   │   ├── Restaurant.java             # Restaurant entity
    │   │   ├── Menu.java                   # Menu item entity
    │   │   ├── Order.java                  # Order entity
    │   │   ├── OrderItem.java              # Order item entity
    │   │   ├── Cart.java                   # Session cart (HashMap-based)
    │   │   └── CartItem.java               # Individual cart item
    │   │
    │   ├── dao/                            # Data Access Object Interfaces
    │   │   ├── UserDAO.java                # User CRUD operations
    │   │   ├── RestaurantDAO.java          # Restaurant CRUD operations
    │   │   ├── MenuDAO.java                # Menu CRUD operations
    │   │   ├── OrderDAO.java               # Order + OrderItem operations
    │   │   └── impl/                       # JDBC Implementations
    │   │       ├── UserDAOImpl.java         # User DAO with PreparedStatement
    │   │       ├── RestaurantDAOImpl.java   # Restaurant DAO implementation
    │   │       ├── MenuDAOImpl.java         # Menu DAO implementation
    │   │       └── OrderDAOImpl.java        # Order DAO with transactions
    │   │
    │   ├── servlet/                        # Servlet Controllers
    │   │   ├── LoginServlet.java           # Authentication (GET/POST)
    │   │   ├── RegisterServlet.java        # User registration (GET/POST)
    │   │   ├── LogoutServlet.java          # Session invalidation (GET)
    │   │   ├── HomeServlet.java            # Restaurant listing + search (GET)
    │   │   ├── MenuServlet.java            # Menu display (GET)
    │   │   ├── CartServlet.java            # Cart operations (GET/POST)
    │   │   └── CheckoutServlet.java        # Checkout + order placement (GET/POST)
    │   │
    │   └── util/                           # Utility Classes
    │       ├── DBConnection.java           # MySQL JDBC connection manager
    │       └── PasswordUtil.java           # SHA-256 password hashing
    │
    └── webapp/
        ├── WEB-INF/
        │   └── web.xml                     # Deployment descriptor
        │
        ├── css/
        │   └── style.css                   # Global stylesheet (1335 lines)
        │
        ├── js/
        │   └── app.js                      # Client-side JavaScript
        │
        ├── index.jsp                       # Login page
        ├── register.jsp                    # Registration page
        ├── home.jsp                        # Restaurant listing page
        ├── menu.jsp                        # Restaurant menu page
        ├── cart.jsp                        # Shopping cart page
        ├── checkout.jsp                    # Checkout/payment page
        ├── order-confirmation.jsp          # Order confirmation page
        └── error.jsp                       # Custom error page
```

---

## 5. Database Design

### 5.1 Entity-Relationship Diagram

```
┌──────────────┐          ┌──────────────────┐
│    users     │          │   restaurants    │
├──────────────┤          ├──────────────────┤
│ PK id        │          │ PK id            │
│    name      │          │    name          │
│    username   │◄─────┐  │    cuisine       │
│    password   │      │  │    rating        │◄──────────┐
│    email     │      │  │    delivery_time  │           │
│    phone     │      │  │    image_url     │           │
│    address   │      │  │    is_active     │           │
│    role      │      │  └──────────┬───────┘           │
│    created_date│     │             │                    │
└──────┬───────┘      │             │ 1:N                │
       │              │             ▼                    │
       │              │  ┌──────────────────┐           │
       │              │  │      menu        │           │
       │              │  ├──────────────────┤           │
       │              │  │ PK id            │           │
       │              │  │ FK restaurant_id │───────────┘
       │              │  │    name          │◄──────┐
       │              │  │    description   │       │
       │              │  │    price         │       │
       │              │  │    rating        │       │
       │              │  │    image_url     │       │
       │              │  │    is_available  │       │
       │              │  └─────────────────┘       │
       │              │                             │
       │ 1:N          │                             │
       ▼              │                             │
┌──────────────┐      │                             │
│    orders    │      │                             │
├──────────────┤      │                             │
│ PK order_id  │      │                             │
│ FK user_id   │──────┘                             │
│ FK restaurant_id│                                 │
│    total_amount │                                 │
│    status      │                                  │
│    payment_mode│                                  │
│    address    │         ┌──────────────────┐      │
│    order_date │         │   order_items    │      │
└──────┬───────┘         ├──────────────────┤      │
       │                  │ PK order_item_id │      │
       │ 1:N              │ FK order_id      │──────┤
       └─────────────────►│ FK menu_id       │──────┘
                          │    quantity      │
                          │    price         │
                          └──────────────────┘
```

### 5.2 Table Definitions

#### `users` — Stores registered user accounts

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | INT | PK, AUTO_INCREMENT | Unique user identifier |
| `name` | VARCHAR(100) | NOT NULL | Full name |
| `username` | VARCHAR(50) | NOT NULL, UNIQUE | Login username |
| `password` | VARCHAR(255) | NOT NULL | SHA-256 hashed password |
| `email` | VARCHAR(100) | NOT NULL | Email address |
| `phone` | VARCHAR(15) | NULLABLE | Phone number |
| `address` | TEXT | NULLABLE | Delivery address |
| `role` | VARCHAR(20) | DEFAULT 'CUSTOMER' | User role (CUSTOMER/ADMIN) |
| `created_date` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Registration timestamp |

#### `restaurants` — Stores restaurant information

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | INT | PK, AUTO_INCREMENT | Unique restaurant identifier |
| `name` | VARCHAR(150) | NOT NULL | Restaurant name |
| `cuisine` | VARCHAR(100) | NOT NULL | Cuisine types |
| `rating` | DECIMAL(2,1) | DEFAULT 0.0 | Average rating (0.0–5.0) |
| `delivery_time` | INT | DEFAULT 30 | Estimated delivery time (minutes) |
| `image_url` | VARCHAR(500) | NULLABLE | Restaurant image URL |
| `is_active` | BOOLEAN | DEFAULT TRUE | Active/inactive status |

#### `menu` — Stores food items per restaurant

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | INT | PK, AUTO_INCREMENT | Unique menu item identifier |
| `restaurant_id` | INT | FK → restaurants(id), ON DELETE CASCADE | Owning restaurant |
| `name` | VARCHAR(150) | NOT NULL | Item name |
| `description` | TEXT | NULLABLE | Item description |
| `price` | DECIMAL(10,2) | NOT NULL | Price in INR |
| `rating` | DECIMAL(2,1) | DEFAULT 0.0 | Item rating |
| `image_url` | VARCHAR(500) | NULLABLE | Item image URL |
| `is_available` | BOOLEAN | DEFAULT TRUE | Availability status |

#### `orders` — Stores placed order records

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `order_id` | INT | PK, AUTO_INCREMENT | Unique order identifier |
| `user_id` | INT | FK → users(id), NOT NULL | Ordering user |
| `restaurant_id` | INT | FK → restaurants(id), NOT NULL | Source restaurant |
| `total_amount` | DECIMAL(10,2) | NOT NULL | Total order amount |
| `status` | ENUM | DEFAULT 'PLACED' | PLACED, CONFIRMED, PREPARING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED |
| `payment_mode` | VARCHAR(50) | NOT NULL | Payment method |
| `address` | TEXT | NOT NULL | Delivery address |
| `order_date` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Order placement time |

#### `order_items` — Stores individual items within an order

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `order_item_id` | INT | PK, AUTO_INCREMENT | Unique order item identifier |
| `order_id` | INT | FK → orders(order_id), ON DELETE CASCADE | Parent order |
| `menu_id` | INT | FK → menu(id), NOT NULL | Menu item reference |
| `quantity` | INT | NOT NULL, DEFAULT 1 | Ordered quantity |
| `price` | DECIMAL(10,2) | NOT NULL | Item price at time of order |

### 5.3 Seed Data

The schema includes pre-populated data:
- **8 Restaurants** — Biryani Palace, Pizza Hub, Dragon Wok, Burger Factory, Dosa Corner, Tandoori Nights, Sushi Studio, Chai & Snacks
- **40 Menu Items** — 5 items per restaurant with descriptions, prices, and images
- **1 Admin User** — Username: `admin`, Password: `admin123` (SHA-256 hashed)

---

## 6. Backend Architecture

### 6.1 Model Layer (`com.foodapp.model`)

All model classes implement `Serializable` for session storage compatibility.

| Class | Table Mapping | Key Properties |
|-------|--------------|----------------|
| `User` | `users` | id, name, username, password, email, phone, address, role, createdDate |
| `Restaurant` | `restaurants` | id, name, cuisine, rating, deliveryTime, imageUrl, isActive |
| `Menu` | `menu` | id, restaurantId, name, description, price, rating, imageUrl, isAvailable |
| `Order` | `orders` | orderId, userId, restaurantId, totalAmount, status, paymentMode, address, orderDate |
| `OrderItem` | `order_items` | orderItemId, orderId, menuId, quantity, price, menuName (transient) |
| `Cart` | — (session only) | restaurantId, restaurantName, items (HashMap<Integer, CartItem>) |
| `CartItem` | — (session only) | menuId, name, price, quantity, restaurantId, restaurantName, imageUrl |

### 6.2 DAO Layer (`com.foodapp.dao`)

The DAO pattern provides an abstraction layer between the business logic and the database:

#### Interface Definitions

**UserDAO:**
```java
boolean addUser(User user)                          // Register new user
User getUserById(int id)                            // Find user by ID
User getUserByUsername(String username)              // Find user by username
User validateUser(String username, String password) // Login authentication
boolean updateUser(User user)                       // Update user details
boolean deleteUser(int id)                          // Delete user
List<User> getAllUsers()                             // List all users (admin)
boolean isUsernameExists(String username)            // Check username availability
```

**RestaurantDAO:**
```java
boolean addRestaurant(Restaurant restaurant)        // Add restaurant
Restaurant getRestaurantById(int id)                // Find by ID
List<Restaurant> getAllRestaurants()                 // List all active restaurants
List<Restaurant> searchRestaurants(String keyword)  // Search by name/cuisine
boolean updateRestaurant(Restaurant restaurant)     // Update details
boolean deleteRestaurant(int id)                    // Delete restaurant
```

**MenuDAO:**
```java
boolean addMenu(Menu menu)                          // Add menu item
Menu getMenuById(int id)                            // Find by ID
List<Menu> getMenuByRestaurantId(int restaurantId)  // Get restaurant menu
boolean updateMenu(Menu menu)                       // Update item
boolean deleteMenu(int id)                          // Delete item
List<Menu> searchMenu(String keyword)               // Search across all menus
```

**OrderDAO:**
```java
int placeOrder(Order order, List<OrderItem> items)  // Place order (transactional)
Order getOrderById(int orderId)                     // Find order by ID
List<Order> getOrdersByUserId(int userId)           // Get user's orders
List<OrderItem> getOrderItems(int orderId)          // Get order items
boolean updateOrderStatus(int orderId, String status) // Update order status
```

### 6.3 DAO Implementation Details

All DAO implementations follow these patterns:

1. **PreparedStatements** — All queries use parameterized `PreparedStatement` to prevent SQL injection
2. **Connection Management** — Each method obtains a fresh connection via `DBConnection.getConnection()` and closes it in the `finally` block
3. **Resource Cleanup** — `closeResources(conn, ps, rs)` helper method ensures proper cleanup of Connection, PreparedStatement, and ResultSet
4. **ResultSet Mapping** — `mapResultSetToXxx(rs)` helper methods map database rows to POJO objects
5. **Transaction Support** — `OrderDAOImpl.placeOrder()` uses manual transaction management with commit/rollback

#### Transaction Management in `OrderDAOImpl.placeOrder()`

```java
conn.setAutoCommit(false);                    // Begin transaction

// 1. Insert order → retrieve auto-generated orderId
orderPs = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
orderPs.executeUpdate();
generatedKeys = orderPs.getGeneratedKeys();
generatedOrderId = generatedKeys.getInt(1);

// 2. Batch insert all order items
itemPs = conn.prepareStatement(itemSql);
for (OrderItem item : items) {
    itemPs.addBatch();                        // Batch for performance
}
itemPs.executeBatch();

conn.commit();                                // Commit on success

// On failure:
conn.rollback();                              // Rollback on error
```

### 6.4 Utility Layer (`com.foodapp.util`)

#### `DBConnection.java`
- Manages MySQL JDBC connections using `DriverManager`
- Loads the MySQL driver (`com.mysql.cj.jdbc.Driver`) once via a static initializer block
- Provides `getConnection()` — returns a fresh `Connection` per call
- Provides `closeConnection(conn)` — null-safe connection closing
- Connection URL: `jdbc:mysql://localhost:3306/food_ordering_db`

#### `PasswordUtil.java`
- Provides SHA-256 password hashing via `java.security.MessageDigest`
- `hashPassword(String)` — Returns 64-character hexadecimal hash
- `verifyPassword(String, String)` — Compares hashed input against stored hash
- Handles leading zero padding to ensure consistent 64-char output

---

## 7. Frontend Architecture

### 7.1 View Technology

The frontend uses **JavaServer Pages (JSP)** with **JSTL** for dynamic content rendering. All pages share a consistent dark-themed design.

### 7.2 Page Descriptions

| Page | URL Pattern | Description |
|------|------------|-------------|
| `index.jsp` | `/login` (GET) | Login form with username/password fields, error/success alerts |
| `register.jsp` | `/register` (GET) | Registration form with name, username, password, email, phone, address |
| `home.jsp` | `/home` (GET) | Restaurant grid with search bar, card layout, navigation bar |
| `menu.jsp` | `/menu?restaurantId=N` (GET) | Restaurant menu items with add-to-cart buttons |
| `cart.jsp` | `/cart` (GET) | Cart items with quantity controls, subtotals, total, checkout button |
| `checkout.jsp` | `/checkout` (GET) | Delivery address input, payment method selection, order summary |
| `order-confirmation.jsp` | POST redirect from `/checkout` | Order success with order ID, items, and total |
| `error.jsp` | 404/500 errors | Custom error page with home navigation |

### 7.3 CSS Architecture (`style.css`)

The stylesheet (1,335 lines) implements a comprehensive design system:

#### Design Tokens (CSS Custom Properties)

```css
:root {
    /* Primary Colors */
    --primary: #FF6B35;           /* Orange accent */
    --primary-light: #FF8C5A;
    --primary-dark: #E55A2B;

    /* Backgrounds */
    --bg-primary: #0D0D0D;        /* Deep black */
    --bg-secondary: #1A1A2E;      /* Dark navy */
    --bg-card: rgba(26, 26, 46, 0.8);  /* Glassmorphism card */

    /* Typography */
    --font-display: 'Outfit', sans-serif;   /* Headings */
    --font-body: 'Inter', sans-serif;       /* Body text */

    /* Spacing, Shadows, Transitions... */
}
```

#### Key CSS Features

| Feature | Implementation |
|---------|---------------|
| **Dark Theme** | Deep black/navy backgrounds with orange accent (#FF6B35) |
| **Glassmorphism** | `backdrop-filter: blur(20px)` on cards and navbar |
| **Gradient Text** | `-webkit-background-clip: text` for gradient headings |
| **Animations** | `fadeInUp`, `fadeIn`, `popIn` keyframe animations |
| **Responsive Grid** | `grid-template-columns: repeat(auto-fill, minmax(320px, 1fr))` |
| **Hover Effects** | Card lift (`translateY(-6px)`), image zoom (`scale(1.08)`) |
| **Mobile Responsive** | Media queries for tablets and mobile breakpoints |
| **Custom Scrollbar** | Themed `::-webkit-scrollbar` styles |

### 7.4 JavaScript (`app.js`)

Client-side JavaScript handles:
- Restaurant card click navigation (`data-href` attribute)
- Cart quantity increment/decrement interactions
- Form validation (client-side)
- Animation triggers and micro-interactions
- Dynamic UI updates

---

## 8. API / Servlet Endpoints

### 8.1 Servlet URL Mapping

All servlets use `@WebServlet` annotation-based mapping:

| Servlet | URL | HTTP Method | Action |
|---------|-----|-------------|--------|
| `LoginServlet` | `/login` | GET | Show login page (index.jsp) |
| `LoginServlet` | `/login` | POST | Authenticate user, create session |
| `RegisterServlet` | `/register` | GET | Show registration form |
| `RegisterServlet` | `/register` | POST | Validate & create new user |
| `LogoutServlet` | `/logout` | GET | Invalidate session, redirect to login |
| `HomeServlet` | `/home` | GET | List all restaurants (or search results) |
| `MenuServlet` | `/menu` | GET | Show menu for `?restaurantId=N` |
| `CartServlet` | `/cart` | GET | View cart contents |
| `CartServlet` | `/cart` | POST | Add/remove/update cart items (via `action` param) |
| `CheckoutServlet` | `/checkout` | GET | Show checkout form |
| `CheckoutServlet` | `/checkout` | POST | Place order, show confirmation |

### 8.2 Request Flow Diagrams

#### Login Flow

```
Browser                    LoginServlet              UserDAOImpl              MySQL
  │                            │                         │                     │
  │── GET /login ─────────────►│                         │                     │
  │◄── Forward index.jsp ──────│                         │                     │
  │                            │                         │                     │
  │── POST /login ────────────►│                         │                     │
  │  (username, password)      │── validateUser() ──────►│                     │
  │                            │                         │── SELECT WHERE ... ─►│
  │                            │                         │◄── ResultSet ────────│
  │                            │◄── User object ─────────│                     │
  │                            │                         │                     │
  │                            │── session.setAttribute()│                     │
  │◄── Redirect /home ────────│                         │                     │
```

#### Add to Cart Flow

```
Browser                    CartServlet              MenuDAOImpl               Cart
  │                            │                         │                     │
  │── POST /cart ─────────────►│                         │                     │
  │  (action=add, menuId=5)    │── getMenuById(5) ──────►│                     │
  │                            │◄── Menu object ─────────│                     │
  │                            │                         │                     │
  │                            │── cart.addItem() ──────────────────────────────►│
  │                            │   (checks restaurant match)                    │
  │                            │◄── boolean (wasReset) ─────────────────────────│
  │                            │                         │                     │
  │                            │── session.setAttribute("cart", cart)           │
  │◄── Redirect /cart ────────│                         │                     │
```

#### Order Placement Flow

```
Browser                 CheckoutServlet           OrderDAOImpl              MySQL
  │                          │                        │                      │
  │── POST /checkout ───────►│                        │                      │
  │  (address, paymentMode)  │                        │                      │
  │                          │── Build Order object    │                      │
  │                          │── Build OrderItem list  │                      │
  │                          │                        │                      │
  │                          │── placeOrder() ────────►│                      │
  │                          │                        │── BEGIN TRANSACTION ──►│
  │                          │                        │── INSERT order ───────►│
  │                          │                        │◄── Generated Key ─────│
  │                          │                        │── BATCH INSERT items ─►│
  │                          │                        │── COMMIT ─────────────►│
  │                          │◄── orderId ────────────│                      │
  │                          │                        │                      │
  │                          │── cart.clear()          │                      │
  │                          │── getOrderById() ──────►│                      │
  │                          │◄── Order + Items ───────│                      │
  │◄── Forward confirmation─│                        │                      │
```

### 8.3 Cart Actions (POST `/cart`)

| Action (`action` param) | Required Params | Behavior |
|--------------------------|----------------|----------|
| `add` | `menuId` | Fetches menu item from DB, adds to cart. Resets cart if different restaurant. |
| `remove` | `menuId` | Removes item from cart by menuId |
| `update` | `menuId`, `quantity` | Updates item quantity. Removes if quantity ≤ 0. |

---

## 9. Security Implementation

### 9.1 Password Security

| Feature | Implementation |
|---------|---------------|
| **Hashing Algorithm** | SHA-256 (`java.security.MessageDigest`) |
| **Hash Format** | 64-character hexadecimal string |
| **Storage** | Only hashed passwords stored in database |
| **Verification** | Hash comparison (not reverse decryption) |
| **Padding** | Leading zero padding ensures consistent 64-char output |

### 9.2 SQL Injection Prevention

All database queries use `PreparedStatement` with parameterized queries:

```java
// ✅ Safe — parameterized query
String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
ps = conn.prepareStatement(sql);
ps.setString(1, username);
ps.setString(2, hashedPassword);

// ❌ Never used — vulnerable string concatenation
// String sql = "SELECT * FROM users WHERE username='" + username + "'";
```

### 9.3 Session Management

| Feature | Configuration |
|---------|--------------|
| **Session Timeout** | 30 minutes (configured in both `web.xml` and `LoginServlet`) |
| **Session Validation** | All protected servlets check `session.getAttribute("user")` |
| **Session Invalidation** | `LogoutServlet` calls `session.invalidate()` |
| **Session Attributes** | `user` (User object), `userId`, `userName`, `cart`, `cartCount` |

### 9.4 Authentication Guard

Every protected servlet includes this guard:

```java
HttpSession session = request.getSession(false);
if (session == null || session.getAttribute("user") == null) {
    response.sendRedirect(request.getContextPath() + "/login");
    return;
}
```

### 9.5 Input Validation

| Validation | Location | Rules |
|-----------|----------|-------|
| Required fields | `RegisterServlet` | Name, username, password, email must not be empty |
| Password match | `RegisterServlet` | Password and confirmPassword must match |
| Password length | `RegisterServlet` | Minimum 6 characters |
| Username uniqueness | `RegisterServlet` → `UserDAO` | Checks DB before registration |
| Address required | `CheckoutServlet` | Delivery address must not be empty |
| Payment mode | `CheckoutServlet` | Payment method must be selected |
| Form data preservation | `RegisterServlet` | Form fields repopulated on validation errors |

---

## 10. Session & Cart Management

### 10.1 Cart Architecture

The cart is a pure session-based, in-memory data structure:

```java
public class Cart implements Serializable {
    private int restaurantId;                    // Current restaurant context
    private String restaurantName;
    private Map<Integer, CartItem> items;         // menuId → CartItem (O(1) lookup)
}
```

### 10.2 Restaurant-Switch Logic

The cart enforces a **single-restaurant policy** — items can only be added from one restaurant at a time:

```
Scenario 1: Empty cart + add item from Restaurant A
  → Cart is initialized with Restaurant A context

Scenario 2: Cart has Restaurant A items + add item from Restaurant A
  → Item quantity is incremented (or new item added)

Scenario 3: Cart has Restaurant A items + add item from Restaurant B
  → Cart is CLEARED, reset to Restaurant B, new item added
  → User sees "Cart was reset" notification
```

### 10.3 Cart Operations Complexity

| Operation | Time Complexity | Implementation |
|-----------|----------------|----------------|
| Add item | O(1) | `HashMap.put()` or `HashMap.get()` + quantity update |
| Remove item | O(1) | `HashMap.remove()` |
| Update quantity | O(1) | `HashMap.get()` + setter |
| Get total | O(n) | Iterates all items, sums `price × quantity` |
| Get item count | O(n) | Iterates all items, sums quantities |

---

## 11. Build & Deployment

### 11.1 Prerequisites

| Software | Required Version |
|----------|-----------------|
| Java JDK | 11 or higher |
| Apache Maven | 3.x |
| MySQL Server | 8.0 |
| Apache Tomcat | 9.x |

### 11.2 Setup Steps

#### Step 1: Clone Repository

```bash
git clone https://github.com/Harikrishnan1305/Online-Food-Ordering-System.git
cd Online-Food-Ordering-System
```

#### Step 2: Database Setup

```bash
mysql -u root -p < sql/schema.sql
```

This creates the `food_ordering_db` database, all tables, and inserts seed data (8 restaurants, 40 menu items, 1 admin user).

#### Step 3: Configure Database Credentials

Edit `src/main/java/com/foodapp/util/DBConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/food_ordering_db";
private static final String USERNAME = "root";
private static final String PASSWORD = "YOUR_MYSQL_PASSWORD";  // ← Set your password
```

#### Step 4: Build

```bash
mvn clean package
```

Output: `target/FoodOrderingSystem.war`

#### Step 5: Deploy

Copy `FoodOrderingSystem.war` to Tomcat's `webapps/` directory:

```bash
cp target/FoodOrderingSystem.war /path/to/tomcat/webapps/
```

#### Step 6: Access

```
http://localhost:8080/FoodOrderingSystem/
```

### 11.3 Maven Build Configuration

| Build Setting | Value |
|--------------|-------|
| Final Name | `FoodOrderingSystem` |
| Packaging | WAR |
| Java Source/Target | 11 |
| Encoding | UTF-8 |
| WAR Plugin Version | 3.3.2 |
| Compiler Plugin Version | 3.11.0 |

### 11.4 Deployment Descriptor (`web.xml`)

```xml
<web-app version="4.0">
    <display-name>Online Food Ordering System</display-name>

    <!-- Default page -->
    <welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>

    <!-- Session timeout: 30 minutes -->
    <session-config>
        <session-timeout>30</session-timeout>
    </session-config>

    <!-- Custom error pages -->
    <error-page>
        <error-code>404</error-code>
        <location>/error.jsp</location>
    </error-page>
    <error-page>
        <error-code>500</error-code>
        <location>/error.jsp</location>
    </error-page>
</web-app>
```

---

## 12. Configuration

### 12.1 Database Configuration

| Property | Value |
|----------|-------|
| JDBC URL | `jdbc:mysql://localhost:3306/food_ordering_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC` |
| Driver Class | `com.mysql.cj.jdbc.Driver` |
| Database Name | `food_ordering_db` |
| Default Username | `root` |
| Password | User-configured |

### 12.2 Server Configuration

| Property | Value |
|----------|-------|
| Application Server | Apache Tomcat 9.x |
| Context Path | `/FoodOrderingSystem` |
| Default Port | 8080 |
| Session Timeout | 30 minutes |
| Servlet API Version | 4.0 |
| Welcome File | `index.jsp` |

### 12.3 Default Credentials

| User | Username | Password | Role |
|------|----------|----------|------|
| Admin | `admin` | `admin123` | ADMIN |

> ⚠️ **Note:** Change the default admin password after deployment.

---

## 13. Testing

### 13.1 Manual Testing Checklist

| # | Test Case | Expected Result |
|---|-----------|----------------|
| 1 | Access `/login` without session | Login page displayed |
| 2 | Login with valid credentials | Redirected to `/home` |
| 3 | Login with invalid credentials | Error message shown |
| 4 | Register new user | Success message, redirect to login |
| 5 | Register with existing username | Error: "Username already taken" |
| 6 | Browse restaurants on `/home` | Grid of restaurant cards |
| 7 | Search restaurants by cuisine | Filtered results displayed |
| 8 | View menu for a restaurant | Menu items with prices shown |
| 9 | Add item to cart | Cart badge count updates |
| 10 | Add item from different restaurant | Cart reset with notification |
| 11 | Update item quantity in cart | Subtotal recalculated |
| 12 | Remove item from cart | Item removed, total updated |
| 13 | Proceed to checkout | Address and payment form shown |
| 14 | Place order | Order confirmation with details |
| 15 | Access protected page without login | Redirected to login |
| 16 | Logout | Session invalidated, redirected to login |

### 13.2 Database Transaction Test

| # | Test Case | Expected Result |
|---|-----------|----------------|
| 1 | Place order with valid data | Order and items inserted atomically |
| 2 | Simulate DB failure during item insert | Transaction rolled back, no partial data |

---

## 14. Future Enhancements

| Enhancement | Description | Priority |
|-------------|-------------|----------|
| **Order History** | User dashboard with past order tracking | High |
| **Admin Panel** | Restaurant/menu CRUD, order management | High |
| **Payment Gateway** | Razorpay/Stripe integration | Medium |
| **Real-time Tracking** | WebSocket-based order status updates | Medium |
| **Email Notifications** | Order confirmation/status emails | Medium |
| **Reviews & Ratings** | User reviews for restaurants and menu items | Low |
| **Connection Pooling** | Replace DriverManager with HikariCP or DBCP | Medium |
| **REST API** | JSON endpoints for mobile app integration | Medium |
| **Image Upload** | Local image storage for menu items | Low |
| **Caching** | Redis caching for restaurant/menu data | Low |

---

## 15. Glossary

| Term | Definition |
|------|-----------|
| **DAO** | Data Access Object — abstraction layer for database operations |
| **JDBC** | Java Database Connectivity — API for database access |
| **JSTL** | JSP Standard Tag Library — tag-based templating for JSP |
| **JSP** | JavaServer Pages — server-side rendering technology |
| **MVC** | Model-View-Controller — architectural design pattern |
| **POJO** | Plain Old Java Object — simple Java class with getters/setters |
| **WAR** | Web Application Archive — deployment package for Java web apps |
| **SHA-256** | Secure Hash Algorithm — 256-bit cryptographic hash function |

---

> 📝 **Document generated for the Online Food Ordering System project**  
> **Author:** Krishnan | **Date:** April 2026
