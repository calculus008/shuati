package leetcode;

/**
 * Created by yuank on 4/18/18.
 */
public class LE_283_Move_Zeroes {
    /**
     * Given an array nums, write a function to move all 0's to the end of it
     * while maintaining the relative order of the non-zero elements.
     *
     * For example, given nums = [0, 1, 0, 3, 12], after calling your function,
     * nums should be [1, 3, 12, 0, 0].
     *
     * Note:
     * You must do this in-place without making a copy of the array.
     * Minimize the total number of operations
     *
     * Easy
     */

    //Time : O(n), Space : O(1)

    //Number of operations : nums.length
    public void moveZeroes1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        int start = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[start++] = nums[i];//!!!
            }
        }

        for (int i = start; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

    /**
     * Number of operations : 2 * number of none-zero,
     * better for case that has many zeros in nums.
     *
     *  i
     * [0, 1, 0, 3, 12]
     *  j
     *     i
     * [0, 1, 0, 3, 12]
     *  j
     *
     * swap:
     *     i
     * [1, 0, 0, 3, 12]
     *     j
     *           i
     * [1, 0, 0, 3, 12]
     *     j
     *
     * swap:
     *           i
     * [1, 3, 0, 0, 12]
     *        j
     *               i
     * [1, 3, 0, 3, 12]
     *        j
     *
     * swap:
     *               i
     * [1, 3, 12, 0, 0]
     *            j
     **/
    public void moveZeroes2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        /**
         * i is runner, j is the index for none-zero number.
         */
        for (int i = 0, j = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j++] = temp;
            }
        }
    }
}
