import java.util.Scanner;

public class WhereInTheWorld {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter coordinates (latitude, longitude, name(optional)): ");
        String input = scanner.nextLine();
        // String[] parts = input.split(",");
        scanner.close();
        // double latitude = Double.parseDouble(parts[0].trim());
        // double longitude = Double.parseDouble(parts[1].trim());
        // String name = null;

        // if (parts.length == 3) {
        // name = parts[2].trim();
        // }

        // System.out.println("Latitude: " + latitude);
        // System.out.println("Longitude: " + longitude);
        // if (name != null) {
        // System.out.println("Name: " + name);
        // }
        if (input.matches("-?\\d+(\\.\\d+)? [NS], -?\\d+(\\.\\d+)? [WE] \\w+")) {
            System.out.println("Input matches the expected string style.");

        } else {
            System.out.println("Input does not match the expected string style.");
        }
    }
}