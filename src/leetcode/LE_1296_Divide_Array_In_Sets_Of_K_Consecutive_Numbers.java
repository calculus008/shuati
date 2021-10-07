package leetcode;

import java.util.*;

public class LE_1296_Divide_Array_In_Sets_Of_K_Consecutive_Numbers {
    /**
     * Given an array of integers nums and a positive integer k, find whether it is possible to divide this array into
     * sets of k consecutive numbers.
     *
     * Return true if it is possible. Otherwise, return false.
     *
     * Example 1:
     * Input: nums = [1,2,3,3,4,4,5,6], k = 4
     * Output: true
     * Explanation: Array can be divided into [1,2,3,4] and [3,4,5,6].
     *
     * Example 2:
     * Input: nums = [3,2,1,2,3,4,3,4,5,9,10,11], k = 3
     * Output: true
     * Explanation: Array can be divided into [1,2,3] , [2,3,4] , [3,4,5] and [9,10,11].
     *
     * Example 3:
     * Input: nums = [3,3,2,2,1,1], k = 3
     * Output: true
     *
     * Example 4:
     * Input: nums = [1,2,3,4], k = 3
     * Output: false
     * Explanation: Each array should be divided in subarrays of size 3.
     *
     * Constraints:
     * 1 <= k <= nums.length <= 105
     * 1 <= nums[i] <= 109
     *
     * Note: This question is the same as 846: https://leetcode.com/problems/hand-of-straights/
     *       LE_846_Hand_Of_Straights
     *
     * https://leetcode.com/problems/divide-array-in-sets-of-k-consecutive-numbers/
     */

    class Solution {
        public boolean isPossibleDivide(int[] nums, int k) {
            TreeMap<Integer, Integer> count = new TreeMap<>();

            for (int n : nums) {
                count.put(n, count.getOrDefault(n, 0) + 1);
            }

            while (count.size() != 0) {
                int key = count.firstKey();

                for (int i = key; i < key + k; i++) {
                    if (!count.containsKey(i)) {
                        return false;
                    }

                    int val = count.get(i);
                    if (val == 1) {
                        count.remove(i);
                    } else {
                        count.put(i, val - 1);
                    }
                }
            }

            return true;
        }
    }
}
