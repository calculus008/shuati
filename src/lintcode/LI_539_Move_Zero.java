package lintcode;

/**
 * Created by yuank on 7/11/18.
 */
public class LI_539_Move_Zero {
    /**
         Given an array nums, write a function to move all 0's to the end of it while maintaining
         the relative order of the non-zero elements.

         Example
         Given nums = [0, 1, 0, 3, 12], after calling your function, nums should be [1, 3, 12, 0, 0].

         Easy
     */

    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length < 1) {
            return;
        }

        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                swap(nums, i, left);
                left++;
            }
        }
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
