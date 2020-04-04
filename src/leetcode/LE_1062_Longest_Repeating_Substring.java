package leetcode;

public class LE_1062_Longest_Repeating_Substring {
    /**
     * Given a string S, find out the length of the longest repeating substring(s).
     * Return 0 if no repeating substring exists.
     *
     * Example 1:
     *
     * Input: "abcd"
     * Output: 0
     * Explanation: There is no repeating substring.
     *
     * Example 2:
     *
     * Input: "abbaba"
     * Output: 2
     * Explanation: The longest repeating substrings are "ab" and "ba", each of which occurs twice.
     *
     * Example 3:
     *
     * Input: "aabcaabdaab"
     * Output: 3
     * Explanation: The longest repeating substring is "aab", which occurs 3 times.
     *
     * Example 4:
     *
     * Input: "aaaaa"
     * Output: 4
     * Explanation: The longest repeating substring is "aaaa", which occurs twice.
     *
     *
     * Note:
     *
     * The string S consists of only lowercase English letters from 'a' - 'z'.
     * 1 <= S.length <= 1500
     *
     * Medium
     */

    /**
     * DP
     *
     * dp[i][j] : length of the longest repeating substring ends at ith and jth char
     *
     * Time  : O(n ^ 2)
     * Space : O(n ^2)
     *
     * This one allows overlapping, for none-overlapping:
     * Longest_Repeating_Non_overlapping_Substring
     */
    class Solution {
        public int longestRepeatingSubstring(String S) {
            if (S == null || S.length() == 0) return 0;

            int n = S.length();
            int[][] dp = new int[n + 1][n + 1];

            int max = 0;

            for (int i = 1; i <= n; i++) {
                for (int j = i + 1; j <= n; j++) {
                    if (S.charAt(i - 1) == S.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                        max = Math.max(max, dp[i][j]);
                    } else {
                        dp[i][j] = 0;
                    }
                }
            }

            return max;
        }
    }
}
