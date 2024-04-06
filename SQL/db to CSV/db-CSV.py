import sqlite3
import csv

# Function to export data from the villains table to a CSV file
def export_villains_to_csv(db_file):
    connection = sqlite3.connect(db_file)
    cursor = connection.cursor()

    # Retrieve data from the villains table
    cursor.execute("SELECT * FROM villains WHERE brand LIKE '%Marvel%'")
    data = cursor.fetchall()

    # Write data to CSV file
    with open('db to CSV/m_villains.csv', 'w', newline='') as csv_file:
        csv_writer = csv.writer(csv_file)
        csv_writer.writerow([i[0] for i in cursor.description])  # Write header
        csv_writer.writerows(data)

    # Retrieve data from the villains table
    cursor.execute("SELECT * FROM villains WHERE brand LIKE '%DC%'")
    data = cursor.fetchall()

    # Write data to CSV file
    with open('db to CSV/dc_villains.csv', 'w', newline='') as csv_file:
        csv_writer = csv.writer(csv_file)
        csv_writer.writerow([i[0] for i in cursor.description])  # Write header
        csv_writer.writerows(data)

    # Close connection
    connection.close()

# Function to export data from the heroes table to a CSV file
def export_heroes_to_csv(db_file):
    connection = sqlite3.connect(db_file)
    cursor = connection.cursor()

    # Retrieve data from the heroes table
    cursor.execute("SELECT * FROM superheroes WHERE brand LIKE '%Marvel%'")
    data = cursor.fetchall()

    # Write data to CSV file
    with open('db to CSV/m_heroes.csv', 'w', newline='') as csv_file:
        csv_writer = csv.writer(csv_file)
        csv_writer.writerow([i[0] for i in cursor.description])  # Write header
        csv_writer.writerows(data)
    
    # Close connection
    connection.close()
    
    connection = sqlite3.connect(db_file)
    cursor = connection.cursor()

    # Retrieve data from the heroes table
    cursor.execute("SELECT * FROM superheroes WHERE brand LIKE '%DC%'")
    data = cursor.fetchall()

    # Write data to CSV file
    with open('db to CSV/dc_heroes.csv', 'w', newline='') as csv_file:
        csv_writer = csv.writer(csv_file)
        csv_writer.writerow([i[0] for i in cursor.description])  # Write header
        csv_writer.writerows(data)
    
    # Close connection
    connection.close()


# Function to combine data from villains and heroes tables into a single CSV file
def combine_data_to_csv(db_file):
    connection = sqlite3.connect(db_file)
    cursor = connection.cursor()

    # Retrieve data from both tables
    cursor.execute("SELECT * FROM villains")
    villains_data = cursor.fetchall()

    cursor.execute("SELECT * FROM superheroes")
    heroes_data = cursor.fetchall()

    # Combine data
    combined_data = villains_data + heroes_data

    # Write combined data to CSV file
    with open('db to CSV/combined_data.csv', 'w', newline='') as csv_file:
        csv_writer = csv.writer(csv_file)
        csv_writer.writerow([i[0] for i in cursor.description])  # Write header
        csv_writer.writerows(combined_data)

    # Close connection
    connection.close()

if __name__ == "__main__":
    db_file = 'db to CSV/superheroes.db'  # SQLite database file name
    export_villains_to_csv(db_file)
    export_heroes_to_csv(db_file)
    combine_data_to_csv(db_file)
