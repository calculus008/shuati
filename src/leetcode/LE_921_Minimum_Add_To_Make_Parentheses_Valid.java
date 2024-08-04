package leetcode;

import java.util.*;

public class LE_921_Minimum_Add_To_Make_Parentheses_Valid {
    /**
     * A parentheses string is valid if and only if:
     *
     * It is the empty string,
     * It can be written as AB (A concatenated with B), where A and B are valid strings, or
     * It can be written as (A), where A is a valid string.
     * You are given a parentheses string s. In one move, you can insert a parenthesis at any position of the string.
     *
     * For example, if s = "()))", you can insert an opening parenthesis to be "(()))" or a closing parenthesis to be "())))".
     * Return the minimum number of moves required to make s valid.
     *
     * Example 1:
     * Input: s = "())"
     * Output: 1
     *
     * Example 2:
     * Input: s = "((("
     * Output: 3
     *
     * Constraints:
     * 1 <= s.length <= 1000
     * s[i] is either '(' or ')'.
     *
     * Medium
     *
     * https://leetcode.com/problems/minimum-add-to-make-parentheses-valid/
     */

    class Solution1 {
        public int minAddToMakeValid(String s) {
            Stack<Character> stack = new Stack<>();
            int count = 0;

            for (char c : s.toCharArray()) {
                if (c == '(') {
                    stack.push(c);
                } else if (c == ')') {
                    if (!stack.isEmpty()) {
                        stack.pop();
                    } else {
                        count++;
                    }
                }
            }

            return count + stack.size();
        }
    }

    class Solution2 {
        public int minAddToMakeValid(String s) {
            int right = 0, left = 0;
            for (char c : s.toCharArray()) {
                if (c == '(') {
                    right++;
                } else if (c == ')') {
                    if (right != 0) {
                        right--;
                    } else {
                        left++;
                    }
                }
            }

            return right + left;
        }
    }
}
