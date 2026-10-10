
CREATE DATABASE IF NOT EXISTS online_quiz;

USE online_quiz;

DROP TABLE IF EXISTS quiz_results;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS quizzes;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE quizzes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL
);

CREATE TABLE questions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    quiz_id INT,
    question VARCHAR(255) NOT NULL,
    option1 VARCHAR(100),
    option2 VARCHAR(100),
    option3 VARCHAR(100),
    option4 VARCHAR(100),
    correct_answer VARCHAR(100),
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id)
);

CREATE TABLE quiz_results (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50),
    quiz_name VARCHAR(100),
    score INT,
    total_questions INT,
    attempt_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (username, password)
VALUES ('admin', '1234');

INSERT INTO users (username, password)
VALUES ('student', '1234');

INSERT INTO quizzes (title)
VALUES ('Java Basics');

INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1, 'Which language is used for Android development?', 'Java', 'HTML', 'CSS', 'SQL', 'Java');

INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1, 'Which keyword is used to create a class in Java?', 'function', 'class', 'define', 'struct', 'class');

INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1, 'Which method is the starting point of a Java program?', 'start()', 'run()', 'main()', 'begin()', 'main()');

INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1, 'Which keyword is used to inherit a class in Java?', 'this', 'super', 'extends', 'implements', 'extends');

INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1, 'Which data type is used to store whole numbers?', 'float', 'int', 'char', 'boolean', 'int');

INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1, 'Which symbol is used to end a Java statement?', '.', ':', ';', ',', ';');

INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1, 'Which keyword is used to create an object?', 'new', 'create', 'object', 'make', 'new');

INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1, 'Which of these is not a Java primitive data type?', 'int', 'float', 'String', 'char', 'String');

INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1, 'Which keyword is used to define a constant variable?', 'static', 'final', 'const', 'constant', 'final');

INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1, 'Which method is used to compare two strings in Java?', 'compare()', 'equals()', 'match()', 'same()', 'equals()');

INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1, 'Which collection does not allow duplicate elements?', 'List', 'ArrayList', 'Set', 'Queue', 'Set');

INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1, 'Which keyword is used to handle exceptions?', 'try', 'check', 'error', 'handle', 'try');

INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1, 'Which access modifier allows access from anywhere?', 'private', 'protected', 'default', 'public', 'public');

INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1, 'Which loop is guaranteed to execute at least once?', 'for', 'while', 'do-while', 'foreach', 'do-while');

INSERT INTO questions
(quiz_id, question, option1, option2, option3, option4, correct_answer)
VALUES
(1, 'Which operator is used for logical AND?', '&', '||', '&&', '!', '&&');

SELECT COUNT(*) AS total_questions
FROM questions
WHERE quiz_id = 1;

SELECT *
FROM questions
WHERE quiz_id = 1
ORDER BY id;

SELECT *
FROM users;

SELECT *
FROM quizzes;

SELECT *
FROM quiz_results
ORDER BY attempt_date DESC;