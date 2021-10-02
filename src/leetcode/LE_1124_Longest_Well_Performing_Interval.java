package leetcode;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LE_1124_Longest_Well_Performing_Interval {
    /**
     * We are given hours, a list of the number of hours worked per day for a given employee.
     *
     * A day is considered to be a tiring day if and only if the number of hours worked is (strictly) greater than 8.
     *
     * A well-performing interval is an interval of days for which the number of tiring days is strictly larger than
     * the number of non-tiring days.
     *
     * Return the length of the longest well-performing interval.
     *
     * Example 1:
     * Input: hours = [9,9,6,0,6,6,9]
     * Output: 3
     * Explanation: The longest well-performing interval is [9,9,6].
     *
     * Example 2:
     * Input: hours = [6,6,6]
     * Output: 0
     *
     * Constraints:
     * 1 <= hours.length <= 104
     * 0 <= hours[i] <= 16
     *
     * Medium
     *
     * https://leetcode.com/problems/longest-well-performing-interval/
     */

    /**
     * HashMap
     *
     * Evolved from sliding window, we use HashMap to find the left boundary of the sliding window.
     *
     * The longest well-performing interval is basically a sub-array, let' say it is from index i t index j.
     * So the prefix sum at index i - prefix sum at index j = 1.
     *
     * Time  : O(n)
     * Space : O(n)
     */
    class Solution1 {
        public int longestWPI(int[] hours) {
            if (hours.length == 0) return 0;

            int n = hours.length;

            /**
             * key is prefix sum in hours array,
             * value is index where that sum appears for the first time (!!!)
             */
            Map<Integer, Integer> map = new HashMap<>();

            /**
             * !!!
             * Must be 0.
             */
            int res = 0;

            /**
             * sum at index i indicates the sum of hours[0:i] after transformation,
             * in other words, it is prefix sum at index i.
             */
            int sum = 0;

            for (int i = 0; i < n; i++) {
                sum += hours[i] > 8 ? 1 : -1;

                if (!map.containsKey(sum)) {
                    map.put(sum, i);
                }

                if (sum > 0) {
                    /**
                     * in hours[0:i], more 1s than -1s
                     * since it starts at index 0, it is the longest length.
                     */
                    res = i + 1;
                } else if (map.containsKey(sum - 1)){
                    /**
                     * Get the index j where sum of hours[0:j] is sum - 1, so that sum of hours[j+1 : i] is 1
                     *
                     * Must use "sum - 1" as key, can't use "-1". For example: [6, 6, 6], use "sum - 1" will
                     * by pass this "else if" branch and end result is 0. Using "-1" will go into "else if" branch
                     * and the result will be wrong.
                     *
                     * "i - map.get(sum - 1)" : 长度是 i - j + 1, 这里"map.get(sum - 1)"得到的x，其实是 j - 1, 所以：
                     * x = j - 1 ==> j = x + 1
                     * hence:
                     * i - j + 1 = i - (x + 1) + 1 = i - x.
                     */
                    res = Math.max(res, i - map.get(sum - 1));
                }
            }

            return res;
        }
    }

    /**
     * Mono Stack
     *
     * https://leetcode.com/problems/longest-well-performing-interval/discuss/335163/O(N)-Without-Hashmap.-Generalized-ProblemandSolution%3A-Find-Longest-Subarray-With-Sum-greater-K.
     *
     * Once we convert hours array to preSum array, it is the exact same problem as LE_862_Shortest_Subarray_With_Sum_At_Least_K
     *
     * Here I propose a more generalized problem and a solution to it.
     *
     * Problem:
     * input: array arr in which elements are arbitrary integers.
     * output: length of a longest subarray arr[i, j) with sum(arr[i], ... , arr[j-1]) >= K.
     *
     *
     * Solution:
     * 1.Compute prefix sum of arr as prefixSum where prefixSum[i] = sum(arr[0], ... arr[i-1]) for i > 0 and
     *   prefixSum[0] = 0.
     * 2.Iterate through prefixSum from begin to end and build a strictly monotone decreasing stack smdStack.
     *   (smdStack.top() is the smallest)
     * 3.Iterate through prefixSum from END to BEGIN. For each prefixSum[i], while smdStack.top() is less than
     *   prefixSum[i] by at least K, pop smdStack and try to update result by subarray [index of top,i).
     *   Until top element is not less than it by K.
     * 4.Return result.
     *
     * Same type problem:
     * LE_862_Shortest_Subarray_With_Sum_At_Least_K
     * LE_962_Maximum_Width_Ramp
     *
     * For this solution, once we create preSum array, we can apply the same solution on preSum array as in problem
     * LE_962_Maximum_Width_Ramp
     */
    class Solution2 {
        public int longestWPI(int[] hours) {
            int n = hours.length;

            int[] preSum = new int[n + 1];   // prefix Sum array with padding
            for (int i = 1; i <= n; i++) { //"i <= n"!!!
                preSum[i] = preSum[i - 1] + (hours[i - 1] > 8 ? 1 : -1);
            }

            Deque<Integer> stack = new LinkedList<>();   // Deque (8ms) is much faster than Stack (18ms)
            for (int i = 0; i <= n; i++) {
                /**
                 * Mono decreasing stack, save index.
                 */
                if (stack.isEmpty() || preSum[stack.peek()] > preSum[i]) {
                    stack.push(i);
                }
            }

            int res = 0;
            for (int i = n; i >= 0; i--) {  // start from end
                while (!stack.isEmpty() && preSum[i] - preSum[stack.peek()] > 0) {
                    /**
                     * len = i - j + 1, stack.pop() = x = j - 1 ==> j = x + 1
                     *
                     * i - (x + 1) + 1 = i - x
                     */
                    res = Math.max(res, i - stack.pop());
                }
            }

            return res;
        }
    }

}
