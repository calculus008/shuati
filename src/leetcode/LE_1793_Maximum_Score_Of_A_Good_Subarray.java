package leetcode;

public class LE_1793_Maximum_Score_Of_A_Good_Subarray {
    /**
     * You are given an array of integers nums (0-indexed) and an integer k.
     *
     * The score of a subarray (i, j) is defined as min(nums[i], nums[i+1], ..., nums[j]) * (j - i + 1).
     * A good subarray is a subarray where i <= k <= j.
     *
     * Return the maximum possible score of a good subarray.
     *
     * Example 1:
     * Input: nums = [1,4,3,7,4,5], k = 3
     * Output: 15
     * Explanation: The optimal subarray is (1, 5) with a score of min(4,3,7,4,5) * (5-1+1) = 3 * 5 = 15.
     *
     * Example 2:
     * Input: nums = [5,5,4,5,4,1,1,1], k = 0
     * Output: 20
     * Explanation: The optimal subarray is (0, 4) with a score of min(5,5,4,5,4) * (4-0+1) = 4 * 5 = 20.
     *
     * Constraints:
     * 1 <= nums.length <= 105
     * 1 <= nums[i] <= 2 * 104
     * 0 <= k < nums.length
     *
     * Hard
     */

    /**
     * Two Pointers
     * We start with i = j = k, the score = A[k].
     * When increment the size of window,
     * we want to reduce the min(A[i]..A[j]) slowly.
     *
     * To do this, we can check the values on both sides of the window.
     * If A[i - 1] < A[j + 1], we do j = j + 1
     * If A[i - 1] >= A[j + 1], we do i = i - 1
     *
     * During this process,
     * there is sense that we reduce min(A[i]..A[j]) step by step.
     *
     *
     * Complexity
     * Time O(n)
     * Space O(1)
     */
    class Solution {
        public int maximumScore(int[] nums, int k) {
            int i = k, j = k;
            int n = nums.length;

            /**
             * !!!
             * MUST set res ad nums[k], for edge case : nums is [5], k is 0.
             */
            int res = nums[k];
            int min = nums[k];

            while (i > 0 || j < n - 1) {
                if (i == 0) {
                    j++;
                } else if (j == n - 1) {
                    i--;
                } else if (nums[i - 1] < nums[j + 1]) {
                    j ++;
                } else {
                    i--;
                }

                min = Math.min(min, Math.min(nums[i], nums[j]));
                res = Math.max(res, (j - i + 1) * min);
            }

            return res;
        }
    }
}
