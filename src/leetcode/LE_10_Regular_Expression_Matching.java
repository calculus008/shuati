package leetcode;

/**
 * Created by yuank on 8/4/18.
 */
public class LE_10_Regular_Expression_Matching {
    /**
         Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.

         '.' Matches any single character.
         '*' Matches zero or more of the preceding element.
         The matching should cover the entire input string (not partial).

         Note:

         s could be empty and contains only lowercase letters a-z.
         p could be empty and contains only lowercase letters a-z, and characters like . or *.
         Example 1:

         Input:
         s = "aa"
         p = "a"
         Output: false
         Explanation: "a" does not match the entire string "aa".

         Example 2:

         Input:
         s = "aa"
         p = "a*"
         Output: true
         Explanation: '*' means zero or more of the precedeng element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".

         Example 3:

         Input:
         s = "ab"
         p = ".*"
         Output: true
         Explanation: ".*" means "zero or more (*) of any character (.)".

         Example 4:

         Input:
         s = "aab"
         p = "c*a*b"
         Output: true
         Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore it matches "aab".

         Example 5:

         Input:
         s = "mississippi"
         p = "mis*is*p*."
         Output: false

         Hard
     */

    class Solution_Practice {
        public boolean isMatch(String s, String p) {
            if (s == null || p == null) return false;

            int m = s.length();
            int n = p.length();
            boolean[][] dp = new boolean[m + 1][n + 1];
            dp[0][0] = true;

            for (int j = 2; j <= n; j++) {
                if (p.charAt(j - 1) == '*') {
                    dp[0][j] = dp[0][j - 2];
                }
            }

            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    char sc = s.charAt(i - 1);
                    char pc = p.charAt(j - 1);

                    if (sc == pc || pc == '.') {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else if (pc == '*') {
                        if (dp[i][j - 2]) {
                            /**
                             * if making 'a*' as empty string in p can match with s,
                             * dp[i][j] is true.
                             */
                            dp[i][j] = true;
                        } else if (sc == p.charAt(j - 2) || p.charAt(j - 2) == '.') {
                            dp[i][j] = dp[i - 1][j];
                        }
                    }
                }
            }

            return dp[m][n];
        }
    }

    class Solution {
        /**
         *  Solution 1
         *  DP

            https://leetcode.com/problems/regular-expression-matching/discuss/5651
            https://www.youtube.com/watch?v=c5vsle60Uw8&list=PLvyIyKZVcfAmSRMbPFWjWP6gBwPWiWwSU&index=10

             1, If p.charAt(j) == s.charAt(i) :  dp[i][j] = dp[i-1][j-1];
             2, If p.charAt(j) == '.' : dp[i][j] = dp[i-1][j-1];
             3, If p.charAt(j) == '*':
                here are two sub conditions:
                        1   if p.charAt(j-1) != s.charAt(i) : dp[i][j] = dp[i][j-2]  //in this case, a* only counts as empty
                        2   if p.charAt(j-1) == s.charAt(i) or p.charAt(j-1) == '.':
                                       dp[i][j] = dp[i-1][j]    //in this case, a* counts as multiple a !!!
                                    or dp[i][j] = dp[i][j-1]   // in this case, a* counts as single a
                                    or dp[i][j] = dp[i][j-2]   // in this case, a* counts as empty

                           Example of case 3.2 ,a* counts as multiple a:
                           s : aaa, p : a*
                           i = 0, j = 0 : a equals a
                           i = 1, j = 1 : char at current j is '*', so we need to see if previous char in p matches current char in s
                                          ('*' is used as the previous char)
                           i = 2, j = 1 : check the matching state of previous char in s,'aaa' - 'a*', combine a in index 1 and 2 in s to match '*' in p
         **/

        public boolean isMatch(String s, String p) {
            if (s == null || p == null) return false;

            boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
            dp[0][0] = true;//!!!

            /**
                pre-process for case 3.1 when i is 0, init dp[0][j] (since i starts from 1 in the main for loop)
                dp[0][j] : empty string matches with pattern string.
                dp[0][j - 1] == true, then current '*' can take the meaning of matching 0 previous char('*' as empty), so dp[0][j] is true
                dp[0][j - 2] == true, then 'a*' as empty, so dp[0][j] = true
             **/
            for (int j = 1; j < dp[0].length; j++) {
                if (p.charAt(j - 1) == '*') {
                    if (dp[0][j - 1] || (j > 1 && dp[0][j - 2])) {
                        dp[0][j] = true;
                    }
                }
            }

            for (int i = 1; i < dp.length; i++) {
                for (int j = 1; j < dp[0].length; j++) {
                    if(s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.') {
                        dp[i][j] = dp[i - 1][j - 1];
                    }

                    if(p.charAt(j - 1) == '*') {
                        if(s.charAt(i - 1) != p.charAt(j - 2) && p.charAt(j - 2) != '.') {
                            dp[i][j] = dp[i][j - 2]; //Case 3.1 : a* only counts as empty, so takes status of j-2 (j moves back 2)
                        } else {
                            dp[i][j] = (dp[i][j - 1] || dp[i][j - 2] || dp[i - 1][j]);
                        }
                    }

                }
            }

            return dp[s.length()][p.length()];
        }
    }


    /**
     * DFS
     * Use the same template as LE_44_Wildcards_Matching
     */
    public boolean isMatch_JiuZhang(String s, String p) {
        if (s == null || p == null) {
            return false;
        }

        boolean[][] visited = new boolean[s.length()][p.length()];
        boolean[][] mem = new boolean[s.length()][p.length()];

        return helper(s, 0, p, 0, visited, mem);
    }

    private boolean helper(String s, int sIdx, String p, int pIdx, boolean[][] visited, boolean[][] mem) {
        //"" == ""
        if (pIdx == p.length()) {
            return sIdx == s.length();
        }

        if (sIdx == s.length()) {
            return isEmpty(p, pIdx);
        }

        if (visited[sIdx][pIdx]) {
            return mem[sIdx][pIdx];
        }

        boolean match = false;
        char sChar = s.charAt(sIdx);
        char pChar = p.charAt(pIdx);

        /**
            consider 'a*' as a bundle
            case 1 : See if 'a*' matches multiple chars in s - chars at sIdx and pIdx match (both are 'a'),
                     move on to the next one in s, recurse.
            case 2 : See 'a*' as empty, jump 2 positions and recurse
         **/
        if (pIdx + 1 < p.length() && p.charAt(pIdx + 1) == '*') {
            match = (isMatch(sChar, pChar) && helper(s, sIdx + 1, p, pIdx, visited, mem)
                    || helper(s, sIdx, p, pIdx + 2, visited, mem));
        } else {
            match = isMatch(sChar, pChar) && helper(s, sIdx + 1, p, pIdx + 1, visited, mem);
        }

        visited[sIdx][pIdx] = true;
        mem[sIdx][pIdx] = match;

        return match;
    }

    private boolean isMatch(char c1, char c2) {
        return c1 == c2 || c2 == '.';
    }

    /**
       For p to be matched as empty, it could be :
       **********
       a*b*c*d*

       '*' should always be related with the char before it.
       "i + 1 >= p.length()" -> XXXXa*b*c
     */
    private boolean isEmpty(String p, int pIdx) {
        for (int i = pIdx; i < p.length(); i += 2) {
            if (i + 1 >= p.length() || p.charAt(i + 1) != '*') {
                return false;
            }
        }
        return true;
    }
}
