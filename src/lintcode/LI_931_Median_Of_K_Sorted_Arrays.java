package lintcode;

/**
 * Created by yuank on 9/29/18.
 */
public class LI_931_Median_Of_K_Sorted_Arrays {
    /**
         There are k sorted arrays nums. Find the median of the given k sorted arrays.

         The length of the given arrays may not equal to each other.
         The elements of the given arrays are all positive number.
         Return 0 if there are no elements in the array.

         Have you met this question in a real interview?
         Example
         Given nums = [[1],[2],[3]], return 2.00.

         Medium
     */

    /**
     * 假如每个数组长度为 N，数组的整数值在 0~ 2 ^ 31 - 1 之间。O(logRange * klogn)
     */
    public double findMedian(int[][] nums) {
        int l = getTotalLength(nums);
        if (l == 0) {
            return 0;
        }

        if (l % 2 == 0) {
            /**
             * !!!
             * Can't do "(findKth(nums, l / 2) + findKth(nums, l / 2 + 1)) / 2.0", it may overflow.
             * For example :
             * [[1,3],[2147483646,2147483647]]
             *
             * (3 + 2147483646) overflows. So we have to do:
             *
             * 3/2 + 2147483646/2
             */
            return findKth(nums, l / 2) / 2.0 + findKth(nums, l / 2 + 1) / 2.0;
        }

        return findKth(nums, l / 2 + 1);
    }

    private int getTotalLength(int[][] nums) {
        int res = 0;
        for (int[] num : nums) {
            res += num.length;
        }
        return res;
    }

    private int findKth(int[][] nums, int k) {
        int start = 0, end = Integer.MAX_VALUE;
        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (getGECount(nums, mid) >= k) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (getGECount(nums, start) >= k) {
            return start;
        }

        return end;
    }

    private int getGECount(int[][] nums, int val) {
        int sum = 0;
        for (int[] num : nums) {
            sum += getCount(num, val);
        }
        return sum;
    }

    // !!! get how many numbers greater than or equal to val in an array
    private int getCount(int[] num, int val) {
        if (num == null || num.length == 0) return 0;

        int start = 0, end = num.length - 1;
        // find first element >= val
        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (num[mid] >= val) {
                end = mid;
            } else {
                start = mid;
            }
        }

        if (num[start] >= val) {
            return num.length - start;
        }

        if (num[end] >= val) {
            return  num.length - end;
        }

        return 0;
    }
}
