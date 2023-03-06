import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CheckDate {
    public static int month = 0;
    public static int day = 0;
    public static int year = 0;
    public static String monthStr = "";
    public static String dayStr = "";
    public static String yearStr = "";

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("/Users/Hamzah/Desktop/COSC326/cosc326-etudes/etude1/1.in");
        Scanner scan = new Scanner(file);
        String dateString = "";
        String newInput = "";

        while (scan.hasNextLine()) {
            String initialInput = scan.nextLine();
            dateString = initialInput;
            dateString = dateString.replace('/', ' ');
            newInput = dateString.replace('-', ' ');
            System.out.println(newInput + "\t!!!");
            dayStr = newInput.substring(0, newInput.indexOf(" "));
            monthStr = newInput.substring(newInput.indexOf(" ") + 1, newInput.lastIndexOf(" "));
            yearStr = newInput.substring(newInput.lastIndexOf(" ") + 1);
            System.out.print(isValidMonth(monthStr) + "------");

        }
    }

    public static boolean isValidString(String date) {
        if (date == null || date.isEmpty() || date.length() < 6) {
            // Null or empty string is not a valid date format
            return false;
        }
        return true;
    }

    public static boolean isValidDay(String dayDate) {
        // int day = 0;
        try {
            day = Integer.parseInt(dayDate);
            if (day > 31) {
                return false;
            }
            return true;
        } catch (NumberFormatException nfe) {
            // System.out.println(dayDate + " - INVALID day");
            return false;
        }
    }

    private static Boolean isValidMonth(String monthDate) {
        String[] monthArr = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
        try {
            month = Integer.parseInt(monthDate);
            return true;
        } catch (NumberFormatException e) {
            for (int i = 0; i < monthArr.length; i++) {
                if (monthArr[i].equalsIgnoreCase(monthDate)) {
                    month = i + 1;
                    return true;
                }
            }
            return false;
        }

    }

    public static boolean isValidYear(String yearDate) {
        try {
            year = Integer.parseInt(yearDate);
            if (year < 100) {
                if (year >= 50) {
                    year += 1900;
                } else {
                    year += 2000;

                }
            }
            if (year < 1753 || year > 3000) {
                System.err.print("Year out of range \t");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // public static void toString(String date) {

    // String[] parts = date.split(" ");
    // int day = Integer.parseInt(parts[0]);
    // int month = Integer.parseInt(parts[1]);
    // int year = Integer.parseInt(parts[2]);
    // System.out.println(month + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

    // String[] monthArr = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG",
    // "SEP", "OCT", "NOV", "DEC" };

    // System.out.println(String.format("%02d %s %04d", day, month, year) +
    // "@@@@@@");

    // }

}