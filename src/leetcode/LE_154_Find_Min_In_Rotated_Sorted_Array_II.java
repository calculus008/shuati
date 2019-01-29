package leetcode;

/**
 * Created by yuank on 3/20/18.
 */
public class LE_154_Find_Min_In_Rotated_Sorted_Array_II {
    /**
        Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

        (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

        Find the minimum element.

        The array may contain duplicates.
     */

    /**
     * http://zxi.mytechroad.com/blog/divide-and-conquer/leetcode-154-find-minimum-in-rotated-sorted-array-ii/
     *
     * As a follow up of LE_153 to analyse time complexity.
     *
     * The key is :
     * If we use the same logic as LE_53, when nums[start] and nums[end] is the same, we can't tell if it is sorted.
     * for example :
     *
     * start           end
     *  2 2 2 2 2 3 1 2 2
     *
     *  2 2 2 2 2 2 2 2 2
     *
     * so we have to keep dividing, until only 1 or 2 elements left.
     *
     * Worst case : time = 2 * T(n / 2)
     * Best case : same as LE_153 O(logn)
     */

    public int findMin(int[] nums) {
        int start = 0;
        int end = nums.length - 1;

        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (nums[mid] < nums[end]) {
                end = mid;
            //**only difference from LE_153
            } else if (nums[mid] > nums[end]){
                start = mid + 1;
            } else {
                end--;
            }
            //**
        }

        if (nums[start] < nums[end]) {
            return nums[start];
        }

        return nums[end];
    }
}
