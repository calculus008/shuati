package Interviews.Apple;

import java.util.*;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pattern_Matching {
    /**
     * Giving a list of strings, for example ['abcdef', 'abdef', 'abc', 'sdef', ''] and a pattern like abc[de]f.
     * the [] means any, so it can match any chars inside in all sequences and 0 zero to any times. In this case,
     * [de] can match d, de, ed, eee, ddee or empty string. The chars outside the [] are the strict match.
     * There are be more than 1 [] in the pattern.
     */

    // Converts the pattern with [] into a regex pattern
    public static String convertToRegex(String pattern) {
        StringBuilder regex = new StringBuilder();
        boolean inBracket = false;

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);

            if (c == '[') {
                inBracket = true;
                regex.append("(");  // Start a capturing group
            } else if (c == ']') {
                inBracket = false;
                regex.append(")*");  // Close the group and allow zero or more repetitions
            } else if (inBracket) {
                // If inside a bracket, just append characters, because they are alternatives
                regex.append(c);
            } else {
                // Strict match for characters outside the bracket
                regex.append(Pattern.quote(Character.toString(c)));  // Escaping any regex special characters
            }
        }
        return regex.toString();
    }


    // Finds and returns all strings that match the flexible pattern
    public static List<String> matchPattern(List<String> strings, String pattern) {
        String regexPattern = convertToRegex(pattern);
        Pattern regex = Pattern.compile(regexPattern);

        List<String> matches = new ArrayList<>();
        for (String str : strings) {
            Matcher matcher = regex.matcher(str);
            if (matcher.matches()) {
                matches.add(str);
            }
        }
        return matches;
    }

//        public static void main(String[] args) {
//            List<String> strings = new ArrayList<>();
//            strings.add("abcdef");
//            strings.add("abdef");
//            strings.add("abc");
//            strings.add("sdef");
//            strings.add("");
//
//            // Example pattern with [de]
//            String pattern = "abc[de]f";
//
//            // Count how many strings match the pattern
//            int count = countMatchingStrings(strings, pattern);
//
//            System.out.println("Count of matching strings: " + count);  // Output: 3
//        }
}

