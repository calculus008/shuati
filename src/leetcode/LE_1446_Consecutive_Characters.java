package leetcode;

public class LE_1446_Consecutive_Characters {
    /**
     * The power of the string is the maximum length of a non-empty substring that contains only one unique character.
     *
     * Given a string s, return the power of s.
     *
     * Example 1:
     * Input: s = "leetcode"
     * Output: 2
     * Explanation: The substring "ee" is of length 2 with the character 'e' only.
     *
     * Example 2:
     * Input: s = "abbcccddddeeeeedcba"
     * Output: 5
     * Explanation: The substring "eeeee" is of length 5 with the character 'e' only.
     *
     * Constraints:
     * 1 <= s.length <= 500
     * s consists of only lowercase English letters.
     *
     * Easy
     *
     * https://leetcode.com/problems/consecutive-characters/
     */

    class Solution {
        public int maxPower(String s) {
            char[] chars = s.toCharArray();
            char pre = chars[0];
            int count = 1;
            int res = 1;

            for (int i = 1; i < chars.length; i++) {
                if (chars[i] == pre) {
                    count++;
                    res = Math.max(res, count);
                } else {
                    pre = chars[i];
                    count = 1;
                }
            }

            return res;
        }
    }
}
