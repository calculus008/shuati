package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 4/27/18.
 */
public class LE_301_Remove_Invalid_Parentheses {
    /**
         Remove the minimum number of invalid parentheses in order to make the input string valid. Return all possible results.

         Note: The input string may contain letters other than the parentheses ( and ).

         Examples:
         "()())()" -> ["()()()", "(())()"]
         "(a)())()" -> ["(a)()()", "(a())()"]
         ")(" -> [""]

         Hard
     */

    /**
     https://www.youtube.com/watch?v=2k_rS_u6EBk&t=1178s

     Time :  O(2 ^ (l + r))  Depth of recursion at most : l + r. Each time we delete left or right parentheses, therefore 2 ^ (l + r)
     Space : O((l + r) ^ 2) ~ O(n ^ 2)

     Key:
     1.Find number of unbalanced left and right parenthese, try all possible way to remove them.
     2.Pruning:
         A.For consecutive same parentheses, only delete the first one to avoid duplication.
         B.Delete right parentheses first, then delete left parentheses.
     */
    public List<String> removeInvalidParentheses(String s) {
        List<String> res = new ArrayList<>();
        if (s == null || s.length() == 0) {
            res.add("");//!!!
            return res;
        }

        int l = 0, r = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                l++;
            } else if (c == ')') {
                if (l == 0) {
                    r++;
                } else {
                    l--;
                }
            }
        }

        helper(s, res, 0, l, r);

        return res;
    }

    public void helper(String s, List<String> res, int pos, int l, int r) {
        if (l == 0 && r == 0) {
            if (isValid(s)) {
                res.add(s);
            }
            return;
        }

        for (int i = pos; i < s.length(); i++) {
            /**
             !!! remove duplication - if there are concecutive parenthesis, for a given postion, just need to remove the first one, otherwise there will be duplication in answer.,
             Example : given "()())()", if we don't have the following check, there will be duplicate answer for "()()()"
             **/
            if(i != pos && s.charAt(i) == s.charAt(i - 1)) {
                continue;
            }

            char c = s.charAt(i);
            if (c == '(' || c == ')') {
                StringBuilder sb = new StringBuilder(s);
                sb.deleteCharAt(i);
                String cur = sb.toString();

                /**
                 !!!
                 1.这两个if的顺序不能颠倒。要优先删右括号，再删左括号。
                 2.往下一层recurs的时候，仍然用"i",而不是"i+1",因为在当前层，第i个char已经被删除了，所以往下层走，起始位置依然为i.
                 */
                if (c == ')' && r > 0) {
                    helper(cur, res, i, l, r - 1);
                } else if  (c == '(' && l > 0){
                    helper(cur, res, i, l - 1, r);
                }
            }
        }
    }

    public boolean isValid(String s) {
        int l = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                l++;
            } else if (c == ')') {
                l--;
            }

            if (l < 0) {
                return false;
            }
        }

        return l == 0;
    }

    /**
     * my version
     */
    public List<String> removeInvalidParentheses2(String s) {
        List<String> res = new ArrayList<>();
        if (s == null || s.length() == 0) {//!!! end condition
            res.add("");
            return res;
        }

        int[] invalid = getInvalid(s);
        helper(s, res, 0, invalid[0], invalid[1]);
        return res;
    }

    private void helper2(String s, List<String> res, int pos, int left, int right) {
        if (left == 0 && right == 0)  {
            if (isValid2(s)) {
                res.add(s);
            }
            return;
        }

        for (int i = pos; i < s.length(); i++) {//!!! for loop
            if (i != pos && s.charAt(i) == s.charAt(i - 1)) {//remove duplicates
                continue;
            }

            char c = s.charAt(i);
            if (c == '(' || c == ')') {
                //!!! use sb only for remove a char, each changed String is passed in params and it's not changed, so no need to do recovery
                StringBuilder sb = new StringBuilder(s);
                sb.deleteCharAt(i);
                String temp = sb.toString();

                if (c == ')' && right > 0) {
                    helper2(temp, res, i, left, right - 1);
                }

                if (c == '(' && left > 0) {
                    helper2(temp, res, i, left - 1, right);
                }
            }
        }
    }

    private int[] getInvalid(String s) {
        int[] res = new int[2];
        for (char c : s.toCharArray()) {
            if (c == '(') {
                res[0]++;
            } else if (c == ')') {
                if (res[0] == 0) {
                    res[1]++;
                } else {
                    res[0]--;
                }
            }
        }

        return res;
    }

    private boolean isValid2(String s) {
        int[] res = getInvalid(s);
        return res[0] == 0 && res[1] == 0;
    }

}
