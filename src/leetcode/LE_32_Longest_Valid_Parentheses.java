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

    /**
     * Stack
     * Time and Space : O(n)
     */
    class Solution1 {
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

    /**
     * DP
     *
     * V[i] represents number of valid parentheses matches from S[j to i] (j<i)
     *
     * V[i] = V[i-1] + 2 + previous matches V[i- (V[i-1] + 2) ] if S[i] = ')' and '(' count > 0
     *
     * Time : O(n)
     * Space : O(n)
     */
    class Solution2 {
        public int longestValidParentheses(String s) {
            int maxans = 0;
            int dp[] = new int[s.length()];

            for (int i = 1; i < s.length(); i++) {
                if (s.charAt(i) == ')') {
                    if (s.charAt(i - 1) == '(') {
                        dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                    } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                        dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                    }
                    maxans = Math.max(maxans, dp[i]);
                }
            }
            return maxans;
        }
    }

    /**
     * Solution with constant space
     *
     * Time  : O(n)
     * Space : O(1)
     */
    public class Solution {
        public int longestValidParentheses(String s) {
            int left = 0, right = 0, maxlength = 0;

            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '(') {
                    left++;
                } else {
                    right++;
                }

                if (left == right) {
                    maxlength = Math.max(maxlength, 2 * right);
                } else if (right >= left) {
                    left = right = 0;
                }
            }

            left = right = 0;

            for (int i = s.length() - 1; i >= 0; i--) {
                if (s.charAt(i) == '(') {
                    left++;
                } else {
                    right++;
                }

                if (left == right) {
                    maxlength = Math.max(maxlength, 2 * left);
                } else if (left >= right) {
                    left = right = 0;
                }
            }
            return maxlength;
        }
    }

    class Solution_Practice {
        public int longestValidParentheses(String s) {
            if (null == s || s.length() == 0) {
                return 0;
            }

            char[] chars = s.toCharArray();

            int l = 0;
            int r = 0;
            int res = 0;

            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '(') {
                    l++;
                } else {
                    r++;
                }

                if (r == l) {
                    res = Math.max(res, 2 * l);
                } else if (r > l) {
                    l = r = 0;
                }
            }

            l = r = 0;

            for (int i = chars.length - 1; i >= 0; i--) {
                if (chars[i] == '(') {
                    l++;
                } else {
                    r++;
                }

                if (r == l) {
                    res = Math.max(res, 2 * l);
                } else if (l > r) {
                    l = r = 0;
                }
            }

            return res;
        }
    }
}
