import urllib
from geojson import Feature, Point, FeatureCollection
import json
import webbrowser

# Read coordinates from stdin
lon, lat = map(float, input(
    "Enter comma-separated longitude and latitude: ").split(","))

# Create GeoJSON feature with the input coordinates
data1 = {
    "type": "Feature",
    "geometry": {
        "type": "Point",
        "coordinates": [lon, lat]
    },
    "properties": {
        "name": "Dinagat Islands"
    }
}

# Convert GeoJSON feature to JSON string and URL encode it
stringify = json.dumps(data1)
uriencoded = urllib.parse.quote(stringify)

# Construct URL and open it in browser
url = "http://geojson.io/#data=data:application/json," + uriencoded
webbrowser.open(url)
