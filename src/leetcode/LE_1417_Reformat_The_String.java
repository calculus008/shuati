package leetcode;

import java.util.ArrayList;
import java.util.List;

public class LE_1417_Reformat_The_String {
    /**
     * Given alphanumeric string s. (Alphanumeric string is a string consisting of lowercase English letters and digits).
     *
     * You have to find a permutation of the string where no letter is followed by another letter and no digit is followed
     * by another digit. That is, no two adjacent characters have the same type.
     *
     * Return the reformatted string or return an empty string if it is impossible to reformat the string.
     *
     * Example 1:
     * Input: s = "a0b1c2"
     * Output: "0a1b2c"
     * Explanation: No two adjacent characters have the same type in "0a1b2c". "a0b1c2", "0a1b2c", "0c2a1b" are also
     * valid permutations.
     *
     * Example 2:
     * Input: s = "leetcode"
     * Output: ""
     * Explanation: "leetcode" has only characters so we cannot separate them by digits.
     *
     * Example 3:
     * Input: s = "1229857369"
     * Output: ""
     * Explanation: "1229857369" has only digits so we cannot separate them by characters.
     *
     * Example 4:
     * Input: s = "covid2019"
     * Output: "c2o0v1i9d"
     *
     * Example 5:
     * Input: s = "ab123"
     * Output: "1a2b3"
     *
     * Constraints:
     * 1 <= s.length <= 500
     * s consists of only lowercase English letters and/or digits.
     *
     * Easy
     */

    /**
     * The tricky part:
     * 1.How to tell if we should return ""
     * 2.Fill in the final String.
     *
     * Here we use a separate function
     */
    class Solution {
        public String reformat(String s) {
            if (s == null || s.length() <= 1) return s;

            /**
             * no need to have a list of digits and one of characters,
             * both list should have characters, otherwise we can't
             * have generateString() to work. It requires both list having
             * the same element type.
             */
            List<Character> chars = new ArrayList<>();
            List<Character> nums = new ArrayList<>();

            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    nums.add(c);
                } else {
                    chars.add(c);
                }
            }

            int m = chars.size();
            int n = nums.size();

            if (m == 0 || n == 0 || Math.abs(m - n) > 1) return "";

            StringBuilder sb = new StringBuilder();
            if (m > n) {
                generateString(sb, chars, nums);
            } else {
                generateString(sb, nums, chars);
            }

            return sb.toString();
        }

        private void generateString(StringBuilder sb, List<Character> l1, List<Character> l2) {
            int len = l2.size();
            for (int i = 0; i < len; i++) {
                sb.append(l1.get(i)).append(l2.get(i));
            }

            /**
             * In this function, l1.size() >= l2.size(), so we have 2 cases:
             * 1.l1 and l2 have the same length;
             * 2.l1 is one element longer than l2, this line is for this case.
             */
            if (l1.size() > len) sb.append(l1.get(l1.size() - 1));
        }
    }

    /**
     * 4ms
     */
    class Solution1 {
        public String reformat(String s) {
            if (s.length() == 1) return s;

            List<Character> chars = new ArrayList<>();
            List<Integer> nums = new ArrayList<>();

            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    nums.add(c - '0');
                } else {
                    chars.add(c);
                }
            }

            int m = chars.size();
            int n = nums.size();

            if (m == 0 || n == 0 || Math.abs(m - n) > 1) return "";

            StringBuilder sb = new StringBuilder();

            if (m > n) {
                for (int i = 0; i < m - 1; i++) {
                    sb.append(chars.get(i)).append(nums.get(i));
                }
                sb.append(chars.get(m - 1));
            } else if (n > m) {
                for (int i = 0; i < n - 1; i++) {
                    sb.append(nums.get(i)).append(chars.get(i));
                }
                sb.append(nums.get(n - 1));
            } else {
                for (int i = 0; i < n; i++) {
                    sb.append(nums.get(i)).append(chars.get(i));
                }
            }

            return sb.toString();
        }
    }

    /**
     * Change from Solution1, only change the section in populating the new string, looks a little concise but it acutally
     * takes longer time - 6ms, so it probably won't worth it.
     */
    class Solution2 {
        public String reformat(String s) {
            if (s == null || s.length() <= 1) return s;

            List<Character> chars = new ArrayList<>();
            List<Integer> nums = new ArrayList<>();

            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    nums.add(c - '0');
                } else {
                    chars.add(c);
                }
            }

            int m = chars.size();
            int n = nums.size();

            if (m == 0 || n == 0 || Math.abs(m - n) > 1) return "";

            StringBuilder sb = new StringBuilder();

            boolean useChar = m > n ? true : false;

            for (int i = 0; i < s.length(); i++) {
                if (useChar) {
                    if (chars.size() > 0) sb.append(chars.remove(0));
                } else {
                    if (nums.size() > 0) sb.append(nums.remove(0));
                }
                useChar = !useChar;
            }

            return sb.toString();
        }
    }
}
