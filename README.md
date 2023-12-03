# Userapp - Mini Assignment 2

## Description

`userapp` is a Spring Boot application designed for user management. It provides RESTful APIs for creating user, and quering user information.

## Table of Contents

- [Dependencies](#technologies-used)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)

## Dependencies

- Java 8+
- Spring Boot 3.2.0
- Spring Web
- Spring WebFlux
- Spring Data JPA
- MySQL Connector
- Lombok
- Jackson Databind
- Mockito
- JSON Path
- Hamcrest

## Prerequisites

To run the application your system must have the following:

- Java 17
- MySQL Database

## Installation

1. Clone/copy the project
2. Navigate to the project directory
3. Build project using Maven

## Configuration

1. Configure the project in the application.yml file

```
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/userdb
    username: your_db_username
    password: your_db_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
```

2. Run the application:

```
mvn spring-boot:run
```

## Usage

Available REST API endpoints:

1. **POST /api/users** : Create N new user.

   **Request payload**

   ```
   {
       "size" : 5
   }
   ```

   here "size" value is in the range of 1 to 5

   **Response payload contains a list of new users created**

1. **GET /api/users** : Get a list of users based on the query parameters

   Should support 4 query params:

   1. `?sortType=<Name/Age>`
   2. `?sortOrder=<EVEN/ODD>`
   3. `?limit=5` limit value can be 1 - 5
   4. `?offset=0` offset value can be 0 - 5

   Response payload contains a list of users and page information
