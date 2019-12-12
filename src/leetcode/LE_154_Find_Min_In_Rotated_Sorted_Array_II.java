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
     * If we use the same logic as LE_153, when nums[start] and nums[end] is the same, we can't tell if it is sorted.
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

    // version 1: just for loop is enough
    public class Solution1 {
        public int findMin(int[] num) {
            /**
             *               这道题目在面试中不会让写完整的程序
             *               只需要知道最坏情况下 [1,1,1....,1] 里有一个0
             *               这种情况使得时间复杂度必须是 O(n)
             *               因此写一个for循环就好了。
             *               如果你觉得，不是每个情况都是最坏情况，你想用二分法解决不是最坏情况的情况，那你就写一个二分吧。
             *               反正面试考的不是你在这个题上会不会用二分法。这个题的考点是你想不想得到最坏情况。
             */

            int min = num[0];
            for (int i = 1; i < num.length; i++) {
                if (num[i] < min)
                    min = num[i];
            }
            return min;
        }
    }

    // version 2: use *fake* binary-search
    public class Solution2 {
        public int findMin(int[] nums) {
            if (nums == null || nums.length == 0) {
                return -1;
            }

            int start = 0, end = nums.length - 1;
            while (start + 1 < end) {
                int mid = start + (end - start) / 2;
                if (nums[mid] == nums[end]) {
                    // if mid equals to end, that means it's fine to remove end
                    // the smallest element won't be removed
                    end--;
                } else if (nums[mid] < nums[end]) {
                    end = mid;
                    // of course you can merge == & <
                } else {
                    start = mid;
                    // or start = mid + 1
                }
            }

            if (nums[start] <= nums[end]) {
                return nums[start];
            }
            return nums[end];
        }
    }

    class Solution_Practice {
        public int findMin(int[] nums) {
            int l = 0;
            int h = nums.length - 1;

            while (l + 1 < h) {
                int m = l + (h - l) / 2;

                if (nums[m] == nums[h]) {
                    h--;
                } else if (nums[m] < nums[h]) {
                    h = m;
                } else {
                    l = m;
                }
            }

            if (nums[h] > nums[l]) return nums[l];

            return nums[h];
        }
    }
}
