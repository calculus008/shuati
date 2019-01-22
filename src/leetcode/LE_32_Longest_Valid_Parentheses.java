package leetcode;

import java.util.Stack;

/**
 * Created by yuank on 2/26/18.
 */
public class LE_32_Longest_Valid_Parentheses {
    /**
         Given a string containing just the characters '(' and ')',
         find the length of the longest valid (well-formed) parentheses substring.

         For "(()", the longest valid parentheses substring is "()", which has length = 2.

         Another example is ")()())", where the longest valid parentheses substring is "()()", which has length = 4.
     */

    public int longestValidParentheses(String s) {
        /**
         * !!!stack里存的是右括号的下标
         */
        Stack<Integer> stack = new Stack<>();

        /**
         !!!cover the corner case that col 0 and 1 is a pair, 1 - (-1) = 2
         */
        stack.push(-1);
        int res = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                /**
                    The idea is simple, we only update the result (max) when we find a “pair”.
                    If we find a pair, we throw this pair away and see how big the gap is between current and previous invalid.
                    Key : the length is current col minus previous invalid col
                    Example : col  0 1 2 3 4 5 6
                                   ) ) ) ( ( ) )
                    First match pair is col 4 and 5, length is 5 - 3 = 2, next match is 3 and 6, length is 6 - 2 = 4
                 */
                stack.pop();

                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    res = Math.max(res, i - stack.peek());
                }
            }
        }

        return res;
    }
}
