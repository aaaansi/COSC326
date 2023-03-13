import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SyllableCounter {
    public static void main(String[] args) {
        Scanner scan = null;
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
            String word = scan.nextLine().toLowerCase();
            int syllables = countSyllables(word);
            System.out.println(syllables);
        }
        scan.close();
    }

    public static int countSyllables(String word) {
        int count = 0;
        boolean prevVowel = false;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            boolean isVowel = isVowel(c);

            if (isVowel && !prevVowel) {
                count++;
            }

            prevVowel = isVowel;
        }

        if (word.endsWith("e")) {
            count--;
        }

        if (count == 0) {
            count = 1;
        }

        return count;
    }

    public static boolean isVowel(char c) {
        return "aeiouy".indexOf(c) != -1;
    }
}
