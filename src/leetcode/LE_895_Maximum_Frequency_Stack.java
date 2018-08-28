package leetcode;

import java.util.*;

/**
 * Created by yuank on 8/26/18.
 */
public class LE_895_Maximum_Frequency_Stack {
    /**
         Implement FreqStack, a class which simulates the operation of a stack-like data structure.

         FreqStack has two functions:

         push(int x), which pushes an integer x onto the stack.
         pop(), which removes and returns the most frequent element in the stack.
         If there is a tie for most frequent element, the element closest to the top of the stack is removed and returned.



         Example 1:

         Input:
         ["FreqStack","push","push","push","push","push","push","pop","pop","pop","pop"],
         [[],[5],[7],[5],[7],[4],[5],[],[],[],[]]
         Output: [null,null,null,null,null,null,null,5,7,5,4]
         Explanation:
         After making six .push operations, the stack is [5,7,5,7,4,5] from bottom to top.  Then:

         pop() -> returns 5, as 5 is the most frequent.
         The stack becomes [5,7,5,7,4].

         pop() -> returns 7, as 5 and 7 is the most frequent, but 7 is closest to the top.
         The stack becomes [5,7,5,4].

         pop() -> returns 5.
         The stack becomes [5,7,4].

         pop() -> returns 4.
         The stack becomes [5,7].



         Note:

         Calls to FreqStack.push(int x) will be such that 0 <= x <= 10^9.
         It is guaranteed that FreqStack.pop() won't be called if the stack has zero elements.
         The total number of FreqStack.push calls will not exceed 10000 in a single test case.
         The total number of FreqStack.pop calls will not exceed 10000 in a single test case.
         The total number of FreqStack.push and FreqStack.pop calls will not exceed 150000 across all test cases.

         Hard
     */
    /**
     * https://zxi.mytechroad.com/blog/desgin/leetcode-895-maximum-frequency-stack/
     *
     * Map and List Solution
     */
    class FreqStack {
        Map<Integer, Integer> map;
        List<Stack<Integer>> list;

        public FreqStack() {
            map = new HashMap<>();
            list = new ArrayList<>();
            list.add(new Stack<>());
        }

        public void push(int x) {
            if (map.containsKey(x)) {
                int freq = map.get(x) + 1;
                map.put(x, freq);

                if (freq > list.size()) {
                    list.add(new Stack<Integer>());
                }

                list.get(freq - 1).push(x);
                return;
            }

            map.put(x, 1);
            list.get(0).push(x);
        }

        public int pop() {
            int idx = list.size() - 1;
            Stack<Integer> stack = list.get(idx);
            int res = stack.pop();
            if (stack.isEmpty()) {
                list.remove(idx);
            }

            int freq = map.get(res) - 1;
            if (freq == 0) {
                map.remove(res);
            } else {
                map.put(res, freq);
            }

            return res;
        }
    }
}
