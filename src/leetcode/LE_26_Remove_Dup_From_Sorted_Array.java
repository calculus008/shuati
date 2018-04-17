package leetcode;

/**
 * Created by yuank on 3/6/18.
 */
public class LE_26_Remove_Dup_From_Sorted_Array {
    /*
        Given a sorted array, remove the duplicates in-place such that each element appear only once and return the new length.

        Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.

        Example:

        Given nums = [1,1,2],

        Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.
        It doesn't matter what you leave beyond the new length.

        Follow up question at LE_80
     */

    //Key trick : when nums[i] == nums[i - 1], only i moves, count does NOT move. Only when nums[i] != nums[i - 1], both count and i moves

    // "c" represents count:

    //   i
    // 1 1 1 2 2 3 3 3   :  nums[i] == nums[i - 1], count stays, only i moves right by one
    //   c

    //     i
    // 1 1 1 2 2 3 3 3
    //   c

    //       i
    // 1 1 1 2 2 3 3 3   : now nums[i] != nums[i - 1]...
    //   c

    //         i
    // 1 2 1 2 2 3 3 3   : put 2 into c points, c moves by 1 and i moves by 1, then nums[i] == nums[i - 1], only i moves by one
    //     c

    //           i
    // 1 2 1 2 2 3 3 3   : now nums[i] != nums[i - 1]...
    //     c

    //             i
    // 1 2 3 2 2 3 3 3   : put 3 into c points, c moves by 1 and i moves by 1,
    //       c

    //               i
    // 1 2 3 2 2 3 3 3   : nums[i] == nums[i - 1] until the end of the array. So c does not move. In the end c = 3.
    //       c

    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i -1]) {
                nums[count++] = nums[i];
            }
        }
        return count;
    }
}
