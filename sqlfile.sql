-- ============================================================
-- ONLINE QUIZ APPLICATION
-- Complete MySQL Database Script
-- ============================================================


-- ============================================================
-- 1. CREATE DATABASE
-- ============================================================

-- Create the database if it does not already exist
CREATE DATABASE IF NOT EXISTS online_quiz;

-- Select the database
USE online_quiz;


-- ============================================================
-- 2. DROP OLD TABLES
-- ============================================================

-- Delete old tables so that the database can be created fresh
-- Foreign-key dependent tables are deleted first

DROP TABLE IF EXISTS quiz_results;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS quizzes;
DROP TABLE IF EXISTS users;


-- ============================================================
-- 3. CREATE USERS TABLE
-- ============================================================

-- This table stores user login information

CREATE TABLE users (

    -- Unique ID for every user
    id INT PRIMARY KEY AUTO_INCREMENT,

    -- Username must be unique
    username VARCHAR(50) NOT NULL UNIQUE,

    -- Stores the user's password
    password VARCHAR(100) NOT NULL
);


-- ============================================================
-- 4. CREATE QUIZZES TABLE
-- ============================================================

-- This table stores information about quizzes

CREATE TABLE quizzes (

    -- Unique ID for every quiz
    id INT PRIMARY KEY AUTO_INCREMENT,

    -- Name/title of the quiz
    title VARCHAR(100) NOT NULL
);


-- ============================================================
-- 5. CREATE QUESTIONS TABLE
-- ============================================================

-- This table stores all MCQ questions

CREATE TABLE questions (

    -- Unique ID for every question
    id INT PRIMARY KEY AUTO_INCREMENT,

    -- ID of the quiz to which the question belongs
    quiz_id INT,

    -- Question text
    question VARCHAR(255) NOT NULL,

    -- Four answer options
    option1 VARCHAR(100),
    option2 VARCHAR(100),
    option3 VARCHAR(100),
    option4 VARCHAR(100),

    -- Correct answer
    correct_answer VARCHAR(100),

    -- Connect question with the quizzes table
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id)
);


-- ============================================================
-- 6. CREATE QUIZ RESULTS TABLE
-- ============================================================

-- This table stores the results of completed quizzes

CREATE TABLE quiz_results (

    -- Unique result ID
    id INT PRIMARY KEY AUTO_INCREMENT,

    -- Username of the person who attempted the quiz
    username VARCHAR(50),

    -- Name of the quiz
    quiz_name VARCHAR(100),

    -- Number of correct answers
    score INT,

    -- Total number of questions
    total_questions INT,

    -- Date and time of the attempt
    attempt_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- ============================================================
-- 7. INSERT USERS
-- ============================================================

-- Add an admin account
INSERT INTO users (username, password)
VALUES ('admin', '1234');

-- Add a sample student account
INSERT INTO users (username, password)
VALUES ('student', '1234');


-- ============================================================
-- 8. INSERT QUIZ
-- ============================================================

-- Create the Java Basics quiz
INSERT INTO quizzes (title)
VALUES ('Java Basics');


-- ============================================================
-- 9. INSERT 15 QUIZ QUESTIONS
-- ============================================================

-- Question 1
INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1,
 'Which language is used for Android development?',
 'Java', 'HTML', 'CSS', 'SQL', 'Java');


-- Question 2
INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1,
 'Which keyword is used to create a class in Java?',
 'function', 'class', 'define', 'struct', 'class');


-- Question 3
INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1,
 'Which method is the starting point of a Java program?',
 'start()', 'run()', 'main()', 'begin()', 'main()');


-- Question 4
INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1,
 'Which keyword is used to inherit a class in Java?',
 'this', 'super', 'extends', 'implements', 'extends');


-- Question 5
INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1,
 'Which data type is used to store whole numbers?',
 'float', 'int', 'char', 'boolean', 'int');


-- Question 6
INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1,
 'Which symbol is used to end a Java statement?',
 '.', ':', ';', ',', ';');


-- Question 7
INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1,
 'Which keyword is used to create an object?',
 'new', 'create', 'object', 'make', 'new');


-- Question 8
INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1,
 'Which of these is not a Java primitive data type?',
 'int', 'float', 'String', 'char', 'String');


-- Question 9
INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1,
 'Which keyword is used to define a constant variable?',
 'static', 'final', 'const', 'constant', 'final');


-- Question 10
INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1,
 'Which method is used to compare two strings in Java?',
 'compare()', 'equals()', 'match()', 'same()', 'equals()');


-- Question 11
INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1,
 'Which collection does not allow duplicate elements?',
 'List', 'ArrayList', 'Set', 'Queue', 'Set');


-- Question 12
INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1,
 'Which keyword is used to handle exceptions?',
 'try', 'check', 'error', 'handle', 'try');


-- Question 13
INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1,
 'Which access modifier allows access from anywhere?',
 'private', 'protected', 'default', 'public', 'public');


-- Question 14
INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1,
 'Which loop is guaranteed to execute at least once?',
 'for', 'while', 'do-while', 'foreach', 'do-while');


-- Question 15
INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1,
 'Which operator is used for logical AND?',
 '&', '||', '&&', '!', '&&');


-- ============================================================
-- 10. CHECK TOTAL NUMBER OF QUESTIONS
-- ============================================================

-- This query checks that the quiz contains exactly 15 questions

SELECT COUNT(*) AS total_questions
FROM questions
WHERE quiz_id = 1;


-- ============================================================
-- 11. DISPLAY ALL QUESTIONS
-- ============================================================

-- Display all questions belonging to Java Basics

SELECT *
FROM questions
WHERE quiz_id = 1
ORDER BY id;


-- ============================================================
-- 12. DISPLAY ALL USERS
-- ============================================================

-- Display registered users

SELECT *
FROM users;


-- ============================================================
-- 13. DISPLAY ALL QUIZZES
-- ============================================================

-- Display available quizzes

SELECT *
FROM quizzes;


-- ============================================================
-- 14. DISPLAY QUIZ RESULTS
-- ============================================================

-- Display all previous quiz attempts

SELECT *
FROM quiz_results
ORDER BY attempt_date DESC;


-- ============================================================
-- END OF DATABASE SCRIPT
-- ============================================================