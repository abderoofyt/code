import sqlite3
import random
import string

# Function to generate random employee data
def generate_employee_data(num_records):
    data = []
    for _ in range(num_records):
        first_name = ''.join(random.choices(string.ascii_lowercase, k=5))
        last_name = ''.join(random.choices(string.ascii_lowercase, k=5))
        age = random.randint(20, 65)
        department = random.choice(['HR', 'Finance', 'IT', 'Marketing'])
        salary = random.randint(30000, 100000)
        data.append((first_name, last_name, age, department, salary))
    return data

# Function to create and insert data into SQLite database
def create_and_insert_data(db_file, num_records):
    connection = sqlite3.connect(db_file)
    cursor = connection.cursor()

    # Create employees table
    cursor.execute('''CREATE TABLE IF NOT EXISTS employees (
                        id INTEGER PRIMARY KEY,
                        first_name TEXT,
                        last_name TEXT,
                        age INTEGER,
                        department TEXT,
                        salary INTEGER
                    )''')

    # Generate and insert data
    employee_data = generate_employee_data(num_records)
    cursor.executemany("INSERT INTO employees (first_name, last_name, age, department, salary) VALUES (?, ?, ?, ?, ?)", employee_data)

    # Commit changes and close connection
    connection.commit()
    connection.close()

if __name__ == "__main__":
    db_file = 'employees.db'  # SQLite database file name
    num_records = 10  # Number of records to generate
    create_and_insert_data(db_file, num_records)
