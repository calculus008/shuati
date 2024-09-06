package leetcode;

public class LE_713_Subarray_Product_Less_Than_K {
    /**
     * Given an array of integers nums and an integer k, return the number of contiguous subarrays where the product
     * of all the elements in the subarray is strictly less than k.
     *
     * Example 1:
     * Input: nums = [10,5,2,6], k = 100
     * Output: 8
     * Explanation: The 8 subarrays that have product less than 100 are:
     * [10], [5], [2], [6], [10, 5], [5, 2], [2, 6], [5, 2, 6]
     * Note that [10, 5, 2] is not included as the product of 100 is not strictly less than k.
     *
     * Example 2:
     * Input: nums = [1,2,3], k = 0
     * Output: 0
     *
     *
     * Constraints:
     *
     * 1 <= nums.length <= 3 * 104
     * 1 <= nums[i] <= 1000
     * 0 <= k <= 106
     *
     * Medium
     *
     * https://leetcode.com/problems/subarray-product-less-than-k
     */

    /**
     * Sliding Window
     * Time : O(n)
     * Space : O(1)
     */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k <= 1) return 0;

        int res = 0;
        int prod = 1;

        for (int i = 0, j = 0; i < nums.length; i++) {
            prod *= nums[i];
            while (j < nums.length && prod >= k) {
                prod /= nums[j];
                j++;
            }
            res += i - j + 1;
        }

        return res;
    }
}
