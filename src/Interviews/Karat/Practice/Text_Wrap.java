package src.Interviews.Karat.Practice;

import java.util.ArrayList;
import java.util.List;

public class Text_Wrap {
    public static  List<String> processWords(String[] words, int max) {
        List<String> res = new ArrayList<>();
        if (words == null || words.length == 0) return res;

        /**
         * !!!
         * j
         */
        for (int i = 0, j; i < words.length; i = j) {
            int len = -1;

            /**
             * !!!
             * "j = i", not "int j = i", j is defined in the outter for loop
             */
            for (j = i; j < words.length && len + words[j].length() + 1 <= max; j++) {
                len += words[j].length() + 1;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(words[i]);

            /**
             * k < j
             */
            for (int k = i + 1; k < j; k++) {
                sb.append("-").append(words[k]);
            }

            res.add(sb.toString());
        }

        return res;
    }

    public static List<String> processWrap(String[] words, int max) {
        List<String> res = new ArrayList<>();
        if (words == null || words.length == 0) return res;

        List<String> list = processWords(words, max);

        for (String s : list) {
            int len = s.length();

            String[] parts = s.split("-");
            int numberOfWords = parts.length;

            if (numberOfWords == 1) {
                res.add(s);
                continue;
            }

            int numberOfGaps = numberOfWords - 1;
            int numberOfPaddings = max - len + numberOfGaps;

            int averagePadding = numberOfPaddings / numberOfGaps;
            int extraPadding = numberOfPaddings % numberOfGaps;

            StringBuilder sb = new StringBuilder();
            sb.append(parts[0]);

            for (int i = 1; i < numberOfWords; i++) {
                for (int j = 0; j < averagePadding; j++) {
                    sb.append("-");
                }
                if (extraPadding > 0) {
                    sb.append("-");
                    extraPadding--;
                }
                sb.append(parts[i]);
            }

            res.add(sb.toString());
        }

        return res;
    }

    private static void printRes(String title, List<String> list) {
        System.out.println(title);
        for (String s : list) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        String[] words1 = {"The", "day", "began", "as", "still", "as", "the", "night",
                "abruptly", "lighted", "with", "brilliant", "flame"};
        printRes("===max 13===", processWords(words1, 13));
        printRes("===max 20===", processWords(words1, 20));

        String[] words2 = {"Hello"};
        String[] words3 = {"Hello", "World"};

        printRes("===max 5===", processWords(words2, 5));
        printRes("===max 5===", processWords(words3, 5));

        printRes("===Wrap max 13===", processWrap(words1, 13));
        printRes("===Wrap max 20===", processWrap(words1, 20));
        printRes("===Wrap max 5===", processWrap(words2, 5));
    }
}
