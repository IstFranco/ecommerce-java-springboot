# 🛒 Franco eCommerce - Backend API

A high-performance RESTful API built with **Java 21** and **Spring Boot 4.0.3**. This project focuses on secure, production-ready operations, featuring automated infrastructure and complex business logic for eCommerce management.

## 🚀 Tech Stack
- **Backend:** Java 21, Spring Boot 4.0.3, Hibernate 7.
- **Security:** Spring Security 7 + JWT (Custom Claims & Role-based access).
- **Database:** PostgreSQL 17 (Supabase) with **PgBouncer** optimization.
- **Infrastructure:** Docker (Multi-stage builds) & Docker Compose.
- **Docs:** Swagger UI / OpenAPI 3.

## ⚙️ Engineering Highlights
- **Token-Driven Identity:** Using `SecurityContextHolder` to extract user identity directly from JWT. No more passing insecure `customer_id` in request bodies.
- **Transactional Integrity:** Full `@Transactional` support for orders, ensuring stock consistency and price "freezing" at the exact moment of purchase.
- **Smart Bulk Loading:** Implemented a robust `/bulk` product endpoint with individual `try/catch` blocks. It skips duplicates and reports errors per item without crashing the whole process.
- **Production Readiness:** Solved critical cloud-specific issues like the `prepared statement already exists` error by fine-tuning JDBC parameters for connection poolers.

## 🛠️ Technical Challenges & Solutions
- **PgBouncer Compatibility:** Fixed statement collisions in Supabase by setting `?prepareThreshold=0`, allowing stable transaction pooling.
- **API Hardening:** Decoupled database entities from the API contract using a full **DTO layer**, preventing sensitive internal fields from leaking through Swagger.
- **Automated Restocking:** Implemented a delete-order logic that automatically recovers product stock, keeping the inventory synchronized.

## 🛣️ API Endpoints Summary

### 🔐 Authentication
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/auth/register` | Public | Register as USER |
| POST | `/auth/login` | Public | Login and receive JWT |
| POST | `/auth/register/admin` | ADMIN | Register new ADMIN |

### 📦 Products
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/v1/products` | Public | List all products |
| POST | `/api/v1/products` | ADMIN | Create product |
| POST | `/api/v1/products/bulk` | ADMIN | Bulk create products (Skip duplicates) |
| DELETE | `/api/v1/products/{id}` | ADMIN | Delete product |

### 👤 Customers
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/v1/customers` | USER/ADMIN | List all customers |
| PUT | `/api/v1/customers/{id}` | USER/ADMIN | Update personal data |

### 📑 Orders
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/v1/orders` | ADMIN | List all orders |
| POST | `/api/v1/orders` | USER/ADMIN | Create order (Identity from JWT) |
| DELETE | `/api/v1/orders/{id}` | ADMIN | Delete order and auto-restock |

## 📦 Infrastructure & Deployment

### Run with Docker
The project uses a **multi-stage Dockerfile** to compile and run the app in an optimized environment.
```bash
# Start the entire stack (Database env + API)
docker-compose up --build
1. **Environment Setup:** Create a `.env` file in the root directory:
   ```text
   DB_URL=jdbc:postgresql://host:port/postgres?prepareThreshold=0
   DB_USERNAME=your_username
   DB_PASSWORD=your_password
   JWT_SECRET=your_secret_key
