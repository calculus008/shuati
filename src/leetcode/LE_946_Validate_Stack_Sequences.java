package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class LE_946_Validate_Stack_Sequences {
    /**
     * Given two sequences pushed and popped with distinct values,
     * return true if and only if this could have been the result
     * of a sequence of push and pop operations on an initially
     * empty stack.
     *
     * Example 1:
     *
     * Input: pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
     * Output: true
     *
     * Explanation: We might do the following sequence:
     * push(1), push(2), push(3), push(4), pop() -> 4,
     * push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
     *
     *
     * Example 2:
     *
     * Input: pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
     * Output: false
     * Explanation: 1 cannot be popped before 2.
     *
     *
     * Note:
     *
     * 0 <= pushed.length == popped.length <= 1000
     * 0 <= pushed[i], popped[i] < 1000
     * pushed is a permutation of popped.
     * pushed and popped have distinct values.
     */

    /**
     * Greedy
     *
     * For each value, push it to the stack.
     * Then, greedily pop values from the stack if they are the next values to pop.
     * At the end, we check if we have popped all the values successfully.
     *
     * Time and Space : O(n)
     */
    class Solution1 {
        public boolean validateStackSequences(int[] pushed, int[] popped) {
            if (null == pushed || null == popped) {
                return false;
            }

            Deque<Integer> stack = new ArrayDeque<>();

            for (int pushIdx = 0, popIdx = 0; pushIdx < pushed.length; pushIdx++) {
                stack.push(pushed[pushIdx]);
                while (!stack.isEmpty() && stack.peek() == popped[popIdx]) {
                    int n = stack.pop();
                    popIdx++;
                }
            }

            return stack.isEmpty();
        }
    }

    /**
     * Using pushed as the stack.
     * This solution will take O(1) extra space,
     * though it also changed the input.
     *
     * Time O(N)
     * Space O(1)
     */
    class Solution2 {
        public boolean validateStackSequences(int[] pushed, int[] popped) {
            int i = 0, j = 0;
            for (int x : pushed) {
                pushed[i++] = x;
                while (i > 0 && pushed[i - 1] == popped[j]) {
                    i--;
                    j++;
                }
            }

            return i == 0;
        }
    }
}
