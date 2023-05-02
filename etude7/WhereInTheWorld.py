import sys
import json

features = []

for line in sys.stdin:
    line = line.strip()

    # Skip empty lines
    if not line:
        continue

    # Split the line into fields
    fields = line.split(',')

    # Attempt to extract the coordinates
    try:
        longitude = float(fields[0])
        latitude = float(fields[1])
    except ValueError:
        print(f"Unable to process: {line}")
        continue

    # Create the GeoJSON feature
    feature = {
        "type": "Feature",
        "geometry": {
            "type": "Point",
            "coordinates": [longitude, latitude]
        }
    }

    # Add a name property if given
    if len(fields) > 2:
        feature["properties"] = {"name": fields[2]}

    features.append(feature)

# Write the feature collection to a file
feature_collection = {"type": "FeatureCollection", "features": features}
with open("output.geojson", "w") as f:
    json.dump(feature_collection, f)