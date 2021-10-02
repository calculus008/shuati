package leetcode;

import java.util.*;

public class LE_962_Maximum_Width_Ramp {
    /**
     * A ramp in an integer array nums is a pair (i, j) for which i < j and nums[i] <= nums[j]. The width of such a ramp is j - i.
     *
     * Given an integer array nums, return the maximum width of a ramp in nums. If there is no ramp in nums, return 0.
     *
     * Example 1:
     * Input: nums = [6,0,8,2,1,5]
     * Output: 4
     * Explanation: The maximum width ramp is achieved at (i, j) = (1, 5): nums[1] = 0 and nums[5] = 5.
     *
     * Example 2:
     * Input: nums = [9,8,1,0,1,9,4,0,4,1]
     * Output: 7
     * Explanation: The maximum width ramp is achieved at (i, j) = (2, 9): nums[2] = 1 and nums[9] = 1.
     *
     * Constraints:
     * 2 <= nums.length <= 5 * 104
     * 0 <= nums[i] <= 5 * 104
     *
     * Medium
     *
     * https://leetcode.com/problems/maximum-width-ramp/
     */

    /**
     * Mono Stack
     *
     * A max-width ramp should have its smaller element appear as "early" in A and as small as possible. All elements
     * larger than A[0] are not possible to be the smaller element in the max-width ramp, hence they shouldn't be
     * considered as the candidate smaller element. The same applies to arbitrary A'[0], where A' is a sublist A[i:]
     * in which A[i] < A[0]; constructing a stack of candidate smaller elements in max-width ramp, which includes A[0]
     * and all later and smaller elements, becomes natural. Otherwise, we must consider those uninteresting elements
     * as candidate smaller element in max-width ramp and compare them, wasting a great deal of time.
     *
     * The mono stack is used to store all candidate start points.And by comparing elements (starting from the end of
     * the array) with those candidates, we can eliminate points that are not bigger than the candidate.
     *
     * Use Mono Stack to get min length:
     * LE_862_Shortest_Subarray_With_Sum_At_Least_K
     *
     * Same type problem:
     * LE_1124_Longest_Well_Performing_Interval
     *
     * Time  : O(n)
     * Space : O(n)
     */
    class Solution {
        public int maxWidthRamp(int[] nums) {
            int n = nums.length;

            Deque<Integer> stack = new LinkedList<>();

            for (int i = 0; i < n; i++) {
                if (stack.isEmpty() || nums[i] < nums[stack.peek()]) {
                    stack.push(i);
                }
            }

            int res = 0;
            for (int i = n - 1; i >= 0; i--) {
                while (!stack.isEmpty() && nums[i] >= nums[stack.peek()]) {
                    res = Math.max(res, i - stack.pop());
                }
            }

            return res;
        }
    }

}
