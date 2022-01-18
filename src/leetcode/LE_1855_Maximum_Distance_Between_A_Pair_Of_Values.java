package leetcode;

public class LE_1855_Maximum_Distance_Between_A_Pair_Of_Values {
    /**
     * You are given two non-increasing 0-indexed integer arrays nums1 and nums2.
     *
     * A pair of indices (i, j), where 0 <= i < nums1.length and 0 <= j < nums2.length, is valid if both i <= j and
     * nums1[i] <= nums2[j]. The distance of the pair is j - i.
     *
     * Return the maximum distance of any valid pair (i, j). If there are no valid pairs, return 0.
     *
     * An array arr is non-increasing if arr[i-1] >= arr[i] for every 1 <= i < arr.length.
     *
     * Example 1:
     * Input: nums1 = [55,30,5,4,2], nums2 = [100,20,10,10,5]
     * Output: 2
     * Explanation: The valid pairs are (0,0), (2,2), (2,3), (2,4), (3,3), (3,4), and (4,4).
     * The maximum distance is 2 with pair (2,4).
     *
     * Example 2:
     * Input: nums1 = [2,2,2], nums2 = [10,10,1]
     * Output: 1
     * Explanation: The valid pairs are (0,0), (0,1), and (1,1).
     * The maximum distance is 1 with pair (0,1).
     *
     * Example 3:
     * Input: nums1 = [30,29,19,5], nums2 = [25,25,25,25,25]
     * Output: 2
     * Explanation: The valid pairs are (2,2), (2,3), (2,4), (3,3), and (3,4).
     * The maximum distance is 2 with pair (2,4).
     *
     * Constraints:
     * 1 <= nums1.length, nums2.length <= 105
     * 1 <= nums1[i], nums2[j] <= 105
     * Both nums1 and nums2 are non-increasing.
     *
     * Medium
     *
     * https://leetcode.com/problems/maximum-distance-between-a-pair-of-values/
     */

    /**
     * Two Pointers
     *
     * It's like walking through two arrays by the order from big to small, always increase index value for the array that
     * has a bigger current value. Along the way, check if required conditions can hold and update result.
     *
     * Time  : O(max(n,m))
     * Space : O(1)
     */
    class Solution {
        public int maxDistance(int[] nums1, int[] nums2) {
            int l1 = nums1.length;
            int l2 = nums2.length;

            int i = 0, j = 0;
            int res = 0;

            /**
             * If we reach the end of either one of the two arrays, no need to check with the one that still has elements.
             * 1.If j reaches end, since both arrays are none-increasing, if nums1[i] is already smaller or equal to the last
             *   value of nums2, the rest of nums1 will also be smaller than the last of nums2, but their index are increasing,
             *   hence it won't be the max distance as required.
             * 2.If i reaches end, nums[i] will be sure to be bigger than the rest of the elements in nums2, so it's not
             *   possible to find the valid pairs.
             */
            while (i < l1 && j < l2) {
                if (nums1[i] <= nums2[j]) {
//                    if (i <= j) {
//                        res = Math.max(res, j - i);
//                    }
                    /**
                     * no need to do if as above, since if i > j, j - i will be negative. Since res is initialized as 0,
                     * it will update res.
                     */
                    res = Math.max(res, j - i);
                    j++;
                } else {
                    i++;
                }
            }

            return res;
        }
    }
}
