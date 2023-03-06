import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class ValidDate {
    public static String dayInput = "";
    public static int yearlyInput = 0;

    public static void main(String[] args) throws FileNotFoundException {
        // Read from file and save to var input
        File file = new File("/Users/Hamzah/Desktop/COSC326/cosc326-etudes/etude1/invalid.txt");
        Scanner scan = new Scanner(file);
        String dateString = "";
        String monthInput = "";
        String inputKla = "";
        String CorrectInput = "";

        while (scan.hasNextLine()) {
            dateString = scan.nextLine();
            String input = dateString.replace('-', ' ');
            input = input.replace('/', ' ');

            if (!isStringValid(input)) {
                System.out.println(input + "- INVALID");
                dateString = scan.nextLine();
            } else {
                System.out.print(input + "\t");
                // replacing any given format to format that contains '/'

                monthInput = input.substring(input.indexOf(' ') + 1, input.lastIndexOf(' ')).toUpperCase();
                // int realMonthInput = Integer.parseInt(monthInput);
                // System.out.println(monthInput);

                // int realDayInput = Integer.parseInt(dayInput);

                // Checks if the year input is 2 digits and formats it properly
                inputKla = input.substring(input.lastIndexOf(' ') + 1, input.length());

                yearlyInput = Integer.parseInt(inputKla);
                if (yearlyInput < 100) {
                    if (yearlyInput >= 50) {
                        yearlyInput += 1900;
                    } else {
                        yearlyInput += 2000;

                    }
                }

                CorrectInput = dayInput + " " + monthInput + " " + yearlyInput;
                // if (dayInput.matches(".*[a-zA-Z].*") || (yearlyInput +
                // "").matches(".*[a-zA-Z].*")) {
                // System.err.println("error");
                // System.exit(1);
                // }
                if (isDateValid(CorrectInput)) {
                    System.out.println(toString(CorrectInput) + "\t");
                } else {
                    System.out.println(CorrectInput + " - INVALID");
                }
            }

        }
        scan.close();

        // System.out.println(CorrectInput);

    }

    private static boolean isStringValid(String date) {

        if (date == null || date.isEmpty()) {
            // Null or empty string is not a valid date format
            return false;
        }

        String dayStr = date.substring(0, date.indexOf(" "));
        String yearStr = date.substring(date.lastIndexOf(" "));
        if (yearStr.length() > 4 || yearStr.length() < 2 || yearStr.length() == 3) {
            return false;
        }
        // try catch that checks if the day is valid
        try {
            dayInput = Integer.parseInt(dayStr) + "";
            yearlyInput = Integer.parseInt(yearStr);
        } catch (NumberFormatException nfe) {
            System.out.println(date + " - INVALID");
            return false;
        }

        // try {
        // yearStr = Integer.parseInt(yearStr) + "";
        // } catch (NumberFormatException e) {
        // System.out.println(date + " - INVALID");
        // return false;
        // }

        return true;
    }

    // Check if the given date is valid based on the provided criteria
    private static boolean isDateValid(String date) {
        String[] components = date.split(" ");
        int year = Integer.parseInt(components[2]);
        int day = Integer.parseInt(components[0]);
        int month = getMonth(date);

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

    public static int getMonth(String date) {
        String[] parts = date.split(" ");
        if (parts.length != 3) {
            System.out.println("INVALID INPUT");
            return -1;
        }
        int month;
        try {
            month = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            String monthStr = parts[1].toUpperCase();
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
