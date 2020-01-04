package leetcode;

public class LE_1060_Missing_Element_In_Sorted_Array {
    /**
     * Given a sorted array A of unique numbers, find the K-th missing number starting from the leftmost number of the array.
     *
     * Example 1:
     * Input: A = [4,7,9,10], K = 1
     * Output: 5
     * Explanation:
     * The first missing number is 5.
     *
     * Example 2:
     * Input: A = [4,7,9,10], K = 3
     * Output: 8
     * Explanation:
     * The missing numbers are [5,6,8,...], hence the third missing number is 8.
     *
     * Example 3:
     * Input: A = [1,2,4], K = 3
     * Output: 6
     * Explanation:
     * The missing numbers are [3,5,6,7,...], hence the third missing number is 6.
     *
     *
     * Note:
     * 1 <= A.length <= 50000
     * 1 <= A[i] <= 1e7
     * 1 <= K <= 1e8
     *
     * Medium
     *
     * A simplified version
     * https://www.geeksforgeeks.org/find-missing-element-in-a-sorted-array-of-consecutive-numbers/
     */

    /**
     * Binary Search
     * O(logn)
     *
     * 关键：
     * 1.Since nums[] is sorted, if there is no missing number between index 0 and idx,
     *   total number of elements should be idx - 0 + 1, it should be equal to nums[idx] - nums[0] + 1.
     *   If there are missing numbers, the number of missing elements should be :
     *   (nums[idx] - nums[0] + 1) - (idx - 0 + 1) = nums[idx] - nums[0] - idx
     *
     * 2.Two cases:
     *   missingNum is number of missing elements in the whole array.
     *   a. missingNum < k, then return nums[n - 1] + k - missingNum
     *   b. missingNum <= k, binarySearch, find the min index "l", so that number of missing elements between
     *      0 and l >= k. Therefore, the kth missing elements is between nums[l - 1] and nums[l], that kth missing
     *      number is :
     *
     *      nums[l - 1] + k - (number of missing elements between index 0 and (l-1))
     *
     */
    class Solution {
        public int missingElement(int[] nums, int k) {
            if (nums == null || nums.length == 0) return 1;

            int n = nums.length;
            int l = 0;
            int h = n - 1;

            int missingNum = getMissingNum(nums, n - 1);

            if (missingNum < k) {
                return nums[n - 1] + k - missingNum;
            }

            while (l < h) {
                int m = l + (h - l) / 2;

                if (getMissingNum(nums, m) >= k) {
                    h = m;
                } else {
                    l = m + 1;
                }
            }

            return nums[l - 1] + k - getMissingNum(nums, l - 1);
        }

        private int getMissingNum(int[] nums, int idx) {
            return nums[idx] - nums[0] - idx;
        }
    }
}
