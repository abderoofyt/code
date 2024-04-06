from pymongo import MongoClient
import re

# Connect to MongoDB
client = MongoClient('mongodb://localhost:27017/')
db = client['superheroes_db']
heroes_collection = db['heroes']
villains_collection = db['villains']

# Function to search and print results from a collection
def search_collection(collection, query):
    print(f"Searching in collection: {collection.name}")
    results = collection.find(query, {'_id': 0})  # Exclude _id field from results
    for result in results:
        print(result)
    print("\n")

# Get search query from user
search_term = input("Enter search term: ")

# Create a regular expression pattern for case-insensitive search
regex_pattern = re.compile(search_term, re.IGNORECASE)

# Construct the query to search for documents containing the search term in the 'name' field
query = {'name': {'$regex': regex_pattern}}

# Search and print results from heroes collection
search_collection(heroes_collection, query)

# Search and print results from villains collection
search_collection(villains_collection, query)
