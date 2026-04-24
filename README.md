# 📚 Bookstore Backend API (Spring Boot)

A production-grade backend system for a Bookstore application built using **Java, Spring Boot, and PostgreSQL**. This project is designed to demonstrate real-world backend engineering practices including secure authentication, API design, system hardening, and scalable architecture patterns.

---

## 🚀 Features

* 🔐 **JWT Authentication & Authorization** (Access + Role-based security)
* 🛡️ **Spring Security Integration** with protected endpoints
* 🌐 **CORS Policy Configuration** for secure cross-origin access
* ⚡ **Rate Limiting** to prevent API abuse and ensure stability
* 📖 **Swagger/OpenAPI Documentation** for interactive API testing
* ❌ **Global Exception Handling** with consistent API error responses
* 📦 Book management (CRUD operations)
* 🧾 Order management system (basic flow)
* 🗄️ PostgreSQL integration using Spring Data JPA / Hibernate
* 🧱 Clean layered architecture (Controller → Service → Repository)
* ✅ Input validation and standardized response structure

---

## 🏗️ Tech Stack

* Java 25 (LTS)
* Spring Boot
* Spring Security
* Spring Data JPA / Hibernate
* PostgreSQL
* JWT (Authentication)
* Swagger / OpenAPI
* **Gradle (Build Tool)**

---

## 🧠 Architecture Overview

This project follows a **layered backend architecture** to ensure scalability and maintainability:

* **Controller Layer** → Handles HTTP requests & responses
* **Service Layer** → Business logic and processing
* **Repository Layer** → Database interaction using JPA
* **Security Layer** → JWT authentication & authorization
* **Exception Layer** → Centralized error handling

---

## 🔐 Security Implementation

* Stateless authentication using **JWT tokens**
* Role-based access control (Admin/User separation)
* Secure endpoints using **Spring Security filters**
* CORS configuration for controlled frontend access

---

## ⚡ Rate Limiting

Implemented to protect APIs from:

* Excessive request flooding
* Abuse and brute-force attempts

Ensures fair usage and backend stability under load.

---

## ❌ Global Exception Handling

* Centralized exception handling using `@ControllerAdvice`
* Consistent API error response structure
* Proper HTTP status codes for all error scenarios

---

## 📖 API Documentation

Swagger UI is enabled for interactive API exploration:

```
/swagger-ui.html
```

Used for:

* Testing endpoints
* Viewing request/response schemas
* API debugging during development

---

## ⚙️ Configuration

This project uses **YAML-based configuration** instead of properties file.

### application.yml

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/bookstore
    username: your_username
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true

server:
  port: 8080

jwt:
  secret: your_secret_key
  expiration: 3600000
```

---

## 📦 Project Structure

```
src/main/java
 └── com.amreshmaurya.bookstore
      ├── controller
      ├── service
      ├── repository
      ├── model/entity
      ├── dto
      ├── dao
      ├── exception
      ├── config
      └── filter
```

---

## 🗄️ Database Design

Core entities:

* Users
* Books
* Orders
* Order Items

Relationships are managed using JPA with proper foreign key mappings.

---

## ⚙️ Getting Started

### 1. Clone repository

```bash
git clone https://github.com/amreshcraft/bookstore.git
cd bookstore
```

### 2. Build project (Gradle)

```bash
./gradlew build
```

### 3. Run application

```bash
./gradlew bootRun
```

---

## 📌 Sample API Endpoints

### Auth

* POST `/api/v1/auth/signup`
* POST `/api/v1/auth/login`

### Books

* GET `/api/v1/books`
* POST `/api/v1/books` (Admin)
* PUT `/api/v1/books/{id}` (Admin)
* DELETE `/api/v1/books/{id}` (Admin)

### Orders

* POST `/api/v1/orders`
* GET `/api/v1/orders/user`

---

## 🧠 Key Engineering Highlights

* Designed for **production-level backend concerns**
* Security-first architecture with JWT + Spring Security
* API protection using **rate limiting & CORS policies**
* YAML-based configuration for cleaner environment management
* Gradle-based build system for faster and flexible builds
* Clean separation of concerns for scalability
* Centralized error handling for consistency

---

## 📈 Future Improvements

* Redis caching for performance optimization
* Full order lifecycle management (payment, status tracking)
* Advanced search & filtering for books
* Docker containerization
* Unit & integration testing with JUnit & Mockito

---

## 👨‍💻 Author

Amresh Maurya [@amreshmaurya](https://amreshmaurya.com)

Backend Developer | Java | Spring Boot | System Design Enthusiast
