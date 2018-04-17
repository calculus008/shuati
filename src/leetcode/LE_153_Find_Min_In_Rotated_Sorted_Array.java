package leetcode;

/**
 * Created by yuank on 3/20/18.
 */
public class LE_153_Find_Min_In_Rotated_Sorted_Array {
    /*
        Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

        (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

        Find the minimum element.

        You may assume no duplicate exists in the array.
     */

    public int findMin(int[] nums) {
        int start = 0;
        int end = nums.length - 1;

        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (nums[mid] < nums[end]) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }

        //注意!!!: 比较的数组中的数值，不是下标!!!
        if (nums[start] < nums[end]) {
            return nums[start];
        }

        return nums[end];
    }
}
