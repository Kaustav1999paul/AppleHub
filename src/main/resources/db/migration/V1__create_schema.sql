-- ==============================
-- FLYWAY MIGRATION SCRIPT
-- ==============================

-- Enable UUID generation functions (if not already)
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ==============================
-- AUTH SCHEMA
-- ==============================
CREATE SCHEMA IF NOT EXISTS auth_service;
-- Users Table
CREATE TABLE IF NOT EXISTS auth_service.users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    address TEXT,
    age INT CHECK (age >= 0),
    gender VARCHAR(20) CHECK (gender IN ('MALE', 'FEMALE', 'OTHER')),
    coordinates VARCHAR(100),
    role VARCHAR(50) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
-- Indexes
CREATE INDEX IF NOT EXISTS idx_users_email ON auth_service.users(email);



-- ==============================
-- PRODUCT SCHEMA
-- ==============================
CREATE SCHEMA IF NOT EXISTS product_service;
-- Products Table
CREATE TABLE IF NOT EXISTS product_service.products (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(10,2) NOT NULL,
    offer_price NUMERIC(10,2),
    stock INT NOT NULL,
    category VARCHAR(100),
    image_url VARCHAR(500),
    refurbished BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
-- Indexes for faster queries
CREATE INDEX IF NOT EXISTS idx_products_category ON product_service.products(category);
CREATE INDEX IF NOT EXISTS idx_products_name ON product_service.products(name);



-- ==============================
-- Carts Table
-- ==============================
CREATE TABLE IF NOT EXISTS product_service.carts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES auth_service.users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );



-- ==============================
-- Cart Items Table
-- ==============================
CREATE TABLE IF NOT EXISTS product_service.cart_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cart_id UUID NOT NULL REFERENCES product_service.carts(id) ON DELETE CASCADE ON UPDATE CASCADE,
    product_id UUID NOT NULL REFERENCES product_service.products(id) ON DELETE CASCADE ON UPDATE CASCADE,
    quantity INT NOT NULL CHECK (quantity > 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Indexes
CREATE INDEX IF NOT EXISTS idx_cart_user ON product_service.carts(user_id);
CREATE INDEX IF NOT EXISTS idx_cart_items_cart ON product_service.cart_items(cart_id);



-- ==============================
-- Orders table
-- ==============================
CREATE TABLE product_service.orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES auth_service.users(id) ON DELETE CASCADE,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING', -- PENDING, COMPLETED, CANCELED
    total_amount NUMERIC(10,2) NOT NULL CHECK (total_amount >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Order Items table
CREATE TABLE product_service.order_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL REFERENCES product_service.orders(id) ON DELETE CASCADE,
    product_id UUID NOT NULL REFERENCES product_service.products(id),
    quantity INT NOT NULL CHECK (quantity > 0),
    price NUMERIC(10,2) NOT NULL CHECK (price >= 0), -- snapshot of price at purchase time
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

