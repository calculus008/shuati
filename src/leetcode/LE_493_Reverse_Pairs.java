package leetcode;

/**
 * Created by yuank on 10/1/18.
 */
public class LE_493_Reverse_Pairs {
    /**
         Given an array nums, we call (i, j) an important reverse pair if i < j and nums[i] > 2*nums[j].

         You need to return the number of important reverse pairs in the given array.

         Example1:

         Input: [1,3,2,3,1]
         Output: 2
         Example2:

         Input: [2,4,3,5,1]
         Output: 3
         Note:
         The length of the given array will not exceed 50,000.
         All the numbers in the input array are in the range of 32-bit integer.

         Hard
     */

    //https://www.youtube.com/watch?v=j68OXAMlTM4

    /**
     * merge sort, Time : O(nlogn)
     */
    public int reversePairs(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }

    private int helper(int[] nums, int start, int end) {
        if (start >= end) return 0; //!!!

        int mid = start + (end - start) / 2;

        int sum = helper(nums, start, mid) + helper(nums, mid + 1, end);

        int l = start, r = mid + 1, k = 0, p = mid + 1;
        int[] merge = new int[end - start + 1];

        while (l <= mid) {
            while (p <= end && (long)nums[l] > (long)2 * nums[p]) {
                p++;
            }

            //"p++" has done one extra in while loop, so here no need subtract 1
            sum += p - (mid + 1);

            while (r <= end && nums[l] >= nums[r]) {
                merge[k++] = nums[r++];
            }

            merge[k++] = nums[l++];
        }

        while (r <= end) {
            merge[k++] = nums[r++];
        }

        System.arraycopy(merge, 0, nums, start, merge.length);

        return sum;
    }
}
