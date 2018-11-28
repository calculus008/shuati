package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 11/26/18.
 */
public class LE_719_Find_Kth_Smallest_Pair_Distance {
    /**
         Given an integer array, return the k-th smallest distance among all the pairs.
         The distance of a pair (A, B) is defined as the absolute difference between A and B.

         Example 1:
         Input:
         nums = [1,3,1]
         k = 1
         Output: 0

         Explanation:
         Here are all the pairs:
         (1,3) -> 2
         (1,1) -> 0
         (3,1) -> 2
         Then the 1st smallest distance pair is (1,1), and its distance is 0.

         Note:
         2 <= len(nums) <= 10000.
         0 <= nums[i] < 1000000.
         1 <= k <= len(nums) * (len(nums) - 1) / 2.

         Hard
     */

    /**
     * Brutal Force : bucket sort, 258 ms
     * Time  : O(n ^ 2)
     * Space : O(max)
     */
    class Solution1 {
        public int smallestDistancePair(int[] nums, int k) {
            int n = nums.length;
            /**
             * !!!
             */
            Arrays.sort(nums);
            int max = nums[n - 1];

            int[] bucket = new int[max + 1];

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    bucket[nums[j] - nums[i]]++;
                }
            }

            for (int i = 0; i <= max; i++) {
                k -= bucket[i];
                if (k <= 0) {
                    return i;
                }
            }

            return 0;
        }
    }

    /**
     * DP + Binary Search, 7 ms
     *
     * Time  : O(nlogn + log(n ^ 2)) -> O(nlogn + 2logn) -> O(nlogn)
     * Space : O(n)
     */
    class Solution2 {
        public int smallestDistancePair(int[] nums, int k) {
            int n = nums.length;
            Arrays.sort(nums);

            int l = 0;
            int r = nums[n - 1];

            /**
             * binary search on distance, find the distance value mid that has k pairs whose
             * distance <= mid.
             */
            while (l <= r) {
                int mid = l + (r - l) / 2;

                if (getCount(nums, mid) >= k) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }

            return l;
        }

        /**
         * Use JiuZhang template for binary search
         */
        public int smallestDistancePair2(int[] nums, int k) {
            int n = nums.length;
            Arrays.sort(nums);

            int l = 0;
            int r = nums[n - 1];

            while (l + 1 < r) {
                int mid = l + (r - l) / 2;

                if (getCount(nums, mid) >= k) {
                    r = mid;
                } else {
                    l = mid;
                }
            }

            /**
             * !!!
             * number of pairs that has distance mid should bigger or equal to k
             */
            if (getCount(nums, l) >= k) return l;

            return r;
        }

        /**
         * Find number of pairs that the distance of the two elements in the pair
         * is smaller or equal to mid.
         */
        private int getCount(int[] nums, int mid) {
            int n = nums.length;
            int j = 0;
            int count = 0;
            for (int i = 0; i < n; i++) {
                /**
                 * !!!
                 * find the smallest j that nums[j] - nums[i] > mid
                 */
                while (j < n && nums[j] - nums[i] <= mid) {
                    j++;
                }
                count += j - i - 1;
            }

            return count;
        }
    }
}
