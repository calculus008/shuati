package leetcode;

import java.util.*;

public class LE_1592_Rearrange_Spaces_Between_Words {
    /**
     * You are given a string text of words that are placed among some number of spaces. Each word consists of one or
     * more lowercase English letters and are separated by at least one space. It's guaranteed that text contains at
     * least one word.
     *
     * Rearrange the spaces so that there is an equal number of spaces between every pair of adjacent words and that
     * number is maximized. If you cannot redistribute all the spaces equally, place the extra spaces at the end,
     * meaning the returned string should be the same length as text.
     *
     * Return the string after rearranging the spaces.
     *
     * Example 1:
     * Input: text = "  this   is  a sentence "
     * Output: "this   is   a   sentence"
     * Explanation: There are a total of 9 spaces and 4 words. We can evenly divide the 9 spaces between the words:
     * 9 / (4-1) = 3 spaces.
     *
     * Example 2:
     * Input: text = " practice   makes   perfect"
     * Output: "practice   makes   perfect "
     * Explanation: There are a total of 7 spaces and 3 words. 7 / (3-1) = 3 spaces plus 1 extra space. We place this
     * extra space at the end of the string.
     *
     * Example 3:
     * Input: text = "hello   world"
     * Output: "hello   world"
     *
     * Example 4:
     * Input: text = "  walks  udp package   into  bar a"
     * Output: "walks  udp  package  into  bar  a "
     *
     * Example 5:
     * Input: text = "a"
     * Output: "a"
     *
     * Constraints:
     * 1 <= text.length <= 100
     * text consists of lowercase English letters and ' '.
     * text contains at least one word.
     *
     * Easy
     */

    /**
     * tricky part:
     * if text is "text   ", should return "text   "
     * if text is " text  ", should return "text   "
     *
     * So, need to process the case of one word separately.
     */

    /**
     * One pass, use extra mem to store words
     * Time : O(n)
     * Space : O(n)
     */
    class Solution1 {
        public String reorderSpaces(String text) {
            if (text == null || text.length() == 0) return text;

            List<String> words = new ArrayList<>();
            int spacesCount = 0;

            char[] chars = text.toCharArray();
            int i = 0;
            while (i < chars.length) {
                if (chars[i] == ' ') {
                    while (i < chars.length && chars[i] == ' ') {
                        spacesCount++;
                        i++;
                    }
                }

                if (i < chars.length  && chars[i] != ' ') {
                    StringBuilder sb = new StringBuilder();
                    while (i < chars.length && chars[i] != ' ') {
                        sb.append(chars[i]);
                        i++;
                    }
                    words.add(sb.toString());
                }
            }

            int wordsCount = words.size();
            int gapSpacesCount = wordsCount <= 1 ? 0 : spacesCount / (wordsCount - 1);
            int extraSpacesCount = spacesCount - gapSpacesCount * (wordsCount - 1);//!!!

            String gapStr = String.join("", Collections.nCopies(gapSpacesCount, " "));
            String tailStr = String.join("", Collections.nCopies(extraSpacesCount, " "));

            return String.join(gapStr, words) + tailStr;
        }
    }

    /**
     * Concise but not efficient (8ms, 6.38%), since it goes over the input two times.
     */
    class Solution2 {
        public String reorderSpaces(String text) {
            /**
             * !!!
             * 1.There are two "escapings" going on here. The first backslash is to escape the second backslash for the
             *   Java language, to create an actual backslash character. The backslash character is what escapes the + or
             *   the s for interpretation by the regular expression engine. That's why you need two backslashes -- one for
             *   Java, one for the regular expression engine. With only one backslash, Java reports \s and + as illegal
             *   escape characters -- not for regular expressions, but for an actual character in the Java language.
             *
             * 2."\\s+" is Java regular expression (regex); "\\s" stands for a single space and + is a quantifier in a
             *   regex,and it indicates one or more [the char or expression] right before it
             *
             * 3.MUST trim the string before doing split(), otherwise, it will have the first element as empty string
             */
            String[] words = text.trim().split("\\s+");

            int count = words.length;
            /**
             * lambda way of count empty chars in a string
             */
            int spaces = (int)text.chars().filter(c -> c == ' ').count();

            int gap = count <= 1 ? 0 : spaces / (count - 1);

            /**
             * concise way of getting number of tail space, no need to check number of words
             */
            int trailingSpaces = spaces - gap * (count - 1);

            /**
             * !!!
             * With String.repeat() is in Java 11:
             *   return String.join(" ".repeat(gap), words) + " ".repeat(trailingSpaces);
             */

            /**
             * With Java 8, use :
             * // create a string made up of n copies of string s
             * String.join("", Collections.nCopies(n, s));
             */
            String s1 = String.join("", Collections.nCopies(gap, " "));
            String s2 = String.join("", Collections.nCopies(trailingSpaces, " "));

            return String.join(s1, words) + s2;
        }
    }

    /**
     * Two passes
     * Time : O(n)
     * Space : O(1), not using extra mem of an array or list to store words.
     */
    class Solution3 {
        public String reorderSpaces(String text) {
            if (text == null || text.length() == 0) return "";

            /**
             * First pass, get number of words and number of spaces
             */
            int numWords = 0, numSpaces = 0;
            for (int i = 0; i < text.length(); ++i) {
                if (text.charAt(i) == ' ') {
                    numSpaces++;
                } else {
                    numWords++;
                    while (i < text.length() && text.charAt(i) != ' ') {
                        i++;     // Skip the current word.
                    }
                    i--;
                }
            }
            int maxSpaces = numWords == 1 ? 0 : numSpaces / (numWords - 1);
            int remainingSpaces = numWords == 1 ? numSpaces : numSpaces % (numWords - 1);

            /**
             * 2nd pass
             */
            String gapString = String.join("", Collections.nCopies(maxSpaces, " "));
            String tailString = String.join("", Collections.nCopies(remainingSpaces, " "));
            boolean skipFirst = true;
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < text.length(); ++i) {
                if (text.charAt(i) != ' ') {
                    // Don't put the whitespaces in front of the first word.
                    if (!skipFirst) result.append(gapString);
                    else skipFirst = false;

                    // Append the current word to the result.
                    while (i < text.length() && text.charAt(i) != ' ') {
                        result.append(text.charAt(i++));
                    }
                    i--;
                }
            }
            return result.append(tailString).toString();
        }
    }
}
