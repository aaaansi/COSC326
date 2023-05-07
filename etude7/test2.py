import json

# Get the input coordinates from the user
input_str = input("Enter the coordinates (latitude, longitude, and location name separated by spaces): ")

# Parse the input string to get the latitude, longitude, and location name (if provided)
latlon, *name = input_str.split()
lat, lon = map(float, latlon.split(','))

# Create a GeoJSON FeatureCollection with a single feature
feature_collection = {
    "type": "FeatureCollection",
    "features": [
        {
            "type": "Feature",
            "geometry": {
                "type": "Point",
                "coordinates": [lon, lat]
            },
            "properties": {}
        }
    ]
}

# If a location name was provided, add it as a property
if name:
    feature_collection["features"][0]["properties"]["name"] = name[0]

# Print the GeoJSON as a formatted JSON string
print(json.dumps(feature_collection, indent=4))
