package leetcode;

import java.util.*;

public class LE_1657_Determine_If_Two_Strings_Are_Close {
    /**
     * Two strings are considered close if you can attain one from the other using the following operations:
     *
     * Operation 1: Swap any two existing characters.
     * For example, abcde -> aecdb
     * Operation 2: Transform every occurrence of one existing character into another existing character,
     * and do the same with the other character.
     *
     * For example, aacabb -> bbcbaa (all a's turn into b's, and all b's turn into a's)
     * You can use the operations on either string as many times as necessary.
     *
     * Given two strings, word1 and word2, return true if word1 and word2 are close, and false otherwise.
     *
     *
     * Example 1:
     * Input: word1 = "abc", word2 = "bca"
     * Output: true
     * Explanation: You can attain word2 from word1 in 2 operations.
     * Apply Operation 1: "abc" -> "acb"
     * Apply Operation 1: "acb" -> "bca"
     *
     * Example 2:
     * Input: word1 = "a", word2 = "aa"
     * Output: false
     * Explanation: It is impossible to attain word2 from word1, or vice versa, in any number of operations.
     *
     * Example 3:
     * Input: word1 = "cabbba", word2 = "abbccc"
     * Output: true
     * Explanation: You can attain word2 from word1 in 3 operations.
     * Apply Operation 1: "cabbba" -> "caabbb"
     * Apply Operation 2: "caabbb" -> "baaccc"
     * Apply Operation 2: "baaccc" -> "abbccc"
     *
     *
     * Constraints:
     *
     * 1 <= word1.length, word2.length <= 105
     * word1 and word2 contain only lowercase English letters.
     *
     * Medium
     *
     * https://leetcode.com/problems/determine-if-two-strings-are-close
     */

    class Solution {
        /**
         * Key Insights, if two strings are "close":
         * 1.Length should be the same
         * 2.Contains exact same set of chars
         * 3.Chars frequency should be the same
         */
        public boolean closeStrings(String word1, String word2) {
            if (word1.length() != word2.length()) return false;

            Map<Character, Integer> m1 = new HashMap<>();
            Map<Character, Integer> m2 = new HashMap<>();

            for (char c : word1.toCharArray()) {
                m1.put(c, m1.getOrDefault(c, 0) + 1);
            }

            for (char c : word2.toCharArray()) {
                m2.put(c, m2.getOrDefault(c, 0) + 1);
            }

            if (!m1.keySet().equals(m2.keySet())) return false;

            List<Integer> l1 = new ArrayList<>(m1.values());
            List<Integer> l2 = new ArrayList<>(m2.values());

            Collections.sort(l1);
            Collections.sort(l2);

            return l1.equals(l2);
        }
    }
}
