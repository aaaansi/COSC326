import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WhereInTheWorld {

    /**
     * Idea:
     * convert string to JSON string, then convert JSON string into a JSON object
     * then into a GeoJSON
     * 
     * GeoJSON format is
     * {
     * "type": "Feature",
     * "geometry": {
     * "type": "Point",
     * "coordinates": [125.6, 10.1]
     * },
     * "properties": {
     * "name": "Dinagat Islands"
     * }
     * }
     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        List<String> features = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            String feature = "";
            String geometry = "";
            String coordinates = "";

            // Parse latitude and longitude from input line
            Pattern pattern = Pattern.compile("([\\d\\.]+)\\s*([NS]),?\\s*([\\d\\.]+)\\s*([EW])");
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                double latitude = Double.parseDouble(matcher.group(1));
                if (matcher.group(2).equals("S")) {
                    latitude = -latitude;
                }

                double longitude = Double.parseDouble(matcher.group(3));
                if (matcher.group(4).equals("W")) {
                    longitude = -longitude;
                }

                coordinates += "[" + longitude + "," + latitude + "]";
                geometry += "{\"type\":\"Point\",\"coordinates\":" + coordinates + "}";

                feature += "{\"type\":\"Feature\",\"geometry\":" + geometry;

                // Parse label from input line
                String label = line.substring(matcher.end()).trim();
                if (!label.isEmpty()) {
                    feature += ",\"properties\":{\"name\":\"" + label + "\"}";
                }

                feature += "}";
                features.add(feature);
            } else {
                System.out.println("Unable to process: " + line);
            }
        }

        // Output GeoJSON feature collection to stdout
        String featureCollection = "{\"type\":\"FeatureCollection\",\"features\":[";
        featureCollection += String.join(",", features);
        featureCollection += "]}";

        System.out.println(featureCollection);
    }
}