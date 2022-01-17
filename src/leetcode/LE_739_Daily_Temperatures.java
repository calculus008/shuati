package leetcode;

import java.util.Stack;

public class LE_739_Daily_Temperatures {
    /**
     * Given a list of daily temperatures T, return a list such that, for each day in the input,
     * tells you how many days you would have to wait until a warmer temperature. If there is no
     * future day for which this is possible, put 0 instead.
     *
     * For example, given the list of temperatures T = [73, 74, 75, 71, 69, 72, 76, 73], your
     * output should be [1, 1, 4, 2, 1, 1, 0, 0].
     *
     * Note: The length of temperatures will be in the range [1, 30000]. Each temperature will
     * be an integer in the range [30, 100].
     *
     * Medium
     */

    /**
     * A twisted version of RunningTemperature
     *
     * Basically, what we need to find:
     * 1.For "next bigger element" - for a given element in an array, find the element that is bigger than it in the elements come after.
     * 2.Then get the distance between current element and the next bigger element.
     *
     * Action of calculating distance happens when we popping out element when we run into a bigger element.
     * Therefore, we use mono-decreasing stack and it saves index of the element, not element itself (for calculating distance).
     *
     * O(n)
     */
    class Solution1 {
        public int[] dailyTemperatures(int[] T) {
            if (T == null || T.length == 0) return new int[]{};

            int n = T.length;
            int[] res = new int[n];
            Stack<Integer> stack = new Stack<>();

            /**
             * going from end to start
             */
            for (int i = n - 1; i >= 0; i--) {
                while (!stack.isEmpty() && T[i] >= T[stack.peek()]) {
                    stack.pop();
                }

                if (stack.isEmpty()) {
                    res[i] = 0;
                } else {
                    res[i] = stack.peek() - i;
                }

                stack.push(i);
            }

            return res;
        }
    }

    /**
     * Stack, but go from start to end
     */
    class Solution2 {
        public int[] dailyTemperatures(int[] temperatures) {
            Stack<Integer> stack = new Stack<>();
            int[] ret = new int[temperatures.length];

            for(int i = 0; i < temperatures.length; i++) {
                while(!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                    /**
                     * 回头看
                     */
                    int idx = stack.pop();
                    ret[idx] = i - idx;
                }
                stack.push(i);
            }

            /**
             * After for loop, stack may not be empty, but we don't need to do
             * the following while loop, because ret[] is init with value 0,
             * we don't need to do anything, just leave the rest of ret[] as 0.
             */
            // while (!stack.isEmpty()) {
            //     ret[stack.pop()] = 0;
            // }

            return ret;
        }
    }

    class Solution3 {
        public int[] dailyTemperatures(int[] temperatures) {
            int[] stack = new int[temperatures.length];
            int top = -1;
            int[] ret = new int[temperatures.length];

            for(int i = 0; i < temperatures.length; i++) {
                while(top > -1 && temperatures[i] > temperatures[stack[top]]) {
                    /**
                     * instead of stack pop, use array and just minus one,
                     * this increases speed.
                     */
                    int idx = stack[top--];
                    ret[idx] = i - idx;
                }
                stack[++top] = i;
            }

            return ret;
        }
    }
}
