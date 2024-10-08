package leetcode;

import java.util.Stack;

public class LE_456_132_Pattern {
    /**
     * Given a sequence of n integers a1, a2, ..., an, a 132 pattern is a
     * subsequence ai, aj, ak such that i < j < k and ai < ak < aj. Design
     * an algorithm that takes a list of n numbers as input and checks
     * whether there is a 132 pattern in the list.
     *
     * Note: n will be less than 15,000.
     *
     * Example 1:
     * Input: [1, 2, 3, 4]
     *
     * Output: False
     *
     * Explanation: There is no 132 pattern in the sequence.
     *
     * Example 2:
     * Input: [3, 1, 4, 2]
     *
     * Output: True
     *
     * Explanation: There is a 132 pattern in the sequence: [1, 4, 2].
     *
     * Example 3:
     * Input: [-1, 3, 2, 0]
     *
     * Output: True
     *
     * Explanation: There are three 132 patterns in the sequence: [-1, 3, 2], [-1, 3, 0] and [-1, 2, 0].
     *
     * Medium
     *
     * https://leetcode.com/problems/132-pattern/description/
     */

    /**
     * Mono Stack Solution
     *
     * Time and Space : O(n)
     *
     * https://leetcode.com/problems/132-pattern/discuss/94071/Single-pass-C%2B%2B-O(n)-space-and-time-solution-(8-lines)-with-detailed-explanation.
     *
     * 从右往左遍历，pop出所有比当前值大的元素，这些元素是132中"2"的候选人，记住他(as "min")，push当前元素("3"的候选人).然后看后面的元素是否小于min.
     * 如果是，则找到了"1"。
     */
    public boolean find132pattern(int[] nums) {
        if (nums == null || nums.length < 3) return false;

        Stack<Integer> stack = new Stack<>();

        int min = Integer.MIN_VALUE;

        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] < min) {
                return true;
            }

            while (!stack.isEmpty() && nums[i] > stack.peek()) {
                min = stack.pop();
            }
            stack.push(nums[i]);
        }

        return false;
    }
}
