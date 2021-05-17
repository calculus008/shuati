package leetcode;

public class LE_10_Regular_Expression_Matching_2 {
    /**
     * Brutal Force Recursion
     *
     * Time Complexity:
     * Let m, n be the lengths of the text and the pattern respectively.
     * In the worst case, a call to match(text[i:], pattern[2j:]) will be made c(i+j, i) times.
     * (c=> combination, that break down into (i+j)!/(i)!.(j)!)
     *
     * Thus, the complexity has the order:  O((m + n) * 2 ^ (m + n / 2))
     *
     *
     * Time : O((m + n) * 2 ^ (m + n / 2))
     * Space : O((m + n) * 2 ^ (m + n / 2))  -> O(m ^ 2 + n ^ 2)
     */

    class Solution {
        public boolean isMatch(String s, String p) {
            if (p.isEmpty()) return s.isEmpty();

            boolean firstMatch = !s.isEmpty() && (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.');

            if (p.length() >= 2 && p.charAt(1) == '*'){
                /**
                 * s : ab####
                 * p : a*####
                 *     or
                 *     .*####
                 *
                 * "isMatch(s, p.substring(2))" : 'x*' in pattern counts as empty string.
                 *  s : ab
                 *  p : d*ab
                 *  After we ignore the first 2 chars in p, we will have a match
                 *
                 * "firstMatch && isMatch(s.substring(1), p)" : for case that the first char in p is '.'
                 *  s: aaa
                 *  p: a*
                 *  After we remove the first 'a' from s and recurse several times, we have s as "" and
                 *  p as "a*", we will have a match going through isMatch(s, p.substring(2)).
                 */
                return isMatch(s, p.substring(2)) || (firstMatch && isMatch(s.substring(1), p));
            } else {
                return firstMatch && isMatch(s.substring(1), p.substring(1));
            }
        }
    }

    /**
     * DP Bottom-Up
     */
    class Solution_Bottom_Up_Practice {
        class Solution {
            public boolean isMatch(String s, String p) {
                if (s == null || p == null) return false;

                int m = s.length();
                int n = p.length();

                boolean[][] dp = new boolean[m + 1][n + 1];
                /**
                 * !!!
                 */
                dp[0][0] = true;

                /**
                 * "j <= n"
                 * "j = 2"
                 */
                for (int j = 2; j <= n; j++) {
                    if (p.charAt(j - 1) == '*') {
                        dp[0][j] = dp[0][j - 2];
                    }
                }

                for (int i = 1; i <= m; i++) {
                    for (int j = 1; j <= n; j++) {
                        char curS = s.charAt(i - 1);
                        char curP = p.charAt(j - 1);

                        /**
                         * "curP = '.'
                         */
                        if (curS == curP || curP == '.') {
                            dp[i][j] = dp[i - 1][j - 1];
                        } else if (curP == '*') {
                            char preP = p.charAt(j - 2);

                            if (preP != curS && preP != '.') {
                                dp[i][j] = dp[i][j - 2];
                            } else {
                                dp[i][j] = dp[i][j - 2] || dp[i - 1][j] || dp[i - 1][j - 2];
                            }
                        }
                    }
                }

                return dp[m][n];
            }
        }
    }

    /**
     * DP Top Down recursion
     *
     *
     */
    class Solution_Top_Down {
        public boolean isMatch(String s, String p) {
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

            if (pIdx + 1 < p.length() && p.charAt(pIdx + 1) == '*') {
                match = (isMatch(sChar, pChar)
                        && helper(s, sIdx + 1, p, pIdx, visited, mem)
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

        private boolean isEmpty(String p, int pIdx) {
            for (int i = pIdx; i < p.length(); i += 2) {
                if (i + 1 >= p.length() || p.charAt(i + 1) != '*') {
                    return false;
                }
            }
            return true;
        }
    }

    class Solution_Bottom_Up {
        public boolean isMatch(String s, String p) {
            // corner case
            if (s == null || p == null) return false;

            int m = s.length();
            int n = p.length();

            // dp[i][j] represents if the 1st i characters in s can match the 1st j characters in p
            boolean[][] dp = new boolean[m + 1][n + 1];

            /**initialization:
             * 1. dp[0][0] = true, since empty string matches empty pattern
             **/
            dp[0][0] = true;

            /**
             * 2. dp[i][0] = false (which is default value of the boolean array) since empty pattern cannot
             *    match non-empty string
             * 3. dp[0][j]: what pattern matches empty string ""? It should be #*#*#*#*..., or (#*)* if allow
             *    me to represent regex using regex :P,
             *
             *    and for this case we need to check manually:
             *    as we can see, the length of pattern should be even && the character at the even position should be *,
             *    thus for odd length, dp[0][j] = false which is default. So we can just skip the odd position,
             *    i.e. j starts from 2, the interval of j is also 2.
             *
             *    and notice that the length of repeat sub-pattern #* is only 2, we can just make use of dp[0][j - 2] rather than scanning j length each time
             *    for checking if it matches #*#*#*#*.
             **/
            for (int j = 2; j < n + 1; j += 2) {
                if (p.charAt(j - 1) == '*' && dp[0][j - 2]) {
                    dp[0][j] = true;
                }
            }

            /**
             Induction rule is very similar to edit dirs, where we also consider from the end.
             And it is based on what character in the pattern we meet.

             1. if p.charAt(j) == s.charAt(i), dp[i][j] = dp[i - 1][j - 1]
                ######a(i)
                ####a(j)
             2. if p.charAt(j) == '.', dp[i][j] = dp[i - 1][j - 1]
                #######a(i)
                ####.(j)
             3. if p.charAt(j) == '*':
                1. if p.charAt(j - 1) != '.' && p.charAt(j - 1) != s.charAt(i), then b* is counted as empty.
                   dp[i][j] = dp[i][j - 2]
                   #####a(i)
                   ####b*(j)
                2.if p.charAt(j - 1) == '.' || p.charAt(j - 1) == s.charAt(i):
                   ######a(i)
                   ####.*(j)

                   #####a(i)
                   ###a*(j)

                   a* : can be counted as empty  - ''
                        can be counted once      - 'a'
                        can be counted multiple  - 'aa'

                  2.1 if p.charAt(j - 1) is counted as empty,
                      p back 2 positions, s at cur position. Therefore :
                      dp[i][j] = dp[i][j - 2]

                    s   #####a
                             ^
                    p   ####
                           ^

                  2.2 if counted as one, then dp[i][j] = dp[i - 1][j - 2]
                      p at position (j - 1) equals to s at position i, so
                      we look at their previous position : p at (j - 2) and s at (i - 1)

                    s   #####a
                            ^
                    p   ####a
                           ^
                  2.3 if counted as multiple, then dp[i][j] = dp[i - 1][j]
                      The final 'a' in s will be merged into group in p represented as "a*"
                    s   #####a
                            ^
                    p   ####a*
                             ^

              abcaa

              abca*   abc, abca, abcaa

             recap:
             dp[i][j] = dp[i - 1][j - 1]
             dp[i][j] = dp[i - 1][j - 1]
             dp[i][j] = dp[i][j - 2]
             dp[i][j] = dp[i][j - 2]
             dp[i][j] = dp[i - 1][j - 2]
             dp[i][j] = dp[i - 1][j]

             Observation: from above, we can see to get dp[i][j], we need to know previous elements in dp,
             i.e. we need to compute them first.
             which determines i goes from 1 to m - 1, j goes from 1 to n + 1
            **/

            for (int i = 1; i < m + 1; i++) {
                for (int j = 1; j < n + 1; j++) {
                    char curS = s.charAt(i - 1);
                    char curP = p.charAt(j - 1);

                    if (curS == curP || curP == '.') {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else if (curP == '*') {
                        char preCurP = p.charAt(j - 2);

                        if (preCurP != '.' && preCurP != curS) {
                            dp[i][j] = dp[i][j - 2];
                        } else {
                            dp[i][j] = (dp[i][j - 2] || dp[i - 1][j - 2] || dp[i - 1][j]);
                        }
                    }
                }
            }

            return dp[m][n];
        }
    }
}
