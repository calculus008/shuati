package leetcode;

/**
 * Created by yuank on 4/11/18.
 */
public class LE_256_Paint_House {
    /**
     There are a row of n houses, each house can be painted with one of the three colors: red, blue or green.
     The cost of painting each house with a certain color is different. You have to paint all the houses such
     that no two adjacent houses have the same color.

     The cost of painting each house with a certain color is represented by a n x 3 cost matrix.
     For example, costs[0][0] is the cost of painting house 0 with color red; costs[1][2] is the cost of painting
     house 1 with color green, and so on... Find the minimum cost to paint all houses.

     Note:
     All costs are positive integers.
     */


    /**
     * DP  Time : O(m), Space : O(m * n)
     */
    class Solution {
        public int minCost(int[][] costs) {
            if (null == costs || costs.length == 0) return 0;

            int m = costs.length;
            int n = costs[0].length;

            /**
             * dp[i][j] : min cost of painting 0 ~ i houses while house i (index) is painted with color j
             */
            int[][] dp = new int[m][n];

            for (int i = 0; i < n; i++) {
                dp[0][i] = costs[0][i];
            }

            for (int i = 1; i < m; i++) {
                dp[i][0] = Math.min(dp[i - 1][1], dp[i - 1][2]) + costs[i][0];
                dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][2]) + costs[i][1];
                dp[i][2] = Math.min(dp[i - 1][0], dp[i - 1][1]) + costs[i][2];
            }

            return Math.min(dp[m - 1][0], Math.min(dp[m - 1][1], dp[m - 1][2]));
        }
    }

    /**
     * DP, optimize space : O(1)
     */
    class Solution2 {
        public int minCost(int[][] costs) {
            if(costs.length==0) return 0;
            int[] pre = new int[3];
            int[] cur = new int[3];

            for(int j=0; j<3; j++)
            {
                pre[j] = costs[0][j];
            }

            for(int i=1; i<costs.length; i++)
            {
                cur[0] = costs[i][0] + Math.min(pre[1], pre[2]);
                cur[1] = costs[i][1] + Math.min(pre[0], pre[2]);
                cur[2] = costs[i][2] + Math.min(pre[0], pre[1]);

                int[] temp = pre;
                pre = cur;
                cur = temp;
            }
            return Math.min(pre[0], Math.min(pre[1], pre[2]));
        }
    }


    /**
     * short but modifying input, not recommended
     */
    public int minCost(int[][] costs) {
        if (costs == null || costs.length == 0) return 0;

        int n = costs.length;
        for (int i = 1; i < costs.length; i++) {
            costs[i][0] += Math.min(costs[i - 1][1], costs[i - 1][2]);
            costs[i][1] += Math.min(costs[i - 1][0], costs[i - 1][2]);
            costs[i][2] += Math.min(costs[i - 1][0], costs[i - 1][1]);
        }

        return Math.min(Math.min(costs[n - 1][0], costs[n - 1][1]), costs[n - 1][2]);
    }
}
