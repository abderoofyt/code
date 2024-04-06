from pymongo import MongoClient
from marvel import heroes as m_heroes
from marvel import villains as m_villains
from dc import heroes as dc_heroes
from dc import villains as dc_villains

# Connect to MongoDB
client = MongoClient('mongodb://localhost:27017/')
db = client['superheroes_db']
heroes_collection = db['heroes']
villains_collection = db['villains']

def transform_heroes(heroes):
    transformed_heroes = []
    for hero in heroes:
        transformed_hero = {
            'name': hero[0],
            'alias': hero[1],
            'age': hero[2],
            'gender': hero[3],
            'abilities': hero[4],
            'weaknesses': hero[5],
            'team_affiliation': hero[6]
        }
        transformed_heroes.append(transformed_hero)
    return transformed_heroes

def transform_villains(villains):
    transformed_heroes = []
    for villain in villains:
        transformed_hero = {
            'name': villain[0],
            'alias': villain[1],
            'gender': villain[2],
            'abilities': villain[3],
            'weaknesses': villain[4],
        }
        transformed_heroes.append(transformed_hero)
    return transformed_heroes

# Function to insert data into a collection
def insert_data(collection, data):
    for item in data:
        collection.insert_one(item)

# Insert Marvel heroes and villains data
insert_data(heroes_collection, transform_heroes(m_heroes()))
insert_data(villains_collection, transform_villains(m_villains()))

# Insert DC heroes and villains data
insert_data(heroes_collection, transform_heroes(dc_heroes()))
insert_data(villains_collection, transform_villains(dc_villains()))

print("Data insertion completed.")
