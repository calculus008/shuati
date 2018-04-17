package leetcode;

/**
 * Created by yuank on 3/7/18.
 */
public class LE_88_Merge_Sorted_Array {
    /*
        Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.

        Note:
        You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2.
        The number of elements initialized in nums1 and nums2 are m and n respectively.
     */

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

        if (i < 0) {
            while (j >= 0) {
                nums1[cur--] = nums2[j--];
            }
        }
    }
}
