<p align="center">
  <img src="https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExYTU0YjkwY2MzNjMwZDRkYjhlY2RjNTg5ZjE1MzE4ZDg0ZGIxNWI3MSZjdD1n/N5W3zINkG91BPK0wL0/giphy.gif" width="600" alt="Banner"/>
</p>

<h1 align="center">💸 PickPay Challenge - Java + Spring Boot</h1>

<p align="center">
  A modern digital wallet system for secure and fast money transfers.
  <br/>
  Built with ❤️ using Java, Spring Boot, PostgreSQL, and Postman.
</p>

<p align="center">
  <a href="https://www.java.com"><img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white"/></a>
  <a href="https://spring.io/projects/spring-boot"><img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/></a>
  <a href="https://www.postgresql.org/"><img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white"/></a>
  <a href="https://www.postman.com/"><img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white"/></a>
</p>

---
---

## 📌 Features

✅ User registration (Common or Merchant)\
✅ Money transfers between users (using CPF)\
✅ Transaction history (sent and received)\
✅ Real-time balance tracking\
✅ User & Transaction DTO mapping\
✅ Clean and layered architecture

---

## 🏗️ Technologies Used

| Layer        | Technology              |
| ------------ | ----------------------- |
| Language     | Java 17                 |
| Framework    | Spring Boot             |
| ORM          | Hibernate / JPA         |
| Database     | PostgreSQL              |
| Testing Tool | Postman                 |
| Build Tool   | Maven                   |
| JSON Mapping | Jackson                 |
| Validation   | Jakarta Bean Validation |

---

## 🔧 How to Run Locally

> Prerequisite: Java 17, Maven, PostgreSQL installed

```bash
# Clone the repo
git clone https://github.com/your-username/pickpay.git
cd pickpay

# Run the application
./mvnw spring-boot:run
```

✅ Access API at: `http://localhost:8080`

---

## 🧚️ API Collection (Postman)

📁 Download or import the collection directly:\
👉 [Click to Open in Postman](https://speeding-eclipse-752598.postman.co/workspace/New-Team-Workspace~ccee2d7a-d365-46bf-85aa-56b41fdc7fed/collection/24192282-9ea5f5a6-1121-4920-be4f-2cb988477a61?action=share\&source=collection_link\&creator=24192282)

### 🔹 User Endpoints

| Method | Endpoint                         | Description        |
| ------ | -------------------------------- | ------------------ |
| POST   | `/users`                         | Create new user    |
| GET    | `/users/findAllUsers`            | List all users     |
| GET    | `/users/findUserByCpf/{cpf}`     | Find user by CPF   |
| GET    | `/users/findUserByEmail/{email}` | Find user by email |

### 🔹 Transaction Endpoints

| Method | Endpoint                           | Description                |
| ------ | ---------------------------------- | -------------------------- |
| POST   | `/transactions/transferMoneyByCpf` | Send money to another user |

---

## 🧠 Architecture Overview

```
com.atircio.pickpay
│
├── controllers        # REST endpoints
├── dtos               # Data Transfer Objects
├── entities           # JPA Entities
├── mappers            # Entity-DTO converters
├── repositories       # JPA repositories
├── services           # Business logic
└── config             # Application configs
```

---

## 🛠️ Example Config (application.yml)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/data-base-name
    username: .......
    password: .......
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        format_sql: true
    database: postgresql
    database-platform: org.hibernate.dialect.PostgreSQLDialect
```

> ⚠️ **Important:** Don't use `create-drop` in production, it will delete data on every restart.

---

## ✨ Screenshots

---

## 🤝 Contributing

Pull requests are welcome! Please fork the repo and open a PR for any features or bug fixes.

---

## 📬 Contact

📧 **Email:** [atircio@example.com](mailto\:atircio@example.com)\
📚 **Built by:** Atircio Matias

---

## 📃 License

This project is just a public challenge by the company PickPay. You can view more details about the challenge at [https://github.com/PicPay/picpay-desafio-backend](https://github.com/PicPay/picpay-desafio-backend).

---

