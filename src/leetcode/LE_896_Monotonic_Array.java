package leetcode;

public class LE_896_Monotonic_Array {
    /**
     * An array is monotonic if it is either monotone increasing or monotone decreasing.
     *
     * An array nums is monotone increasing if for all i <= j, nums[i] <= nums[j]. An array nums is monotone decreasing if for all i <= j, nums[i] >= nums[j].
     *
     * Given an integer array nums, return true if the given array is monotonic, or false otherwise.
     *
     * Example 1:
     * Input: nums = [1,2,2,3]
     * Output: true
     *
     * Example 2:
     * Input: nums = [6,5,4,4]
     * Output: true
     *
     * Example 3:
     * Input: nums = [1,3,2]
     * Output: false
     *
     * Constraints:
     * 1 <= nums.length <= 105
     * -105 <= nums[i] <= 105
     *
     * Easy
     *
     * https://leetcode.com/problems/monotonic-array/
     */

    class Solution1 {
        public boolean isMonotonic(int[] nums) {
            if (nums.length == 1) return true;

            int flag = 0;
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] > nums[i - 1]) {
                    if (flag == 0) {
                        flag = 1;
                    } else if (flag < 0) {
                        return false;
                    }
                } else if (nums[i] < nums[i - 1]) {
                    if (flag == 0) {
                        flag = -1;
                    } else if (flag > 0) {
                        return false;
                    }
                }
            }

            return true;
        }
    }

    class Solution2 {
        public boolean isMonotonic(int[] A) {
            if (A.length == 1) return true;
            int n = A.length;

            /**
             * Check the trend by comparing the first and the last elements
             */
            boolean up = (A[n - 1] - A[0]) > 0;

            for (int i = 0; i < n - 1; i++) {
                if (A[i + 1] != A[i] && (A[i + 1] - A[i] > 0) != up)
                    return false;
            }

            return true;
        }
    }
}
