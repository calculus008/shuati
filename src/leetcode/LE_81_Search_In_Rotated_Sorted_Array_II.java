package leetcode;

/**
 * Created by yuank on 3/6/18.
 */
public class LE_81_Search_In_Rotated_Sorted_Array_II {
    /*
        Follow up for "Search in Rotated Sorted Array":
        What if duplicates are allowed?

        Would this affect the run-time complexity? How and why?

        Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

        (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

        Write a function to determine if a given target is in the array.

        The array may contain duplicates.
     */

    //Follow up question from LE_33
    //Time : Worest case O(n)
    public static boolean search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        int start = 0;
        int end = nums.length - 1;

        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (nums[mid] == target) {
                return true;
            }

            if (nums[start] == nums[mid] && nums[mid] == nums[end]) {
                start++;
                end--;
            } else if (nums[start] <= nums[mid]) {
                if(nums[start] <= target && target <= nums[mid]) {
                    end = mid;
                } else {
                    start = mid;
                }
            } else {
                if(nums[mid] <= target && target <=nums[end]) {
                    start = mid;
                } else {
                    end = mid;
                }
            }
        }

        if (nums[start] == target) return true;
        if (nums[end] == target) return true;
        return false;
    }
}
