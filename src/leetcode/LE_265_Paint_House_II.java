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

     dp[i][j] represents the min paint cost from house 0 to house i when house i use color j.
     Transition:
     dp[i][j] = Math.min(any k!= j| dp[i-1][k]) + costs[i][j].

     是从Solution2优化而来的，都要会。
     */
    class Solution1 {
        public int minCostII(int[][] costs) {
            if (costs == null || costs.length == 0 || costs[0].length == 0) return 0;

            int n = costs.length;
            int k = costs[0].length;
            /**!!!
             * dp[i][j], the min cost when we end with ith house and paint it with jth color
             **/
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

    /**
     * O(m * n * n) time, O(m * n) space solution (using Solution1 notation, it is O(n * k ^ 2)
     * Easy to understand
     */
    class Solution2 {
        public int minCostII(int[][] costs) {
            if(costs.length == 0) return 0;

            int m = costs.length;
            int n = costs[0].length;

            int[][] dp = new int[m][n];
            for(int i = 0; i < n; i++){
                dp[0][i] = costs[0][i];
            }

            for(int i = 1; i < m; i++){
                for(int j = 0; j < n; j++){
                    dp[i][j] = Integer.MAX_VALUE;

                    for(int k = 0; k < n; k++){
                        if(k == j) {
                            continue;
                        }

                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + costs[i][j]);
                    }
                }
            }

            int res = Integer.MAX_VALUE;
            for(int i = 0; i < n; i++){
                res = Math.min(res, dp[m - 1][i]);
            }
            return res;
        }
    }

    /**
     * O(n * k) time, O(1) space
     * Optimized from Solution2
     * Same kind of logic as Solution1, just easier to understand.
     *
     * Instead of check all k colors to find the lowest cost, we just keep
     * tracking the lowest and the 2nd lowest.
     */
    class Solution3 {
        public int minCostII(int[][] costs) {
            int m = costs.length;
            if (m == 0) {
                return 0;
            }

            int n = costs[0].length;
            int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
            int min1index = -1;

            /**
             * Init, for first house, find the lowest and the 2nd lowest cost and
             * index of the lowest cost.
             */
            for (int i = 0; i < n; i++) {
                if (costs[0][i] < min1) {
                    min1index = i;
                    min2 = min1;
                    min1 = costs[0][i];
                } else if (costs[0][i] < min2) {
                    min2 = costs[0][i];
                }
            }

            for (int i = 1; i < m; i++) {
                int temp1 = min1;
                int temp2 = min2;

                //color index which has the lowest cost to paint for previous house (i - 1)
                int tempindex = min1index;

                min1 = Integer.MAX_VALUE;
                min2 = Integer.MAX_VALUE;

                for (int j = 0; j < n; j++) {
                    int val = 0;

                    if (j != tempindex) {
                        val = temp1 + costs[i][j];
                    } else {
                        val = temp2 + costs[i][j];
                    }

                    if (val < min1) {
                        min1index = j;
                        min2 = min1;
                        /**
                         * !!!
                         * when we roll forward, min1 and min2 is no longer just the min and 2nd min cost
                         * for house i, it is the accumulated current min cost after we painted house 0 to i,
                         * hence it is val as we calculate.
                         */
                        min1 = val;
                    } else if (val < min2) {
                        /**
                         * !!!
                         */
                        min2 = val;
                    }
                }
            }

            return min1;
        }
    }
}
