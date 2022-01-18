package leetcode;

public class LE_1888_Minimum_Number_Of_Flips_To_Make_The_Binary_String_Alternating {
    /**
     * You are given a binary string s. You are allowed to perform two types of operations on the string in any sequence:
     *
     * Type-1: Remove the character at the start of the string s and append it to the end of the string.
     * Type-2: Pick any character in s and flip its value, i.e., if its value is '0' it becomes '1' and vice-versa.
     * Return the minimum number of type-2 operations you need to perform such that s becomes alternating.
     *
     * The string is called alternating if no two adjacent characters are equal.
     *
     * For example, the strings "010" and "1010" are alternating, while the string "0100" is not.
     *
     * Example 1:
     * Input: s = "111000"
     * Output: 2
     * Explanation: Use the first operation two times to make s = "100011".
     * Then, use the second operation on the third and sixth elements to make s = "101010".
     *
     * Example 2:
     * Input: s = "010"
     * Output: 0
     * Explanation: The string is already alternating.
     *
     * Example 3:
     * Input: s = "1110"
     * Output: 1
     * Explanation: Use the second operation on the second element to make s = "1010".
     *
     * Constraints:
     * 1 <= s.length <= 105
     * s[i] is either '0' or '1'.
     *
     * Medium
     *
     * https://leetcode.com/problems/minimum-number-of-flips-to-make-the-binary-string-alternating/
     */

    /**
     * Greedy
     *
     * Improved from Naive solution:
     * 1.For the 1st operation, we can simply do s += s to append the whole string to the end
     * 2.Then we move a sliding window of size n
     * This way to simulate operation 1.
     * Example:
     *  Given s = "11100"
     *  double s: 1110011100
     *  size n window: |11100|11100 ==> 1|11001|1100 ==> 11|10011|100 and so on
     * When we move one step of the sliding window, it is the same to append 1 more element from beginning.
     *
     * Time  : O(n)
     * Space : O(1)
     */
    class Solution {
        public int minFlips(String s) {
            if (s.length() == 1) return 0;

            int n = s.length();
            int res = Integer.MAX_VALUE;

            s += s;
            char c1 = '0', c2 = '1';
            char c3 = '0', c4 = '1';

            int r1 = 0, r2 = 0;

            for (int i = 0; i < 2 * n; i++) {
                r1 += (s.charAt(i) ^ c1);
                r2 += (s.charAt(i) ^ c2);
                c1 ^= 1;
                c2 ^= 1;

                /**
                 * Remove the flips that is already out of the sliding window
                 */
                if (i > n - 1) {
                    int idx = i - n;
                    r1 -= s.charAt(idx) ^ c3;
                    r2 -= s.charAt(idx) ^ c4;
                    c3 ^= 1;
                    c4 ^= 1;
                }

                /**
                 * Once we reach sliding window size, calculate result
                 */
                if (i >= n - 1) {
                    res = Math.min(res, Math.min(r1, r2));
                }
            }

            return res;
        }
    }

    /**
     * Naive solution
     *
     * Same greedy approach, each operation1 creates a new string, hence it caused "Output Limit Exceeded"
     *
     * Time  : O(n ^ 2)
     * Space : O(n ^ 2), n is length of s.
     */
    class Solution_Naive {
        public int minFlips(String s) {
            if (s.length() == 1) return 0;

            int n = s.length();
            int res = Integer.MAX_VALUE;
            char start = s.charAt(0);
            char end = s.charAt(s.length() - 1);
            String s1 = s;

            for (int i = 1; i < n; i++) {
                s1 = s1.substring(1) + start;
                System.out.println(s1);

                start = s1.charAt(0);
                end = start;

                int r = Math.min(getDiff(s, '0'), getDiff(s, '1'));
                int r1 = Math.min(getDiff(s1, '0'), getDiff(s1, '1'));

                res = Math.min(res, Math.min(r, r1));
            }

            return res;
        }

        //O(n)
        private int getDiff(String s, char c) {
            int count = 0;
            for (char cur : s.toCharArray()) {
                count += (cur ^ c);
                c ^= 1;
            }
            return count;
        }
    }
}
