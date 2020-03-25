package leetcode;

public class LE_1049_Last_Stone_Weight_II {
    /**
     * We have a collection of rocks, each rock has a positive integer weight.
     *
     * Each turn, we choose any two rocks and smash them together.  Suppose the
     * stones have weights x and y with x <= y.  The result of this smash is:
     *
     * If x == y, both stones are totally destroyed;
     * If x != y, the stone of weight x is totally destroyed, and the stone
     * of weight y has new weight y-x.
     * At the end, there is at most 1 stone left.  Return the smallest possible
     * weight of this stone (the weight is 0 if there are no stones left.)
     *
     * Example 1:
     * Input: [2,7,4,1,8,1]
     * Output: 1
     * Explanation:
     * We can combine 2 and 4 to get 2 so the array converts to [2,7,1,8,1] then,
     * we can combine 7 and 8 to get 1 so the array converts to [2,1,1,1] then,
     * we can combine 2 and 1 to get 1 so the array converts to [1,1,1] then,
     * we can combine 1 and 1 to get 0 so the array converts to [1] then that's the optimal value.
     *
     * Note:
     * 1 <= stones.length <= 30
     * 1 <= stones[i] <= 100
     *
     * Medium
     */

    /**
     * DP Knapsack
     *
     * This question eaquals to partition an array into 2 subsets whose difference is minimal
     * (1) S1 + S2  = S
     * (2) S1 - S2 = diff
     *
     * ==> -> diff = S - 2 * S2  ==> minimize diff equals to  maximize S2
     *
     * Now we should find the maximum of S2 , range from 0 to S / 2, using dp can solve this
     *
     * Very classic knapsack problem solved by DP.
     * In this solution, I use dp to record the achievable sum of the smaller group.
     * dp[x] = 1 means the sum x is possible.
     *
     * Time  : O(NS)
     * Space : O(S), where S = sum(A).
     */

    public int lastStoneWeightII(int[] A) {
        /**
         * 1 <= stones.length <= 30
         * 1 <= stones[i] <= 100
         *
         * So the max sum of A is 3000, sum / 2 = 1500
         */
        boolean[] dp = new boolean[1501];
        dp[0] = true;
        int sumA = 0;

        for (int a : A) {
            sumA += a;

            for (int i = Math.min(1500, sumA); i >= a; i--) {
                dp[i] |= dp[i - a];
            }
        }

        for (int i = sumA / 2; i > 0; --i) {
            if (dp[i]) {
                /**
                 * dp[i] is true, means i is an achiveable value for sumA.
                 * i  : the max sum of the S2.
                 * sumA - i  : S1
                 *
                 * S1 - S2 = sumA - i - i
                 */
                return sumA - i - i;
            }
        }

        return 0;
    }

    class Solution_Practice {
        public int lastStoneWeightII(int[] stones) {
            int sum = 0;
            boolean[] dp = new boolean[1501];
            dp[0] = true;

            for (int stone : stones) {
                sum += stone;

                for (int i = Math.min(1500, sum); i >= stone; i--) {
                    dp[i] |= dp[i - stone];
                }
            }

            for (int i = sum / 2; i > 0; i--) {
                if (dp[i]) {
                    return sum - i - i;
                }
            }

            return 0;
        }
    }
}
