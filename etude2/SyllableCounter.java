import java.util.Scanner;

public class SyllableCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String word = scanner.nextLine().toLowerCase();
            int syllables = countSyllables(word);
            System.out.println(syllables);
        }
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
