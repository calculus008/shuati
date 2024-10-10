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
     *   b. missingNum >= k, binarySearch, find the min index "l", so that number of missing elements between
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

            if (missingNum < k) {//!!!
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

        /**
         * get the number of missing numbers between index 0 and idx:
         * (nums[i] - nums[0] + 1) - (i + 1) = nums[i] - nums[0] - i
         */
        private int getMissingNum(int[] nums, int idx) {
            return nums[idx] - nums[0] - idx;
        }
    }



    public int missingElement_clean(int[] arr, int k) {
        int left = 0, right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int missingUntilMid = arr[mid] - arr[0] - mid;

            if (missingUntilMid < k) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return arr[0] + k + right; //!!! The result is arr[right] + (k - missingUntilRight)
    }

    /**
     * The formula arr[0] + k + right comes from the result of the binary search. Here's why:
     *
     * right: After binary search, right points to the last index where the number of missing elements is less than k.
     * Missing elements before arr[right]: There are arr[right] - arr[0] - right missing numbers before arr[right].
     * Final result:
     * The K-th missing number is the offset from the beginning (arr[0]) plus the difference between k and missing
     * elements up to right. Thus, arr[0] + k + right gives the correct result.
     *
     * missingUntilRight = arr[right] − arr[0] − right
     * remainingMissing = k - (arr[right] - arr[0] - right) = k -arr[right] + arr[0] + right
     * result = arr[right] + remainingMissing
     *        = arr[right] + k -arr[right] + arr[0] + right
     *        = k + arr[0] + right
     */


    public int findKthMissing_iteration(int[] arr, int k) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) { // Go through the array and count missing numbers
            int missingBetween = arr[i + 1] - arr[i] - 1;            // Calculate the number of missing numbers between arr[i] and arr[i + 1]
            if (k <= missingBetween) { // If the k-th missing number is between arr[i] and arr[i + 1]
                return arr[i] + k;
            }
            k -= missingBetween; // Otherwise, move on to the next pair and reduce k by the number of missing numbers found
        }

        return arr[n - 1] + k;// If the k-th missing number is greater than all elements in the array
    }
}
