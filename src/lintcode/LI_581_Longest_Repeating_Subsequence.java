package lintcode;

/**
 * Created by yuank on 10/23/18.
 */
public class LI_581_Longest_Repeating_Subsequence {
    /**
         Given a string, find length of the longest repeating subsequence such that the two
         subsequence don’t have same string character at same position, i.e., any ith character
         in the two subsequences shouldn’t have the same index in the original string.

         Example
         str = abc, return 0, There is no repeating subsequence

         str = aab, return 1, The two subsequence are a(first) and a(second).
         Note that b cannot be considered as part of subsequence as it would be at same index in both.

         str = aabb, return 2

         Medium
     */

    /**
     * i代表str的前i个character, j代表str的前j个character.
     * dp[i][j] : index不重合的情况下，character相等的个数
     *
     *     a a b b
     *     0 0 0 0
     * a 0 0 1 1 1
     * a 0 1 1 1 1
     * b 0 1 1 1 2
     * b 0 1 1 2 2
     *
     */
    public int longestRepeatingSubsequence(String str) {
        if (str == null || str.length() == 0) return 0;

        int n = str.length();
        int[][] dp = new int[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (str.charAt(i - 1) == str.charAt(j - 1) && i != j) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[n][n];
    }
}

