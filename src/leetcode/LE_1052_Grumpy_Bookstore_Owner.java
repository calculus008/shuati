package leetcode;

public class LE_1052_Grumpy_Bookstore_Owner {
    /**
     * There is a bookstore owner that has a store open for n minutes. Every minute, some number of customers enter the store.
     * You are given an integer array customers of length n where customers[i] is the number of the customer that enters the
     * store at the start of the ith minute and all those customers leave after the end of that minute.
     *
     * On some minutes, the bookstore owner is grumpy. You are given a binary array grumpy where grumpy[i] is 1 if the bookstore
     * owner is grumpy during the ith minute, and is 0 otherwise.
     *
     * When the bookstore owner is grumpy, the customers of that minute are not satisfied, otherwise, they are satisfied.
     *
     * The bookstore owner knows a secret technique to keep themselves not grumpy for minutes consecutive minutes, but can only use it once.
     *
     * Return the maximum number of customers that can be satisfied throughout the day.
     *
     *
     * Example 1:
     * Input: customers = [1,0,1,2,1,1,7,5], grumpy = [0,1,0,1,0,1,0,1], minutes = 3
     * Output: 16
     * Explanation: The bookstore owner keeps themselves not grumpy for the last 3 minutes.
     * The maximum number of customers that can be satisfied = 1 + 1 + 1 + 1 + 7 + 5 = 16.
     *
     * Example 2:
     * Input: customers = [1], grumpy = [0], minutes = 1
     * Output: 1
     *
     *
     * Constraints:
     * n == customers.length == grumpy.length
     * 1 <= minutes <= n <= 2 * 104
     * 0 <= customers[i] <= 1000
     * grumpy[i] is either 0 or 1.
     *
     * Medium
     *
     * https://leetcode.com/problems/grumpy-bookstore-owner/
     */

    /**
     * Sliding Window
     *
     */

    /**
     * My solution:
     * Iterate and calculate each window of lenght "minutes", the ideal window to use "minutes" is the one that the diff between
     * using the "minutes" window and the one without the window.
     *
     * In iteration, going through the while length, then check:
     * 1.if the window length is satisfied
     * 2.If we need to remove the left most element
     *
     * NOTICE: grumpy[i] = 0 if the owner is NOT grumpy, so customers[i] * grumpy[i] is wrong.
     */
    class Solution1 {
        public int maxSatisfied(int[] customers, int[] grumpy, int minutes) {
            int sum = 0, sum1 = 0, sum2 = 0, j = 0;
            int start = 0;
            int maxDiff = Integer.MIN_VALUE;

            for (int i = 0; i < customers.length; i++) {
                sum1 += grumpy[i] == 0 ? customers[i] : 0;
                sum2 += customers[i];

                // need to remove left most element to keep window size
                if (i >= minutes) {
                    j = i - minutes;
                    sum1 -= grumpy[j] == 0 ? customers[j] : 0;
                    sum2 -= customers[j];
                }

                // we have a window of required size
                if (i >= minutes - 1) {
                    int diff = sum2 - sum1;
                    if (maxDiff < diff) {
                        maxDiff = diff;
                        start = i - minutes + 1;
                        sum = sum2;
                    }
                }
            }

            for (int i = 0; i < customers.length; i++) {
                if (i >= start && i < start + minutes) {
                    continue;
                }

                sum += grumpy[i] == 0 ? customers[i] : 0;
            }

            return sum;
        }
    }

    /**
     * Same idea as Solution1 but simplified:
     *
     * 1.sum1 records sum of customers when no "minutes" window  is used.
     * 2.sum2 records the gain (increase) in a window of length "minutes"
     * 3.we only maintain one sliding window sum by removing left most element (if grumpy value is 1).
     * 4.Each iteration, we maintain the max gain.
     *
     * In the end, the answer is the sum1 + max gain
     */
    class Solution2 {
        public int maxSatisfied(int[] customers, int[] grumpy, int minutes) {
            int sum1 = 0, sum2 = 0;
            int maxGain = Integer.MIN_VALUE;

            for (int i = 0; i < customers.length; i++) {
                if (grumpy[i] == 0) {
                    sum1 += customers[i];
                } else {
                    sum2 += customers[i];
                }

                if (i >= minutes) {
                    sum2 -= grumpy[i - minutes] * customers[i - minutes];
                }

                maxGain = Math.max(maxGain, sum2);
            }

            return sum1 + maxGain;
        }
    }
}
