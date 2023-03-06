import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class ValidDate {

    public static void main(String[] args) throws FileNotFoundException {
        // Read from file and save to var input
        File file = new File("/Users/Hamzah/Desktop/COSC326/COSC326-ETUDES/etude1/1.in");
        Scanner scan = new Scanner(file);
        String dateString = "";
        String monthInput = "";
        String dayInput = "";
        String inputKla = "";
        String CorrectInput = "";
        int yearlyInput = 0;
        while (scan.hasNextLine()) {
            dateString = scan.nextLine();
            dateString = dateString.replace("/", " ");
            dateString = dateString.replace("-", " ");
            dayInput = dateString.substring(0, dateString.indexOf(' '));
            if (dayInput.contains("-")) {
                System.out.print(dateString + "- INVALID day");
                dateString = scan.nextLine();
            }
            System.out.print(dateString + "\t");
            // replacing any given format to format that contains '/'
            // if (dateString.indexOf('/') && dateString.indexOf(dateString.indexOf('/') +
            // 1, dateString.indexOf('-'))) {
            // }
            // String input = dateString.replace('-', ' ');
            // input = input.replace('/', ' ');

            monthInput = dateString.substring(dateString.indexOf(' ') + 1, dateString.lastIndexOf(' ')).toUpperCase();
            // int realMonthInput = Integer.parseInt(monthInput);
            // System.out.println(monthInput);

            // int realDayInput = Integer.parseInt(dayInput);

            // Checks if the year input is 2 digits and formats it properly
            inputKla = dateString.substring(dateString.lastIndexOf(' ') + 1, dateString.length());

            yearlyInput = Integer.parseInt(inputKla);
            if (yearlyInput < 100) {
                if (yearlyInput >= 50) {
                    yearlyInput += 1900;
                } else {
                    yearlyInput += 2000;

                }
            }

            CorrectInput = dayInput + " " + monthInput + " " + yearlyInput;
            if (dayInput.matches(".*[a-zA-Z].*") || (yearlyInput + "").matches(".*[a-zA-Z].*")) {
                System.err.println("error");
                System.exit(1);
            }
            if (isDateValid(CorrectInput)) {
                System.out.println(toString(CorrectInput) + "\t");
            } else {
                System.out.println(CorrectInput + " - INVALID");
            }
        }
        scan.close();

        // System.out.println(CorrectInput);

    }

    // Check if the given date is valid based on the provided criteria
    private static boolean isDateValid(String date) {
        String[] components = date.split(" ");
        if (date == null || date.isEmpty() || date.length() < 6) {
            // Null or empty string is not a valid date format
            return false;
        }
        int year = Integer.parseInt(components[2]);
        int day = isValidDay(components[0]);
        int month = getMonth(components[1]);

        // YEAR RANGE CHECK
        if (year < 1753 || year > 3000) {
            System.err.print("Year out of range \t");
            return false;
        }
        // if the month is FEB check if its also a leap year. if it is check if the day
        // > 29
        // else day > 28
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
    }

    public static int getMonth(String dateMonth) {
        int month;
        try {
            month = Integer.parseInt(dateMonth);
        } catch (NumberFormatException e) {
            String monthStr = dateMonth.toUpperCase();
            switch (monthStr) {
                case "JAN":
                    month = 1;
                    break;
                case "FEB":
                    month = 2;
                    break;
                case "MAR":
                    month = 3;
                    break;
                case "APR":
                    month = 4;
                    break;
                case "MAY":
                    month = 5;
                    break;
                case "JUN":
                    month = 6;
                    break;
                case "JUL":
                    month = 7;
                    break;
                case "AUG":
                    month = 8;
                    break;
                case "SEP":
                    month = 9;
                    break;
                case "OCT":
                    month = 10;
                    break;
                case "NOV":
                    month = 11;
                    break;
                case "DEC":
                    month = 12;
                    break;
                default:
                    System.out.println("INVALID INPUT");
                    return -1;
            }
        }
        if (month < 1 || month > 12) {
            System.out.println("INVALID INPUT");
            return -1;
        }
        return month;
    }

    public static int isValidDay(String dayDate) {
        int day = 0;
        try {
            day = Integer.parseInt(dayDate);
            if (day > 31) {
                return -1;
            }
            return day;
        } catch (NumberFormatException nfe) {
            System.out.println(dayDate + " - INVALID day");
            return -1;
        }
    }

    // toString method that returns the output in the specified format
    public static String toString(String date) {
        String[] parts = date.split(" ");
        int day = Integer.parseInt(parts[0]);
        int month = getMonth(date);
        int year = Integer.parseInt(parts[2]);

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
                return "INVALID INPUT";
        }

        return String.format("%02d %s %04d", day, monthName, year);
    }

}
