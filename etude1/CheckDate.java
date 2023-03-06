import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CheckDate {
    public static int month = 0;
    public static int day = 0;
    public static String monthStr = "";
    public static String dayStr = "";
    public static String yearStr = "";
    public static int year = 0;

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("/Users/Hamzah/Desktop/COSC326/cosc326-etudes/etude1/1.in");
        Scanner scan = new Scanner(file);
        String dateString = "";
        String newInput = "";

        while (scan.hasNextLine()) {
            dateString = scan.nextLine();
            dateString = dateString.replace('/', ' ');
            newInput = dateString.replace('-', ' ');
            System.out.println(newInput + "\t!!!");
            dayStr = newInput.substring(0, newInput.indexOf(" "));
            monthStr = newInput.substring(newInput.indexOf(" ") + 1, newInput.lastIndexOf(" "));
            yearStr = newInput.substring(newInput.lastIndexOf(" ") + 1);
            // System.out.print(isValidYear(yearStr) + "------");
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

    public static void toString(String date) {
        String[] parts = date.split(" ");
        int day = Integer.parseInt(parts[0]);
        // int year = Integer.parseInt(parts[2]);

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