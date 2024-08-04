package leetcode;

/**
 * You are given two strings word1 and word2. Merge the strings by adding letters in alternating order, starting with word1.
 * If a string is longer than the other, append the additional letters onto the end of the merged string.
 *
 * Return the merged string.
 * Example 1:
 * Input: word1 = "abc", word2 = "pqr"
 * Output: "apbqcr"
 * Explanation: The merged string will be merged as so:
 * word1:  a   b   c
 * word2:    p   q   r
 * merged: a p b q c r
 *
 * Example 2:
 * Input: word1 = "ab", word2 = "pqrs"
 * Output: "apbqrs"
 * Explanation: Notice that as word2 is longer, "rs" is appended to the end.
 * word1:  a   b
 * word2:    p   q   r   s
 * merged: a p b q   r   s
 * Example 3:
 *
 * Input: word1 = "abcd", word2 = "pq"
 * Output: "apbqcd"
 * Explanation: Notice that as word1 is longer, "cd" is appended to the end.
 * word1:  a   b   c   d
 * word2:    p   q
 * merged: a p b q c   d
 *
 *
 * Constraints:
 * 1 <= word1.length, word2.length <= 100
 * word1 and word2 consist of lowercase English letters.
 *
 * Easy
 *
 */

public class LE_1768_Merge_Strings_Alternately {
    class Solution1 {
        public String mergeAlternately(String word1, String word2) {
            StringBuffer sb = new StringBuffer();

            char[] chars1 = word1.toCharArray();
            char[] chars2 = word2.toCharArray();
            int l1 = chars1.length;
            int l2 = chars2.length;

            int i = 0;
            while (i < l1 && i < l2) {
                sb.append(chars1[i]);
                sb.append(chars2[i]);
                i++;
            }

            if (i == l1 && i == l2) {
                // do nothing
            } else if (i == l1) {
                for (int j = i; j < l2; j++) {
                    sb.append(chars2[j]);
                }
            } else if (i == l2) {
                for (int j = i; j < l1; j++) {
                    sb.append(chars1[j]);
                }
            }

            return sb.toString();
        }
    }

    /**
     * Same algorithm as Solution1, but cleaner, use two ifs to append, so it does not have to deal with the rest
     * of the string, nice!
     */
    class Solution2 {
        public String mergeAlternately(String word1, String word2) {
            StringBuilder sb = new StringBuilder();
            int i = 0;

            while (i < word1.length() || i < word2.length()) {
                if (i < word1.length()) {
                    sb.append(word1.charAt(i));
                }
                if (i < word2.length()) {
                    sb.append(word2.charAt(i));
                }
                i++;
            }
            return sb.toString();
        }
    }
}
