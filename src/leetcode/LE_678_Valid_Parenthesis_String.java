package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 11/23/18.
 */
public class LE_678_Valid_Parenthesis_String {
    /**
         Given a string containing only three types of characters: '(', ')' and '*',
         write a function to check whether this string is valid.
         We define the validity of a string by these rules:

         1.Any left parenthesis '(' must have a corresponding right parenthesis ')'.
         2.Any right parenthesis ')' must have a corresponding left parenthesis '('.
         3.Left parenthesis '(' must go before the corresponding right parenthesis ')'.
         4.'*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string.
         An empty string is also valid.

         Example 1:
         Input: "()"
         Output: True

         Example 2:
         Input: "(*)"
         Output: True

         Example 3:
         Input: "(*))"
         Output: True

         Note:
         The string size will be in the range [1, 100].

         Medium
     */

    /**
     * Solution 1
     * Time  : O(n)
     * Space : O(1)
     *
     * min : 强制匹配左括号，只把'('看作合法的左括号
     * max : 非强制匹配左括号， 把非')'都看作为左括号。
     *
     * min, max : be the smallest and largest possible number of open left brackets after processing the current character in the string.
     *
     * https://leetcode.com/problems/valid-parenthesis-string/discuss/107577/Short-Java-O(n)-time-O(1)-space-one-pass
     *
     * Scan from left to right, and record counts of unpaired ‘(’ for all possible cases.
     *
     * '*' has 3 branches - '(', '', ')'
     *
     * In the very end, we find that there is a route that can reach count == 0.
     * Which means all ‘(’ and ‘)’ are cancelled. So, the original String is valid.
     *
     * Another finding is counts of unpaired ‘(’ for all valid routes are consecutive integers.
     * So we only need to keep a lower and upper bound of that consecutive integers to save space.
     *
     */
    class Solution1 {
        public boolean checkValidString(String s) {
            int min = 0, max = 0;
            for (char c : s.toCharArray()) {
                if (c == '(') {
                    min++;
                } else {
                    min--;
                }

                if (c != ')') {
                    max++;
                } else {
                    max--;
                }

                if (max < 0) {
                    return false;
                }

                min = Math.max(0, min);
            }

            return min == 0;
        }
    }

    /**
     * http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-678-valid-parenthesis-string/
     *
     * DP
     * Recursion + Memization, top-down
     * Time  : O(n ^ 3)
     * Space : O(n ^ 2)
     */
    class Solution2 {
        int[][] dp;

        public boolean checkValidString(String s) {
            int n = s.length();
            dp = new int[n][n];

            for (int[] d : dp) {
                Arrays.fill(d, -1);
            }

            return helper(s, 0, n - 1) == 1 ? true : false;
        }

        private int helper(String s, int i, int j) {
            if (i > j) return 1;

            if (dp[i][j] >= 0) {
                return dp[i][j];
            }

            if (i == j) {
                return s.charAt(i) == '*' ? 1 : 0;
            }

            char l = s.charAt(i);
            char r = s.charAt(j);
            if ((l == '(' || l == '*') &&
                    (r == ')' || r == '*') &&
                    helper(s, i + 1, j - 1) == 1) {
                dp[i][j] = 1;
                return 1;
            }

            for (int p = i; p < j; p++) {
                if (helper(s, i, p) == 1 && helper(s, p + 1, j) == 1) {
                    dp[i][j] = 1;
                    return 1;
                }
            }

            dp[i][j] = 0;
            return 0;
        }
    }
}
