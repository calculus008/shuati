package leetcode;

public class LE_1155_Number_Of_Dice_Rolls_With_Target_Sum {
    /**
     * You have d dice, and each die has f faces numbered 1, 2, ..., f.
     *
     * Return the number of possible ways (out of fd total ways) modulo 10^9 + 7
     * to roll the dice so the sum of the face up numbers equals target.
     *
     *
     *
     * Example 1:
     * Input: d = 1, f = 6, target = 3
     * Output: 1
     * Explanation:
     * You throw one die with 6 faces.  There is only one way to get a sum of 3.
     *
     * Example 2:
     * Input: d = 2, f = 6, target = 7
     * Output: 6
     * Explanation:
     * You throw two dice, each with 6 faces.  There are 6 ways to get a sum of 7:
     * 1+6, 2+5, 3+4, 4+3, 5+2, 6+1.
     *
     * Example 3:
     * Input: d = 2, f = 5, target = 10
     * Output: 1
     * Explanation:
     * You throw two dice, each with 5 faces.  There is only one way to get a sum of 10: 5+5.
     *
     * Example 4:
     * Input: d = 1, f = 2, target = 3
     * Output: 0
     * Explanation:
     * You throw one die with 2 faces.  There is no way to get a sum of 3.
     *
     * Example 5:
     * Input: d = 30, f = 30, target = 500
     * Output: 222616187
     * Explanation:
     * The answer must be returned modulo 10^9 + 7.
     *
     *
     * Constraints:
     *
     * 1 <= d, f <= 30
     * 1 <= target <= 1000
     *
     * Medium
     */

    /**
     * This problem is like LE_518_Coin_Change_II, with the difference that the total number of coins (dices) should be equal to d
     * 是 LE_518_Coin_Change_II 升级版。
     *
     * Top-Down DP
     *
     * dp[i][j] : total number of ways to sum to value j with the first i dices.
     *
     * Transition:
     * For 518, "dp[i][j] = sum of ways of using ith coins and NOT using ith coins", use or not use, two possible values.
     * Here we use dice, which has f different values, therefore:
     *   dp[i][j] += dp[i - 1][j - k], for each k which satisfies j - k >= 0 and k <= f
     *   这个就是说， 当前可以用第i个dice, 取值为k，凑成值j.
     *
     * Here, 0 is not an option for k
     *
     * Time  : O(d * f * target).
     * Space : O(d * target), for the memoisation.
     */
    class Solution1 {
        public int numRollsToTarget(int d, int f, int target) {
            if (d <= 0 || target < d || target > d * f) return 0;

            return  dfs(d, f, target, new int[d + 1][target + 1]);
        }

        private int dfs(int d, int f, int target, int[][] mem) {
            if (d == 0) {
                if (target == 0) {
                    return 1;
                }
                return 0;
            }

            /**
             * !!!
             * Very important pruning, otherwise it will TLE
             */
            if (target > d * f || target < d) return 0;

            if (mem[d][target] != 0) return mem[d][target];

            int res = 0;
            for (int i = 1; i <= f; i++) {
                res = (res + dfs(d - 1, f, target - i, mem)) % 1000000007;
            }

            mem[d][target] = res;

            return res;
        }
    }

    /**
     * Bottom up DP
     */
    class Solution2 {
        public int numRollsToTarget(int d, int f, int target) {
            if (d <= 0 || target < d || target > d * f) return 0;

            int[][] dp = new int[d + 1][target + 1];

            dp[0][0] = 1;

            for (int i = 1; i <= d; i++) {
                for (int j = 1; j <= target; j++) {
                    /**
                     * !!!
                     */
                    if (j > i * f || j < i) continue;

                    for (int k = 1; k <= f && j - k >= 0; k++) {
                        dp[i][j] = (dp[i][j] + dp[i - 1][j - k]) % 1000000007;
                    }
                }
            }

            return dp[d][target];
        }
    }

    /**
     * Naive DFS, TLE
     *
     * Time  : O(d ^ f)
     * Space : O(d)
     */
    class Solution3 {
        public int numRollsToTarget(int d, int f, int target) {
            if (d <= 0 || target < d || target > d * f) return 0;

            return  dfs(d, f, target);
        }

        private int dfs(int d, int f, int target) {
            if (d == 0) {
                if (target == 0) {
                    return 1;
                }
                return 0;
            }

            int res = 0;
            for (int i = 1; i <= f; i++) {
                res = (res + dfs(d - 1, f, target - i)) % 1000000007;
            }

            return res;
        }
    }
}
