package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 10/28/18.
 */
public class LE_377_Combination_Sum_IV {
    /**
         Given an integer array with all positive numbers and no duplicates,
         find the number of possible combinations that add up to a positive integer target.

         Example:

         nums = [1, 2, 3]
         target = 4

         The possible combination ways are:
         (1, 1, 1, 1)
         (1, 1, 2)
         (1, 2, 1)
         (1, 3)
         (2, 1, 1)
         (2, 2)
         (3, 1)

         Note that different sequences are counted as different combinations.

         (For different sequences are counted as the same combinations, check :
          LI_562_Backpack_IV )

         Therefore the output is 7.
         Follow up:
         What if negative numbers are allowed in the given array?
         How does it change the problem?
         What limitation we need to add to the question to allow negative numbers?

         Medium
     */

    /**
     * This is different from LE_518_Coin_Change_II
     *
     * Here we want number of combinations, in example above,
     * {1, 1, 2} and {1, 2, 1} count as different combination.
     * But for LE_518_Coin_Change_II, they will be counted as
     * one way.
     *
     * 如果写成了完全背包，这样的写法不能区分数字一样顺序不同的那些结果
     */

    /**
     * https://leetcode.com/problems/combination-sum-iv/discuss/85036/1ms-Java-DP-Solution-with-Detailed-Explanation
     *
     * Solution 1
     * Recursion
     */
    public int combinationSum4_1(int[] nums, int target) {
        if (target == 0) {
            return 1; //!!!
        }
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (target >= nums[i]) {
                res += combinationSum4_1(nums, target - nums[i]);
            }
        }
        return res;
    }

    /**
     * Solution 2
     * DP top-down
     */
    private int[] dp;

    public int combinationSum4(int[] nums, int target) {
        dp = new int[target + 1];
        Arrays.fill(dp, -1);
        dp[0] = 1;
        return helper(nums, target);
    }

    private int helper(int[] nums, int target) {
        if (dp[target] != -1) {
            return dp[target];
        }
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (target >= nums[i]) {
                res += helper(nums, target - nums[i]);
            }
        }
        dp[target] = res;
        return res;
    }


    /**
     * Time : O(target * |nums\)
     *
     * dp[1] += dp[1 - 1]=1
     * dp[2] += dp[2 - 1]=1
     * dp[2] += dp[2 - 2]=2
     * dp[3] += dp[3 - 1]=2
     * dp[3] += dp[3 - 2]=3
     * dp[3] += dp[3 - 3]=4
     * dp[4] += dp[4 - 1]=4
     * dp[4] += dp[4 - 2]=6
     * dp[4] += dp[4 - 3]=7
     */
    public int backPackVI(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;

        for (int i = 1; i <= target; i++) {
            for (int num : nums) {
                if (i >= num) {
                    dp[i] += dp[i - num];
                }
            }
        }

        return dp[target];
    }

    /**
     * As comparison, here is the solution for LE_518_Coin_Change_II
     *
     * dp[1] += dp[1 - 1]=1
     * dp[2] += dp[2 - 1]=1
     * dp[3] += dp[3 - 1]=1
     * dp[4] += dp[4 - 1]=1
     * dp[5] += dp[5 - 1]=1
     * dp[2] += dp[2 - 2]=2
     * dp[3] += dp[3 - 2]=2
     * dp[4] += dp[4 - 2]=3
     * dp[5] += dp[5 - 2]=3
     * dp[5] += dp[5 - 5]=4
     */
    public int change(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;

        for (int num : nums) {
            for(int i = num; i <= target; i++) {
                dp[i] += dp[i - num];
            }
        }

        return dp[target];
    }
}
