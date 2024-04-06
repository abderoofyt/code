import sqlite3
from marvel import heroes as m_heroes
from marvel import villains as m_villains
from dc import heroes as dc_heroes
from dc import villains as dc_villains

# Function to create and populate the superhero database
def create_superhero_database(db_file):
    connection = sqlite3.connect(db_file)
    cursor = connection.cursor()

    # Create the superheroes table with all columns
    cursor.execute('''CREATE TABLE IF NOT EXISTS superheroes (
                        id INTEGER PRIMARY KEY,
                        brand TEXT,
                        name TEXT,
                        alias TEXT,
                        age INTEGER,
                        gender TEXT,
                        abilities TEXT,
                        weaknesses TEXT,
                        team_affiliation TEXT
                    )''')
    
    # Create the villains table with all columns
    cursor.execute('''CREATE TABLE IF NOT EXISTS villains (
                        id INTEGER PRIMARY KEY,
                        brand TEXT,
                        name TEXT,
                        alias TEXT,
                        gender TEXT,
                        abilities TEXT,
                        weaknesses TEXT
                    )''')

    while True:
        # Ask user if they want to insert new data
        insert_choice = input("Do you want to insert new data? (yes/no: ")
        if insert_choice.lower() == 'no':
            print("No new data inserted.")
            break
        elif insert_choice.lower() == 'yes':
            while True:
                data_type = input("Enter data type (marvel heroes, marvel villains, dc heroes, dc villains): ")
                if data_type.lower() == 'marvel heroes':
                    data = input("Enter Marvel Heroes data (name, alias, age, gender, abilities, weaknesses, team affiliation): ")
                    data = data.split(",")
                    if len(data) != 7:
                        print("Incorrect number of inputs for Marvel Heroes. Please provide name, alias, age, gender, abilities, weaknesses, and team affiliation.")
                        continue
                    try:
                        cursor.execute("INSERT INTO superheroes (brand, name, alias, age, gender, abilities, weaknesses, team_affiliation) VALUES ('Marvel', ?, ?, ?, ?, ?, ?, ?)", data)
                        print("Marvel Heroes data inserted successfully.")
                    except sqlite3.Error as e:
                        print("Error inserting Marvel Heroes data:", e)
                    retry = input("Do you want to insert another Marvel Heroes data? (yes/no): ")
                    if retry.lower() != 'yes':
                        break
                elif data_type.lower() == 'marvel villains':
                    data = input("Enter Marvel Villains data (name, alias, gender, abilities, weaknesses): ")
                    data = data.split(",")
                    if len(data) != 5:
                        print("Incorrect number of inputs for Marvel Villains. Please provide name, alias, gender, abilities, and weaknesses.")
                        continue
                    try:
                        cursor.execute("INSERT INTO villains (brand, name, alias, gender, abilities, weaknesses) VALUES ('Marvel', ?, ?, ?, ?, ?)", data)
                        print("Marvel Villains data inserted successfully.")
                    except sqlite3.Error as e:
                        print("Error inserting Marvel Villains data:", e)
                    retry = input("Do you want to insert another Marvel Villains data? (yes/no): ")
                    if retry.lower() != 'yes':
                        break
                elif data_type.lower() == 'dc heroes':
                    data = input("Enter DC Heroes data (name, alias, age, gender, abilities, weaknesses, team affiliation): ")
                    data = data.split(",")
                    if len(data) != 7:
                        print("Incorrect number of inputs for DC Heroes. Please provide name, alias, age, gender, abilities, weaknesses, and team affiliation.")
                        continue
                    try:
                        cursor.execute("INSERT INTO superheroes (brand, name, alias, age, gender, abilities, weaknesses, team_affiliation) VALUES ('DC', ?, ?, ?, ?, ?, ?, ?)", data)
                        print("DC Heroes data inserted successfully.")
                    except sqlite3.Error as e:
                        print("Error inserting DC Heroes data:", e)
                    retry = input("Do you want to insert another DC Heroes data? (yes/no): ")
                    if retry.lower() != 'yes':
                        break
                elif data_type.lower() == 'dc villains':
                    data = input("Enter DC Villains data (name, alias, gender, abilities, weaknesses): ")
                    data = data.split(",")
                    if len(data) != 5:
                        print("Incorrect number of inputs for DC Villains. Please provide name, alias, gender, abilities, and weaknesses.")
                        continue
                    try:
                        cursor.execute("INSERT INTO villains (brand, name, alias, gender, abilities, weaknesses) VALUES ('DC', ?, ?, ?, ?, ?)", data)
                        print("DC Villains data inserted successfully.")
                    except sqlite3.Error as e:
                        print("Error inserting DC Villains data:", e)
                    retry = input("Do you want to insert another DC Villains data? (yes/no): ")
                    if retry.lower() != 'yes':
                        break
                else:
                    print("Invalid data type. Please choose from marvel heroes, marvel villains, dc heroes, dc villains.")
        else:
            print("Invalid choice. Please enter 'yes', 'no', or 'done'.")

    # Commit changes and close connection
    connection.commit()
    connection.close()

if __name__ == "__main__":
    db_file = 'Heroes/superheroes.db'  # SQLite database file name
    create_superhero_database(db_file)
