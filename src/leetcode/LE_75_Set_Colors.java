package leetcode;

/**
 * Created by yuank on 3/5/18.
 */
public class LE_75_Set_Colors {
    /**
        Given an array with n objects colored red, white or blue, sort them so that objects of the same color are adjacent,
        with the colors in the order red, white and blue.

        Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.

        Note:
        You are not suppose to use the library's sort function for this problem.

        So called "Dutch Flag" problem, can also be used for 3 way partition : https://en.wikipedia.org/wiki/Dutch_national_flag_problem#Pseudocode
     */

    /**
        Time: O(n), Space : O(1)
        left : final location of the end of 0,
        right : final location of the start of 1
    **/
    public static void sortColors(int[] nums) {
        if(nums == null || nums.length == 0) return;

        int left = 0;
        int right = nums.length - 1;
        int index = 0;

        while (index <= right) {
            if (nums[index] == 0) {
                swap(nums, left++, index++);
            } else if (nums[index] == 1) {
                index++;
            } else {
                swap(nums, right--, index);
            }
        }

    }

    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
