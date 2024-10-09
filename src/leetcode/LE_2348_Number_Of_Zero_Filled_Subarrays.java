package leetcode;

public class LE_2348_Number_Of_Zero_Filled_Subarrays {
    /**
     * Given an integer array nums, return the number of subarrays filled with 0.
     *
     * A subarray is a contiguous non-empty sequence of elements within an array.
     *
     *
     *
     * Example 1:
     *
     * Input: nums = [1,3,0,0,2,0,0,4]
     * Output: 6
     * Explanation:
     * There are 4 occurrences of [0] as a subarray.
     * There are 2 occurrences of [0,0] as a subarray.
     * There is no occurrence of a subarray with a size more than 2 filled with 0. Therefore, we return 6.
     *
     * Example 2:
     *
     * Input: nums = [0,0,0,2,0,0]
     * Output: 9
     * Explanation:
     * There are 5 occurrences of [0] as a subarray.
     * There are 3 occurrences of [0,0] as a subarray.
     * There is 1 occurrence of [0,0,0] as a subarray.
     * There is no occurrence of a subarray with a size more than 3 filled with 0. Therefore, we return 9.
     *
     * Example 3:
     *
     * Input: nums = [2,10,2019]
     * Output: 0
     * Explanation: There is no subarray filled with 0. Therefore, we return 0.
     *
     *
     * Constraints:
     *
     * 1 <= nums.length <= 105
     * -109 <= nums[i] <= 109
     *
     * Medium
     *
     * https://leetcode.com/problems/number-of-zero-filled-subarrays
     */

    class Solution {
        public long zeroFilledSubarray(int[] nums) {
            long ans = 0, numSubarray = 0;

            // Iterate over nums, if num = 0, it has 1 more zero-filled subarray
            // than the previous one, otherwise, it has 0 zero-filled subarray.
            for (int num : nums) {
                if (num == 0) {
                    numSubarray++;
                } else {
                    numSubarray = 0;
                }
                ans += numSubarray;
            }

            return ans;
        }
    }

    /**
     * Sliding Window
     */
    class Solution_2 {
        public long zeroFilledSubarray(int[] nums) {
            long res = 0;
            long sum = 0;

            for (int i = 0, j = 0; i < nums.length; i++) {
                sum += nums[i];

                if (sum == 0) {
                    res += (i - j + 1);
                }

                while (sum != 0) {
                    sum -= nums[j];
                    j++;
                }
            }

            return res;
        }
    }
}
