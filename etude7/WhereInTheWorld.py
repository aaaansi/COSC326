import urllib
from geojson import Feature, Point, FeatureCollection
import json
import webbrowser
import re


def identify_coordinate_type(coord_string):
    coord_string = coord_string.strip()  # Remove leading and trailing spaces

    standard_form_regex = r'^[+-]?\d{1,3}(?:\.\d{1,6})?, [+-]?\d{1,3}(?:\.\d{1,6})?$'
    standard_form_decimal_diff_regex = r'^[+-]?\d{1,3}(?:\.\d{1,6})?, [+-]?\d{1,3}(?:\.\d+)?$'
    standard_form_missing_comma_regex = r'^[+-]?\d{1,3}(?:\.\d{1,6})? [+-]?\d{1,3}(?:\.\d{1,6})?$'
    non_negative_lat_long_regex = r'^[+-]?\d{1,3}(?:\.\d{1,6})?(?:[NS]|[SN]), [+-]?\d{1,3}(?:\.\d{1,6})?(?:[EW]|[WE])$'
    dms_form_regex = r'^[+-]?\d{1,3}(?:\.\d+)?°?[NSEW]?, [+-]?\d{1,3}(?:\.\d+)?°?[NSEW]?, [+-]?\d{1,3}(?:\.\d+)?(?:\'|"|″)?[NSEW]?$'
    ddm_form_regex = r'^[+-]?\d{1,3}(?:\.\d+)?°?[NSEW]?, [+-]?\d{1,2}(?:\.\d+)?\'?[NSEW]?$'
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

        return True, latitude, longitude, label

    if re.match(standard_form_regex, coord_string):
        return True, None, None, None
    elif re.match(standard_form_decimal_diff_regex, coord_string):
        return True, None, None, None
    elif re.match(standard_form_missing_comma_regex, coord_string):
        return True, None, None, None
    elif re.match(non_negative_lat_long_regex, coord_string):
        return True, None, None, None
    elif re.match(dms_form_regex, coord_string):
        return True, None, None, None
    elif re.match(ddm_form_regex, coord_string):
        return True, None, None, None
    else:
        return False, None, None, None


# Example usage
coord_str = input("ENTER COORDINATE: ")
result, lat, lon, name = identify_coordinate_type(coord_str)
print(identify_coordinate_type(coord_str))

if result:
    # Create GeoJSON feature with the input coordinates
    data1 = {
        "type": "Feature",
        "geometry": {
            "type": "Point",
            "coordinates": [lon, lat]
        },
        "properties": {
            "name": [name]
        }
    }
    # Convert GeoJSON feature to JSON string and URL encode it
    stringify = json.dumps(data1)
    uriencoded = urllib.parse.quote(stringify)
    # Construct URL and open it in browser
    url = "http://geojson.io/#data=data:application/json," + uriencoded
    webbrowser.open(url)
else:
    print("Unable to process: " + coord_str)
