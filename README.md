# TaskFlow API

A complete REST API for task management with JWT authentication, developed in Java with Spring Boot 3.

## 🚀 Technologies

- **Java 17+**
- **Spring Boot 3.2.0**
- **Spring Security** (JWT Authentication)
- **Spring Data JPA**
- **PostgreSQL**
- **Docker & Docker Compose**
- **Maven**

## 📋 Features

### Authentication

- ✅ User registration
- ✅ JWT login
- ✅ Route protection

### Task Management

- ✅ Complete task CRUD
- ✅ Status and priority filters
- ✅ Pagination
- ✅ Task statistics
- ✅ User access control

### Task Status

- `PENDING` - Pending
- `IN_PROGRESS` - In progress
- `COMPLETED` - Completed
- `CANCELLED` - Cancelled

### Priorities

- `LOW` - Low
- `MEDIUM` - Medium
- `HIGH` - High
- `URGENT` - Urgent

## 🛠️ Installation and Execution

### Prerequisites

- Docker and Docker Compose
- Java 17+ (for local development)

### Docker Execution (Recommended)

1. Clone the repository:

```bash
git clone <repository-url>
cd taskflow-api
```

2. Start services with Docker Compose:

```bash
docker-compose up --build -d
```

### Local Execution

1. Install PostgreSQL and create the `taskflow` database
2. Configure credentials in `application.yml`
3. Run:

```bash
mvn spring-boot:run
```

## 📡 API Endpoints

### Authentication

```
POST /api/auth/register
POST /api/auth/login
```

### Tasks

```
GET    /api/tasks              # List tasks (with pagination and filters)
POST   /api/tasks              # Create task
GET    /api/tasks/{id}         # Get task by ID
PUT    /api/tasks/{id}         # Update task
DELETE /api/tasks/{id}         # Delete task
GET    /api/tasks/status/{status}  # List tasks by status
GET    /api/tasks/stats        # Task statistics
```

## 🔐 Authentication

All task routes require JWT authentication. Include the token in the header:

```
Authorization: Bearer <your-jwt-token>
```

## 📝 Usage Examples

### 1. Register user

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@email.com",
    "password": "123456"
  }'
```

### 2. Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@email.com",
    "password": "123456"
  }'
```

### 3. Create task

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-token>" \
  -d '{
    "title": "Implement API",
    "description": "Develop REST endpoints",
    "priority": "HIGH",
    "status": "IN_PROGRESS"
  }'
```

### 4. List tasks with filters

```bash
curl "http://localhost:8080/api/tasks?status=IN_PROGRESS&priority=HIGH&page=0&size=10" \
  -H "Authorization: Bearer <your-token>"
```

## 🐳 Docker

### Container Structure

- **taskflow-api**: Spring Boot application (port 8080)
- **taskflow-postgres**: PostgreSQL database (port 5432)

### Useful Commands

```bash
# Start services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down

# Complete rebuild
docker-compose down && docker-compose up --build -d
```

## 🌐 AWS Deployment (manual)

### Options

1. **AWS ECS with Fargate** (recommended)
2. **AWS Elastic Beanstalk** (simple alternative)

### Environment Variables (production)

```bash
DATABASE_URL=jdbc:postgresql://your-rds-endpoint:5432/taskflow
DATABASE_USERNAME=your-username
DATABASE_PASSWORD=your-password
JWT_SECRET=your-super-secret-key
PORT=8080
```

SPRING_PROFILES_ACTIVE=prod

````

## 🧪 Tests

```bash
# Run tests
mvn test

# Run with coverage
mvn test jacoco:report
```

## 📖 Swagger / OpenAPI

- UI: `http://localhost:8080/swagger-ui.html`
- Docs JSON: `http://localhost:8080/v3/api-docs`

To test protected endpoints in Swagger UI:
- Click "Authorize" and provide `Bearer <your-jwt-token>`

## 📊 Monitoring

The application includes:

- Structured logs
- Health checks
- Basic metrics

## 🤝 Contributing

1. Fork the project
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is under the MIT license. See the `LICENSE` file for more details.

## 👨‍💻 Author

Developed as a portfolio project to demonstrate skills in:

- Clean architecture in Java
- REST APIs with Spring Boot
- Security with JWT
- Docker and containerization
- Development best practices
````
