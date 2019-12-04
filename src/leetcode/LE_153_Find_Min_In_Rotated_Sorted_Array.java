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
     * Example :
     *
     * idx 0 1 2 3 4 5 6
     *     4 5 6 7 0 1 2
     *                              result = 0
     *                       l = 0, r = 6, m = 3
     *                   4 /                     \0
     *          l = 0, r = 2                    l = 3, r = 6
     *              m = 1                            m = 4
     *            4 /   \ 5                     3 /            \ 0
     *   l = 0, r = 0   l = 1, r = 2        l = 3, r = 3         l = 4, r = 6
     *    min(nums[0],    min(nums[1],      min(nums[3],              m = 5
     *        nums[0])        nums[2])          nums[3])        0 /            \ 1
     *     return 4          return 5         return 7     l = 4, r = 4     l = 5, r = 6
     *                                                       min(nums[4],     min(nums[5],
     *                                                           nums[4])         nums[6])
     *                                                          return 0         return 1
     */

    /**
     * Huahua's version, divide and conquer, recursion
     */
    class Solution_Practice_1 {
        public int findMin(int[] nums) {
            return helper(nums, 0, nums.length - 1);
        }

        private int helper(int[] nums, int l, int h) {
            /**
             * !!!
             * "h - l <= 1", "<="
             */
            if (h - l <= 1) return Math.min(nums[l], nums[h]);

            if (nums[l] < nums[h]) return nums[l];

            int m = l + (h - l) / 2;

            return Math.min(helper(nums, l, m), helper(nums, m, h));
        }
    }

    /**
     * Binary Search, iterative, with Jiuzhang template
     */
    class Solution_Practice_2 {
        public int findMin(int[] nums) {
            int l = 0;
            int h = nums.length - 1;

            while (l + 1 < h) {
                int m = l + (h - l) / 2;

                if (nums[m] < nums[h]) {
                    h = m;
                } else {
                    l = m;
                }
            }

            if (nums[l] > nums[h]) return nums[h];

            return nums[l];
        }
    }


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

    /**
     * Iterative binary search
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
