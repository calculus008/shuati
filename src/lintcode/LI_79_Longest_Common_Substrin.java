package lintcode;

/**
 * Created by yuank on 10/26/18.
 */
public class LI_79_Longest_Common_Substrin {
    /**
         Given two strings, find the longest common substring.

         Return the length of it.

         Example
         Given A = "ABCD", B = "CBCE", return 2.

         Challenge
         O(n x m) time and memory.

         Notice
         The characters in substring should occur continuously in original string.
         This is different with subsequence.

         Medium
     */

    /**
     * Solution 1
     * DP
     * Time and Space : O(m * n)
     * dp[i][j] : the length of common substring in the first i element of A
     *            and the first j elements of B, AND the common substring ends at
     *            ith element in A and jth element in B.
     */
    public int longestCommonSubstring1(String A, String B) {
        if (A == null || B == null || A.length() == 0 || B.length() == 0) return 0;

        int m = A.length();
        int n = B.length();
        int[][] dp = new int[m + 1][n + 1];

        int res = 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (A.charAt(i - 1) == B.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    res = Math.max(res, dp[i][j]);
                } else {
                    dp[i][j] = 0;
                }
            }
        }

        return res;
    }

    /**
     * Solution 2
     * None DP
     */
    public int longestCommonSubstring2(String A, String B) {
        int n = A.length();
        int m = B.length();
        int max = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int current = 0;
                while (i + current < n && j + current < m && A.charAt(i + current) == B.charAt(j + current))
                    current++;
                max = Math.max(max, current);
            }
        }
        return max;
    }
}
