package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

public class LE_20_Valid_Parentheses {
    /**
     * Given a string containing just the characters '(', ')', '{', '}', '[' and ']',
     * determine if the input string is valid.
     *
     * An input string is valid if:
     *
     * Open brackets must be closed by the same type of brackets.
     * Open brackets must be closed in the correct order.
     * Note that an empty string is also considered valid.
     *
     * Example 1:
     * Input: "()"
     * Output: true
     *
     * Example 2:
     * Input: "()[]{}"
     * Output: true
     *
     * Example 3:
     * Input: "(]"
     * Output: false
     *
     * Example 4:
     * Input: "([)]"
     * Output: false
     *
     * Example 5:
     * Input: "{[]}"
     * Output: true
     */

    public class Solution {
        public boolean isValid(String s) {
            Deque<Character> stack = new ArrayDeque<>(s.length());

            for(char c: s.toCharArray()) {
                if(c=='(') {
                    stack.push(')');
                } else if(c=='{') {
                    stack.push('}');
                } else if(c=='[') {
                    stack.push(']');
                } else {
                    if(stack.isEmpty() || stack.pop() != c)
                        return false;
                }
            }

            return stack.isEmpty();
        }
    }
}
