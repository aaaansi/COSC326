import re
import json
import urllib
from geojson import Feature, Point, FeatureCollection
import webbrowser
import sys


def identify_coordinate_type(coord_string):
    coord_string = coord_string.strip()  # Remove leading and trailing spaces

    standard_form_regex = r'^-?(\d{1,3}\.\d{6})\s?,\s?-?(\d{1,3}\.\d{6})$'
    standard_form_decimal_diff_regex = r'^-?(\d{1,3}\.\d{1,6})\s?,\s?-?(\d{1,3}\.\d{1,6})$'
    standard_form_missing_comma_regex = r'^-?(\d{1,3}\.\d{6})\s?-?(\d{1,3}\.\d{6})$'
    non_negative_lat_long_regex = r'^-?(\d{1,3}\.\d{6})[NS]\s?,\s?-?(\d{1,3}\.\d{6})[EW]$'
    dms_form_regex = r'^-?(\d{1,3})[°|\s]?\s?(\d{1,2})[|\s]?\s?(\d{1,2}(\.\d+)?)["|\s]?\s?([NS])?,?\s?(\d{1,3})[°|\s]?\s?(\d{1,2})[|\s]?\s?(\d{1,2}(\.\d+)?)["|\s]?\s?([EW])?$'
    ddm_form_regex = r'^-?(\d{1,3})[°|\s]?\s?(\d{1,2}(\.\d+)?)[|\s]?\s?([NS])?,?\s?(\d{1,3})[°|\s]?\s?(\d{1,2}(\.\d+)?)[|\s]?\s?([EW])?$'
    form_with_Label = r'^(-?\d+(?:\.\d+)?)\s*([NS])?,?\s*(-?\d+(?:\.\d+)?)\s*([WE])?(?:\s+(.+))?$'

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

    if re.match(standard_form_regex, coord_string):
        return None, None, None
    elif re.match(standard_form_decimal_diff_regex, coord_string):
        return None, None, None
    elif re.match(standard_form_missing_comma_regex, coord_string):
        return None, None, None
    elif re.match(non_negative_lat_long_regex, coord_string):
        return None, None, None
    elif re.match(dms_form_regex, coord_string):
        return None, None, None
    elif re.match(ddm_form_regex, coord_string):
        return None, None, None
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
                latitude, longitude, label = identify_coordinate_type(coord_string)
                if latitude is not None:
                    feature = Feature(
                        geometry=Point((longitude, latitude)),
                        properties={"name": label}
                    )
                    coordinates.append(feature)
    else:
        while True:
            try:
                coord_string = input("ENTER COORDINATE (or press Enter to finish): ")
                if coord_string == "":
                    break

                latitude, longitude, label = identify_coordinate_type(coord_string)
                if latitude is not None:
                    feature = Feature(
                        geometry=Point((longitude, latitude)),
                        properties={"name": label}
                    )
                    coordinates.append(feature)
            except EOFError:
                print("Error: End of input reached. Make sure the input file is not empty.")
                sys.exit(1)

    # Create a FeatureCollection object containing all the coordinates
    data = FeatureCollection(coordinates)

    # Convert the data to a JSON string
    stringify = json.dumps(data)

    # URL-encode the JSON string
    uriencoded = urllib.parse.quote(stringify)

    # Construct the URL and open it in the browser
    url = "http://geojson.io/#data=data:application/json," + uriencoded
    webbrowser.open(url)

except FileNotFoundError:
    print("Error: File not found. Please provide a valid file path.")
    sys.exit(1)
