package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

public class LE_503_Next_Greater_Element_II {
    /**
     * Given a circular array (the next element of the last element is the first
     * element of the array), print the Next Greater Number for every element.
     * The Next Greater Number of a number x is the first greater number to its
     * traversing-order next in the array, which means you could search circularly
     * to find its next greater number. If it doesn't exist, output -1 for this number.
     *
     * Example 1:
     * Input: [1,2,1]
     * Output: [2,-1,2]
     * Explanation: The first 1's next greater number is 2;
     * The number 2 can't find next greater number;
     * The second 1's next greater number needs to search circularly, which is also 2.
     *
     * Note: The length of given array won't exceed 10000.
     *
     * Medium
     *
     * https://leetcode.com/problems/next-greater-element-ii
     */

    /**
     * Iterate from left to right
     * No need to use HashMap, use an array res[].
     *
     * 3 variations to deal with circular array:
     *   we can scan the array twice using 2 for loop, but will cause redundancy code
     *   we can expand the original array, but can be simplified by method 3
     *   we can simulate expanding the original array like the author's code
     *
     * But remember we push elements to stack in the 1st round, but only pop elements in the 2nd round!!!
     *
     * Example : [1, 2, 1]
     *
     * [1, 2, 1, 1, 2, 1]
     *
     * 1
     * stack: 1
     * res: []
     *
     * 2
     * stack: 2
     * res: [2]
     *
     * 1
     * stack: 2 1
     *
     * 1
     * stack: 2 1
     *
     * 2
     * stack: 2
     * res: [2, , 2]
     *
     * 1
     * stack: 2
     *
     * for loop ends, popping stack.
     *
     * res: [2, -1, 2]
     *
     *
     */
    class Solution1 {
        public int[] nextGreaterElements(int[] nums) {
            if (nums == null) return null;

            Deque<Integer> stack = new ArrayDeque<>();
            int n = nums.length;
            int[] res = new int[n];

            for (int i = 0; i < n * 2; i++) {
                int num = nums[i % n];

                while (!stack.isEmpty() && num > nums[stack.peek()]) {
                    res[stack.pop()] = num;
                }

                if (i < n) {
                    stack.push(i);
                }
            }

            while (!stack.isEmpty()) {
                res[stack.pop()] = -1;
            }

            return res;
        }
    }

    /**
     * Iterate from right to left
     */
    class Solution2 {
        public int[] nextGreaterElements(int[] nums) {
            if (nums == null) return null;

            Deque<Integer> stack = new ArrayDeque<>();
            int n = nums.length;
            int[] res = new int[n];

            for (int i = 2 * n - 1; i >= 0; i--) {
                while (!stack.isEmpty() && nums[i % n] >= nums[stack.peek()]) {
                    stack.pop();
                }

                if (i < n) {
                    res[i] = stack.isEmpty() ? -1 : nums[stack.peek()];
                }

                stack.push(i % n);
            }

            return res;
        }
    }
}
