import sqlite3

# Function to view data in the employees table
def view_employee_data(db_file):
    connection = sqlite3.connect(db_file)
    cursor = connection.cursor()

    # Retrieve data from the employees table
    cursor.execute("SELECT * FROM employees")
    data = cursor.fetchall()

    # Print the data
    if len(data) > 0:
        print("Employee Data:")
        for row in data:
            print(row)
    else:
        print("No employee data found.")

    # Close connection
    connection.close()

if __name__ == "__main__":
    db_file = 'employees.db'  # SQLite database file name
    view_employee_data(db_file)
