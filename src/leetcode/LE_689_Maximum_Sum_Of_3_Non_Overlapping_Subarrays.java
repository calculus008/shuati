package leetcode;

public class LE_689_Maximum_Sum_Of_3_Non_Overlapping_Subarrays {
    /**
     * In a given array nums of positive integers, find three non-overlapping subarrays with maximum sum.
     *
     * Each subarray will be of size k, and we want to maximize the sum of all 3*k entries.
     *
     * Return the result as a list of indices representing the starting position of each interval (0-indexed).
     * If there are multiple answers, return the lexicographically smallest one.
     *
     * Example:
     *
     * Input: [1,2,1,2,6,7,5,1], 2
     * Output: [0, 3, 5]
     * Explanation: Subarrays [1, 2], [2, 6], [7, 5] correspond to the starting indices [0, 3, 5].
     * We could have also taken [2, 1], but an answer of [1, 3, 5] would be lexicographically larger.
     *
     *
     * Note:
     *
     * nums.length will be between 1 and 20000.
     * nums[i] will be between 1 and 65535.
     * k will be between 1 and floor(nums.length / 3).
     *
     * Hard
     */

    /**
     * DP
     * https://leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays/discuss/108231/C%2B%2BJava-DP-with-explanation-O(n)
     *
     * The question asks for three non-overlapping intervals with maximum sum of all 3 intervals. If the middle interval is
     * [i, i+k-1], where k <= i <= n-2k, the left interval has to be in subrange [0, i-1], and the right interval is from
     * subrange [i+k, n-1].
     *
     *      Array left[i] is the starting index for the left interval in range [0, i] (that has the max sum);
     *      Array right[i] is the starting index for the right interval in range [i, n-1] (that has the max sum);
     *
     * Then we test every possible starting index of middle interval, i.e. k <= i <= n-2k, and we can get the corresponding
     * left and right max sum intervals easily from DP. And the run time is O(n).
     *
     * #How to calculate left[] and right[]:
     *  If we know the starting index of the middle interval, say, i, its ending index is i + k - 1.
     *
     *  For the interval with max value to the left of current middle interval, its starting index
     *  should be in left[i - 1].
     *
     *  For the interval with max value to the right of current middle interval, its starting index
     *  should be in right[i + k]
     *
     *
     */
    class Solution {
        public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
            int n = nums.length;

            int[] left = new int[n];
            int[] right = new int[n];
            int[] sum = new int[n];

            /**
             * prefix sum without the padding
             */
            sum[0] = nums[0];
            for (int i = 1; i < n; i++) {
                sum[i] = sum[i - 1] + nums[i];
            }

            int max = Integer.MIN_VALUE;
            //loop for ending index of the left
            for (int i = k - 1; i < n; i++) {
                /**
                 * current sum, ending index is i, starting index is i - k + 1
                 *
                 * Since we use prefix sum array that does not have padding, the way
                 * to calculate sum between index j and i is (inclusive):
                 *
                 *      sum[i] - sum[j] + nums[j]
                 *
                 * Here j is i - k + 1
                 **/
                int cur = sum[i] - sum[i - k + 1] + nums[i - k + 1];
                if (cur > max) {
                    left[i] = i - k + 1;//record starting index because the result should show starting index of the interval
                    max = cur;//!!!
                } else {
                    left[i] = left[i - 1];
                }
            }

            max = Integer.MIN_VALUE;
            //loop for starting index of the right
            for (int i = n - k; i >= 0; i--) {
                int cur = sum[i + k - 1] - sum[i] + nums[i];
                /**
                 * !!!
                 * For the right interval, we iterate from right to left, because of the requirement
                 * "If there are multiple answers, return the lexicographically smallest one", when we
                 * move to left, we have to use "cur >= max" to always get the left most intervals.
                 */
                if (cur >= max) {
                    right[i] = i;//record starting index because the result should show starting index of the interval
                    max = cur;//!!!
                } else {
                    right[i] = right[i + 1];
                }
            }

            max = Integer.MIN_VALUE;
            int[] res = new int[3];
            for (int i = k; i <= n - 2 * k; i++) {
                int ls = left[i - 1];
                int rs = right[i + k];

                int total = sum[ls + k - 1] - sum[ls] + nums[ls];// max sum subarray on the left
                total += sum[i + k - 1] - sum[i] + nums[i];      // max sum subarray in the middle
                total += sum[rs + k - 1] - sum[rs] + nums[rs];   // max sum subarray on the right

                if (total > max) {
                    max = total;//!!! Don't forget this step
                    res[0] = ls;
                    res[1] = i;
                    res[2] = rs;
                }
            }

            return res;
        }
    }
}
