package leetcode;

/**
 * Created by yuank on 3/20/18.
 */
public class LE_153_Find_Min_In_Rotated_Sorted_Array {
    /**
        Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

        (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

        Find the minimum element.

        You may assume no duplicate exists in the array.
     */

    /**
     * Best solution, from Huahua
     * http://zxi.mytechroad.com/blog/divide-and-conquer/leetcode-153-find-minimum-in-rotated-sorted-array/
     *
     * Essence is divide-conquer.
     *
     * Divide nums into 2 parts, always has one side sorted.
     * For the part that is sorted (nums[start] < nums[end] because there's no duplicate), min is nums[start],
     * it takes O(1)
     *
     * Time : O(1) + T(n / 2) = O(logn)
     *
     */
    class Solution {
        public int findMin(int[] nums) {
            return findMin(nums, 0, nums.length - 1);
        }

        private int findMin(int[] nums, int left, int right) {
            /**
             * 1 or 2 elements left.
             */
            if (left + 1 >= right) {
                return Math.min(nums[left], nums[right]);
            }

            /**
             * check if it is sorted, we can do it because there's no duplicate
             */
            if (nums[left] < nums[right]) {
                return nums[left];
            }

            int mid = left + (right - left) / 2;
            return Math.min(findMin(nums, left, mid - 1), findMin(nums, mid, right));
        }
    }




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

    /**
     * Use what condition to make OOOOXXXX
     *
     * For X : X <= nums[nums.length - 1]
     */

    public int findMin_JiuZhang(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int start = 0;
        int end = nums.length - 1;
        int target = nums[nums.length - 1];

        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (nums[mid] <= target) {
                end = mid;
            } else {
                start = mid;
            }
        }

        if (nums[start] <= target) {
            return nums[start];
        } else {
            return nums[end];
        }
    }
}
