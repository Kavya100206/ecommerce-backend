# E-Commerce Backend API (Spring Boot)

## Overview
This project is a minimal e-commerce backend system built using **Spring Boot** and **MongoDB**.  
It supports product management, cart handling, order processing, payment simulation using a webhook mechanism, and additional bonus features such as order history, order cancellation, and product search.

The project was developed as a **mini project for class evaluation** and demonstrates real-world backend concepts such as REST APIs, database relationships, business logic handling, and asynchronous workflows.

---

## Tech Stack
- Java 17+
- Spring Boot
- Spring Web
- Spring Data MongoDB
- MongoDB
- Maven
- Postman (for API testing)
- Git & GitHub

---

## Features Implemented

### Core Features
- Create and list products
- Add items to cart
- View and clear cart
- Create orders from cart
- Stock validation and update
- Mock payment creation
- Payment webhook handling
- Order status updates after payment

### Bonus Features
- Order history for a user
- Order cancellation (only if not paid)
- Product search by name

---

## Project Structure

com.example.ecommerce
```
├── controller # REST Controllers
├── service # Business logic
├── repository # MongoDB repositories
├── model # Entity classes
├── dto # Request/response DTOs
├── webhook # Payment webhook controller
├── config # Configuration classes
└── EcommerceApplication.java
```


---

## Database Design
The application uses MongoDB collections for:
- User
- Product
- CartItem
- Order
- OrderItem
- Payment

Relationships are handled using referenced IDs to maintain simplicity and clarity.

---

## API Endpoints

### Product APIs
- `POST /api/products` – Create a product
- `GET /api/products` – List all products
- `GET /api/products/search?q={name}` – Search products by name

### Cart APIs
- `POST /api/cart/add` – Add item to cart
- `GET /api/cart/{userId}` – View user cart
- `DELETE /api/cart/{userId}/clear` – Clear cart

### Order APIs
- `POST /api/orders` – Create order from cart
- `GET /api/orders/{orderId}` – Get order details
- `GET /api/orders/user/{userId}` – Get order history for user
- `POST /api/orders/{orderId}/cancel` – Cancel order (only if status is CREATED)

### Payment APIs
- `POST /api/payments/create` – Create payment for an order
- `POST /api/webhooks/payment` – Mock payment webhook callback

---

## Order Flow
1. Products are created and stored in the database.
2. User adds products to the cart.
3. Cart is converted into an order.
4. Total amount is calculated and stock is updated.
5. Payment is created with status PENDING.
6. Webhook simulates payment success or failure.
7. Order and payment status are updated accordingly.

---

## Setup Instructions

### Prerequisites
- Java 17 or above
- MongoDB running locally
- Maven installed

### Steps to Run
1. Clone the repository:
git clone https://github.com/Kavya100206/ecommerce-backend.git


2. Configure MongoDB in `application.yml`:
spring.data.mongodb.uri=mongodb://localhost:27017/ecommerce_db


3. Run the application:
mvn spring-boot:run


4. The server will start on:
http://localhost:8081

---
