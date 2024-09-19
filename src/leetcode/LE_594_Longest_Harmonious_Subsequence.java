package leetcode;

import java.util.*;

public class LE_594_Longest_Harmonious_Subsequence {
    /**
     * We define a harmonious array as an array where the difference between its maximum
     * value and its minimum value is exactly 1.
     *
     * Given an integer array nums, return the length of its longest harmonious subsequence
     * among all its possible subsequences.
     *
     * A subsequence of array is a sequence that can be derived from the array by deleting
     * some or no elements without changing the order of the remaining elements.
     *
     * Example 1:
     *
     * Input: nums = [1,3,2,2,5,2,3,7]
     * Output: 5
     * Explanation: The longest harmonious subsequence is [3,2,2,2,3].
     *
     * Example 2:
     *
     * Input: nums = [1,2,3,4]
     * Output: 2
     *
     * Example 3:
     *
     * Input: nums = [1,1,1,1]
     * Output: 0
     *
     * Constraints:
     * 1 <= nums.length <= 2 * 104
     * -109 <= nums[i] <= 109
     *
     * Easy
     *
     * https://leetcode.com/problems/longest-harmonious-subsequence
     */

    /**
     * !!! it looks for subsequence, not subarray!
     */

    /**
     * O(N)
     * we need to consider the existence of both key+1 and key−1 exclusively and determine
     * the counts corresponding to both the cases. This is needed now because it could be
     * possible that key has already been added to the map and later on key−1 is encountered.
     * In this case, if we consider the presence of key+1 only, we'll go in the wrong direction.
     */
    public class Solution {
        public int findLHS(int[] nums) {
            HashMap < Integer, Integer > map = new HashMap < > ();
            int res = 0;

            for (int num: nums) {
                map.put(num, map.getOrDefault(num, 0) + 1);
                if (map.containsKey(num + 1))
                    res = Math.max(res, map.get(num) + map.get(num + 1));
                if (map.containsKey(num - 1))
                    res = Math.max(res, map.get(num) + map.get(num - 1));
            }
            return res;
        }
    }
}
