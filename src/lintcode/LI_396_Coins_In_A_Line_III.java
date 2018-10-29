package lintcode;

/**
 * Created by yuank on 10/23/18.
 */
public class LI_396_Coins_In_A_Line_III {
    /**
         There are n coins in a line. Two players take turns to take a coin from one of the
         ends of the line until there are no more coins left. The player with the larger
         amount of money wins.

         Could you please decide the first player will win or lose?

         Example
         Given array A = [3,2,2], return true.

         Given array A = [1,2,4], return true.

         Given array A = [1,20,4], return false.

         Challenge
         Follow Up Question:

         If n is even. Is there any hacky algorithm that can decide whether first
         player will win or lose in O(1) memory and O(n) time?

         Hard
     */

    /**
     * Solution 1
     * Similar with Solution 1 for LI_395_Coins_In_A_Line_II
     *
     * dp[i][j] means maximum value CURRENT player will make in a subarray starts from i and ends at j。
     *
     */
    public boolean firstWillWin1(int[] values) {
        int n = values.length;
        int[] sum = new int[n + 1];
        sum[0] = 0;

        for (int i = 1; i <= n; ++i) {
            sum[i] = sum[i - 1] + values[i - 1];
        }


        int[][] dp = new int[n][n];

        for (int i = 0; i < n; ++i) {
            dp[i][i] = values[i];
        }

        for (int len = 2; len <= n; ++len) {//!!!
            for (int i = 0; i < n; ++i) {//!!!
                int j = i + len - 1;//!!!

//                if (j >= n) continue;

                // sum between values[j] and values[i] = sum[j + 1] -  sum[i];
                int s = sum[j + 1] - sum[i];
                dp[i][j] = Math.max(s - dp[i + 1][j], s - dp[i][j - 1]);
            }
        }

        return dp[0][n - 1]  > sum[n] / 2;
    }


    /**
     * Solution 2
     *
     * 每次拿一个coin, 可以从两头拿。
     *
     * 区间dp :
     * dp[i][j] means maximum value CURRENT player will make in a subarray starts from i and ends at j。
     * i and j are index, so they are zero based.
     */
    public boolean firstWillWin2(int[] values) {
        if (values == null || values.length <= 1) {
            return true;
        }

        int n = values.length;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += values[i];
        }

        int[][] dp = new int[n][n];

        /**
         * initialize dp[][]
         **/
        for (int i = 0; i < n; i++) {
            dp[i][i] = values[i];
            if (i != n - 1) {
                dp[i][i + 1] = Math.max(values[i], values[i + 1]);
            }
        }

        /**
         * calculate dp[i][j] from back to front
         *
         * 1.values[i] + Math.min(dp[i + 2][j], dp[i + 1][j - 1]) :
         * 如果player1拿values[i], player2的option:
         *   拿values[i + 1], 或拿values[j]
         * 那么player1只可能拿到dp[i + 2][j]和dp[i + 1][j - 1]中较小的那个。
         *
         * 2.values[j] + Math.min(dp[i][j - 2], dp[i + 1][j - 1])
         * 同理，player1取values[j]时的情况。
         *
         * Move backwards, from right to left, 区间[i, j]的最小长度为2。
         * **/
        for (int i = n - 2; i >= 0; i--) {//!!!
            for (int j = i + 2; j < n; j++) {//!!!
                dp[i][j] = Math.max(values[i] + Math.min(dp[i + 2][j], dp[i + 1][j - 1]),
                                    values[j] + Math.min(dp[i][j - 2], dp[i + 1][j - 1]));
            }
        }

        return dp[0][n - 1]  > sum / 2;
    }
}
