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
     **/
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        if (n <= 0) {
            return res;
        }

        helper(n, res, new StringBuilder(), 0, 0);
        return res;
    }

    private void helper(int n, List<String> res, StringBuilder sb, int left, int right){
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
