package lintcode;

/**
 * Created by yuank on 10/23/18.
 */
public class LI_395_Coins_In_A_Line_II {
    /**
         There are n coins with different value in a line. Two players take turns to take one
         or two coins from left side until there are no more coins left. The player who take
         the coins with the most value wins.

         Could you please decide the first player will win or lose?

         Example
         Given values array A = [1,2,2], return true.

         Given A = [1,2,4], return false.
     */

    /**
     * Solution 1
     *
     * dp[i] : 当前剩i个coin, 当前player所能拿到的coins的最大值。
     */
    public boolean firstWillWin1(int[] values) {
        int n = values.length;
        int[] sum = new int[n + 1];

        for (int i = 1; i <= n; ++i) {
            sum[i] = sum[i - 1] + values[n - i];
        }

        int[] dp = new int[n + 1];
        dp[1] = values[n - 1];

        /**
         * dp[i], i here is the number of coins still left, so we
         * basically move backwards, from right to left.
         */
        for (int i = 2; i <= n; ++i) {
            dp[i] = Math.max(sum[i] - dp[i - 1], sum[i] - dp[i - 2]);
        }

        /**
         * 面对n个coins, 如果当前player(一定是player1)最多能拿到超过总值一半
         * 的coins,那他一定胜出。
         */
        return dp[n]  > sum[n] / 2;
    }

    /**
     * Solution 2
     *
     * Let dp[i] be that for a sub-game in between [i, n - 1] inclusive, the optimized score that
     * Player 1 could get over Player 2 (in other words, difference between player 1 and 2 if both
     * of them play optimally, and the difference could be negative!). Thus we have our DP formula:
     *
     * dp[i] = max(coin[i] - dp[i + 1], coin[i] + coin[i + 1] - dp[i + 2])
     *
     * 从第i - 1先手开始取最多能赢几个硬币, 从右往左走，最后判断从0位置其实能赢的硬币是否大于0
     *
     * 我们从后往前看。dp[i] 表示当剩下range为 i - n-1 的时候先手能取得的最大价值。既然我们无论先后手都会使自己的价值最大，
     * 因此我们可以认为后手也是用 dp[i] 来得到自己的价值。那么，当我们还剩 i 枚硬币的时候，如果先手先取一枚，
     * 那么后手一定会得到 dp[i + 1] 的价值；同理，如果先手先取两枚，那么后手一定会得到 f[i + 2] 的价值。那么，
     * 我们计算先手的时候，就应该用最后 i 枚硬币的总和减去后手得到的价值，然后取这两种方案的最大值
     */


    public boolean firstWillWin(int[] values) {
        if (values == null || values.length == 0) return false;

        if (values.length < 3) return true;

        int n = values.length;
        int[] dp = new int[n];

        dp[n - 1] = values[n - 1];
        dp[n - 2] = values[n - 1] + values[n - 2];

        for (int i = n - 3; i >= 0; i--) {
            dp[i] = Math.max(values[i] - dp[i + 1], values[i] + values[i + 1] - dp[i + 2]);
        }

        return dp[0] >= 0;
    }
}
