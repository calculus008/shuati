package leetcode;

public class LE_1658_Minimum_Operations_To_Reduce_X_To_Zero {
    /**
     * You are given an integer array nums and an integer x. In one operation, you can either remove the
     * leftmost or the rightmost element from the array nums and subtract its value from x. Note that this
     * modifies the array for future operations.
     *
     * Return the minimum number of operations to reduce x to exactly 0 if it is possible, otherwise, return -1.
     *
     *
     * Example 1
     * Input: nums = [1,1,4,2,3], x = 5
     * Output: 2
     * Explanation: The optimal solution is to remove the last two elements to reduce x to zero.
     *
     * Example 2:
     * Input: nums = [5,6,7,8,9], x = 4
     * Output: -1
     *
     * Example 3:
     * Input: nums = [3,2,20,1,1,3], x = 10
     * Output: 5
     * Explanation: The optimal solution is to remove the last three elements and the first two elements (5 operations in total) to reduce x to zero.
     *
     *
     * Constraints:
     * 1 <= nums.length <= 105
     * 1 <= nums[i] <= 104
     * 1 <= x <= 109
     *
     * Medium
     *
     * https://leetcode.com/problems/minimum-operations-to-reduce-x-to-zero
     */

    // Two Pointers
    class Solution {
        public int minOperations(int[] nums, int x) {
            int sum = 0; //sum as the sum of the left part from left and the right part from right, not including left and right
            for (int n : nums) {
                sum += n;
            }

            int min = Integer.MAX_VALUE;
            int n = nums.length;

            for (int i = 0, j = 0; i < n; i++) {
                sum -= nums[i];

                while (sum < x && j <= i) {
                    sum += nums[j];
                    j++;
                }

                if (sum == x) {
                    min = Math.min(min, (n - 1 - i) + j);
                }
            }

            return min == Integer.MAX_VALUE ? -1 : min;
        }
    }
}
