from pymongo import MongoClient

# Connect to MongoDB
client = MongoClient('mongodb://localhost:27017/')
db = client['superheroes_db']
heroes_collection = db['heroes']
villains_collection = db['villains']

# Function to search and print results from a collection
def search_collection(collection, query):
    print(f"Searching in collection: {collection.name}")
    results = collection.find(query)
    for result in results:
        print(result)
    print("\n")

# Get search query from user
search_query = input("Enter search query: {'name': 'Spider Man'):\n")

# Convert search query to dictionary
try:
    query_dict = eval(search_query)
except Exception as e:
    print("Error: Invalid search query format.")
    exit()

# Search and print results from heroes collection
search_collection(heroes_collection, query_dict)

# Search and print results from villains collection
search_collection(villains_collection, query_dict)
