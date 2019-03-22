package leetcode;

/**
 * Created by yuank on 2/26/18.
 */
public class LE_35_Search_For_Insertion_Point {
    /**
    Given a sorted array and a target value, return the index if the target is found.
    If not, return the index where it would be if it were inserted in order.

    You may assume no duplicates in the array.

    Example 1:

    Input: [1,3,5,6], 5
    Output: 2
    Example 2:

    Input: [1,3,5,6], 2
    Output: 1
    Example 3:

    Input: [1,3,5,6], 7
    Output: 4
    Example 1:

    Input: [1,3,5,6], 0
    Output: 0
     */

    public int searchInsert1(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;

        while(start <= end) {
            int mid = (end - start)/2 + start;
            if(target > nums[mid]) {
                start = mid + 1;
            } else if(target < nums[mid]) {
                end = mid - 1;
            } else {
                return mid;
            }
        }

        return start;
    }

    public int searchInsert2(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;

        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (nums[mid] == target) {
                return mid;
            } else if(nums[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (target <= nums[start]) {
            return start;
        } else if(target <= nums[end]) {
            return end;
        } else {
            return end + 1;//!!!
        }
    }
}
