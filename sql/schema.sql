-- ============================================================
-- Online Food Ordering System — Database Schema
-- Database: food_ordering_db
-- ============================================================

CREATE DATABASE IF NOT EXISTS food_ordering_db;
USE food_ordering_db;

-- ============================================================
-- 1. USERS TABLE
-- ============================================================
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    address TEXT,
    role VARCHAR(20) DEFAULT 'CUSTOMER',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- 2. RESTAURANTS TABLE
-- ============================================================
CREATE TABLE IF NOT EXISTS restaurants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    cuisine VARCHAR(100) NOT NULL,
    rating DECIMAL(2,1) DEFAULT 0.0,
    delivery_time INT DEFAULT 30,
    image_url VARCHAR(500),
    is_active BOOLEAN DEFAULT TRUE
);

-- ============================================================
-- 3. MENU TABLE
-- ============================================================
CREATE TABLE IF NOT EXISTS menu (
    id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT NOT NULL,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    rating DECIMAL(2,1) DEFAULT 0.0,
    image_url VARCHAR(500),
    is_available BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE
);

-- ============================================================
-- 4. ORDERS TABLE
-- ============================================================
CREATE TABLE IF NOT EXISTS orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    restaurant_id INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status ENUM('PLACED','CONFIRMED','PREPARING','OUT_FOR_DELIVERY','DELIVERED','CANCELLED') DEFAULT 'PLACED',
    payment_mode VARCHAR(50) NOT NULL,
    address TEXT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

-- ============================================================
-- 5. ORDER ITEMS TABLE
-- ============================================================
CREATE TABLE IF NOT EXISTS order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    menu_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES menu(id)
);

-- ============================================================
-- SEED DATA: Restaurants
-- ============================================================
INSERT INTO restaurants (name, cuisine, rating, delivery_time, image_url, is_active) VALUES
('Biryani Palace', 'South Indian, Biryani', 4.5, 30, 'https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=400', TRUE),
('Pizza Hub', 'Italian, Pizza', 4.3, 25, 'https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=400', TRUE),
('Dragon Wok', 'Chinese, Asian', 4.2, 35, 'https://images.unsplash.com/photo-1512058564366-18510be2db19?w=400', TRUE),
('Burger Factory', 'American, Fast Food', 4.1, 20, 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400', TRUE),
('Dosa Corner', 'South Indian, Vegetarian', 4.6, 25, 'https://images.unsplash.com/photo-1630383249896-424e482df921?w=400', TRUE),
('Tandoori Nights', 'North Indian, Mughlai', 4.4, 35, 'https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?w=400', TRUE),
('Sushi Studio', 'Japanese, Sushi', 4.7, 40, 'https://images.unsplash.com/photo-1579871494447-9811cf80d66c?w=400', TRUE),
('Chai & Snacks', 'Street Food, Snacks', 4.0, 15, 'https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?w=400', TRUE);

-- ============================================================
-- SEED DATA: Menu Items
-- ============================================================

-- Biryani Palace (restaurant_id = 1)
INSERT INTO menu (restaurant_id, name, description, price, rating, image_url) VALUES
(1, 'Chicken Biryani', 'Aromatic basmati rice layered with tender chicken, herbs, and spices', 249.00, 4.6, 'https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=300'),
(1, 'Mutton Biryani', 'Slow-cooked mutton pieces with fragrant long-grain rice', 349.00, 4.7, 'https://images.unsplash.com/photo-1642821373181-696a54913e93?w=300'),
(1, 'Veg Biryani', 'Mixed vegetables cooked with aromatic basmati rice and spices', 199.00, 4.3, 'https://images.unsplash.com/photo-1604908176997-125f25cc6f3d?w=300'),
(1, 'Egg Biryani', 'Boiled eggs in a bed of flavored rice with traditional spices', 179.00, 4.2, 'https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=300'),
(1, 'Raita', 'Cool yogurt with cucumber, onion, and mild spices', 49.00, 4.0, 'https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?w=300');

-- Pizza Hub (restaurant_id = 2)
INSERT INTO menu (restaurant_id, name, description, price, rating, image_url) VALUES
(2, 'Margherita Pizza', 'Classic pizza with fresh mozzarella, tomato sauce, and basil', 299.00, 4.4, 'https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=300'),
(2, 'Pepperoni Pizza', 'Loaded with spicy pepperoni slices and melted cheese', 399.00, 4.5, 'https://images.unsplash.com/photo-1628840042765-356cda07504e?w=300'),
(2, 'Veggie Supreme', 'Topped with bell peppers, olives, mushrooms, and onions', 349.00, 4.3, 'https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=300'),
(2, 'Garlic Bread', 'Crispy bread with garlic butter and herb seasoning', 129.00, 4.1, 'https://images.unsplash.com/photo-1619535860434-ba1d8fa12536?w=300'),
(2, 'Pasta Alfredo', 'Creamy white sauce pasta with mushrooms and herbs', 249.00, 4.2, 'https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?w=300');

-- Dragon Wok (restaurant_id = 3)
INSERT INTO menu (restaurant_id, name, description, price, rating, image_url) VALUES
(3, 'Veg Fried Rice', 'Wok-tossed rice with fresh vegetables and soy sauce', 179.00, 4.1, 'https://images.unsplash.com/photo-1512058564366-18510be2db19?w=300'),
(3, 'Chicken Manchurian', 'Crispy chicken in tangy manchurian sauce', 229.00, 4.3, 'https://images.unsplash.com/photo-1525755662778-989d0524087e?w=300'),
(3, 'Hakka Noodles', 'Stir-fried noodles with vegetables and Indo-Chinese spices', 189.00, 4.2, 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=300'),
(3, 'Spring Rolls', 'Crispy rolls stuffed with seasoned vegetables', 149.00, 4.0, 'https://images.unsplash.com/photo-1606525437817-8e1bfece2f73?w=300'),
(3, 'Sweet & Sour Chicken', 'Tender chicken pieces in a sweet and sour glaze', 259.00, 4.4, 'https://images.unsplash.com/photo-1525755662778-989d0524087e?w=300');

-- Burger Factory (restaurant_id = 4)
INSERT INTO menu (restaurant_id, name, description, price, rating, image_url) VALUES
(4, 'Classic Beef Burger', 'Juicy beef patty with lettuce, tomato, and special sauce', 199.00, 4.3, 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=300'),
(4, 'Chicken Zinger', 'Crispy fried chicken fillet with spicy mayo and coleslaw', 179.00, 4.2, 'https://images.unsplash.com/photo-1606755962773-d324e0a13086?w=300'),
(4, 'Veggie Burger', 'Plant-based patty with fresh veggies and herb dressing', 149.00, 4.0, 'https://images.unsplash.com/photo-1550547660-d9450f859349?w=300'),
(4, 'French Fries', 'Golden crispy fries with seasoning', 99.00, 4.1, 'https://images.unsplash.com/photo-1573080496219-bb080dd4f877?w=300'),
(4, 'Chocolate Shake', 'Rich and creamy chocolate milkshake', 129.00, 4.4, 'https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=300');

-- Dosa Corner (restaurant_id = 5)
INSERT INTO menu (restaurant_id, name, description, price, rating, image_url) VALUES
(5, 'Masala Dosa', 'Crispy crepe filled with spiced potato masala', 99.00, 4.7, 'https://images.unsplash.com/photo-1630383249896-424e482df921?w=300'),
(5, 'Mysore Masala Dosa', 'Dosa with spicy red chutney and potato filling', 119.00, 4.6, 'https://images.unsplash.com/photo-1668236543090-82eba5ee5976?w=300'),
(5, 'Idli Sambar', 'Soft steamed rice cakes served with lentil soup', 79.00, 4.5, 'https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=300'),
(5, 'Pongal', 'Savory rice and lentil dish tempered with ghee', 89.00, 4.3, 'https://images.unsplash.com/photo-1630383249896-424e482df921?w=300'),
(5, 'Filter Coffee', 'Traditional South Indian filter coffee', 39.00, 4.8, 'https://images.unsplash.com/photo-1461023058943-07fcbe16d735?w=300');

-- Tandoori Nights (restaurant_id = 6)
INSERT INTO menu (restaurant_id, name, description, price, rating, image_url) VALUES
(6, 'Butter Chicken', 'Tender chicken in rich, creamy tomato-based gravy', 299.00, 4.6, 'https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?w=300'),
(6, 'Dal Makhani', 'Slow-cooked black lentils in buttery, creamy gravy', 199.00, 4.4, 'https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=300'),
(6, 'Tandoori Roti', 'Whole wheat bread baked in clay oven', 29.00, 4.2, 'https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?w=300'),
(6, 'Paneer Tikka', 'Marinated cottage cheese grilled to perfection', 249.00, 4.5, 'https://images.unsplash.com/photo-1567188040759-fb8a883dc6d8?w=300'),
(6, 'Chicken Tikka', 'Spiced chicken pieces grilled in tandoor', 279.00, 4.5, 'https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?w=300');

-- Sushi Studio (restaurant_id = 7)
INSERT INTO menu (restaurant_id, name, description, price, rating, image_url) VALUES
(7, 'California Roll', 'Crab, avocado, and cucumber wrapped in seasoned rice', 399.00, 4.6, 'https://images.unsplash.com/photo-1579871494447-9811cf80d66c?w=300'),
(7, 'Salmon Nigiri', 'Fresh salmon slices over pressed vinegared rice', 449.00, 4.8, 'https://images.unsplash.com/photo-1583623025817-d180a2221d0a?w=300'),
(7, 'Miso Soup', 'Traditional Japanese soup with tofu and seaweed', 149.00, 4.3, 'https://images.unsplash.com/photo-1547592166-23ac45744acd?w=300'),
(7, 'Tempura Platter', 'Assorted vegetables and shrimp in light crispy batter', 349.00, 4.5, 'https://images.unsplash.com/photo-1615361200141-f45040f367be?w=300'),
(7, 'Edamame', 'Steamed young soybeans with sea salt', 129.00, 4.1, 'https://images.unsplash.com/photo-1564093497595-593b96d80571?w=300');

-- Chai & Snacks (restaurant_id = 8)
INSERT INTO menu (restaurant_id, name, description, price, rating, image_url) VALUES
(8, 'Masala Chai', 'Spiced Indian tea brewed with ginger and cardamom', 29.00, 4.5, 'https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?w=300'),
(8, 'Samosa (2 pcs)', 'Crispy pastry filled with spiced potatoes and peas', 49.00, 4.3, 'https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?w=300'),
(8, 'Vada Pav', 'Mumbai-style spiced potato fritter in a bun', 39.00, 4.4, 'https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?w=300'),
(8, 'Pani Puri (6 pcs)', 'Crispy hollow shells with spiced water and filling', 59.00, 4.6, 'https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?w=300'),
(8, 'Bread Omelette', 'Fluffy egg omelette with toasted bread', 69.00, 4.1, 'https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?w=300');

-- ============================================================
-- SEED DATA: Default Admin User (password: admin123 — SHA-256 hashed)
-- SHA-256 of "admin123" = 240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9
-- ============================================================
INSERT INTO users (name, username, password, email, phone, address, role) VALUES
('Admin User', 'admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'admin@foodapp.com', '9876543210', 'Chennai, Tamil Nadu', 'ADMIN');
