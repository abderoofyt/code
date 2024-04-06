import os
import sqlite3
from marvel import heroes as m_heroes
from marvel import villains as m_villains
from dc import heroes as dc_heroes
from dc import villains as dc_villains

# Function to create and populate the superhero database
def create_superhero_database(db_file):
    # Delete the existing database if it exists
    if os.path.exists(db_file):
        os.remove(db_file)

    connection = sqlite3.connect(db_file)
    cursor = connection.cursor()

    # Create the superheroes table with all columns
    cursor.execute('''CREATE TABLE superheroes (
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
    cursor.execute('''CREATE TABLE villains (
                        id INTEGER PRIMARY KEY,
                        brand TEXT,
                        name TEXT,
                        alias TEXT,
                        gender TEXT,
                        abilities TEXT,
                        weaknesses TEXT
                    )''')

    # Insert the Marvel data into tables
    cursor.executemany("INSERT INTO superheroes (brand, name, alias, age, gender, abilities, weaknesses, team_affiliation) VALUES ('Marvel', ?, ?, ?, ?, ?, ?, ?)", m_heroes())
    cursor.executemany("INSERT INTO villains (brand, name, alias, gender, abilities, weaknesses) VALUES ('Marvel', ?, ?, ?, ?, ?)", m_villains())
    
    # Insert the DC data into tables
    cursor.executemany("INSERT INTO superheroes (brand, name, alias, age, gender, abilities, weaknesses, team_affiliation) VALUES ('DC', ?, ?, ?, ?, ?, ?, ?)", dc_heroes())
    cursor.executemany("INSERT INTO villains (brand, name, alias, gender, abilities, weaknesses) VALUES ('DC', ?, ?, ?, ?, ?)", dc_villains())
    
    # Commit changes and close connection
    connection.commit()
    connection.close()

if __name__ == "__main__":
    db_file = 'Heroes/superheroes.db'  # SQLite database file name
    create_superhero_database(db_file)
