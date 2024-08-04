package leetcode;

public class LE_845_Longest_Mountain_In_Array {
    /**
     * You may recall that an array arr is a mountain array if and only if:
     *
     * arr.length >= 3
     * There exists some index i (0-indexed) with 0 < i < arr.length - 1 such that:
     * arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
     * arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
     * Given an integer array arr, return the length of the longest subarray, which is a mountain.
     * Return 0 if there is no mountain subarray.
     *
     * Example 1:
     * Input: arr = [2,1,4,7,3,2,5]
     * Output: 5
     * Explanation: The largest mountain is [1,4,7,3,2] which has length 5.
     *
     * Example 2:
     * Input: arr = [2,2,2]
     * Output: 0
     * Explanation: There is no mountain.
     *
     * Constraints:
     * 1 <= arr.length <= 104
     * 0 <= arr[i] <= 104
     *
     * Medium
     *
     * https://leetcode.com/problems/longest-mountain-in-array/
     */

    /**
     * One pass, O(1) solution.
     *
     * List of problems that involved 1 or 2 passes from left to right/right to left:
     * 42. Trapping Rain Water (3-pass)
     * 53 Maximum Subarray
     * 121 Best Time to Buy and Sell Stock
     * 152 Maximum Product Subarray
     * 238 Product of Array Except Self
     * 581. Shortest Unsorted Continuous Subarray
     * 739 Daily Temperatures
     * 769 Max Chunks to Make Sorted
     * 768 Max Chunks to Make Sorted II
     * 821 Shortest Distance to a Character
     * 845 Longest Mountain in Array
     * 896 Monotonic Array
     *
     */
    class Solution {
        public int longestMountain(int[] arr) {
            int res = 0;

            int len = arr.length;
            /**
             * !!!
             * We start from index 1 and look back to previous element.
             * Hence, we miss one count of the uphill:
             * up:    1  2  3
             * down:            1
             *    [1, 2, 3, 4, 1]
             *
             * Therefore, we need to add 1 back when calculating the mountain length!!!
             */
            int i = 1;

            while (i < len) {
                /**
                 * eliminating equal values
                 */
                while (i < len && arr[i - 1] == arr[i]) {
                    i++;
                }

                int up = 0;
                while (i < len && arr[i - 1] < arr[i]) {
                    up++;
                    i++;
                }

                int down = 0;
                while (i < len && arr[i - 1] > arr[i]) {
                    down++;
                    i++;
                }

                /**
                 * !!!
                 * Must have this check, otherwise, for [2,2,2], you will get result 1 (0 + 0 + 1).
                 * A mountain requires both up and down is bigger than 0.
                 */
                if (up > 0 && down > 0) {
                    res = Math.max(res, up + down + 1);
                }
            }

            return res;
        }
    }
}
