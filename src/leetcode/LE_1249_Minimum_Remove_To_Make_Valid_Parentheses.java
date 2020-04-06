package leetcode;

import java.util.Stack;

public class LE_1249_Minimum_Remove_To_Make_Valid_Parentheses {
    /**
     * Given a string s of '(' , ')' and lowercase English characters.
     *
     * Your task is to remove the minimum number of parentheses ( '(' or ')', in any positions )
     * so that the resulting parentheses string is valid and return any valid string.
     *
     * Formally, a parentheses string is valid if and only if:
     *
     * It is the empty string, contains only lowercase characters, or
     * It can be written as AB (A concatenated with B), where A and B are valid strings, or
     * It can be written as (A), where A is a valid string.
     *
     *
     * Example 1:
     * Input: s = "lee(t(c)o)de)"
     * Output: "lee(t(c)o)de"
     * Explanation: "lee(t(co)de)" , "lee(t(c)ode)" would also be accepted.
     *
     * Example 2:
     * Input: s = "a)b(c)d"
     * Output: "ab(c)d"
     *
     * Example 3:
     * Input: s = "))(("
     * Output: ""
     * Explanation: An empty string is also valid.
     *
     * Example 4:
     * Input: s = "(a(b(c)d)"
     * Output: "a(b(c)d)"
     *
     * Constraints:
     * 1 <= s.length <= 10^5
     * s[i] is one of  '(' , ')' and lowercase English letters.
     *
     * Medium
     */

    /**
     * Time and Space O(n)
     */

    /**
     * 18ms, 58%
     */
    class Solution1 {
        public String minRemoveToMakeValid(String s) {
            Stack<Integer> stack = new Stack<>();

            StringBuilder sb = new StringBuilder(s);

            for (int i = 0; i < sb.length(); i++) {
                char c = sb.charAt(i);
                if (c == '(') {
                    stack.push(i);
                } else if (c == ')') {
                    if (!stack.isEmpty()) {
                        stack.pop();
                    } else {
                        sb.setCharAt(i, '*');
                    }
                }
            }

            while (!stack.isEmpty()) {
                sb.setCharAt(stack.pop(), '*');
            }

            return sb.toString().replaceAll("\\*", "");
        }
    }

    /**
     * 15ms, 78%
     */
    class Solution2 {
        public String minRemoveToMakeValid(String s) {
            StringBuilder sb = remove(s, '(', ')');
            StringBuilder sb1 = remove(sb.reverse().toString(), ')', '(');
            return sb1.reverse().toString();
        }

        private StringBuilder remove(String s, char open, char close) {
            int count = 0;
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == open) {
                    count++;
                } else if (c == close) {
                    if (count == 0) {
                        continue;
                    } else {
                        count--;
                    }
                }

                sb.append(c);
            }

            return sb;
        }
    }

    /**
     * Simplified from Solution2
     *
     * 14ms 84%
     *
     * we can do the first parse and then look at the balance to see how many "(" we need to remove.
     * It turns out that if we remove the rightmost '(', we are guaranteed to have a balanced string.
     * So for the second parse, we only need to remove balance "(", starting from the right.
     *
     * In order to avoid iterating backwards (which adds needless complexity to the algorithm),
     * we also keep track of how many "(" are in the string in the first parse. This way, we can calculate
     * how many "(" we are keeping, and count these down as we iterate through the string on the second parse.
     * Then once we've kept enough, we can start dropping them.
     */
    class Solution {
        public String minRemoveToMakeValid(String s) {
            int rightCount = 0;
            int balanceCount = 0;
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '(') {
                    rightCount++;
                    balanceCount++;
                } else if (c == ')') {
                    if (balanceCount == 0) {
                        continue;
                    } else {
                        balanceCount--;
                    }
                }

                sb.append(c);
            }

            StringBuilder sb1 = new StringBuilder();
            int count = rightCount - balanceCount;

            /**
             * Can't do it - example "))(("
             */
            // if (count == 0) return sb.toString();

            for (int i = 0; i < sb.length(); i++) {
                char c = sb.charAt(i);
                if (c == '(') {
                    count--;
                    if (count < 0) continue;
                }

                sb1.append(c);
            }

            return sb1.toString();
        }


    }
}
