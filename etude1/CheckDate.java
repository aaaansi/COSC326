import java.io.File;
import java.io.FileNotFoundException;
import java.security.AllPermission;
import java.util.Scanner;

public class CheckDate {
    public static int month = 0;
    public static int day = 0;
    public static int year = 0;
    public static String monthStr = "";
    public static String dayStr = "";
    public static String yearStr = "";

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = null;
        String dateString = "";
        String newInput = "";
        if (args.length > 0) { // if command line argument provided
            try {
                scan = new Scanner(new File(args[0]));
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + args[0]);
                System.exit(1);
            }
        } else { // otherwise, use standard input
            scan = new Scanner(System.in);
        }
        while (scan.hasNextLine()) {
            String initialInput = scan.nextLine();
            dateString = initialInput;
            if (isValidString(dateString)) {
                dateString = dateString.replace('/', ' ');
                newInput = dateString.replace('-', ' ');
                // System.out.println(newInput + "\t!!!");
                dayStr = newInput.substring(0, newInput.indexOf(" "));
                monthStr = newInput.substring(newInput.indexOf(" ") + 1, newInput.lastIndexOf(" "));
                yearStr = newInput.substring(newInput.lastIndexOf(" ") + 1);

                if (isValidDay(dayStr) && isValidYear(yearStr) && isValidMonth(monthStr)) {
                    System.out.println(toString(day + " " + month + " " + year));
                } else {
                    System.out.println(initialInput + " - INVALID");
                }
                // System.out.println(+"------");

            } else {
                System.out.println(initialInput + " - INVALID");
            }

        }
        scan.close();
    }

    public static boolean isValidString(String date) {
        Boolean flag = date.contains("-") && date.contains("/");

        String[] dateParts = date.split("[/ -]");

        if (dateParts.length != 3) {

            return false;
        }
        int yearLength = dateParts[2].length();
        // Boolean secondFlag = !date.contains(" ");
        if (date == null || date.isEmpty() || date.length() < 6 || date.contains(".") || flag) {
            // Null or empty string is not a valid date format
            return false;
        }
        if (yearLength == 4 || yearLength == 2) {
            return true;
        } else {
            return false;
        }

        // return true;
    }

    public static boolean isValidDay(String dayDate) {
        // int day = 0;
        try {

            if (dayDate.length() > 2) {
                return false;
            }
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
            if (monthDate.length() > 3) {
                return false;
            }
            if (monthDate.length() == 3) {
                month = Integer.parseInt(monthDate);
                return false;
            } else {
                month = Integer.parseInt(monthDate);
            }

            if (month == 2) {
                if (year % 4 != 0) {
                    if (day > 28) {
                        return false;
                    } else {
                        return true;
                    }
                } else if (year % 100 != 0) {
                    if (day > 29) {
                        return false;
                    } else {
                        return true;
                    }
                } else if (year % 400 != 0) {
                    if (day > 28) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    if (day > 29) {
                        return false;
                    } else {
                        return true;
                    }
                }

            }
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                if (day > 31) {
                    System.err.println("INVALID DAY OUT OF RANGE FOR MONTHS WITH 31 DAYS");
                    return false;
                }
                return true;
            } else {
                if (day > 30) {
                    System.err.println("INVALID DAY OUT OF RANGE FOR MONTHS WITH 30 DAYS");
                    return false;
                }
            }
            return true;
        } catch (NumberFormatException e) {
            for (int i = 0; i < monthArr.length; i++) {
                boolean allLowercase = monthDate.equals(monthDate.toLowerCase());
                boolean allUppercase = monthDate.equals(monthDate.toUpperCase());
                boolean firstUppercase = monthDate.substring(0,
                        1).equals(monthDate.substring(0, 1).toUpperCase())
                        && monthDate.substring(1).equals(monthDate.substring(1).toLowerCase());
                // System.out.println(allLowercase + "\t " + allUppercase + "\t" +
                // firstUppercase);
                // System.out.print(allLowercase + " " + allUppercase + " " + firstUppercase +
                // "------");
                if (allLowercase || allUppercase || firstUppercase) {
                    if (monthArr[i].equalsIgnoreCase(monthDate)) {
                        month = i + 1;
                        return true;
                    }
                } else {
                    return false;
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

    public static String toString(String data) {
        String monthName;
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
                return " - INVALID";
        }

        return String.format("%02d %s %04d", day, monthName, year);
    }

}