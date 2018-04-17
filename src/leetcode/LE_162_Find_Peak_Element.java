package leetcode;

/**
 * Created by yuank on 3/20/18.
 */
public class LE_162_Find_Peak_Element {
    /*
        A peak element is an element that is greater than its neighbors.

        Given an input array where num[i] ≠ num[i+1], find a peak element and return its index.

        The array may contain multiple peaks, in that case return the index to any one of the peaks is fine.

        You may imagine that num[-1] = num[n] = -∞.

        For example, in array [1, 2, 3, 1], 3 is a peak element and your function should return the index number 2.
     */

    //Time : O(logn), Space : O(1)
    public int findPeakElement(int[] nums) {
        int start = 0;
        int end = nums.length - 1;

        while (start + 1 < end) {
            int mid = (end - start ) / 2 + start;
            if (nums[mid] > nums[mid + 1]) {// 1 2 3 1 : if current mid is bigger than its right neighbor, it could be the peak value, we need to look at its left neighbor, therefore, we move to left.
                end = mid;
            } else {// 1 2 3 2 1 : now current mid is smaller or equal than its right neighbor, so it must NOT be the peak element, we have to move to right side.
                start = mid + 1;
            }
        }

        if (nums[start] > nums[end]) return start;

        return end;
    }
}
