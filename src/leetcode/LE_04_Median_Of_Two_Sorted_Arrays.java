package leetcode;

/**
 * Created by yuank on 8/29/18.
 */
public class LE_04_Median_Of_Two_Sorted_Arrays {
    /**
         There are two sorted arrays nums1 and nums2 of size m and n respectively.

         Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

         You may assume nums1 and nums2 cannot be both empty.

         Example 1:

         nums1 = [1, 3]
         nums2 = [2]

         The median is 2.0
         Example 2:

         nums1 = [1, 2]
         nums2 = [3, 4]

         The median is (2 + 3)/2 = 2.5
     */

    // Best Solution :  https://discuss.leetcode.com/topic/16797/very-concise-o-log-min-m-n-iterative-solution-with-detailed-explanation?page=1

    // http://windliang.cc/2018/07/18/leetCode-4-Median-of-Two-Sorted-Arrays/

    /**
     * Time : O(log(min(n1, n2))
     *
     * Key Insights :
     *
     * 1.
     *
     * **/
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n1 = nums1.length;
        int n2 = nums2.length;

        //since m1+m2=n1+n2, we can only search in one of the arrays, so we take the shorter one, which makes O(long(min(n1,n2)))
        if(n1 > n2) return findMedianSortedArrays(nums2, nums1);

        //!!!unisveral formula for calculating median, regardless of length(odd or even)
        if(n1 == 0) return (double)(nums2[(n2-1)/2] + nums2[n2/2])/2;

        //do binary search in n1
        int low = 0;
        int high = 2*n1; //n1 is the one with smaller length, so 2 * n1 won't exceed the total length after two arrays combined

        while(low <= high) {
            /**
             * Note : m1, m2 are defined as positions, not the real index in the array.
             *   [6 9 13 18]  ->   [# 6 # 9 # 13 # 18 #]    (N = 4)
               position index       0 1 2 3 4 5  6 7  8     (N_Position = 9)
             */
            int m1 = low + (high-low)/2; //cut point for nums1
            int m2 = n1 + n2 - m1; //cut point for nums2

            //!!!use MIN and MAX to handle the case that left and right idx falls out of the array boundary
            double l1 = m1==0? Integer.MIN_VALUE:nums1[(m1-1)/2];
            double l2 = m2==0? Integer.MIN_VALUE:nums2[(m2-1)/2];

            double r1 = m1==n1*2? Integer.MAX_VALUE:nums1[m1/2];
            double r2 = m2==n2*2? Integer.MAX_VALUE:nums2[m2/2];

            if(l1 > r2) { //means there are too many big elemts on the left side of nums1, we need to move to left in nums1
                high = m1 - 1;
            } else if(l2 > r1) {//means there are too many big elements on the left side of nums2, we need to move right in nums1
                low = m1 + 1;
            } else {
                return (Math.max(l1, l2) + Math.min(r1, r2))/2;
            }
        }

        return -1;
    }
}
