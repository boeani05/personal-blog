# Personal Blog Application

A full-stack personal blog application built with Spring Boot and Thymeleaf. This project is based on the [roadmap.sh Personal Blog project](https://roadmap.sh/projects/personal-blog).

## 📋 Project Overview

This is a modern, secure blog platform that allows authenticated administrators to create, edit, and publish articles. The application features a public-facing blog interface and a secure admin dashboard for content management.

**Technology Stack:**
- Backend: Spring Boot 4.0.3, Java 21
- Frontend: Thymeleaf, HTML5, CSS3
- Database: PostgreSQL
- Security: Spring Security with BCrypt encryption
- Build Tool: Maven

## 🏗️ Architecture & Credits

### Backend Development
- **Core Implementation & Problem-Solving**: Developed by Bernhard Oberlechner
- The backend was built from scratch to meet the project requirements and bring the application into a working state

### Frontend & Code Quality
- **HTML Templates**: AI-generated (OpenAI/ChatGPT)
- **Code Refinement**: AI-assisted cleanup and optimization
- The AI tools were used for polishing and organizing the code structure, not for core functionality implementation

### Project Origin
This project is based on the [Personal Blog project from roadmap.sh](https://roadmap.sh/projects/personal-blog), which provides the project specification and requirements.

## ✨ Features

### Public Interface
- 🌐 View published articles with slug-based URLs
- 📖 Read full article content
- 🔍 Browse all published articles on the home page

### Admin Dashboard
- 🔐 Secure login with environment-based credentials
- ➕ Create new articles with automatic slug generation
- ✏️ Edit existing articles
- 🗑️ Delete articles
- 📊 Dashboard overview of all articles
- 🚪 Secure logout

### Security Features
- Spring Security with BCrypt password encryption
- CSRF protection enabled
- XSS protection headers
- Content Security Policy (CSP)
- Frame options (clickjacking protection)
- Session management (1 session per user max)
- Environment-based admin credentials
- Secure error handling (no stack traces exposed)

### Logging
- Rolling file appenders with 30-day rotation
- Separate error log file with 60-day retention
- Console and file logging
- Configurable log levels per component
- Total log size cap at 1GB

## 🚀 Getting Started

### Prerequisites
- Java 21+
- Maven 3.9+
- PostgreSQL 12+
- Git

### Installation

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd personal-blog
   ```

2. **Set up environment variables:**
   ```bash
   cp .env.example .env
   ```
   
   Edit `.env` and set your values:
   ```env
   # Admin Credentials
   BLOG_ADMIN_USERNAME=your_admin_username
   BLOG_ADMIN_PASSWORD=YourSecurePassword123!

   # Database Configuration
   DB_URL=jdbc:postgresql://localhost:5432/blogdb
   DB_USERNAME=postgres
   DB_PASSWORD=your_db_password
   ```

3. **Create the PostgreSQL database:**
   ```bash
   createdb -U postgres blogdb
   ```

4. **Build the project:**
   ```bash
   mvn clean install
   ```

5. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

   Or build a JAR and run it:
   ```bash
   mvn clean package
   java -jar target/personal-blog-0.0.1-SNAPSHOT.jar
   ```

6. **Access the application:**
   - Home: `http://localhost:8080`
   - Admin Dashboard: `http://localhost:8080/admin/dashboard`
   - Login: `http://localhost:8080/login`

## 📁 Project Structure

```
personal-blog/
├── src/
│   ├── main/
│   │   ├── java/com/bernhard/personal_blog/
│   │   │   ├── PersonalBlogApplication.java      # Main entry point
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java           # Spring Security configuration
│   │   │   ├── controller/
│   │   │   │   ├── admin/
│   │   │   │   │   └── AdminController.java      # Admin endpoints
│   │   │   │   └── guest/
│   │   │   │       └── GuestController.java      # Public endpoints
│   │   │   ├── dto/
│   │   │   │   ├── ArticleRequest.java           # Create article DTO
│   │   │   │   └── ArticleUpdateRequest.java     # Update article DTO
│   │   │   ├── entity/
│   │   │   │   └── Article.java                  # Article JPA entity
│   │   │   ├── exception/
│   │   │   │   └── GlobalExceptionHandler.java   # Global exception handling
│   │   │   ├── repo/
│   │   │   │   └── ArticleRepository.java        # Article JPA repository
│   │   │   ├── service/
│   │   │   │   └── ArticleService.java           # Business logic
│   │   │   └── status/
│   │   │       └── ArticleStatus.java            # Article status enum
│   │   └── resources/
│   │       ├── application.properties            # Application configuration
│   │       ├── logback-spring.xml               # Logging configuration
│   │       ├── static/
│   │       │   └── assets/
│   │       │       └── styles.css               # Stylesheet
│   │       └── templates/
│   │           ├── home.html                    # Home page
│   │           ├── login.html                   # Login page
│   │           ├── logout-success.html          # Logout confirmation
│   │           ├── admin/
│   │           │   ├── dashboard.html           # Admin dashboard
│   │           │   ├── addArticle.html          # Create article form
│   │           │   └── editArticle.html         # Edit article form
│   │           ├── error/
│   │           │   ├── 403.html                # Access denied
│   │           │   ├── 404.html                # Not found
│   │           │   ├── 500.html                # Server error
│   │           │   └── error.html              # Generic error
│   │           └── public/
│   │               └── article.html             # Article detail page
│   └── test/
│       └── java/com/bernhard/personal_blog/
│           ├── PeresonalBlogApplicationTests.java
│           └── service/
│               └── ArticleServiceTest.java
├── pom.xml                                      # Maven configuration
├── mvnw & mvnw.cmd                             # Maven wrapper
├── .env.example                                # Environment variables template
├── .gitignore                                  # Git ignore rules
└── README.md                                   # This file
```

## 🔐 Security Configuration

The application uses environment variables to manage sensitive credentials:

- **Admin Credentials**: Set via `BLOG_ADMIN_USERNAME` and `BLOG_ADMIN_PASSWORD`
- **Database Credentials**: Set via `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD`

These should **never** be committed to version control. The `.gitignore` file excludes `.env` files automatically.

### Security Headers
- X-Frame-Options: DENY (prevents clickjacking)
- X-XSS-Protection: 1; mode=block
- Content-Security-Policy: Restricts resource loading to same-origin

### Password Security
- Passwords are hashed using BCrypt with Spring Security
- Default cost factor ensures strong hashing

## 📊 Database Schema

The application uses JPA with Hibernate for ORM. The main entity is:

### Article Entity
- `id` (Long, Primary Key)
- `title` (String, Required)
- `content` (Text, Required)
- `slug` (String, Unique, Required) - URL-friendly identifier
- `createdAt` (LocalDateTime)
- `updatedAt` (LocalDateTime)
- `publishedAt` (LocalDateTime)
- `articleStatus` (Enum: PUBLISHED, DRAFT)

Migrations are handled automatically via `spring.jpa.hibernate.ddl-auto=update`.

## 🧪 Testing

Run the test suite:
```bash
mvn test
```

### Test Coverage
- `ArticleServiceTest.java` - Service layer tests
- `PeresonalBlogApplicationTests.java` - Application context tests

## 📝 API Endpoints

### Public Endpoints
- `GET /` - Home page with article list
- `GET /public/article/{slug}` - View article by slug
- `GET /login` - Login page
- `POST /login` - Submit login credentials
- `GET /logout-success` - Logout confirmation

### Admin Endpoints (Requires ADMIN role)
- `GET /admin/dashboard` - Admin dashboard
- `GET /admin/add` - Add article form
- `POST /admin/add` - Create new article
- `GET /admin/edit/{id}` - Edit article form
- `POST /admin/edit/{id}` - Update article
- `POST /admin/delete/{id}` - Delete article
- `GET /logout` - Logout

### Error Endpoints
- `GET /error/403` - Access denied
- `GET /error/404` - Not found
- `GET /error/500` - Server error

## 📋 Logging

Logs are configured via `logback-spring.xml`:

- **Console Output**: All logs are printed to console
- **File Logs**: Stored in `logs/personal-blog.log` (rotated daily)
- **Error Logs**: Stored separately in `logs/error.log` (90-day retention)
- **Log Levels**:
  - Application (com.bernhard.personal_blog): DEBUG
  - Spring Framework: INFO
  - Hibernate: INFO
  - SQL: DEBUG (only SQL statements, not values)

## 🌐 Environment Configuration

The application can be configured via `application.properties` and environment variables:

```properties
# Application Name
spring.application.name=personal-blog

# Database
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/blogdb}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:postgres}

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false

# Security
server.error.include-message=never
server.error.include-binding-errors=never
server.error.include-stacktrace=never
server.error.include-exception=false
```

## 🚢 Deployment

### Docker (Recommended)
Create a `Dockerfile`:
```dockerfile
FROM eclipse-temurin:21-jdk-alpine
COPY target/personal-blog-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

Build and run:
```bash
mvn clean package
docker build -t personal-blog .
docker run -p 8080:8080 \
  -e BLOG_ADMIN_USERNAME=admin \
  -e BLOG_ADMIN_PASSWORD=secure_password \
  -e DB_URL=jdbc:postgresql://db:5432/blogdb \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=db_password \
  personal-blog
```

### Traditional Deployment
1. Build the JAR: `mvn clean package`
2. Copy `target/personal-blog-0.0.1-SNAPSHOT.jar` to your server
3. Set environment variables on the server
4. Run: `java -jar personal-blog-0.0.1-SNAPSHOT.jar`

## 🐛 Troubleshooting

### Application won't start
- Check if PostgreSQL is running
- Verify environment variables are set: `echo %BLOG_ADMIN_USERNAME%` (Windows) or `echo $BLOG_ADMIN_USERNAME` (Linux/Mac)
- Check logs for detailed error messages

### Can't login
- Verify `BLOG_ADMIN_USERNAME` and `BLOG_ADMIN_PASSWORD` are set correctly
- Check logs for authentication errors
- Ensure credentials contain no trailing spaces

### Database connection errors
- Verify PostgreSQL is running on the configured host/port
- Check `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` are correct
- Ensure the database exists: `createdb -U postgres blogdb`

### Port already in use
Change the port in `application.properties`:
```properties
server.port=8081
```

## 📚 Dependencies

Key dependencies managed by Spring Boot 4.0.3:
- Spring Security
- Spring Data JPA
- Spring Web MVC
- Thymeleaf
- PostgreSQL JDBC Driver
- SLF4J & Logback
- Jakarta Validation API

See `pom.xml` for complete dependency list.

## 📄 License

This project is provided as-is for educational and personal use.

## 👨‍💻 Authors

- **Backend Development & Problem-Solving**: Bernhard Erlacher
- **Frontend Templates & Code Refinement**: AI-Assisted
- **Project Specification**: Based on [roadmap.sh Personal Blog](https://roadmap.sh/projects/personal-blog)

## 🔗 Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Reference](https://spring.io/projects/spring-security)
- [Thymeleaf Documentation](https://www.thymeleaf.org/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [roadmap.sh Personal Blog Project](https://roadmap.sh/projects/personal-blog)

## 💡 Future Enhancements

Potential improvements for future versions:
- [ ] Multiple user accounts with role-based access
- [ ] Article categories and tags
- [ ] Search functionality
- [ ] Comments system
- [ ] Markdown support for article content
- [ ] Draft saving functionality
- [ ] Article scheduling/publishing in the future
- [ ] REST API endpoints
- [ ] Swagger/OpenAPI documentation
- [ ] Rate limiting for brute-force protection
- [ ] Database migrations with Flyway
- [ ] Integration tests with TestContainers
- [ ] CI/CD pipeline (GitHub Actions)

## 📞 Support

For issues or questions, refer to the [roadmap.sh Personal Blog project](https://roadmap.sh/projects/personal-blog) specification.

