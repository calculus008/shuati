package leetcode;

import java.util.HashMap;
import java.util.Map;

public class LE_659_Split_Array_Into_Consecutive_Subsequences {
    /**
     * Given an array nums sorted in ascending order, return true if and only if you
     * can split it into 1 or more subsequences such that each subsequence consists
     * of consecutive integers and has length at least 3.
     *
     *
     * Example 1:
     *
     * Input: [1,2,3,3,4,5]
     * Output: True
     * Explanation:
     * You can split them into two consecutive subsequences :
     * 1, 2, 3
     * 3, 4, 5
     *
     * Example 2:
     *
     * Input: [1,2,3,3,4,4,5,5]
     * Output: True
     * Explanation:
     * You can split them into two consecutive subsequences :
     * 1, 2, 3, 4, 5
     * 3, 4, 5
     *
     * Example 3:
     *
     * Input: [1,2,3,4,4,5]
     * Output: False
     *
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10000
     *
     * Medium
     */

    /**
     * https://leetcode.com/problems/split-array-into-consecutive-subsequences/discuss/106496/Java-O(n)-Time-O(n)-Space
     */
    class Solution {
        public boolean isPossible(int[] nums) {
            Map<Integer, Integer> freq = new HashMap<>();
            Map<Integer, Integer> next = new HashMap<>();

            for (int num : nums) {
                freq.put(num, freq.getOrDefault(num, 0) + 1);
            }

            for (int num : nums) {
                if (freq.get(num) == 0) {
                    continue;
                } else if (next.getOrDefault(num, 0) > 0) {
                    // freq.put(num, freq.get(num) - 1);
                    next.put(num, next.get(num) - 1);
                    next.put(num + 1, next.getOrDefault(num + 1, 0) + 1);
                } else if (freq.getOrDefault(num + 1, 0) > 0 && freq.getOrDefault(num + 2, 0) > 0) {
                    // freq.put(num, freq.get(num) - 1);
                    freq.put(num + 1, freq.get(num + 1) - 1);
                    freq.put(num + 2, freq.get(num + 2) - 1);
                    next.put(num + 3, next.getOrDefault(num + 3, 0) + 1);
                } else {
                    System.out.println(next.getOrDefault(num, 0));
                    return false;
                }

                freq.put(num, freq.get(num) - 1);

            }

            return true;
        }
    }
}
