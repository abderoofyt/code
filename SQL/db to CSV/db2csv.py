import sqlite3
import csv

def export_to_csv(data, filename):
    with open(filename, 'w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(data[0].keys())  # Write header
        for row in data:
            writer.writerow(row.values())

def separate_and_export(db_file):
    connection = sqlite3.connect(db_file)
    cursor = connection.cursor()

    # Query DC heroes
    cursor.execute("SELECT * FROM superheroes WHERE brand LIKE '%DC%'")
    dc_heroes = cursor.fetchall()
    export_to_csv(dc_heroes, 'dc_heroes.csv')

    # Query Marvel heroes
    cursor.execute("SELECT * FROM superheroes WHERE brand LIKE '%Marvel%'")
    marvel_heroes = cursor.fetchall()
    export_to_csv(marvel_heroes, 'marvel_heroes.csv')

    # Query DC villains
    cursor.execute("SELECT * FROM villains WHERE brand LIKE '%DC%'")
    dc_villains = cursor.fetchall()
    export_to_csv(dc_villains, 'dc_villains.csv')

    # Query Marvel villains
    cursor.execute("SELECT * FROM villains WHERE TeamAffiliation LIKE '%Marvel%'")
    marvel_villains = cursor.fetchall()
    export_to_csv(marvel_villains, 'marvel_villains.csv')

    # Close connection
    connection.close()

if __name__ == "__main__":
    db_file = 'superheroes.db'
    separate_and_export(db_file)
