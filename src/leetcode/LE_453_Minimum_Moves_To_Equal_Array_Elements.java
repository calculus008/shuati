package leetcode;

public class LE_453_Minimum_Moves_To_Equal_Array_Elements {
    /**
     * Given a non-empty integer array of size n, find the minimum number of moves
     * required to make all array elements equal, where a move is incrementing n - 1
     * elements by 1.
     *
     * Example:
     *
     * Input:
     * [1,2,3]
     *
     * Output:
     * 3
     *
     * Explanation:
     * Only three moves are needed (remember each move increments two elements):
     *
     * [1,2,3]  =>  [2,3,3]  =>  [3,4,3]  =>  [4,4,4]
     */

    /**
     *  you only care about the difference between elements instead of the final value.
     *  In the perspective of difference, add one to all the other elements is equivalent
     *  to subtract one from current element.
     *
     * Adding 1 to n - 1 elements is the same as subtracting 1 from one element,
     * w.r.t goal of making the elements in the array equal.
     * So, best way to do this is make all the elements in the array equal to the min element.
     */
    class Solution {
        public int minMoves(int[] nums) {
            int min = Integer.MAX_VALUE;
            for (int num : nums) {
                min = Math.min(num, min);
            }

            int res = 0;
            for (int num : nums) {
                res += num - min;
            }

            return res;
        }
    }
}
