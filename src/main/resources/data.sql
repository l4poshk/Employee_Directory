-- =============================================================================
-- Employee Directory - Seed Data
-- =============================================================================
-- NOTE: This file is NOT auto-executed. The DataLoader.java component handles
-- seeding on first startup using BCryptPasswordEncoder for reliable password
-- encoding. To use this file instead, set the following in application.properties:
--
--   spring.sql.init.mode=always
--   spring.jpa.defer-datasource-initialization=true
--
-- And remove or disable the DataLoader component.
-- =============================================================================

-- Users (passwords are BCrypt encoded)
-- admin / admin123
-- user  / user123
-- INSERT INTO users (username, password, role) VALUES
--   ('admin', '$2a$10$EIXe6ypNhGDWJmIKmHSvB.OTNuHx7MlfXkWfPijWp5VBfeR7GC6fG', 'ROLE_ADMIN'),
--   ('user',  '$2a$10$EIXe6ypNhGDWJmIKmHSvB.OTNuHx7MlfXkWfPijWp5VBfeR7GC6fG', 'ROLE_USER');

-- Departments
-- INSERT INTO departments (name) VALUES ('Engineering'), ('Human Resources');

-- Employees
-- INSERT INTO employees (name, position, department_id) VALUES
--   ('Alice Johnson',  'Software Engineer', 1),
--   ('Bob Smith',      'Senior Developer',  1),
--   ('Carol Williams', 'HR Manager',        2),
--   ('David Brown',    'DevOps Engineer',   1),
--   ('Eva Martinez',   'HR Specialist',     2);
