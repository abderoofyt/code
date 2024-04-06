import sqlite3

def search_database(db_file, query):
    connection = sqlite3.connect(db_file)
    cursor = connection.cursor()

     # Search in superheroes table
    cursor.execute("SELECT * FROM superheroes WHERE name LIKE ? OR alias LIKE ? OR age LIKE ? OR gender LIKE ? OR abilities LIKE ? OR weaknesses LIKE ? OR team_affiliation LIKE ?", 
                   ('%' + query + '%', '%' + query + '%', '%' + query + '%', '%' + query + '%', '%' + query + '%', '%' + query + '%', '%' + query + '%'))
    superhero_results = cursor.fetchall()

    # Search in villains table
    cursor.execute("SELECT * FROM villains WHERE name LIKE ? OR alias LIKE ? OR gender LIKE ? OR abilities LIKE ? OR weaknesses LIKE ?", 
                   ('%' + query + '%', '%' + query + '%', '%' + query + '%', '%' + query + '%', '%' + query + '%'))
    villain_results = cursor.fetchall()

    connection.close()

    return superhero_results, villain_results

if __name__ == "__main__":
    db_file = 'superheroes.db'

    # Example search query
    search_query = input("Enter search query: ")
    superhero_results, villain_results = search_database(db_file, search_query)

    print("Superhero results:")
    for result in superhero_results:
        print(result)

    print("\nVillain results:")
    for result in villain_results:
        print(result)
