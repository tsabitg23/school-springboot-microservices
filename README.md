# Spring Boot Microservice

## Description
Made for exercise and hiring process. The theme of microservice is school. 
It has 4 module
- School (`school-service`)
- Student (`student-service`)
- Library (`library-service`)
- Event (`pensi-service`)

## Dependencies
- Java 17
- MongoDB 7.0.7
- Mysql 8

## How to Run
- Clone this repository
- Run Maven install
```bash
$ mvn clean verify -DskipTests
```
- Create DB in mysql for each module (school, student, library, pensi)
- Then updated `application.properties` in each module
- Run by starting each module
```bash
# Run Eureka Service
$ cd eureka-service && mvn spring-boot:run

# Run School Service
$ cd school-service && mvn spring-boot:run

# Run Student Service
$ cd student-service && mvn spring-boot:run

# Run Library Service
$ cd library-service && mvn spring-boot:run

# Run Event Service
$ cd pensi-service && mvn spring-boot:run

# Run API Gateway
$ cd api-gateway && mvn spring-boot:run
```

## API Documentation
You can access the API documentation by importing postman collection in `postman` folder

Api gateway is available at `http://localhost:8080`

For demo purposes, it is recommended to first insert the data using the postman collection by order
`School -> Student -> (Else)`

## Features
- Eureka Service Discovery
- MongoDB and Mysql
- API Gateway