This project is a small Spring Boot backend for a store/commerce app, centered around products and orders.

What it is
• Java 17 + Spring Boot 4 project
• Uses Spring Data JPA for database access
• Uses PostgreSQL as the database
• Exposes a REST API
• Includes Springdoc OpenAPI for API docs
• Uses dotenv-java to load environment variables from a .env file

Main entry point
• src/main/java/com/stiru/creatorstore/CreatorStoreApplication.java
• This starts the app and loads environment variables before Spring bootstraps

Configuration
• src/main/resources/application.yaml

Reads:
◦ DATABASE_URL
◦ DATABASE_USERNAME
◦ DATABASE_PASSWORD
• Hibernate is set to update schema automatically:
◦ spring.jpa.hibernate.ddl-auto: update

Project structure
• com.stiru.creatorstore
◦ controllers
◦ services
◦ repositories
◦ entites
• The package name is a bit inconsistent: “entites” is misspelled for “entities”

Core domain model
• Product
◦ Fields: id, name, description, category, price, stockQuantity
◦ Validations like not blank, price > 0, stock >= 0
◦ Linked to order items with a one-to-many relationship
• Order
◦ Fields: id, customerName, customerEmail, status, totalPrice, createdAt
◦ Has a list of order items
•Orderitems
◦ Many-to-one to Order
◦ Many-to-one to Product
◦ Includes quantity and priceAtPurchase
◦ Table name: order_items

Repository layer
• ProductRepository extends JpaRepository<Product, Long>
• OrderRepository extends JpaRepository<Order, Long>
• OrderitemRepository extends JpaRepository<Orderitems, Long>

Service layer
• ProductService handles CRUD:
◦createProduct
◦updateProduct
◦getProducts
◦getProductById
◦deleteProduct

•It uses ProductRepository and throws RuntimeException when a product isn’t found.

Controller layer
• ProductController maps:
◦POST /api/products
◦PUT /api/products/{id}
◦GET /api/products
◦GET /api/products/{id}
◦DELETE /api/products/{id}

So the app is essentially a product catalog and order backend API, with starting support for orders.

