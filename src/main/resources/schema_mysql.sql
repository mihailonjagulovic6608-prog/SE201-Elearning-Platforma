-- MySQL schema for elearn_console
CREATE DATABASE IF NOT EXISTS elearn_console CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE elearn_console;

CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(190) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  role VARCHAR(30) NOT NULL,
  disabled TINYINT(1) NOT NULL DEFAULT 0,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS courses (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  category VARCHAR(100) NOT NULL,
  level VARCHAR(50) NOT NULL,
  status VARCHAR(30) NOT NULL DEFAULT 'DRAFT',
  instructor_id INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_courses_cat_level (category, level),
  CONSTRAINT fk_course_instructor FOREIGN KEY (instructor_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS enrollments (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  course_id INT NOT NULL,
  payment_status VARCHAR(30) NOT NULL DEFAULT 'PAID',
  enrolled_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uq_user_course (user_id, course_id),
  CONSTRAINT fk_enroll_user FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT fk_enroll_course FOREIGN KEY (course_id) REFERENCES courses(id)
);

CREATE TABLE IF NOT EXISTS login_attempts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  success TINYINT(1) NOT NULL,
  attempted_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_attempt_user_time (user_id, attempted_at),
  CONSTRAINT fk_attempt_user FOREIGN KEY (user_id) REFERENCES users(id)
);
