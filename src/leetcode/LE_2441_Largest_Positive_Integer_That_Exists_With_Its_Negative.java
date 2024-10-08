package leetcode;

import java.util.*;

public class LE_2441_Largest_Positive_Integer_That_Exists_With_Its_Negative {
    /**
     * Given an integer array nums that does not contain any zeros, find the largest positive integer k such that -k also exists in the array.
     *
     * Return the positive integer k. If there is no such integer, return -1.
     *
     *
     * Example 1:
     * Input: nums = [-1,2,-3,3]
     * Output: 3
     * Explanation: 3 is the only valid k we can find in the array.
     *
     * Example 2:
     * Input: nums = [-1,10,6,7,-7,1]
     * Output: 7
     * Explanation: Both 1 and 7 have their corresponding negative values in the array. 7 has a larger value.
     *
     * Example 3:
     * Input: nums = [-10,8,6,7,-2,-3]
     * Output: -1
     * Explanation: There is no a single valid k, we return -1.
     *
     *
     * Constraints:
     * 1 <= nums.length <= 1000
     * -1000 <= nums[i] <= 1000
     * nums[i] != 0
     *
     * Easy
     *
     * https://leetcode.com/problems/largest-positive-integer-that-exists-with-its-negative
     */

    /**
     * Hash Set
     *
     * Time and Space: O(n)
     */
    class Solution_hashset {
        public int findMaxK(int[] nums) {
            int res = -1;
            Set<Integer> set = new HashSet<>();

            for (int num : nums) {
                set.add(num);
                if (set.contains(-num)) {
                    res = Math.max(res, Math.abs(num));
                }
            }
            return res;
        }
    }

    /**
     * Two Pointers
     *
     * Time: O(nlogn + n)
     * Space: O(1)
     */
    class Solution_Two_Pointers {
        public int findMaxK(int[] nums) {
            int res = -1;

            Arrays.sort(nums);
            int i = 0;
            int j = nums.length - 1;

            while (i < j) {
                int sum = nums[i] + nums[j];
                if (sum == 0) {
                    res = Math.max(res, Math.abs(nums[i]));
                    i++;
                    j--;
                } else if (sum > 0) {
                    j--;
                } else {
                    i++;
                }
            }

            return res;
        }
    }
}
