package leetcode;

import java.util.Arrays;

public class LE_462_Minimum_Moves_To_Equal_Array_Elements_II {
    /**
     * Given a non-empty integer array, find the minimum number of moves required to make
     * all array elements equal, where a move is incrementing a selected element by 1 or
     * decrementing a selected element by 1.
     *
     * You may assume the array's length is at most 10,000.
     *
     * Example:
     *
     * Input:
     * [1,2,3]
     *
     * Output:
     * 2
     *
     * Explanation:
     * Only two moves are needed (remember each move increments or decrements one element):
     *
     * [1,2,3]  =>  [2,2,3]  =>  [2,2,2]
     */

    /**
     * we sort the nums array, assume all nums will finally be modified to some value C in
     * the middle of the array, so we need to sum the diff of all nums larger than C, and
     * all nums less than C, so it transforms to:
     *
     * nums[n]-C+nums[n-1]-C....+(C-nums[mid-1]+C-nums[mid-2]+...C-nums[0])
     * =nums[n]-nums[0]+nums[n-1]-nums[1]+....
     */
    class Solution {
        public int minMoves2(int[] nums) {
            Arrays.sort(nums);

            int i = 0;
            int j = nums.length - 1;
            int res = 0;

            while (i < j) {
                res += nums[j] - nums[i];
                i++;
                j--;
            }

            return res;
        }
    }
}
