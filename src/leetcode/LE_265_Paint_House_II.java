package leetcode;

/**
 * Created by yuank on 4/13/18.
 */
public class LE_265_Paint_House_II {
    /**
     *
         There are a row of n houses, each house can be painted with one of the k colors.
         The cost of painting each house with a certain color is different.
         You have to paint all the houses such that no two adjacent houses have the same color.

         The cost of painting each house with a certain color is represented by a n x k cost matrix.
         For example, costs[0][0] is the cost of painting house 0 with color 0; costs[1][2] is the cost of painting house 1 with color 2,
         and so on... Find the minimum cost to paint all houses.

         Note:
         All costs are positive integers.
     */

    /**
     DP
     Time and Space : O(nk)
     */
    public int minCostII(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0].length == 0) return 0;

        int n = costs.length;
        int k = costs[0].length;
        //!!!dp[i][j], the min cost when we end with ith house and paint it with jth color
        int dp[][] = new int[n][k];

        int min1 = -1, min2 = -1;

        for (int i = 0; i < n; i++) {
            int last1 = min1, last2 = min2;
            min1 = -1;
            min2 = -1;

            //j是颜色的下标
            for (int j = 0; j < k; j++) {
                /**
                 当前的颜色不是前一个房子cost最小的颜色, 也就是说，给定了个颜色j，
                 只要j不同与前一个房子花费最小的颜色last1，就认为前一个房子被涂成那个花费最小的颜色last1
                 否则，就认为前一个房子被涂成那个花费第二小的颜色last2
                 */
                if (j != last1) {
                    dp[i][j] = costs[i][j] + (last1 < 0 ? 0 : dp[i - 1][last1]); //!!! "dp", "[i - 1]", "last1"
                } else {
                    dp[i][j] = costs[i][j] + (last2 < 0 ? 0 : dp[i - 1][last2]);
                }

                //!!! "dp", NOT "costs"
                if (min1 < 0 || dp[i][j] < dp[i][min1]) {
                    min2 = min1;
                    min1 = j;
                } else if (min2 < 0 || dp[i][j] < dp[i][min2]) {
                    min2 = j;
                }
            }
        }

        return dp[n - 1][min1];
    }
}
