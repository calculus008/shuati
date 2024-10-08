package leetcode;

/**
 * Created by yuank on 3/7/18.
 */
public class LE_88_Merge_Sorted_Array {
    /**
        Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.

        Note:
        You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2.
        The number of elements initialized in nums1 and nums2 are m and n respectively.

        Easy

        https://leetcode.com/problems/merge-sorted-array
     */

    class Solution_clean {
        public void merge(int[] nums1, int m, int[] nums2, int n) {
            int p1 = m - 1, p2 = n - 1;
            int p3 = m + n - 1;

            while (p1 >= 0 && p2 >= 0) {
                if (nums1[p1] > nums2[p2]) {
                    nums1[p3] = nums1[p1];
                    p1--;
                } else {
                    nums1[p3] = nums2[p2];
                    p2--;
                }
                p3--;
            }

            while (p2 >= 0) {
                nums1[p3] = nums2[p2];
                p2--;
                p3--;
            }
        }
    }


    //关键 ： 从后往前走
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        if (n == 0) return;

        int cur = m + n - 1;
        int i = m - 1;
        int j = n - 1;
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[cur--] = nums1[i--];
            } else {
                nums1[cur--] = nums2[j--];
            }
        }

        /**
         * !!!
         * Must check i (index in nums1[]), can't do "if (j > 0)", for case:
         * nums1 : [0]
         * 0
         * nums2 : [1]
         * 1
         *
         * if check j > 0, will return [0], while the correct answer is [1]
         */
        if (i < 0) {
            while (j >= 0) {
                nums1[cur--] = nums2[j--];
            }
        }
    }
}
