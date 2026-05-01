# Spring Boot Projects - Order Management & Book Store

## Overview
Two Spring Boot applications:
1. **Project 1:** Basic Order Management System with REST APIs (CRUD)
2. **Project 2:** Minimal Online Book Store with 4 Pages (Home, Login, Register, Catalog)

---

## 📁 Project Folder Structure

### **PROJECT 1: Basic Order Management System**
```
project1-basic/
├── order-entity.java
├── order-repository.java
├── order-service.java
├── order-controller.java
├── order-management-application.java
├── application.properties
└── pom.xml
```

### **PROJECT 2: Online Book Store (Minimal)**
```
project2-bookstore/
├── book-store-user-entity.java
├── book-store-user-repository.java
├── book-store-user-service.java
├── book-store-user-dto.java
├── book-store-book-entity.java
├── book-store-book-repository.java
├── book-store-book-service.java
├── book-store-book-dto.java
├── book-store-home-controller.java
├── book-store-auth-controller.java
├── book-store-catalog-controller.java
├── book-store-application.java
├── application.properties
└── pom.xml
```

---

## 🚀 Quick Start

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- IDE: IntelliJ IDEA
- Database: H2 (embedded) or MySQL

### Setup Steps

1. **Clone/Create Project**
   ```bash
   cd spring-boot
   ```

2. **Build Project**
   ```bash
   mvn clean install
   ```

3. **Run Application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access Application**
   - Base URL: `http://localhost:8080`
   - Project 1 H2 Console: `http://localhost:8080/h2-console`

---

## 📋 API Endpoints & Pages

### **PROJECT 1 - Order Management REST APIs**

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/orders` | Create new order |
| GET | `/api/orders` | Get all orders |
| GET | `/api/orders/{id}` | Get order by ID |
| GET | `/api/orders/number/{orderNumber}` | Get by order number |
| GET | `/api/orders/customer/{customerId}` | Get customer's orders |
| GET | `/api/orders/status/{status}` | Get orders by status |
| GET | `/api/orders/search?customerName=...` | Search by name |
| PUT | `/api/orders/{id}` | Update order |
| PATCH | `/api/orders/{id}/status` | Update status only |
| DELETE | `/api/orders/{id}` | Delete order |
| GET | `/api/orders/stats/total` | Get total count |

### **PROJECT 2 - Book Store Pages & APIs**

**Pages (Thymeleaf):**
- `GET /` - Home page with featured books
- `GET /login` - Login form
- `GET /register` - Registration form
- `GET /catalog` - Book catalog with search & filters

**REST APIs:**
- `POST /api/auth/login` - User login
- `POST /register` - User registration
- `GET /api/books` - Get all books
- `GET /api/books/{id}` - Get book by ID
- `GET /api/books/search?keyword=...` - Search books
- `GET /api/books/category/{category}` - Filter by category
- `GET /api/books/author/{author}` - Filter by author

---

## 🧪 Testing with cURL

### Project 1 - Order Management

```bash
# Create Order
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "customerName": "John Doe",
    "totalAmount": 99.99,
    "status": "PENDING",
    "shippingAddress": "123 Main St"
  }'

# Get All Orders
curl http://localhost:8080/api/orders

# Get Order by ID
curl http://localhost:8080/api/orders/1

# Update Order Status
curl -X PATCH "http://localhost:8080/api/orders/1/status?status=SHIPPED" \
  -H "Content-Type: application/json"

# Delete Order
curl -X DELETE http://localhost:8080/api/orders/1
```

### Project 2 - Book Store

```bash
# Get All Books
curl http://localhost:8080/api/books

# Search Books
curl "http://localhost:8080/api/books/search?keyword=java"

# Get Books by Category
curl "http://localhost:8080/api/books/category/Fiction"

# Get Books by Author
curl "http://localhost:8080/api/books/author/James%20Patterson"
```

---

## 📦 Database Configuration

### Project 1: H2 (Embedded)
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.h2.console.enabled=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
```

### Project 2: MySQL
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookstore_db
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
```

**Setup MySQL:**
```bash
# Create database
mysql -u root -p
> CREATE DATABASE bookstore_db;
> EXIT;
```

---

## 📦 Dependencies

### Project 1 (pom.xml)
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

### Project 2 (pom.xml)
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-crypto</artifactId>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>bootstrap</artifactId>
    <version>5.3.0</version>
</dependency>
```

---

## 🔧 Configuration Files

### Project 1: application.properties
```properties
spring.application.name=order-management-basic
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8080
logging.level.root=WARN
logging.level.com.order=DEBUG
logging.level.org.springframework.web=INFO
```

### Project 2: application.properties
```properties
spring.application.name=online-book-store
spring.datasource.url=jdbc:mysql://localhost:3306/bookstore_db
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

spring.thymeleaf.enabled=true
spring.thymeleaf.prefix=/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.cache=false

server.port=8080
server.servlet.session.timeout=30m

logging.level.root=WARN
logging.level.com.bookstore=DEBUG
logging.level.org.springframework.web=INFO
```

---

## 📝 File Naming Convention

**Project 1:** All files prefixed with `order-`
- `order-entity.java` - JPA entity
- `order-repository.java` - Data access
- `order-service.java` - Business logic
- `order-controller.java` - REST endpoints
- `order-management-application.java` - Entry point

**Project 2:** All files prefixed with `book-store-`
- `book-store-user-entity.java` - User entity
- `book-store-book-entity.java` - Book entity
- `book-store-user-service.java` - User logic
- `book-store-book-service.java` - Book logic
- `book-store-home-controller.java` - Home page
- `book-store-auth-controller.java` - Login/Register
- `book-store-catalog-controller.java` - Book catalog

---

## ⚙️ IntelliJ Copy-Paste Setup

1. **Create New Maven Project**
   - File → New Project → Maven
   - GroupId: `com.order` or `com.bookstore`
   - Java: 11

2. **Copy Files**
   - Create package: `src/main/java/com/order/` or `src/main/java/com/bookstore/`
   - Copy all `.java` files from respective project folder
   - Copy `application.properties` to `src/main/resources/`
   - Copy `pom.xml` to project root

3. **Setup Build**
   - Right-click `pom.xml` → Maven → Reload Projects
   - Wait for dependencies to download

4. **Run Application**
   - Click Run or use: `mvn spring-boot:run`
   - Application starts on `http://localhost:8080`

---

## 🐛 Troubleshooting

### Port Already in Use
```bash
# Change in application.properties
server.port=8081
```

### MySQL Connection Error
```bash
# Check MySQL is running
mysql -u root -p
# Enter password
# Type: CREATE DATABASE bookstore_db;
```

### H2 Console Access (Project 1)
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave blank)

### Template Not Found (Project 2)
- Create folder: `src/main/resources/templates/`
- Place `.html` files in this folder
- Restart application

---

## ✅ What's Included

✅ **Project 1:**
- 11 REST API endpoints for CRUD operations
- H2 embedded database (no setup needed)
- Comprehensive Order entity with validations
- Custom repository queries
- Service layer with business logic
- Error handling in controller

✅ **Project 2:**
- 4 page views (Home, Login, Register, Catalog)
- User registration with password encoding
- Book catalog with search & filters
- MySQL database integration
- Thymeleaf templating ready
- Bootstrap CSS via WebJars

---

## 📚 Entity Details

### Order Entity (Project 1)
- id, orderNumber (unique), customerId, customerName
- totalAmount, status, shippingAddress, notes
- createdAt, updatedAt (auto-managed)

### User Entity (Project 2)
- id, username (unique), email (unique), password
- firstName, lastName, phoneNumber, address, city
- enabled, role, createdAt, updatedAt

### Book Entity (Project 2)
- id, title, author, isbn (unique), description
- category, price, stockQuantity, publisher
- publishedDate, rating, reviewCount, imageUrl
- createdAt, updatedAt

---

## 🚀 Ready to Use!

Both projects are complete and ready for IntelliJ copy-paste. No additional setup needed beyond creating a Maven project and copying the files.

Happy coding! 💻
