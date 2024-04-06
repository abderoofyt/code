import sqlite3

def view_superheroes_database(db_file):
    connection = sqlite3.connect(db_file)
    cursor = connection.cursor()

    # Query the superheroes table
    cursor.execute("SELECT * FROM superheroes")
    superheroes = cursor.fetchall()

    print("Superheroes:")
    print("ID | Name | Alias | Age | Gender | Abilities | Weaknesses | Team Affiliation")
    for hero in superheroes:
        print(hero)

    # Query the villains table
    cursor.execute("SELECT * FROM villains")
    villains = cursor.fetchall()

    print("\nVillains:")
    print("ID | Name | Alias | Gender | Abilities | Weaknesses")
    for villain in villains:
        print(villain)

    # Close connection
    connection.close()

if __name__ == "__main__":
    db_file = 'Heroes/superheroes.db'
    view_superheroes_database(db_file)
