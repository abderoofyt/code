from pymongo import MongoClient
from faker import Faker
import random

# Connect to MongoDB
client = MongoClient('mongodb://localhost:27017/')
db = client['superheroes_db']
heroes_collection = db['heroes']
villains_collection = db['villains']

# Create Faker instance
fake = Faker()

# Function to generate random superhero data
def generate_superhero():
    name = fake.name()
    alias = fake.user_name()
    age = random.randint(20, 80)
    gender = random.choice(['Male', 'Female', 'Other'])
    abilities = ", ".join(fake.words(nb=random.randint(1, 5)))
    weaknesses = ", ".join(fake.words(nb=random.randint(1, 3)))
    team_affiliation = fake.company()
    return {
        'name': name,
        'alias': alias,
        'age': age,
        'gender': gender,
        'abilities': abilities,
        'weaknesses': weaknesses,
        'team_affiliation': team_affiliation
    }

# Function to generate random villain data
def generate_villain():
    name = fake.name()
    alias = fake.user_name()
    gender = random.choice(['Male', 'Female', 'Other'])
    abilities = ", ".join(fake.words(nb=random.randint(1, 5)))
    weaknesses = ", ".join(fake.words(nb=random.randint(1, 3)))
    return {
        'name': name,
        'alias': alias,
        'gender': gender,
        'abilities': abilities,
        'weaknesses': weaknesses
    }

# Generate and insert superhero data
for _ in range(10):
    superhero_data = generate_superhero()
    heroes_collection.insert_one(superhero_data)

# Generate and insert villain data
for _ in range(10):
    villain_data = generate_villain()
    villains_collection.insert_one(villain_data)

print("Data insertion completed.")
