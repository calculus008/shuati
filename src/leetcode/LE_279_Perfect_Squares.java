package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 4/17/18.
 */
public class LE_279_Perfect_Squares {
    /**
     * Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...) which sum to n.

     For example, given n = 12, return 3 because 12 = 4 + 4 + 4; given n = 13, return 2 because 13 = 4 + 9.
     */

    /**
     DP : Time : O(n), Space : O(n)

     Given n = 8:
     i = 0, dp[0] = 0;
     i = 1, j = 1, Math.max(dp[1], dp[1 - 1 * 1] + 1) => dp[1] = 1
     i = 2, j = 1, Math.max(dp[2], dp[2 - 1 * 1] + 1) => dp[2] = 2
     i = 3, j = 1, Math.max(dp[3], dp[3 - 1 * 1] + 1) => dp[3] = 3

     i = 4, j = 1, Math.max(dp[4], dp[4 - 1 * 1] + 1) => dp[4] = 4
     j = 2, Math.max(dp[4], dp[4 - 2 * 2] + 1) => dp[4] = 1

     i = 5, j = 1, Math.max(dp[5], dp[5 - 1 * 1] + 1) => dp[5] = 2
     j = 2, Math.max(dp[5], dp[5 - 2 * 2] + 1) => dp[5] = 2

     i = 6, j = 1, Math.max(dp[6], dp[6 - 1 * 1] + 1) => dp[6] = 3
     j = 2, Math.max(dp[6], dp[6 - 2 * 2] + 1) => dp[6] = 3

     i = 7, j = 1, Math.max(dp[7], dp[7 - 1 * 1] + 1) => dp[7] = 4
     j = 2, Math.max(dp[7], dp[7 - 2 * 2] + 1) => dp[7] = 4

     i = 8, j = 1, Math.max(dp[8], dp[8 - 1 * 1] + 1) => dp[6] = 4
     j = 2, Math.max(dp[8], dp[8 - 2 * 2] + 1) => dp[4] = 2

     i = 9, j = 1, Math.max(dp[9], dp[9 - 1 * 1] + 1) => dp[9] = 5  (dp[7] = 4, [2, 1, 1, 1] => dp[9] = 5, [2, 1, 1, 1, 1])
     j = 2, Math.max(dp[9], dp[9 - 2 * 2] + 1) => dp[9] = 3  (dp[5] = 2 ,[2, 1] => dp[9] = 3, [2, 1, 2], 2 * 2 + 1 + 2 * 2)
     j = 3, Math.max(dp[9], dp[9 - 3 * 3] + 1) => dp[9] = 1

     */
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 0; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1); //这里加的1就是指的用j (j * j)
            }
        }

        return dp[n];
    }
}
