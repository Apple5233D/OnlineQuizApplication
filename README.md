# Online Quiz Application

## Project Overview

The Online Quiz Application is a Java-based desktop application that allows users to take multiple-choice quizzes and view their results. It provides a simple interface for students to answer questions and for administrators to manage quizzes.

## Technologies Used

- **Programming Language:** Java
- **User Interface:** Java Swing
- **Database:** MySQL
- **Database Connectivity:** JDBC
- **IDE:** IntelliJ IDEA

## Features

### User Features
- User registration and login
- Select and attempt available quizzes
- Answer multiple-choice questions with four options
- Receive feedback after submitting an answer
- View the final score after completing a quiz
- Review incorrectly answered questions
- Track quiz results and previous attempts

### Admin Features
- Admin login
- Create new quizzes
- Add and manage quiz questions
- Edit existing quizzes and questions
- Delete quizzes and questions

### Quiz System
- Each quiz contains 15 multiple-choice questions
- Questions are displayed one at a time
- Answers are checked and scores are calculated automatically
- Quiz results are stored in the database

## Database

The application uses MySQL to store and manage application data.

**Database name:** `online_quiz`

Main tables:
- `users` – Stores user account details
- `quizzes` – Stores quiz information
- `questions` – Stores questions and answer options
- `quiz_results` – Stores quiz scores and results

## How to Run the Project

1. Install Java JDK and MySQL.
2. Clone or download this repository.
3. Open the project in IntelliJ IDEA.
4. Create the MySQL database using the provided SQL script.
5. Configure the MySQL connection details in the Java application.
6. Add the MySQL Connector/J library to the project.
7. Run `Main.java` to start the application.

## Project Structure

- `Main.java` – Main application entry point and application logic
- `sqlfile.sql` – MySQL database setup script

## Learning Outcomes

This project helped develop practical knowledge of Java programming, GUI development using Swing, database management with MySQL, JDBC connectivity, user authentication, and CRUD operations.

## Author

Developed as an academic project to demonstrate Java desktop application development and database integration.
