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

## 📦 Dependencies

### Core Dependencies (pom.xml)
```xml
<!-- Spring Boot Starters -->
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
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- Database -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>

<!-- Lombok for reducing boilerplate -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!-- JWT for Authentication (Optional) -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.11.5</version>
</dependency>

<!-- Bootstrap for Frontend -->
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>bootstrap</artifactId>
    <version>5.3.0</version>
</dependency>

<!-- Testing -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```

---

## 🔧 Configuration Files

### application.properties
```properties
spring.application.name=online-book-store
spring.datasource.url=jdbc:mysql://localhost:3306/bookstore_db
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8080

logging.level.root=WARN
logging.level.com.bookstore=DEBUG

# Thymeleaf Configuration
spring.thymeleaf.enabled=true
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html

# Session Configuration
server.servlet.session.timeout=30m
```

### application.yml (Alternative)
```yaml
spring:
  application:
    name: online-book-store
  datasource:
    url: jdbc:mysql://localhost:3306/bookstore_db
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    database-platform: org.hibernate.dialect.MySQL8Dialect
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
  thymeleaf:
    enabled: true
    prefix: classpath:/templates/
    suffix: .html

server:
  port: 8080
  servlet:
    session:
      timeout: 30m

logging:
  level:
    root: WARN
    com.bookstore: DEBUG
```

---

## 📝 File Naming Convention

All book-store-related files follow this pattern:
- Entities: `book-store-{entity-name}.java` 
- Controllers: `book-store-{resource}-controller.java`
- Services: `book-store-{resource}-service.java`
- Repositories: `book-store-{resource}-repository.java`
- DTOs: `book-store-{entity-name}-dto.java`
- Tests: `book-store-{resource}-test.java`
- Config: `book-store-{config-name}.java`

Example:
- `book-store-user-entity.java` → UserEntity.java
- `book-store-book-controller.java` → BookController.java
- `book-store-book-service.java` → BookService.java

---

## 🛠️ Development Tips

### For IntelliJ Users:
1. **Copy-Paste Code**: All code is formatted for direct paste into IntelliJ
2. **Auto-Format**: Use `Ctrl+Alt+L` to auto-format after pasting
3. **Create Classes**: Right-click package → New → Java Class
4. **Run Application**: Shift+F10 or Green Run button
5. **Debug**: F9 to run with debugger

### Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| Port already in use | Change port in `application.properties` |
| Lombok not working | Enable Annotation Processing: Settings → Compiler → Annotation Processors → Enable |
| H2 Console not showing | Add `spring.h2.console.enabled=true` |
| 404 errors on GET | Check @RestController and @RequestMapping annotations |
| Database not persisting | H2 in-memory resets on restart. Use MySQL for persistence |

---

## 📚 Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [REST API Best Practices](https://restfulapi.net/)
- [HTTP Status Codes](https://httpwg.org/specs/rfc7231.html#status.codes)

---

## 📞 Support

For issues or questions:
1. Check the configuration files
2. Verify database connectivity
3. Review application logs
4. Check API endpoint URLs and methods
5. Validate JSON request payloads

---

**Last Updated**: May 2026
**Version**: 1.0
**Created for**: Spring Boot Order Management
