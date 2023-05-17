import re
import json
import urllib
from geojson import Feature, Point, FeatureCollection
import webbrowser
import sys


def identify_coordinate_type(coord_string):
    coord_string = coord_string.strip()  # Remove leading and trailing spaces

    standard_form_regex = r'^[+-]?\d{1,3}(?:\.\d{1,6})?, [+-]?\d{1,3}(?:\.\d{1,6})?$'
    standard_form_decimal_diff_regex = r'^[+-]?\d{1,3}(?:\.\d{1,6})?, [+-]?\d{1,3}(?:\.\d+)?$'
    standard_form_missing_comma_regex = r'^[+-]?\d{1,3}(?:\.\d{1,6})? [+-]?\d{1,3}(?:\.\d{1,6})?$'
    non_negative_lat_long_regex = r'^[+-]?\d{1,3}(?:\.\d{1,6})?(?:[NS]|[SN]), [+-]?\d{1,3}(?:\.\d{1,6})?(?:[EW]|[WE])?$'
    dms_form_regex = r'^(-?\d+)°(\d+)\'(\d+(\.\d+)?)\"([NS]), (\d+)°(\d+)\'(\d+(\.\d+)?)\"([EW])(?: (.+))?'
    ddm_form_regex = r'^[+-]?\d{1,3}(?:\.\d+)?°?[NSEW]?, [+-]?\d{1,2}(?:\.\d+)?\'?[NSEW]?$'
    form_with_Label = r'^(-?\d+(?:\.\d+)?)\s*([NS])?,?\s*(-?\d+(?:\.\d+)?)\s*([WE])?(?:\s+(.+))?$'
    pattern_label = r'^([NS]) (\d+(\.\d+)?), ([EW]) (\d+(\.\d+)?) ?(.+)?$'
    pattern_label_degree = r'^(\d+(\.\d+)?)° ([NS]), (\d+(\.\d+)?)° ([EW])(?: (.+))?$'
    reverse_label = r'^(-?\d+(\.\d+)?)\s?([NS]),\s?(-?\d+(\.\d+)?)\s?([WE])(?:\s(.+))?$'

    match = re.match(form_with_Label, coord_string)
    if match:
        latitude = float(match.group(1))
        if match.group(2) == 'S':
            latitude *= -1

        longitude = float(match.group(3))
        if match.group(4) == 'W':
            longitude *= -1

        label = match.group(5)
        if label is None:
            label = "Null"

        return latitude, longitude, label
    elif re.match(reverse_label, coord_string):
        match = re.match(reverse_label, coord_string)
        latitude = float(match.group(1))
        latitude_dir = match.group(3)
        latitude = -latitude if latitude_dir == 'W' else latitude

        longitude = float(match.group(4))
        longitude_dir = match.group(6)
        longitude = -longitude if longitude_dir == 'S' else longitude

        label = match.group(7) if match.group(7) else 'Null'
        
        return latitude, longitude, label
    elif re.match(standard_form_regex, coord_string):
        return None, None, None
    elif re.match(standard_form_decimal_diff_regex, coord_string):
        return None, None, None
    elif re.match(standard_form_missing_comma_regex, coord_string):
        return None, None, None
    elif re.match(non_negative_lat_long_regex, coord_string):
        return None, None, None
    
    elif re.match(dms_form_regex, coord_string):
        match = re.match(dms_form_regex, coord_string)
        latitude_deg = float(match.group(1))
        latitude_min = float(match.group(2))
        latitude_sec = float(match.group(3))
        latitude_dir = match.group(5)
        latitude = -(latitude_deg + latitude_min/60 + latitude_sec/3600) if latitude_dir == 'S' else (latitude_deg + latitude_min/60 + latitude_sec/3600)

        longitude_deg = float(match.group(6))
        longitude_min = float(match.group(7))
        longitude_sec = float(match.group(8))
        longitude_dir = match.group(10)
        longitude = -(longitude_deg + longitude_min/60 + longitude_sec/3600) if longitude_dir == 'W' else (longitude_deg + longitude_min/60 + longitude_sec/3600)

        label = match.group(11) if match.group(11) else 'Null'
        
        return latitude, longitude, label
    elif re.match(ddm_form_regex, coord_string):
        return None, None, None
    elif re.match(pattern_label_degree, coord_string):
        match = re.match(pattern_label_degree, coord_string)
        latitude = float(match.group(1))
        latitude_dir = match.group(3)
        latitude = -latitude if latitude_dir == 'S' else latitude

        longitude = float(match.group(4))
        longitude_dir = match.group(6)
        longitude = -longitude if longitude_dir == 'W' else longitude

        label = match.group(7) if match.group(7) else 'Null'

        return latitude,longitude, label
    elif re.match(pattern_label, coord_string):
        match = re.match(pattern_label, coord_string)
        latitude_dir = match.group(1)
        latitude = -float(match.group(2)) if latitude_dir == 'S' else float(match.group(2))

        longitude_dir = match.group(4)
        longitude = -float(match.group(5)) if longitude_dir == 'W' else float(match.group(5))

        label = match.group(7) if match.group(7) else 'Null'

        return latitude, longitude, label
    else:
        print("Unable to process: ", coord_string)
        return None, None, None


# Example usage
coordinates = []

try:
    if len(sys.argv) > 1:  # Check if command-line arguments are provided
        # Get the file path from the command-line argument
        file_path = sys.argv[1]

        # Example usage (continued)
        with open(file_path, "r") as file:
            for line in file:
                coord_string = line.strip()
                latitude, longitude, label = identify_coordinate_type(
                    coord_string)
                if latitude is not None:
                    coordinates.append({
                        "type": "Feature",
                        "geometry": {
                            "type": "Point",
                            "coordinates": [longitude, latitude]
                        },
                        "properties": {
                            "name": label
                        }
                    })
    else:
        while True:
            try:
                coord_string = input(
                    "ENTER COORDINATE (or press Enter to finish): ")
                if coord_string == "":
                    break

                latitude, longitude, label = identify_coordinate_type(
                    coord_string)
                if latitude is not None:
                    coordinates.append({
                        "type": "Feature",
                        "geometry": {
                            "type": "Point",
                            "coordinates": [longitude, latitude]
                        },
                        "properties": {
                            "name": label
                        }
                    })
            except EOFError:
                print(
                    "Error: End of input reached. Make sure the input file is not empty.")
                sys.exit(1)

    # Create a JSON object containing all the coordinates
    data = {
        "type": "FeatureCollection",
        "features": coordinates
    }

    # Convert the data to JSON string
    stringify = json.dumps(data)

    uriencoded = urllib.parse.quote(stringify)
    # Construct URL and open it in the browser
    url = "http://geojson.io/#data=data:application/json," + uriencoded
    webbrowser.open(url)

except FileNotFoundError:
    print("Error: File not found. Please provide a valid file path.")
    sys.exit(1)
