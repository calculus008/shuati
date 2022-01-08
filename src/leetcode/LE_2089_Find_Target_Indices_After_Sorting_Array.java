package leetcode;

import java.util.*;

public class LE_2089_Find_Target_Indices_After_Sorting_Array {
    /**
     * You are given a 0-indexed integer array nums and a target element target.
     *
     * A target index is an index i such that nums[i] == target.
     *
     * Return a list of the target indices of nums after sorting nums in non-decreasing order. If there are no target indices,
     * return an empty list. The returned list must be sorted in increasing order.
     *
     * Example 1:
     * Input: nums = [1,2,5,2,3], target = 2
     * Output: [1,2]
     * Explanation: After sorting, nums is [1,2,2,3,5].
     * The indices where nums[i] == 2 are 1 and 2.
     *
     * Example 2:
     * Input: nums = [1,2,5,2,3], target = 3
     * Output: [3]
     * Explanation: After sorting, nums is [1,2,2,3,5].
     * The index where nums[i] == 3 is 3.
     *
     * Example 3:
     * Input: nums = [1,2,5,2,3], target = 5
     * Output: [4]
     * Explanation: After sorting, nums is [1,2,2,3,5].
     * The index where nums[i] == 5 is 4.
     *
     * Constraints:
     * 1 <= nums.length <= 100
     * 1 <= nums[i], target <= 100
     *
     * Easy
     *
     * https://leetcode.com/problems/find-target-i1ndices-after-sorting-array/
     */

    /**
     * Improved from Solution2, only one loop and space is O(1)
     *
     * Time  : O(n)
     * Space : O(1)
     */
    class Solution1 {
        public List<Integer> targetIndices(int[] nums, int target) {
            int count = 0, lessthan = 0;
            for (int n : nums) {
                if (n == target) count++;
                if (n < target) lessthan++;
            }

            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                result.add(lessthan++);
            }
            return result;
        }
    }

    /**
     * Time : O(n)
     * Space : O(n)
     */
    class Solution2 {
        public List<Integer> targetIndices(int[] nums, int target) {
            int[] count = new int[100];
            List<Integer> res = new ArrayList<>();
            boolean found = false;

            for (int num : nums) {
                count[num - 1]++;
                if (num == target) found = true;
            }

            if (!found) return res;

            int idx = 0;
            for (int i = 0; i < target - 1; i++) {
                idx += count[i];
            }

            for (int i = 0; i < count[target - 1]; i++) {
                res.add(idx++);
            }

            return res;
        }
    }
}
