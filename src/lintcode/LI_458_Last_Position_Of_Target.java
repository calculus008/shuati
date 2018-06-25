package lintcode;

/**
 * Created by yuank on 6/24/18.
 */
public class LI_458_Last_Position_Of_Target {
    /**
     * Description
     Find the last position of a target number in a sorted array. Return -1 if target does not exist.

     Example
     Given [1, 2, 2, 4, 5, 5].

     For target = 2, return 2.

     For target = 5, return 5.

     For target = 6, return -1.
     */

    public int lastPosition(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                start = mid;
            } else if (nums[mid] < target) {
                start = mid;
                // or start = mid + 1
            } else {
                end = mid;
                // or end = mid - 1
            }
        }

        if (nums[end] == target) {
            return end;
        }
        if (nums[start] == target) {
            return start;
        }
        return -1;
    }
}
