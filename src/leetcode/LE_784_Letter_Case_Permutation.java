package leetcode;

import java.util.ArrayList;
import java.util.List;

public class LE_784_Letter_Case_Permutation {
    /**
         Given a string S, we can transform every letter individually to be lowercase
         or uppercase to create another string.  Return a list of all possible strings we could create.

         Examples:
         Input: S = "a1b2"
         Output: ["a1b2", "a1B2", "A1b2", "A1B2"]

         Input: S = "3z4"
         Output: ["3z4", "3Z4"]

         Input: S = "12345"
         Output: ["12345"]
         Note:

         S will be a string with length between 1 and 12.
         S will consist only of letters or digits.

         Medium
     */

    /**
     * http://zxi.mytechroad.com/blog/searching/leetcode-784-letter-case-permutation/
     *
     * DSF
     * 6 ms
     *
     * Time  : O(n * 2 ^ l), l is number of letters in S
     * Space : O(n) + O(n * 2 ^ l)
     */

    class Solution1 {
        public List<String> letterCasePermutation(String S) {
            List<String> res = new ArrayList<>();
            helper(S, 0, res, new StringBuilder());
            return res;
        }

        private void helper(String s, int pos, List<String> res, StringBuilder sb) {
            if (pos == s.length()) {
                res.add(sb.toString());
                return;
            }

            /**
             * !!! save current length
             */
            int n = sb.length();
            char temp = s.charAt(pos);
            helper(s, pos + 1, res, sb.append(temp));

            /**
             * !!! recover after each recursion
             */
            sb.setLength(n);

            if (!Character.isLetter(temp)) {
                return;
            }

            char c = Character.isLowerCase(temp) ? Character.toUpperCase(temp) : Character.toLowerCase(temp);
            helper(s, pos + 1, res, sb.append(c));

            /**
             * !!! recover after each recursion
             */
            sb.setLength(n);
        }
    }

    class Solution2 {
        public List<String> letterCasePermutation(String S) {
            List<String> ans = new ArrayList<>();
            dfs(S.toCharArray(), 0, ans);
            return ans;
        }

        private void dfs(char[] S, int i, List<String> ans) {
            if (i == S.length) {
                ans.add(new String(S));
                return;
            }
            dfs(S, i + 1, ans);
            if (!Character.isLetter(S[i])) return;
            S[i] ^= 1 << 5;
            dfs(S, i + 1, ans);
            S[i] ^= 1 << 5;
        }
    }
}