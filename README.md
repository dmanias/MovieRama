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
- Search functionality for movies

## Tech Stack

- Java 17
- Spring Boot 3.3.3
- Spring Security for authentication and authorization
- PostgreSQL for data persistence
- Maven for dependency management and build automation
- Thymeleaf for server-side HTML templating
- Bootstrap 5 for responsive front-end design

## Prerequisites

- Java Development Kit (JDK) 17 or later
- Maven 3.6+
- PostgreSQL 13+

## Installation and Running Instructions

There are three ways to run the MovieRama application:

1. Using the provided JAR file
2. Local setup with Maven
3. Using Docker

For detailed instructions on each method, please refer to the following documents in the `deliverables` folder:

- `RunningMovieRama.doc`: Step-by-step guide for running the application using the provided JAR file.
- `MovieRama_Architecture.md`: Detailed explanation of the application's architecture and design decisions.
- `Docker.md`: Instructions for running MovieRama using Docker.
- `movierama-0.0.1-SNAPSHOT.jar`: The runnable JAR file for the application.

To quickly run the application using the JAR file, use the following command:
```
java -jar deliverables/movierama-0.0.1-SNAPSHOT.jar
```

### Local Setup

1. **Database Setup**
   - Install PostgreSQL if not already installed
   - Create a database named `movierama_dev`:
     ```
     createdb movierama_dev
     ```
   - Update `src/main/resources/application-dev.properties` with your database credentials:
     ```
     spring.datasource.url=jdbc:postgresql://localhost:5432/movierama_dev
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

2. **Build and Run the Application**
   ```
   mvn clean install
   mvn spring-boot:run
   ```

3. **Access the Application**
   - Open a web browser and go to `http://localhost:8080`

### Docker Setup

For instructions on running MovieRama using Docker, please refer to the `Docker.md` file in the `deliverables` folder. This document provides detailed steps for setting up and running the application in a Docker container.

### Running JAR File

1. Navigate to the deliverables folder
2. Run the following command:
```
java -jar movierama-0.0.1-SNAPSHOT.jar
```
3. Access the application at http://localhost:8080

For more detailed instructions, please refer to the `RunningMovieRama.doc` in the `deliverables` folder.

## Accessing the Application

1. Register a new user account
2. Log in with your credentials
3. Start adding movies and interacting with the application

## API Documentation

API documentation is available via Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```

## Troubleshooting

- If port 8080 is already in use locally, you can change the port in `application-dev.properties`:
  ```
  server.port=8081
  ```
- Ensure that the PostgreSQL service is running before starting the application
- For any issues, please check the application logs in the console

## Contact

For any questions or issues, please contact:
Dimosthenis Manias - dimosthenis.manias@gmail.com