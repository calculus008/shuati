package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 8/7/18.
 */
public class LE_22_Generate_Parentheses {
    /**
         Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

         For example, given n = 3, a solution set is:

         [
         "((()))",
         "(()())",
         "(())()",
         "()(())",
         "()()()"
         ]

         Medium
     */

    /**
     * This is permutation.
     *
     * Time  : O(n!)
     * Space : O(n)
     *
     * Another theory:
     * This analysis is outside the scope of this article, but it turns out this is the n-th Catalan number.
     * which is bounded asymptotically by : 4 ^ n / n * sqrt(n)
     *
     * Time  : O(4 ^ n / sqrt(n))  Each valid sequence has at most n steps during the backtracking procedure.
     * Space : O(4 ^ n / sqrt(n))
     *
     *
     * StringBuilder solution
     **/
    class Solution1 {
        public List<String> generateParenthesis(int n) {
            List<String> res = new ArrayList<>();
            if (n <= 0) {
                return res;
            }

            helper(n, res, new StringBuilder(), 0, 0);
            return res;
        }

        private void helper(int n, List<String> res, StringBuilder sb, int left, int right) {
            if (left + right == 2 * n) {
                res.add(sb.toString());
                return;
            }

            int len = sb.length();
            if (left < n) {
                sb.append("(");
                helper(n, res, sb, left + 1, right);
                sb.setLength(len);
            }

            if (right < left) {
                sb.append(")");
                helper(n, res, sb, left, right + 1);
                sb.setLength(len);
            }
        }
    }

    class Solution2 {
        public List<String> generateParenthesis(int n) {
            List<String> res = new ArrayList<>();
            if (n <= 0) {
                return res;
            }

            helper(0, 0, n, "", res);
            return res;
        }

        private void helper(int l, int r, int n, String temp, List<String> res) {
            if (l == n && r == n) {
                res.add(temp);
                return;
            }

            if (l < n) {
                helper(l + 1, r, n, temp + "(", res);
            }

            if (r < l) {
                helper(l, r + 1, n, temp + ")", res);
            }
        }
    }
}
