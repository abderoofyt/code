import math
from geopy.geocoders import Nominatim

# Function to calculate distance between two points using Haversine formula
def haversine_distance(lat1, lon1, lat2, lon2):
    R = 6371.0  # Radius of Earth in kilometers

    # Convert latitude and longitude from degrees to radians
    lat1_rad = math.radians(lat1)
    lon1_rad = math.radians(lon1)
    lat2_rad = math.radians(lat2)
    lon2_rad = math.radians(lon2)

    dlat = lat2_rad - lat1_rad
    dlon = lon2_rad - lon1_rad

    a = math.sin(dlat / 2)**2 + math.cos(lat1_rad) * math.cos(lat2_rad) * math.sin(dlon / 2)**2
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))

    distance = R * c
    return distance

# Get user's current location
geolocator = Nominatim(user_agent="location_sorting_script")
location_input = input("Enter location? (e.g. Cape Town, South Africa) : ")
user_location = geolocator.geocode(location_input)  # Replace with user's location

your_latitude = user_location.latitude
your_longitude = user_location.longitude

# List of items with their coordinates
items = [
    {"name": "Johannesburg", "latitude": -26.2041, "longitude": 28.0473},
    {"name": "Cape Town", "latitude": -33.9249, "longitude": 18.4241},
    {"name": "Lagos", "latitude": 6.5244, "longitude": 3.3792},
    {"name": "Hanover Park", "latitude": -33.9929, "longitude": 18.5315},
    {"name": "Maitland", "latitude": -33.9204, "longitude": 18.5019},
]

# Calculate distances and sort items based on distance
sorted_items = sorted(items, key=lambda item: haversine_distance(your_latitude, your_longitude, item["latitude"], item["longitude"]))

# Print sorted items
for item in sorted_items:
    print(f"{item['name']} - Distance: {haversine_distance(your_latitude, your_longitude, item['latitude'], item['longitude']):.2f} km")
