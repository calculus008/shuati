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
     *
     * dp[i][j] : sub problem answer for the first j elements and they are divided into i groups
     *
     * [0, ....., k, k + 1, ...., n - 1]
     *  |_________|    |____________|
     *    sub problem    sum(a[k + 1]..a[n - 1])
     *  split a[0] to a[k]    |
     *  into m -  groups      |
     *       |________________|
     *              |
     *             max()
     *              |
     *             min()
     *
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

            for (int i = 2; i <= m; i++) {//elements
                for (int j = i; j <= n; j++) {//groups
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

    /**
     * Binary Search
     * Use huahua's binary search template
     *
     * Time : O(n * log(max))
     * Space : O(1)
     *
     * One important detail, all variables involves num range should be long type to prevent int value overflow.
     * "left", "right", "target", add conversion when return result.
     *
     * !!!
     * "Given an array which consists of non-negative integers and an integer m"
     * This is a very important given condition, it allows us to use binary search since it guarantees the range
     * of sum is increasing.
     *
     * It we don't have this condition, then we have to use DP in Solution1.
     */
    class Solution3 {
        public int splitArray(int[] nums, int m) {
            if (null == nums || nums.length == 0) return 0;

            long left = 0;
            long right = 0;
            //The range of the sum is between the biggest nums and total sum.
            for (int num : nums) {
                left = Math.max(num, left);
                right += num;
            }

            while (left < right) {
                long mid = left + (right - left) / 2;

                if (isBigger(nums, mid, m)) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            /**
             * conversion!!!
             */
            return (int)left;
        }

        //The target here is the possible sum of each subarray. If we could form m+ subarrays with sum > target,
        // means the actual min sum is greater than this target. Therefore, the left point should adjust.
        private boolean isBigger(int[] nums, long target, int m) {
            long sum = 0;//!!!
            int count = 1;
            for (int num : nums) {
                if (sum + num > target) {
                    count++;
                    sum = num; //!!! This makes sure the next subarray starts from the current num.

                    if (count > m) {
                        return true;
                    }
                } else {
                    sum += num;
                }
            }

            return false;
        }
    }

    /**
     * Binary Search
     * Same format as in LE_1231_Divide_Chocolate
     */
    class Solution4 {
        public int splitArray(int[] nums, int m) {
            int l = Integer.MIN_VALUE;
            int r = 0;

            for (int num : nums) {
                l = Math.max(l, num);
                r += num;
            }

            int res = 0;
            while (l <= r) {
                int mid = l + (r - l) / 2;
                if (isValid(nums, m, mid)) {
                    /**
                     * !!! min(), used for find group sum
                     */
                    res = Math.min(res, mid);
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            return res;

            //This binary search form also works
//            while (l < r) {
//                int mid = l + (r - l) / 2;
//                if (isValid(nums, m, mid)) {
//                    l = mid + 1;
//                } else {
//                    r = mid;
//                }
//            }
//            return l;
        }

        private boolean isValid(int[] nums, int m, int mid) {
            int count = 0;
            int sum = 0;

            for (int num : nums) {
                /**
                 * count current number to the next group when overshoot
                 */
                if (sum + num <= mid) {
                    sum += num;
                } else {
                    count++;
                    sum = num;

                    if (count >= m) return true;
                }
            }

            return false;
        }
    }
}
