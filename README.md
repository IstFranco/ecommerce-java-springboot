# 🛒 Franco eCommerce - Backend API

A robust RESTful API developed with **Java 21** and **Spring Boot 3.4**, designed to manage the core operations of an e-commerce platform with a strong focus on security, data consistency, and architectural best practices.

## 🚀 Tech Stack
* **Language:** Java 21 (JDK 21)
* **Framework:** Spring Boot 3.4
* **Security:** Spring Security + **JWT** (Role-based Authentication & Authorization)
* **Persistence:** Spring Data JPA / Hibernate
* **Database:** PostgreSQL (Hosted on **Supabase**)
* **Infrastructure:** Docker & Docker Compose
* **Documentation:** Swagger UI (OpenAPI 3)

## ⚙️ Core Features & Business Logic
* **Transactional Order Processing:** Multi-item purchase management ensuring data integrity through `@Transactional` boundaries.
* **Real-time Stock Control:** Automatic stock validation before sale confirmation and automated restocking upon order deletion.
* **Hardened Security:** Implementation of **DTOs** to prevent entity exposure and **Global Exception Handling** (`@ControllerAdvice`) for standardized API responses.
* **Price Integrity:** Unit price "freezing" at the moment of transaction to protect historical accounting data against future price variations.

## 🏗️ Architecture & Design
The project follows a **N-Layer design** (Controller, Service, Repository, Model) ensuring a clean separation of concerns. A secure architecture was implemented where user identity is retrieved directly from the **SecurityContext (JWT)**, eliminating the need for insecure client ID passing in requests.

## 📦 Local Installation & Usage (Docker)

1. Clone the repository.
2. Create a `.env` file in the root directory (use `application.properties` as a reference for keys).
3. Run the following command:
   ```bash
   docker-compose up --build
