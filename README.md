# 🧠 FinBrains: AI-Powered Personal Finance Assistant

## 🚀 Project Overview

**FinBrains** is an intelligent personal finance management application designed to give users granular control over their spending, budgeting, and financial goals. Built with a modern microservices architecture, this project leverages **Spring Boot** for a robust backend and integrates with **Machine Learning** to provide automated expense categorization and proactive financial insights.

This project serves as a final-year demonstration of proficiency in scalable backend development, database design, and real-world AI integration patterns.

---

## ✨ Key Features & Functionality

| Feature | Status | Description |
| :--- | :---: | :--- |
| **User Authentication** | ✅ Done | Secure **JWT-based** authentication (Sign-up, Log-in) and user profile management. |
| **Expense CRUD** | ✅ Done | Full **Create, Read, Update, Delete (CRUD)** lifecycle for personal transaction records. |
| **Automated Categorization** | 🚧 In Progress | **(Future ML Integration):** Seamlessly categorize expenses using a custom-trained AI model. |
| **Anomaly Detection** | 🚧 In Progress | Flag unusual or potentially fraudulent transactions in real-time. |
| **Predictive Budgeting** | 💡 Planned | Use historical data to forecast spending and warn users about budget overruns. |

---

## 🛠️ Technology Stack

### Backend & Data Services
| Component | Technology | Description |
| :--- | :--- | :--- |
| **Backend Framework** | **Spring Boot** (Java 17+) | Core RESTful API development, security, and business logic. |
| **Database** | **MongoDB** | NoSQL document storage for scalable, flexible financial records and user profiles. |
| **Security** | **Spring Security** & **JWT** | Token-based authentication and secure access to resources. |

### AI/ML Microservice (Future Integration)
| Component | Technology | Description |
| :--- | :--- | :--- |
| **ML Framework** | **Python** / **FastAPI** | Dedicated microservice for model hosting and prediction endpoints. |
| **Libraries** | Scikit-learn, Pandas | Used for training the custom categorization and anomaly detection models. |

---

## 🏗️ Architecture Design

The system employs a **Layered Architecture** with a clear separation of concerns, built for extensibility:

1.  **Controller Layer:** Handles HTTP requests, JWT token validation, and request mapping.
2.  **Service Layer:** Contains the core business logic (CRUD operations, data validation, and AI integration calls).
3.  **Repository Layer:** Interfaces directly with MongoDB for data persistence and retrieval.

**Database Schema:**
The MongoDB schema is designed for performance and flexibility, utilizing **embedded documents** (`Users,Category,`Budgets`) to minimize joins and optimize data retrieval for the end-user.

---

## ⚙️ Setup and Local Development

### Prerequisites

* JDK 17+
* Maven 3.6+
* Docker (Optional, for running MongoDB locally)

### 1. Database Setup

1.  Ensure **MongoDB** is running locally (e.g., via Docker):
    ```bash
    docker run --name finbrains-mongo -p 27017:27017 -d mongo
    ```

### 2. Backend Configuration

1.  Clone the repository:
    ```bash
    git clone [Your-Repo-URL]
    cd FinBrains
    ```
2.  Update `application.properties` with your MongoDB connection string:
    ```properties
    spring.data.mongodb.uri=mongodb://localhost:27017/finance_assistant_db
    # ... other properties
    ```
3.  Build and run the Spring Boot application:
    ```bash
    ./mvnw spring-boot:run
    ```

The application will start on `http://localhost:8080`.

---

## 📞 API Endpoints (Current Implementation)

All endpoints are secured and require a valid JWT in the `Authorization: Bearer <token>` header.

| Feature | Method | Endpoint | Request Body (DTO) |
| :--- | :--- | :--- | :--- |
| **User Sign Up** | `POST` | `/api/v1/auth/signup` | `UserRequest` |
| **User Log In** | `POST` | `/api/v1/auth/login` | `LoginRequest` |
| **Add Expense** | `POST` | `/api/v1/expenses` | `ExpenseRequest` |
| **Get All Expenses**| `GET` | `/api/v1/expenses` | None |
| **Update Expense** | `PUT` | `/api/v1/expenses/{id}`| `ExpenseRequest` |
| **Delete Expense** | `DELETE`| `/api/v1/expenses/{id}`| None |

---

## ⏭️ Future Enhancements

* Complete Python **ML Microservice** integration for category and anomaly prediction.
* Implement real-time **WebSockets** for budget alerts.
* Develop the **React.js** frontend (Web Application Interface).
* Add comprehensive Unit and Integration testing using JUnit and Mockito.

---

Made with ❤️ by **Somalika** 
