import math
from geopy.geocoders import Nominatim

def haversine_distance(lat1, lon1, lat2, lon2):
    R = 6371.0  # Radius of Earth in kilometers

    lat1_rad, lon1_rad = math.radians(lat1), math.radians(lon1)
    lat2_rad, lon2_rad = math.radians(lat2), math.radians(lon2)

    dlat, dlon = lat2_rad - lat1_rad, lon2_rad - lon1_rad

    a = math.sin(dlat / 2)**2 + math.cos(lat1_rad) * math.cos(lat2_rad) * math.sin(dlon / 2)**2
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))

    distance = R * c
    return distance

def get_user_location():
    geolocator = Nominatim(user_agent="location_sorting_script")
    location_input = input("Enter your location (e.g. Cape Town, South Africa): ")
    user_location = geolocator.geocode(location_input)
    if user_location:
        return user_location.latitude, user_location.longitude
    else:
        print("Could not find location. Please try again.")
        return get_user_location()

def print_sorted_items(sorted_items, your_latitude, your_longitude):
    for item in sorted_items:
        item_name = item['name']
        item_lat, item_lon = item['latitude'], item['longitude']
        item_distance = haversine_distance(your_latitude, your_longitude, item_lat, item_lon)
        print(f"{item_name} - Distance: {item_distance:.2f} km")

def main():
    user_latitude, user_longitude = get_user_location()

    items = [
        {"name": "Johannesburg", "latitude": -26.2041, "longitude": 28.0473},
        {"name": "Cape Town", "latitude": -33.9249, "longitude": 18.4241},
        {"name": "Lagos", "latitude": 6.5244, "longitude": 3.3792},
        {"name": "Hanover Park", "latitude": -33.9929, "longitude": 18.5315},
        {"name": "Maitland", "latitude": -33.9204, "longitude": 18.5019},
    ]

    sorted_items = sorted(items, key=lambda item: haversine_distance(user_latitude, user_longitude, item["latitude"], item["longitude"]))

    print_sorted_items(sorted_items, user_latitude, user_longitude)

if __name__ == "__main__":
    main()
