package leetcode;

import java.util.*;

public class LE_1477_Find_Two_Non_Overlapping_Sub_Arrays_Each_With_Target_Sum {
    /**
     * Given an array of integers arr and an integer target.
     * You have to find two non-overlapping sub-arrays of arr each with a sum equal target. There can be multiple answers
     * so you have to find an answer where the sum of the lengths of the two sub-arrays is minimum.
     *
     * Return the minimum sum of the lengths of the two required sub-arrays, or return -1 if you cannot find such two sub-arrays.
     *
     * Example 1:
     * Input: arr = [3,2,2,4,3], target = 3
     * Output: 2
     * Explanation: Only two sub-arrays have sum = 3 ([3] and [3]). The sum of their lengths is 2.
     *
     * Example 2:
     * Input: arr = [7,3,4,7], target = 7
     * Output: 2
     * Explanation: Although we have three non-overlapping sub-arrays of sum = 7 ([7], [3,4] and [7]), but we will choose
     * the first and third sub-arrays as the sum of their lengths is 2.
     *
     * Example 3:
     * Input: arr = [4,3,2,6,2,3,4], target = 6
     * Output: -1
     * Explanation: We have only one sub-array of sum = 6.
     *
     * Example 4:
     * Input: arr = [5,5,4,4,5], target = 3
     * Output: -1
     * Explanation: We cannot find a sub-array of sum = 3.
     *
     * Example 5:
     * Input: arr = [3,1,1,1,5,1,2,1], target = 3
     * Output: 3
     * Explanation: Note that sub-arrays [1,2] and [2,1] cannot be an answer because they overlap.
     *
     *
     * Constraints:
     * 1 <= arr.length <= 105
     * 1 <= arr[i] <= 1000 (!!!)
     * 1 <= target <= 108
     *
     * Medium
     *
     * Similar Problem
     * LE_560_Subarray_Sum_Equals_K
     */

    /**
     * Sliding Window
     *
     * Time and Space : O(n)
     *
     * The important condition is "1 <= arr[i] <= 1000", it implies that we can use sliding window.
     *
     * First move right pointer, add value at right pointer to sum, if sum > target, it means we are over the target,
     * then we move left pointer by subtracting value at left pointer and increase left pointer by one.
     *
     * The tricky part is how to make sure the two subarrays are not overlapped. Here we use best[] as flags. Once we
     * have a subarray that ends at "right" and sums to target, we set best[right] to "bestsofar", which is the min
     * length of subarrays that sums to target. Before we have an subarray that sums to target, "bestsofar" has value
     * "Integer.MAX_VALUE". By checking if best[left] is "Integer.MAX_VALUE", we will know if the current subarray is
     * the 1st subarray that meets the requirement.
     *
     * From another point of view, best[] services two purposes:
     * 1.If we already have the first subarray that sums to target;
     * 2.What is the length of that subarray.
     *
     * Example:
     * [3,2,2,4,3]
     * 3
     *
     * best[] in each iteration:
     * [1, 2147483647, 2147483647, 2147483647, 2147483647]  : sum == target
     * [1, 1, 2147483647, 2147483647, 2147483647]
     * [1, 1, 1, 2147483647, 2147483647]
     * [1, 1, 1, 1, 2147483647]
     * [1, 1, 1, 1, 1] :  sum == target
     */
    class Solution1 {
        public int minSumOfLengths(int[] arr, int target) {
            int n = arr.length;
            int[] best = new int[n];
            Arrays.fill(best, Integer.MAX_VALUE);

            int bestsofar = Integer.MAX_VALUE;
            int res = Integer.MAX_VALUE;
            int sum = 0;

            for (int left = 0, right = 0; right < n; right++) {
                sum += arr[right];

                while (sum > target) {
                    sum -= arr[left];
                    left++;
                }

                if (sum == target) {
                    if (left > 0 && best[left - 1] != Integer.MAX_VALUE) {
                        res = Math.min(res, best[left - 1] + right - left + 1);
                    }
                    bestsofar = Math.min(bestsofar, right - left + 1);
                }
                best[right] = bestsofar;
            }

            return res == Integer.MAX_VALUE ? -1 : res;
        }
    }

    /**
     * Two passes HashMap solution that works for case that arr[i] can be any int value.
     *
     * left[i] is the size of the min size subarray in arr[0, i] that sums up to target. Is Integer.MAX_VALUE if there
     * is no subarray that sums up to target.
     * right[i] is the size of the min size subarray in arr[i, arr.length - 1] that sums up to target.
     * for (int i = 1; i < arr.length; ++i) result = Math.min(result, left[i - 1] + right[i]);
     */
    class Solution2 {
        public int minSumOfLengths(int[] arr, int target) {
            int[] left = new int[arr.length];
            int sum = 0;
            int bestsofar = Integer.MAX_VALUE;
            Map<Integer, Integer> map = new HashMap<>();
            map.put(0, -1);//!!!
            for (int i = 0; i < arr.length; ++i) {
                sum += arr[i];
                if (map.containsKey(sum - target)) {
                    bestsofar = Math.min(bestsofar, i - map.get(sum - target));//!!!
                }
                left[i] = bestsofar;
                map.put(sum, i);
            }

            int[] right = new int[arr.length];
            sum = 0;
            bestsofar = Integer.MAX_VALUE;
            map = new HashMap<>();
            map.put(0, arr.length);//!!!
            for (int i = arr.length - 1; i >= 0; --i) {
                sum += arr[i];
                if (map.containsKey(sum - target)) {
                    bestsofar = Math.min(bestsofar, map.get(sum - target) - i);//!!!
                }
                right[i] = bestsofar;
                map.put(sum, i);
            }

            int result = Integer.MAX_VALUE;
            for (int i = 1; i < arr.length; ++i) {
                if (left[i - 1] != Integer.MAX_VALUE && right[i] != Integer.MAX_VALUE) {
                    result = Math.min(result, left[i - 1] + right[i]);
                }
            }
            return result == Integer.MAX_VALUE ? -1 : result;
        }
    }

    /**
     * One pass HashMap Solution that works for any value in arr[]
     *
     * It uses Integer[] as flags, so if there's no valid min value, the element is null.
     */
    class Solution4 {
        public int minSumOfLengths(int[] arr, int target) {
            if (arr == null || arr.length == 0) return 0;
            /**
             * minSubarray[i] is the size of the smallest subarray in arr[0, i] whose sum is target. If the value is null
             * then there is no subarray in arr[0, i] that sums up to target. Same concept as best[] in Solution1
             */
            Integer[] minSubarray = new Integer[arr.length];
            Integer bestSoFar = null;
            Map<Integer, Integer> preSums = new HashMap<>();
            preSums.put(0, -1);

            Integer result = null;
            Integer sum = 0;

            for (int i = 0; i < arr.length; ++i) {
                sum += arr[i];

                /**
                 * Try finding the minimum size subarray that sums up to target and that ends at arr[i].
                 **/
                Integer rightSize = preSums.containsKey(sum - target) ? i - preSums.get(sum - target) : null;

                /**
                 * Try finding the minimum size subarray in arr[0, preSums.get(sum - target)], that is, is completely
                 * to the left of the right subarray we have found.
                 * "rightSize != null" : we have an subarray matching requirement
                 */
                Integer leftSize = rightSize != null && rightSize < i + 1 ? minSubarray[preSums.get(sum - target)] : null;

                /**
                 * Check whether there is a left and a right subarray, and that both are non-empty.
                 **/
                if (leftSize != null && rightSize != null) {
                    result = result == null ? leftSize + rightSize : Math.min(result, leftSize + rightSize);
                }

                // If in this loop we have discovered a new subarray that sums up to target then check if its the shortest one we have discovered so far.
                if (rightSize != null) {
                    bestSoFar = bestSoFar == null ? rightSize : Math.min(bestSoFar, rightSize);
                }
                minSubarray[i] = bestSoFar;
                preSums.put(sum, i);
            }

            return result == null ? -1 : result;
        }
    }
}
