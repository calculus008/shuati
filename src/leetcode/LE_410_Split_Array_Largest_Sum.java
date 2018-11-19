package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 11/18/18.
 */
public class LE_410_Split_Array_Largest_Sum {
    /**
         Given an array which consists of non-negative integers and an integer m,
         you can split the array into m non-empty continuous subarrays.
         Write an algorithm to minimize the largest sum among these m subarrays.

         Note:
         If n is the length of array, assume the following constraints are satisfied:

         1 ≤ n ≤ 1000
         1 ≤ m ≤ min(50, n)
         Examples:

         Input:
         nums = [7,2,5,10,8]
         m = 2

         Output:
         18

         Explanation:
         There are four ways to split nums into two subarrays.
         The best way is to split it into [7,2,5] and [10,8],
         where the largest sum among the two subarrays is only 18.

         Hard
     */

    /**
     * http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-410-split-array-largest-sum/
     */

    /**
     * Solution 1
     * DP
     * Similar to LE_813_Largest_Sum_Of_Averages
     * dp[i][j] : sub problem answer for the first j elements and they are divided into i groups
     * Transition - 找分割点:
     *  dp[i][j] = min(max(dp[i - 1][k], sum(k + 1, j)))
     * init:
     *  dp[1][j] = sum(1 ~ j)
     * Anwser : dp[m][n]
     *
     * Time  : O(m * n ^ 2)
     * Space : O(m * n)
     **/
    class Solution1 {
        public int splitArray(int[] nums, int m) {
            int n = nums.length;
            int[][] dp = new int[m + 1][n + 1];

            //!!!
            for (int[] d : dp) {
                Arrays.fill(d, Integer.MAX_VALUE);
            }

            //sums[i] : sum of the first i elements
            int[] sums = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                sums[i] = sums[i - 1] + nums[i - 1];
                dp[1][i] = sums[i];
            }

            for (int i = 2; i <= m; i++) {
                for (int j = i; j <= n; j++) {
                    for (int k = i - 1; k < j; k++) {
                        dp[i][j] = Math.min(dp[i][j], Math.max(dp[i - 1][k], sums[j] - sums[k]));
                    }
                }
            }

            return dp[m][n];
        }
    }

    /**
     * Solution 2
     * Binary Search
     *
     * Time  : O(nlog(sum(nums))
     * Space : O(1);
     *
     */
    class Solution2 {
        public int splitArray(int[] nums, int m) {
            /**
             * left and right is defined as long to prevent overflow
             */
            long left = Long.MIN_VALUE;
            long right = 0;
            for (int num : nums) {
                left = Math.max(num, left);
                right += num;
            }

            while (left + 1 < right) {
                long mid = left + (right - left) / 2;
                /**
                 * group number is bigger than m, then target number is too small
                 */
                if (checkGroup(nums, mid) > m) {
                    left = mid;
                } else {
                    right = mid;
                }
            }

            if (checkGroup(nums, left) <= m) {
                return (int)left;
            }

            return (int)right;
        }

        /**
         * if the smallest sum of the max group is target, how many groups can it make
         */
        private int checkGroup(int[] nums, long target) {
            int groups = 1;
            long sum = 0;
            for (int num : nums) {
                if (sum + num > target) {
                    groups++;
                    sum = num;
                } else {
                    sum += num;
                }
            }
            return groups;
        }
    }
}
