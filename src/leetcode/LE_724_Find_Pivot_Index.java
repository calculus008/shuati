package leetcode;

public class LE_724_Find_Pivot_Index {
    /**
     * Given an array of integers nums, write a method
     * that returns the "pivot" index of this array.
     *
     * We define the pivot index as the index where the
     * sum of the numbers to the left of the index is equal
     * to the sum of the numbers to the right of the index.
     *
     * If no such index exists, we should return -1.
     * If there are multiple pivot indexes, you should
     * return the left-most pivot index.
     *
     * Example 1:
     *
     * Input:
     * nums = [1, 7, 3, 6, 5, 6]
     * Output: 3
     * Explanation:
     * The sum of the numbers to the left of index 3 (nums[3] = 6) is equal to the sum of numbers to the right of index 3.
     * Also, 3 is the first index where this occurs.
     *
     *
     * Example 2:
     *
     * Input:
     * nums = [1, 2, 3]
     * Output: -1
     * Explanation:
     * There is no index that satisfies the conditions in the problem statement.
     *
     *
     * Note:
     *
     * The length of nums will be in the range [0, 10000].
     * Each element nums[i] will be an integer in the range [-1000, 1000].
     *
     * Easy
     */

    /**
     * Key
     * Need to deal with corner case that pivot index is 0 or n - 1, for example :
     * {0, 1, ,1, -1, -1}
     * {1, -1, 0}
     *
     */
    class Solution {
        public int pivotIndex(int[] nums) {
            if (nums == null || nums.length == 0) return -1;

            int sum = 0;
            for (int num : nums) {
                sum += num;
            }

            int left = 0;
            for (int i = 0; i < nums.length; i++) {
                /**
                 * inexpicitly, left is set to 0, that's why it can
                 * dealt with case that pivot index is 0.
                 */
                if (i != 0) {
                    left += nums[i - 1];
                }

                /**
                 * if i is pivot, left == right, so sum = left + nums[i] + right = 2 * left + nums[i]
                 */
                if (sum - nums[i] == left * 2) {
                    return i;
                }
            }

            return -1;
        }
    }

    class Solution_Mine {
        public int pivotIndex(int[] nums) {
            if (nums == null || nums.length == 0) return -1;

            int n = nums.length;
            int[] sum1 = new int[n];
            int[] sum2 = new int[n];

            sum1[0] = nums[0];
            for (int i = 1; i < n; i++) {
                sum1[i] = sum1[i - 1] + nums[i];
            }

            sum2[n - 1] = nums[n - 1];
            for (int i = n - 2; i >= 0; i--) {
                sum2[i] = sum2[i + 1] + nums[i];
            }

            if (sum2[1] == 0) return 0;

            for (int i = 1; i < n - 1; i++) {
                if (sum1[i] == sum2[i] && sum1[i - 1] == sum2[i + 1]) {
                    return i;
                }
            }

            if (sum1[n - 2] == 0) return n - 1;

            return -1;
        }
    }
}
