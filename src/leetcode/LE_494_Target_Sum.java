package leetcode;

/**
 * Created by yuank on 11/26/18.
 */
public class LE_494_Target_Sum {
    /**
         You are given a list of non-negative integers, a1, a2, ..., an, and a target, S.
         Now you have 2 symbols + and -. For each integer, you should choose one from + and - as its new symbol.

         Find out how many ways to assign symbols to make sum of integers equal to target S.

         Example 1:
         Input: nums is [1, 1, 1, 1, 1], S is 3.
         Output: 5
         Explanation:

         -1+1+1+1+1 = 3
         +1-1+1+1+1 = 3
         +1+1-1+1+1 = 3
         +1+1+1-1+1 = 3
         +1+1+1+1-1 = 3

         There are 5 ways to assign symbols to make the sum of nums be target 3.

         Note:
         The length of the given array is positive and will not exceed 20.
         The sum of elements in the given array will not exceed 1000.(!!!)
         Your output answer is guaranteed to be fitted in a 32-bit integer.

         Medium
     */

    /**
     *http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-494-target-sum/
     */

    /**
     * Solution 1
     * Brutal Force, DFS
     * Time  : O(2 ^ n)
     * Space : O(n)
     * 681 ms
     */
    class Solution1 {
        int res = 0;
        public int findTargetSumWays(int[] nums, int S) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }

            /**
             * !!! 都是正整数
             */
            if (sum < S) {
                return 0;
            }

            dfs(nums, S, 0);

            return res;
        }

        private void dfs(int[] nums, int s, int pos) {
            if (pos == nums.length) {
                if (s == 0) {
                    res++;
                }
                return;
            }

            dfs(nums, s + nums[pos], pos + 1);
            dfs(nums, s - nums[pos], pos + 1);
        }
    }

    /**
     * DP
     * dp[i][j] : ways to achieve sum value j using the first i elements
     * init : dp[0][0] = 1;
     * Transition : dp[i][j] = dp[i - 1][j + nums[i]] + dp[i - 1][j - nums[i]]
     * Answer : dp[n][S];
     */
    class Solution2 {
        int res = 0;

        /**
         * Time  : O(n * sum)
         * Space : O(sum)
         * 12 ms
         */
        public int findTargetSumWays1(int[] nums, int S) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }

            if (sum < S) {
                return 0;
            }

            int n = nums.length;
            /**
             * j range is : - sum ~ sum. But index can not be negative number.
             * therefore, we have a offset, its value is sum. So dp[0][0] is
             * translated into dp[0][offset]
             */
            int[][] dp = new int[n + 1][2 * sum + 1];
            int offset = sum;
            dp[0][offset] = 1;//!!!

            for (int i = 0; i < n; i++) {
                for (int j = nums[i]; j < 2 * sum + 1 - nums[i]; j++) {
                    if (dp[i][j] > 0) {
                        dp[i + 1][j + nums[i]] += dp[i][j];
                        dp[i + 1][j - nums[i]] += dp[i][j];
                    }
                }
            }

            return dp[n][S + offset];
        }
    }

    /**
     * Space optimized with rolling arrays
     * Time  : O(n * sum)
     * Space : O(sum)
     * 11 ms
     */
    public int findTargetSumWays2(int[] nums, int S) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        if (sum < S) {
            return 0;
        }

        int n = nums.length;
        int[] dp = new int[2 * sum + 1];
        int offset = sum;
        dp[offset] = 1;

        for (int i = 0; i < n; i++) {
            int[] temp = new int[2 * sum + 1];//!!!

            for (int j = nums[i]; j < 2 * sum + 1 - nums[i]; j++) {
                if (dp[j] > 0) {
                    temp[j + nums[i]] += dp[j];
                    temp[j - nums[i]] += dp[j];
                }
            }

            dp = temp;
        }

        return dp[S + offset];
    }

    /**
     * Convert to 0-1 knapsack
     * Fastest solution, only need to iterate range 0 - sum, compared with -sum ~ sum in Solution 2
     */
    class Solution3 {
        int res = 0;
        public int findTargetSumWays(int[] nums, int S) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }

            if (sum < S || (S + sum) % 2 != 0) {
                return 0;
            }

            int target = (S + sum) / 2;
            int[] dp = new int[target + 1];
            dp[0]= 1;

            for (int num : nums) {
                for (int j = target; j >= num; j--) {
                    dp[j] += dp[j - num];
                }
            }

            return dp[target];
        }
    }
}
