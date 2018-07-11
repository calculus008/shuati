package leetcode;

/**
 * Created by yuank on 3/6/18.
 */
public class LE_80_Remove_Dup_From_Sorted_Array_II {
    /*
        Follow up for "Remove Duplicates":
        What if duplicates are allowed at most twice?

        For example,
        Given sorted array nums = [1,1,1,2,2,3],

        Your function should return length = 5, with the first five elements of nums being 1, 1, 2, 2 and 3. It doesn't matter what you leave beyond the new length.
     */

    //A follow up question of LE_26

    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int count = 2;

        /**
            when nums[i] == nums[count - 2], count stays the same, i will increase 1 in for loop.
            It means, i always keeps moving to the element. Only when i gets to the element that
            is not the same value as nums[count - 2], we put that value into nums[count] to replace its orignal value, then
            count move forward by 1. In other words, count stays at the location where the replacement
            action should happen.
         **/
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] != nums[count - 2]) {
                nums[count++] = nums[i];
            }
        }

        /**
         * Even though count points to the next location, but since it is index (0 based),
         * so it happens to be the right value to be returned at the end.
         *
         * For example : [1,1,1,2,2,3], when exit for loop, count value is 5, it is the correct count of total elements.
         */
        return count;
    }
}
