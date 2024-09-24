# MovieRama

MovieRama is a social sharing platform for movie enthusiasts. Users can share their favorite movies, view others' submissions, and express their opinions through likes and dislikes.

## Features

- User authentication (login/signup)
- Movie submission with title, description, and auto-tracked publication date
- Like/dislike voting system (one vote per user per movie)
- Movie listing with sorting options (likes, dislikes, date added)
- User-specific movie filtering
- Responsive design for various devices
- RESTful API with JWT authentication
- Rate limiting to prevent abuse
- Secure password hashing

## Tech Stack

- Java 17
- Spring Boot 3.3.3
- Spring Security for authentication and authorization
- PostgreSQL for data persistence
- Maven for dependency management and build automation
- Thymeleaf for server-side HTML templating
- Bootstrap 5 for responsive front-end design
- Docker for containerization
- GitHub Actions for CI/CD

## Prerequisites

- Java Development Kit (JDK) 17 or later
- Maven 3.6+
- PostgreSQL 13+
- Docker (optional, for containerized deployment)

## Installation

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/MovieRama.git
   cd MovieRama
   ```

2. Configure the database:
    - Create a PostgreSQL database named `movierama_db`
    - Update `src/main/resources/application.properties` with your database credentials

3. Build the project:
   ```
   mvn clean install
   ```

4. Run the application:
   ```
   java -jar target/movierama-0.0.1-SNAPSHOT.jar
   ```

   Or use Spring Boot Maven plugin:
   ```
   mvn spring-boot:run
   ```

5. Access the application at `http://localhost:8080`

## Docker Deployment

1. Build the Docker image:
   ```
   docker build -t movierama:latest .
   ```

2. Run the container:
   ```
   docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prod movierama:latest
   ```

## API Documentation

API documentation is available via Swagger UI. After starting the application, visit:
```
http://localhost:8080/swagger-ui.html
```

## Running Tests

Execute the test suite using Maven:
```
mvn test
```

## Security

- JWT is used for stateless authentication
- Passwords are hashed using BCrypt
- Rate limiting is implemented to prevent brute-force attacks
- Input validation and sanitization are in place to prevent injection attacks

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Bootstrap](https://getbootstrap.com/)
- [PostgreSQL](https://www.postgresql.org/)

## Contact

Your Name - [@yourtwitter](https://twitter.com/yourtwitter) - email@example.com

Project Link: [https://github.com/yourusername/MovieRama](https://github.com/yourusername/MovieRama)