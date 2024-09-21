package leetcode;

import java.util.*;

public class LE_1456_Maximum_Number_Of_Vowels_In_A_Substring_Of_Given_Length {
    /**
     * Given a string s and an integer k, return the maximum number of vowel letters in any substring of s with length k.
     *
     * Vowel letters in English are 'a', 'e', 'i', 'o', and 'u'.
     *
     *
     *
     * Example 1:
     * Input: s = "abciiidef", k = 3
     * Output: 3
     * Explanation: The substring "iii" contains 3 vowel letters.
     *
     * Example 2:
     * Input: s = "aeiou", k = 2
     * Output: 2
     * Explanation: Any substring of length 2 contains 2 vowels.
     *
     * Example 3:
     * Input: s = "leetcode", k = 3
     * Output: 2
     * Explanation: "lee", "eet" and "ode" contain 2 vowels.
     *
     *
     * Constraints:
     * 1 <= s.length <= 105
     * s consists of lowercase English letters.
     * 1 <= k <= s.length
     *
     * Medium
     *
     * https://leetcode.com/problems/maximum-number-of-vowels-in-a-substring-of-given-length
     */

    class Solution_with_set {
        public int maxVowels(String s, int k) {
            int n = s.length();
            if (s.length() < k) return 0;

//            Set<Character> set = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
            Set<Character> set = Set.of('a', 'e', 'i', 'o', 'u'); //!!! how to init a set with characters

            int count = 0, res = 0;
            for (int i = 0, j = 0; i < n; i++) {
                if (set.contains(s.charAt(i))) count++;

                if (i >= k - 1) {
                    res = Math.max(res, count);
                    if (set.contains(s.charAt(j))) count--;
                    j++;
                }
            }

            return res;
        }
    }

    class Solution_no_set {
        public int maxVowels(String s, int k) {
            int n = s.length();
            if (s.length() < k) return 0;

            int count = 0, res = 0;
            for (int i = 0, j = 0; i < n; i++) {
                if (isVowel(s.charAt(i))) count++;

                if (i >= k - 1) {
                    res = Math.max(res, count);
                    if (isVowel(s.charAt(j))) count--;
                    j++;
                }
            }

            return res;
        }

        public boolean isVowel(char c) {
            return (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u');
        }
    }
}
