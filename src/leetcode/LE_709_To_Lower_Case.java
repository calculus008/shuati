package src.leetcode;

public class LE_709_To_Lower_Case {
    /**
     * Implement function ToLowerCase() that has a string parameter str, and returns the same string in lowercase.
     *
     * Example 1:
     *
     * Input: "Hello"
     * Output: "hello"
     *
     * Example 2:
     *
     * Input: "here"
     * Output: "here"
     *
     * Example 3:
     *
     * Input: "LOVELY"
     * Output: "lovely"
     */

    class Solution {
        public String toLowerCase(String str) {
            if (null == str) return str;

            StringBuilder sb = new StringBuilder();

            for (char c : str.toCharArray()) {
                if (c >= 'A' && c <= 'Z') {
                    sb.append((char)('a' + c - 'A'));
                } else {
                    sb.append(c);
                }
            }

            return sb.toString();

        }
    }
}
