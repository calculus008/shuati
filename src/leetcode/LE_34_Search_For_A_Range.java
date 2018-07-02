package leetcode;

/**
 * Created by yuank on 2/26/18.
 */
public class LE_34_Search_For_A_Range {
    /**
        Given an array of integers sorted in ascending order, find the starting and ending position of a given target value.

        Your algorithm's runtime complexity must be in the order of O(log n).

        If the target is not found in the array, return [-1, -1].

        For example,
        Given [5, 7, 7, 8, 8, 10] and target value 8,
        return [3, 4].
     **/

    //https://www.youtube.com/watch?v=pZ7ypg3mU64&list=PLvyIyKZVcfAlKHPFECFxlkG7jOvD64r9V&index=9

    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }

        int start = findFirst(nums, target);
        //!!!
        if (start == -1) return new int[]{-1, -1};

        int end = findLast(nums, target);

        return new int[]{start, end};
    }

    private int findFirst(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;

        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (nums[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if(nums[start] == target) {
            return start;
        }

        if(nums[end] == target){
            return end;
        }

        return -1;
    }

    private int findLast(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;

        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            //!!!
            if (nums[mid] > target) {
                end = mid;
            } else {
                start = mid;
            }
        }

        //!!!
        if(nums[end] == target){
            return end;
        }

        if(nums[start] == target) {
            return start;
        }

        return -1;

    }
}
