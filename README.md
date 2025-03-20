# Movie Management Application

This project is a comprehensive Java-based application designed to manage a collection of movies. It provides a RESTful API for performing CRUD (Create, Read, Update, Delete) operations on movie data. The application is built using Jakarta EE, leveraging its robust features for enterprise-level applications.

## Project Overview

The Movie Management Application is intended to serve as a backend service for managing movie-related data. It allows users to add new movies, update existing movie details, delete movies, and retrieve information about movies. The application is structured to ensure scalability, maintainability, and ease of use.

## Architecture

The application follows a layered architecture, which separates concerns and promotes clean code practices:

- **Entity Layer**: Represents the data model and is mapped to the database using JPA (Java Persistence API). The `Movie` entity is the core component of this layer.

- **DTO Layer**: Data Transfer Objects (DTOs) are used to transfer data between the client and server. This layer includes `MovieDTO`, `CreateMovieDTO`, and `UpdateMovieDTO` for different operations.

- **Repository Layer**: Handles database interactions using the repository pattern. The `MovieRepository` provides methods for CRUD operations on the `Movie` entity.

- **Service Layer**: Contains the business logic of the application. The `MovieService` class orchestrates operations between the repository and other components, ensuring data integrity and business rules are enforced.

- **Mapper Layer**: Utilizes MapStruct to convert between entities and DTOs, ensuring a clear separation between the data model and the API contract.

## Key Components

- **Movie Entity**: Represents a movie with attributes such as title, description, release date, director, and duration. It includes validation annotations to ensure data integrity.

- **MovieDTO**: A record that encapsulates movie data for API responses.

- **CreateMovieDTO & UpdateMovieDTO**: Records used for creating and updating movie data, respectively, with validation constraints.

- **MovieService**: Provides methods for creating, retrieving, updating, and deleting movies. It ensures transactional integrity and handles exceptions.

- **MovieRepository**: An interface extending `CrudRepository`, providing methods for database operations.

- **MovieMapper**: A MapStruct mapper that converts between `Movie` entities and DTOs.
