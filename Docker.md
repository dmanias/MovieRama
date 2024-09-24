# Docker Instructions for MovieRama

## Prerequisites

- Docker
- Docker Compose

## Development Environment

### Setting Up

1. Ensure you have Docker and Docker Compose installed on your system.

2. Create a `docker-compose.yml` file in the project root with the following content:

   ```yaml
   version: '3.8'
   
   services:
     app:
       build: .
       ports:
         - "8080:8080"
       environment:
         - SPRING_PROFILES_ACTIVE=dev
         - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/movierama_db
         - SPRING_DATASOURCE_USERNAME=movierama_user
         - SPRING_DATASOURCE_PASSWORD=movierama_password
       depends_on:
         - db
   
     db:
       image: postgres:13-alpine
       environment:
         - POSTGRES_DB=movierama_db
         - POSTGRES_USER=movierama_user
         - POSTGRES_PASSWORD=movierama_password
       volumes:
         - postgres_data:/var/lib/postgresql/data
   
   volumes:
     postgres_data:
   ```

### Running the Application

1. Build and start the containers:
   ```
   docker-compose up --build
   ```

2. Access the application at `http://localhost:8080`

3. To stop the application:
   ```
   docker-compose down
   ```

### Development with Hot-Reloading

1. Ensure your IDE is set up to compile classes automatically.
2. Add the spring-boot-devtools dependency to your project.
3. Run the application using `docker-compose up`.
4. Make changes to your code, and the application should automatically reload.

## Production Deployment

1. Build the production Docker image:
   ```
   docker build -t movierama:prod .
   ```

2. Run the container:
   ```
   docker run -d -p 8080:8080 \
     -e SPRING_PROFILES_ACTIVE=prod \
     -e SPRING_DATASOURCE_URL=jdbc:postgresql://your-prod-db-host:5432/movierama_db \
     -e SPRING_DATASOURCE_USERNAME=your_prod_username \
     -e SPRING_DATASOURCE_PASSWORD=your_prod_password \
     movierama:prod
   ```

   Replace the database URL, username, and password with your production database details.

## Docker Commands Cheat Sheet

- Build the Docker image: `docker build -t movierama .`
- Run a container: `docker run -p 8080:8080 movierama`
- List running containers: `docker ps`
- Stop a container: `docker stop <container_id>`
- Remove a container: `docker rm <container_id>`
- List Docker images: `docker images`
- Remove a Docker image: `docker rmi movierama`

## Troubleshooting

- If you encounter permission issues, you may need to run Docker commands with `sudo`.
- Ensure ports 8080 and 5432 are not in use by other applications.
- If the application can't connect to the database, check that the database service is fully up before the app service starts.