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
     * Solution 3
     * DP bottom-up
     */
    public int combinationSum4_3(int[] nums, int target) {
        int[] comb = new int[target + 1];
        comb[0] = 1;
        for (int i = 1; i < comb.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i >= nums[j]) {
                    comb[i] += comb[i - nums[j]];
                }
            }
        }
        return comb[target];
    }


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
}
