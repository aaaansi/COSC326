import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CheckDate {
    public static int month = 0;
    public static int day = 0;

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("/Users/Hamzah/Desktop/COSC326/cosc326-etudes/etude1/1.in");
        Scanner scan = new Scanner(file);
        String dateString = "";
        String dayStr = "";
        String monthStr = "";
        String yearStr = "";

        while (scan.hasNextLine()) {
            dateString = scan.nextLine();
            if (isValidString(dateString)) {
                dateString = dateString.replace('-', ' ');
                dateString = dateString.replace('/', ' ');

                dayStr = dateString.substring(0, dateString.indexOf(" "));
                monthStr = dateString.substring(dateString.indexOf(" ") + 1, dateString.lastIndexOf(" "));
                yearStr = dateString.substring(dateString.lastIndexOf(" ") + 1);
                // System.out.println(dayStr);
                // System.out.println(monthStr);
                // System.out.println(yearStr);
                if (isValidDay(dayStr) && isValidMonth(monthStr) && isValidYear(yearStr)) {
                    toString(dateString);
                } else {
                    System.out.print(isValidYear(yearStr) + "\t");
                    System.out.println(dateString + "- Invalid Range");
                }
            } else {
                System.out.println(dateString + "- INVALID INPUT");
            }

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
            System.out.println(dayDate + " - INVALID day");
            return false;
        }
    }

    private static Boolean isValidMonth(String monthDate) {
        String[] monthArr = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
        for (int i = 0; i < monthArr.length; i++) {
            if (monthArr[i].equalsIgnoreCase(monthDate)) {
                month = i + 1;
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidYear(String yearDate) {
        // Check if year is either 2 or 4 digits
        if (yearDate.length() != 2 && yearDate.length() != 4) {
            return false;
        }

        // Check if year contains only digits
        for (char c : yearDate.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        // Convert year to integer and check if it is between 1 and 9999 (inclusive)
        int yearValue = Integer.parseInt(yearDate);
        if (yearValue > 1773 || yearValue < 3000) {
            return false;
        }

        // Year is valid if all checks pass
        return true;
    }

    public static void toString(String date) {
        String[] parts = date.split(" ");
        int day = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[2]);

        String monthName = "";
        switch (month) {
            case 1:
                monthName = "Jan";
                break;
            case 2:
                monthName = "Feb";
                break;
            case 3:
                monthName = "Mar";
                break;
            case 4:
                monthName = "Apr";
                break;
            case 5:
                monthName = "May";
                break;
            case 6:
                monthName = "Jun";
                break;
            case 7:
                monthName = "Jul";
                break;
            case 8:
                monthName = "Aug";
                break;
            case 9:
                monthName = "Sep";
                break;
            case 10:
                monthName = "Oct";
                break;
            case 11:
                monthName = "Nov";
                break;
            case 12:
                monthName = "Dec";
                break;
            default:
                // System.out.println("INVALID INPUT booty");
        }

        System.out.println(String.format("%02d %s %04d", day, monthName.toUpperCase(), year));

    }

}