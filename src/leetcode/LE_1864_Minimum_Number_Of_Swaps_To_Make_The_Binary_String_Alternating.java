package leetcode;

public class LE_1864_Minimum_Number_Of_Swaps_To_Make_The_Binary_String_Alternating {
    /**
     * Given a binary string s, return the minimum number of character swaps to make it alternating, or -1 if it is impossible.
     *
     * The string is called alternating if no two adjacent characters are equal. For example, the strings "010" and "1010"
     * are alternating, while the string "0100" is not.
     *
     * Any two characters may be swapped, even if they are not adjacent.
     *
     * Example 1:
     * Input: s = "111000"
     * Output: 1
     * Explanation: Swap positions 1 and 4: "111000" -> "101010"
     * The string is now alternating.
     *
     * Example 2:
     * Input: s = "010"
     * Output: 0
     * Explanation: The string is already alternating, no swaps are needed.
     *
     * Example 3:
     * Input: s = "1110"
     * Output: -1
     *
     * Constraints:
     * 1 <= s.length <= 1000
     * s[i] is either '0' or '1'.
     *
     * Medium
     *
     * https://leetcode.com/problems/minimum-number-of-swaps-to-make-the-binary-string-alternating/
     */

    /**
     * The key to solve this problem is to count number of indexes which contain wrong character. So for string s = 11001,
     * count will be 2 as characters at s[1] and s[2] are wrong. And number of swaps will be count / 2. For example,
     * we can just swap character at index 1 and index 2 to obtain s = 10101.
     *
     * Solving this problem will be impossible if difference between number of ones and number of zeros will be greater than 1.
     *
     * If number of ones is greater than number of zeroes then 1 should be the first character of resulting string.
     * Similarly if number of zeroes is greater than number of ones then 0 should be the first character of resulting string.
     *
     * If number of ones is equal to number of zeroes, we will find minimum number of swaps of both cases:
     *  Case 1 : 1 is the first character.
     *  Case 2 : 0 is the first character.
     */
    class Solution {
        public int minSwaps(String s) {
            int ones = 0, zeros = 0;

            for (char c : s.toCharArray()) {
                if (c == '1') {
                    ones++;
                } else {
                    zeros++;
                }
            }

            if (Math.abs(ones - zeros) > 1) return -1;

            if (zeros > ones) {
                return helper(s, '0');
            } else if (ones > zeros) {
                return helper(s, '1');
            }

            return Math.min(helper(s, '0'), helper(s, '1'));
        }

        private int helper(String s, char c) {
            int count = 0;
            for (char cur : s.toCharArray()) {
                if (cur != c) count++;
                c ^= 1;
            }
            return count / 2;
        }
    }
}
