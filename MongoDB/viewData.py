from pymongo import MongoClient

# Connect to MongoDB
client = MongoClient('mongodb://localhost:27017/')
db = client['superheroes_db']
heroes_collection = db['heroes']
villains_collection = db['villains']

# Function to view data from a collection
def view_data(collection):
    print(f"Viewing data from collection: {collection.name}")
    for doc in collection.find():
        print(doc)
    print("\n")

# View data from heroes collection
view_data(heroes_collection)

# View data from villains collection
view_data(villains_collection)
