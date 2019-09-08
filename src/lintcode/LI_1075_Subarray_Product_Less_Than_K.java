package lintcode;

public class LI_1075_Subarray_Product_Less_Than_K {
    /**
     * Your are given an array of positive integers nums.
     *
     * Count and print the number of (contiguous) subarrays where
     * the product of all the elements in the subarray is less than k.
     *
     * 0 < nums.length <= 50000.
     * 0 < nums[i] < 1000.
     * 0 <= k < 10^6.
     *
     * Example 1:
     * 	Input:  nums = [10, 5, 2, 6], k = 100
     * 	Output:  8
     *
     * 	Explanation:
     * 	The 8 subarrays that have product less than 100 are:
     * 	[10], [5], [2], [6], [10, 5], [5, 2], [2, 6], [5, 2, 6].
     * 	Note that [10, 5, 2] is not included as the product of 100 is not strictly less than k.
     *
     *
     * Example 2:
     * 	Input: nums = [5,10,2], k = 10
     * 	Output:  2
     *
     * 	Explanation:
     * 	Only [5] and [2].
     *
     * 	Medium
     */

    /**
     * 维护一个滑动窗口，left为窗口的左端点，i用来探索下一个数，left和i组成的滑动窗口为[left, i]
     * 如果当前窗口中的所有数的乘积 >= k， 说明窗口不再满足条件( < k), 则把left指向的左端点的数从窗口中去掉，
     * 反映在窗口乘积上应该是除以要删除的这个数，然后left++，一直重复下去直到窗口再次满足条件，则又找到了一个新的窗口，
     * 窗口的长度就是当前窗口中满足条件的子数组个数，
     *
     * 窗口长度用 i - left + 1来表示。
     *
     * 时间复杂度为O(n)
     */

    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 1) {
            return 0;
        }

        int left = 0, product = 1, res = 0;
        for (int i = 0; i < nums.length; i++) {
            product *= nums[i];
            while (product >= k) {
                product /= nums[left++];
            }
            res += i - left + 1;
        }
        return res;
    }

    public int numSubarrayProductLessThanK_myversion(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) return 0;

        int p = 1;
        int res = 0;

        for (int i = 0, j = 0; i < nums.length; i++) {
            p *= nums[i];

            while (p >= k) {
                p /= nums[j];
                j++;
            }

            res += i - j + 1;
        }

        return res;
    }
}
