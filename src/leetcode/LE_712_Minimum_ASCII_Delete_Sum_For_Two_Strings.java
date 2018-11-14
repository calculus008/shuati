package leetcode;

/**
 * Created by yuank on 11/13/18.
 */
public class LE_712_Minimum_ASCII_Delete_Sum_For_Two_Strings {
    /**
         Given two strings s1, s2, find the lowest ASCII sum of DELETED(!!!) characters to make two strings equal.

         Example 1:
         Input: s1 = "sea", s2 = "eat"
         Output: 231
         Explanation: Deleting "s" from "sea" adds the ASCII value of "s" (115) to the sum.
         Deleting "t" from "eat" adds 116 to the sum.
         At the end, both strings are equal, and 115 + 116 = 231 is the minimum sum possible to achieve this.

         Example 2:
         Input: s1 = "delete", s2 = "leet"
         Output: 403
         Explanation: Deleting "dee" from "delete" to turn the string into "let",
         adds 100[d]+101[e]+101[e] to the sum.  Deleting "e" from "leet" adds 101[e] to the sum.
         At the end, both strings are equal to "let", and the answer is 100+101+101+101 = 403.
         If instead we turned both strings into "lee" or "eet", we would get answers of 433 or 417, which are higher.

         Note:
         0 < s1.length, s2.length <= 1000.
         All elements of each string will have an ASCII value in [97, 122].
     */

    /**
         DP
         Time  : O(m * n)
         Space : O(m * n)

         dp[i][j] : min sum of deleted chars to make first i chars from s1 and first j chars from s2 equal.

         if ith char in s1 == jth char in s2 : dp[i][j] = dp[i - 1][j - 1]
         else, must delete one : dp[i][j] = min (dp[i - 1][j] + ith char, dp[i][j - 1] + jth char)
     **/
    public int minimumDeleteSum(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] + (int)s1.charAt(i - 1);
        }

        for (int i = 1; i <= n; i++) {
            dp[0][i] = dp[0][i - 1] + (int)s2.charAt(i - 1);
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j] + s1.charAt(i - 1), dp[i][j - 1] + s2.charAt(j - 1));
                }
            }
        }

        return dp[m][n];
    }
}
