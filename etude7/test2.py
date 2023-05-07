import re

def extract_coordinates(coord_str):
    # Regular expressions to match different coordinate formats
    regex_patterns = [
        # -88.746344, 156.767883
        r'^(-?\d+(?:\.\d+)?),\s*(-?\d+(?:\.\d+)?)$',
        # -88.746343464, 156.7673643883
        r'^(-?\d+(?:\.\d+)?)(?:,\s*|\s+)(-?\d+(?:\.\d+)?)$',
        # -88.746344 156.767883
        r'^(-?\d+(?:\.\d+)?)[ ,]+(-?\d+(?:\.\d+)?)$',
        # S 88.746344, E 156.767883
        r'^[NS]\s*(-?\d+(?:\.\d+)?)[ ,]*[WE]\s*(-?\d+(?:\.\d+)?)$',
        # 88.746344 S, 156.767883 E
        r'^(-?\d+(?:\.\d+)?)\s*[NS],?\s*(-?\d+(?:\.\d+)?)\s*[WE]$',
        # 88.746344 W, 156.767883 N
        r'^(-?\d+(?:\.\d+)?)\s*[WE],?\s*(-?\d+(?:\.\d+)?)\s*[NS]$',
        # 45°30'30.0"N, 122°40'30.0"W
        r'^(\d+)[°d]\s*(\d+)[\'m]\s*(\d+(?:\.\d+)?)["s]*\s*([NS])\s*,\s*(\d+)[°d]\s*(\d+)[\'m]\s*(\d+(?:\.\d+)?)["s]*\s*([WE])$',
        # 40.7128° N, 74.0060° W
        r'^(-?\d+(?:\.\d+)?)°?\s*([NS])\s*,\s*(-?\d+(?:\.\d+)?)°?\s*([WE])$',
        # -33° 51' 35.6" S, 151° 12' 40.8" E
        r'^(-?\d+)[°d]\s*(\d+)[\'m]\s*(\d+(?:\.\d+)?)["s]*\s*([NS])\s*,\s*(-?\d+)[°d]\s*(\d+)[\'m]\s*(\d+(?:\.\d+)?)["s]*\s*([WE])$',
        # 23.6 W, -50.00
        r'^(-?\d+(?:\.\d+)?)\s*([NS])?,?\s*(-?\d+(?:\.\d+)?)\s*([WE])?$',
        # 23.6 N, 50.00 W
        r'^([NS])?\s*(-?\d+(?:\.\d+)?)\s*,\s*([WE])?\s*(-?\d+(?:\.\d+)?)$',
        # N 23.6, W 50.00
        r'^([NS])\s*(-?\d+(?:\.\d+)?)\s*,\s*([WE])\s*(-?\d+(?:\.\d+)?)$',
        # 45.9 S, 170.5 E Dunedin
        r'^(-?\d+(?:\.\d+)?)\s*([NS])?,?\s*(-?\d+(?:\.\d+)?)\s*([WE])?[^\n]+$',
    ]
    
    # Loop through regex patterns and check if any of them matches the input string
    for pattern in regex_patterns:
        match = re.match(pattern, coord_str)
        if match:
            # Extract latitude and longitude from the matched groups
            groups = match.groups()
            if len(groups) == 4:
                lat, lon = float(groups[0]), float(groups[2])
                if groups[1] in ('S', 's', 'W', 'w'):
                    lat *= -1
                if groups[3] in ('S', 's', 'W', 'w'):
                    lon *= -1
            else:
                lat, lon = float(groups[1]), float(groups[3])
                if groups[0] in ('S', 's', 'W', 'w'):
                    lat *= -1
                if groups[2] in ('S', 's', 'W', 'w'):
                    lon *= -1
            return True, lat, lon
    
    # If no pattern matches, return False
    return False, None, None

