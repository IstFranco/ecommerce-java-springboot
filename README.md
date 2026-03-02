# 🛒 E-Commerce Backend API

RESTful API developed in **Java and Spring Boot** to manage the core operations of an e-commerce platform. This project is designed with a strong focus on robust business logic and architectural best practices.

## 🚀 Technologies Used
* **Language:** Java 21
* **Framework:** Spring Boot (Spring Web, Spring Data JPA)
* **Database:** PostgreSQL
* **Tools:** Hibernate, Lombok, Postman, Maven

## ⚙️ Core Features
* **Product Management:** Full CRUD operations with strict real-time **stock** control.
* **Customer Management:** User data registration and administration.
* **Order Processing:** * Creation of purchase orders with multiple items (`OrderItem`).
  * Automatic stock deduction when processing a sale.
  * Freezing the `unitPrice` at the exact moment of the transaction to maintain accounting integrity against future price variations.
  * Automatic calculation of the order's `total` amount.

## 🏗️ Architecture
The project follows a multi-layer design (Controller, Service, Repository, Model) to ensure separation of concerns and maintainability. A relational database approach was implemented, handling 1:N and N:M relationships with manual bridge tables for historical data control.

## 🛣️ Roadmap / Next Steps
- [ ] Implementation of DTOs to avoid exposing DB entities.
- [ ] Global exception handling (`@ControllerAdvice`).
- [ ] Security and Authentication with JWT (Spring Security).
- [ ] Containerization with Docker.
- [ ] Cloud Deployment (AWS / Render).
