-- Create a temporary table to hold the generated data
CREATE TEMPORARY TABLE temp_employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    age INT,
    department VARCHAR(50),
    salary INT
);

-- Generate random data and insert into the temporary table
INSERT INTO temp_employees (first_name, last_name, age, department, salary)
SELECT
    SUBSTRING(MD5(RAND()) FROM 1 FOR 5) AS first_name, -- Random first name
    SUBSTRING(MD5(RAND()) FROM 1 FOR 5) AS last_name, -- Random last name
    FLOOR(20 + RAND() * 45) AS age, -- Random age between 20 and 65
    CASE FLOOR(RAND() * 4)
        WHEN 0 THEN 'HR'
        WHEN 1 THEN 'Finance'
        WHEN 2 THEN 'IT'
        ELSE 'Marketing'
    END AS department, -- Random department
    FLOOR(30000 + RAND() * (100000 - 30000)) AS salary -- Random salary between 30000 and 100000
FROM
    information_schema.tables AS t1,
    information_schema.tables AS t2;

-- Select the generated data from the temporary table
SELECT * FROM temp_employees;

-- Drop the temporary table
DROP TEMPORARY TABLE temp_employees;