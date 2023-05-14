import re

def identify_coordinate_type(coord_string):
    coord_string = coord_string.strip()  # Remove leading and trailing spaces

    standard_form_regex = r'^[+-]?\d{1,3}(?:\.\d{1,6})?, [+-]?\d{1,3}(?:\.\d{1,6})?$'
    standard_form_decimal_diff_regex = r'^[+-]?\d{1,3}(?:\.\d{1,6})?, [+-]?\d{1,3}(?:\.\d+)?$'
    standard_form_missing_comma_regex = r'^[+-]?\d{1,3}(?:\.\d{1,6})? [+-]?\d{1,3}(?:\.\d{1,6})?$'
    non_negative_lat_long_regex = r'^[+-]?\d{1,3}(?:\.\d{1,6})?(?:[NS]|[SN]), [+-]?\d{1,3}(?:\.\d{1,6})?(?:[EW]|[WE])$'
    dms_form_regex = r'^[+-]?\d{1,3}(?:\.\d+)?°?[NSEW]?, [+-]?\d{1,3}(?:\.\d+)?°?[NSEW]?, [+-]?\d{1,3}(?:\.\d+)?(?:\'|"|″)?[NSEW]?$'
    ddm_form_regex = r'^[+-]?\d{1,3}(?:\.\d+)?°?[NSEW]?, [+-]?\d{1,2}(?:\.\d+)?\'?[NSEW]?$'
    form_with_Label =  r'^(-?\d+(?:\.\d+)?)\s*([NS])?,?\s*(-?\d+(?:\.\d+)?)\s*([WE])?(?:\s+(.+))?$'

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
        return "Standard Form"
    elif re.match(standard_form_decimal_diff_regex, coord_string):
        return "Standard Form (Different Decimal Points)"
    elif re.match(standard_form_missing_comma_regex, coord_string):
        return "Standard Form (Missing Comma)"
    elif re.match(non_negative_lat_long_regex, coord_string):
        return "Non-Negative Lat/Long"
    elif re.match(dms_form_regex, coord_string):
        return "Degrees, Minutes, Seconds Form"
    elif re.match(ddm_form_regex, coord_string):
        return "Degrees and Decimal Minutes Form"
    else:
        return "Unknown"

# Example usage

coord_string = input("ENTER COORDINATE: ")
result = identify_coordinate_type(coord_string)

if isinstance(result, tuple):
    latitude, longitude, label = result
    print(f"Latitude: {latitude}")
    print(f"Longitude: {longitude}")
    print(f"Label: {label}")
else:
    print(f"The coordinate type is: {result}")