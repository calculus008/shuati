package leetcode;

import java.util.*;

public class LE_2962_Count_Subarrays_Where_Max_Element_Appears_At_Least_K_Times {
    /**
     * You are given an integer array nums and a positive integer k.
     *
     * Return the number of subarrays where the maximum element of nums appears at least k times in that subarray.
     *
     * A subarray is a contiguous sequence of elements within an array.
     *
     * Example 1:
     * Input: nums = [1,3,2,3,3], k = 2
     * Output: 6
     * Explanation: The subarrays that contain the element 3 at least 2 times are: [1,3,2,3], [1,3,2,3,3], [3,2,3], [3,2,3,3], [2,3,3] and [3,3].
     *
     * Example 2:
     * Input: nums = [1,4,2,1], k = 3
     * Output: 0
     * Explanation: No subarray contains the element 4 at least 3 times.
     *
     * Constraints:
     *
     * 1 <= nums.length <= 105
     * 1 <= nums[i] <= 106
     * 1 <= k <= 105
     *
     * Medium
     *
     * https://leetcode.com/problems/count-subarrays-where-max-element-appears-at-least-k-times
     */

    // sliding window, Time O(n), Space O(1)
    class Solution {
        public long countSubarrays(int[] nums, int k) {
            int n = nums.length;
            if (n < k) return 0;

            int max = 0;
            long res = 0;

            for (int num : nums) {
                max = Math.max(max, num);
            }

            int count = 0;
            for (int i = 0, j = 0; i < n; i++) {
                if (nums[i] == max) count++;

                while (count >= k) {
                    res += (n - 1) - i + 1; //!!! if current window has more than k max number, then the subarray from right index i to end of array also have at least k max number, so add it to res.
                    if (nums[j] == max) count--;
                    j++;
                }
            }

            return res;
        }
    }
}
