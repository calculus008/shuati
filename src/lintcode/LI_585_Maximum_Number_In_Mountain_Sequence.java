package lintcode;

/**
 * Created by yuank on 6/24/18.
 */
public class LI_585_Maximum_Number_In_Mountain_Sequence {
    /**
         Given a mountain sequence of n integers which increase firstly and then decrease, find the mountain top.

         Example
         Given nums = [1, 2, 4, 8, 6, 3] return 8
         Given nums = [10, 9, 8, 7], return 10

         Medium
     */


    /**
     * Binary Search : OOOOXXXXX
     * Find the first element which is bigger than its next element
     */
    public int mountainSequence(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int start = 0;
        int end = nums.length -1 ;

        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (nums[mid] > nums[mid + 1]) {
                end = mid;
            } else {
                start = mid;
            }
        }

        return Math.max(nums[start], nums[end]);
    }
}
