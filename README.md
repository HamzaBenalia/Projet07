# Poseidon Application 
![application header](https://github.com/HamzaBenalia/Projet07/assets/99022185/415951d2-ff88-4f98-94ed-11f257035369)

Poseidon is a software company deployed as a web application to generate more transactions on financial markets. The application's objective is to gather as much as possible of information from different sources.

## Technical Stack
- Backend : Java 17 and Spring Boot framework v3.0.1.
- Spring Security (to secure the application)
- Frontend : HTML, Thymeleaf and Bootstrap v5
- Database : MySQL

## Requirement
In order that the application work for your properly, you need the following :
- JAVA jdk 17
- Database called Poseidon
- Dependencies: Thymeleaf, spring security, bootstrap.


## Setting up the application
In your computer, choose an application that allows your to create a dataBase. For example workBench.
- Create the API Database with either : `CREATE DATABASE demo` or copy the sql script and copy it in workbench and send it. (You can find a script in the app folder above)
- Make sure that you are using the Poseidon DataBase.
- If everything is set up correctly, you can now start the application with : `mvn spring-boot:run`

## Testing the application
If you loaded the database creation file, there should be 2 pre-created users :
- An admin account : `Username: Eric; Password: 1234`
- A simple user account : `Username: toto; Password :Hamza@240422`

copy and paste it on your Url: [http://localhost:8080](http://localhost:8080) to start using the application

## Features
The application is made of 6 differents endpoints : 
- BidList
- Curve Points
- Rating
- Trade
- Rule
- Users : As a matter of information; only ADMINS can access this page and only the latters can apply a CRUD on this entity.

## Unit test (Jacoco).


![jacoco new](https://github.com/HamzaBenalia/Projet07/assets/99022185/9c71f074-093b-4dbb-b901-ca88788e7d17)

