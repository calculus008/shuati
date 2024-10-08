package leetcode;

/**
 * Created by yuank on 2/27/18.
 */
public class LE_44_Wildcards_Matching {
    /**
        Implement wildcard pattern matching with support for '?' and '*'.

        '?' Matches any single character.
        '*' Matches any sequence of characters (including the empty sequence).

        The matching should cover the entire input string (not partial).

        The function prototype should be:
        bool isMatch(const char *s, const char *p)

        Some examples:
        isMatch("aa","a") → false
        isMatch("aa","aa") → true
        isMatch("aaa","aa") → false
        isMatch("aa", "*") → true
        isMatch("aa", "a*") → true
        isMatch("ab", "?*") → true
        isMatch("aab", "c*a*b") → false

        Hard

        https://leetcode.com/problems/wildcard-matching/description/
     */

    class Solution_backtracking_clean {
        public boolean isMatch(String s, String p) {
            int sLen = s.length(), pLen = p.length();
            int sIdx = 0, pIdx = 0;
            int pTmpIdx = -1, sTmpIdx = -1;

            while (sIdx < sLen) {
                if (pIdx < pLen && (p.charAt(pIdx) == '?' || p.charAt(pIdx) == s.charAt(sIdx))) {
                    sIdx++;
                    pIdx++;
                } else if (pIdx < pLen && p.charAt(pIdx) == '*') {
                    pTmpIdx = pIdx;
                    sTmpIdx = sIdx;
                    pIdx++;
                } else if (pTmpIdx == -1){
                    return false;
                } else {
                    pIdx = pTmpIdx + 1;
                    sIdx = sTmpIdx + 1;
                    sTmpIdx = sIdx;
                }
            }

            for (int i = pIdx; i < pLen; i++) {
                if (p.charAt(i) != '*') {
                    return false;
                }
            }

            return true;
        }
    }
    class Solution_backtracking {
        /**
         * Time:
         * Best case  O(min(s, p))
         * Worst case O(s * p)
         *
         * Space: O(1)
         */
        public boolean isMatch(String s, String p) {
            int sLen = s.length(), pLen = p.length();
            int sIdx = 0, pIdx = 0;
            int starIdx = -1, sTmpIdx = -1;

            while (sIdx < sLen) {
                // If the pattern character = string character
                // or pattern character = '?'
                if (pIdx < pLen && (p.charAt(pIdx) == '?' || p.charAt(pIdx) == s.charAt(sIdx))) {
                    ++sIdx;
                    ++pIdx;
                    // If pattern character = '*'
                } else if (pIdx < pLen && p.charAt(pIdx) == '*') {
                    // Check the situation
                    // when '*' matches no characters
                    starIdx = pIdx;
                    sTmpIdx = sIdx;
                    ++pIdx;
                    // If pattern character != string character
                    // or pattern is used up
                    // and there was no '*' character in pattern
                } else if (starIdx == -1) {
                    return false;
                    // If pattern character != string character
                    // or pattern is used up
                    // and there was '*' character in pattern before
                } else {
                    // Backtrack: check the situation
                    // when '*' matches one more character
                    pIdx = starIdx + 1;
                    sIdx = sTmpIdx + 1;
                    sTmpIdx = sIdx;
                }
            }

            // The remaining characters in the pattern should all be '*' characters
            for (int i = pIdx; i < pLen; i++) {
                if (p.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * ********************************************
     */

    /**
     * Time : O(m * n), best case O(min(m, n))
     * Space : O(1)
     *
     * Worst Case : After we get to 'b' (last char in p),
     * we need to move all the way back to the first 'a' in p
     * s = "aaaaaaaaaaaaaaaaaaaa" p = "*aaaaaaaaaaaaaaaaaab"
     *
     * https://leetcode.com/problems/wildcard-matching/solution/
     * Solution3
     * "Backtrack in no match situation: come back to the previous
     *  star and assume that it matches one more char"
     **/

    /** BL
     *  if there is multiple *, only backtrack to the current last one. as previous ones are all matched.
     * @param str
     * @param pattern
     * @return
     */
    public boolean isMatch_practice(String str, String pattern) {
        if (str == null || pattern == null) return false;

        int p = 0, s = 0, starIdx = -1, match = 0;
        int n = pattern.length();

        while (s < str.length()) {
            if (p < n && (str.charAt(s) == pattern.charAt(p) || pattern.charAt(p) == '?')) {
                s++;
                p++;
            } else if (p < n && pattern.charAt(p) == '*') {
                starIdx = p;
                p++;
                match = s;
            } else if (starIdx != -1) {
                p = starIdx;
                match++;
                s = match;
            } else {
                return false;
            }
        }

        while (p < pattern.length() && pattern.charAt(p) == '*') {
            p++;
        }

        return p == pattern.length();
    }

    /**
     * Preferred version
     * same version from leetocde discussion
     * https://leetcode.com/problems/wildcard-matching/discuss/17810/Linear-runtime-and-constant-space-solution
     *
     * https://leetcode.com/problems/wildcard-matching/solution/
     * Approach 3: Backtracking (!!!)
     *
     * "Let's just pick up the first opportunity "matches zero characters" and proceed further.
     * If this assumption would lead in "no match" situation, then backtrack : come back to the
     * previous star, assume now that it matches one more character (one) and proceed again.
     * Again "no match" situation? Backtrack again : come back to the previous star, and assume
     * now that it matches one more character (two), etc."
     */
    boolean comparison(String str, String pattern) {
        int s = 0, p = 0, match = 0, starIdx = -1;
        while (s < str.length()){
            /**
             * advancing both pointers
             */
            if (p < pattern.length()  && (pattern.charAt(p) == '?' || str.charAt(s) == pattern.charAt(p))){
                s++;
                p++;
            }

            /**
             * '*' found, only advancing pattern pointer
             *
             * check the situation when '*' matches no char
             *
             * starIdx : remember location of '*' in pattern
             * match : remember index in s when the last '*" appears
             */
            else if (p < pattern.length() && pattern.charAt(p) == '*'){
                starIdx = p;
                match = s;
                p++;
            }

            /**
             * the last pattern pointer was *, advancing string pointer
             *
             * !!!
             * If pattern character != string character
             *   OR
             * pattern is used up
             *     AND
             * there was '*' character in pattern before :
             *
             * Backtrack check the situation when '*' matches one more character
             */
            else if (starIdx != -1){
                p = starIdx + 1;
                match++;
                s = match;
            }

            /**
             * current pattern pointer is not star, last pattern pointer was not *
             * characters do not match
             */
            else return false;
        }

        /**
         * check for remaining characters in pattern
         */
        while (p < pattern.length() && pattern.charAt(p) == '*') {
            p++;
        }

        return p == pattern.length();
    }

    class Solution1 {
        public boolean isMatch(String s, String p) {
            int sp = 0;
            int pp = 0;
            int match = 0;
            int star = -1;

            while (sp < s.length()) {
                if (pp < p.length() && (p.charAt(pp) == s.charAt(sp) || p.charAt(pp) == '?')) {
                    pp++;
                    sp++;
                    System.out.println("char match, sp=" + sp + ", pp=" + pp);
                } else if (pp < p.length() && p.charAt(pp) == '*') { //"bbarc", "*c"
                    //star saves the indx in p for the last time we see '*'
                    star = pp;

                    /**
                     * Save the comparison point in s when the last '*" appears,
                     * or the index in s when the last '*" appears:
                     *   #1.sp does not increase here
                     *      reason: try to match case like :
                     *        "abcdef"
                     *        "abc*def"
                     *      as '*' can match to empty string, we don't move sp in case
                     *      we need to use '*' in p to match empty string.
                     *
                     *   #2.Why use 'match'? For case like:
                     *         "aaaa"
                     *         "***a"
                     *      need to move pp back to the next of the last '*' after pp is
                     *      already out of array bound by using "pp = start + 1".
                     *
                     *      when bringing back pp, we need sp to stay at the location in the last loop.
                     *      Therefore we can't simply increase sp in "post star" branch, we need to use
                     *      "match" to remember the last location.
                     *
                     *       output for "sp++"
                     *       star, sp=0, pp=1, match=0
                     *       star, sp=0, pp=2, match=0
                     *       star, sp=0, pp=3, match=0
                     *       char match, sp=1, pp=4
                     *       post star, sp=2, pp=3, match=1
                     *       char match, sp=3, pp=4
                     *       post star, sp=4, pp=3, match=2
                     *       final pp=3
                     *       false
                     *
                     *       output for using "sp = match"
                     *       star, sp=0, pp=1, match=0
                     *       star, sp=0, pp=2, match=0
                     *       star, sp=0, pp=3, match=0
                     *       char match, sp=1, pp=4
                     *       post star, sp=1, pp=3, match=1
                     *       char match, sp=2, pp=4
                     *       post star, sp=2, pp=3, match=2
                     *       char match, sp=3, pp=4
                     *       post star, sp=3, pp=3, match=3
                     *       char match, sp=4, pp=4
                     *       final pp=4
                     *       true
                     */

                    match = sp;
                    pp++;

                    System.out.println("star, start=" + star + " sp=" + sp + ", pp=" + pp + ", match=" + match);
                } else if (star != -1) {//已经见过*了
                    //move to the one next to '*' in p
                    pp = star + 1;
                    match++;
                    sp = match;
//                sp++;

                    System.out.println("post star, sp=" + sp + ", pp=" + pp + ", match=" + match);
                } else {
                    System.out.println("return false, sp=" + sp + ", pp=" + pp);
                    return false;
                }
            }

            while (pp < p.length() && p.charAt(pp) == '*') {
                pp++;
            }

            System.out.println("final pp=" + pp);

            return pp == p.length();
        }

//        public static void main(String[] args) {
//            System.out.println(isMatch("aaaa", "***a"));
//        }

        /**
         * same algorithm, use different variable names
         */
        public boolean isMatch1(String s, String p) {
            if (s == null || p == null) return false;

            /**
             * sm : marker on s
             * pm : marker on p, save the last index so far that has '*' ('star' in first version)
             */
            int sp = 0, pp = 0, sm = 0, pm = -1;
            while (sp < s.length()) {
                if (pp < p.length() && (s.charAt(sp) == p.charAt(pp) || p.charAt(pp) == '?')) {
                    sp++;
                    pp++;
                } else if (pp < p.length() && p.charAt(pp) == '*') {
                    pm = pp;
                    sm = sp;
                    pp++;
                } else if (pm != -1) {
                    pp = pm + 1;
                    sm++;
                    sp = sm;
                } else {
                    return false;
                }
            }

            while (pp < p.length() && p.charAt(pp) == '*') {
                pp++;
            }

            return pp == p.length();
        }
    }

    /**
     * Solution 2
     * 使用深度优先搜索 + 记忆化的版本。
     * 用一个二维的 boolean 数组来当记忆化数组，记录 s 从 sIndex 开始的后缀 能够匹配上 p 从 pIndex 开始的后缀
     * Time : O(n ^ 2) (or O(m * n)?)
     */
    class Solution2 {
        public boolean isMatch2_JiuZhang(String s, String p) {
            if (s == null || p == null) {
                return false;
            }

            boolean[][] memo = new boolean[s.length()][p.length()];
            boolean[][] visited = new boolean[s.length()][p.length()];
            return isMatchHelper(s, 0, p, 0, memo, visited);
        }

        private boolean isMatchHelper(String s, int sIndex,
                                      String p, int pIndex,
                                      boolean[][] memo,
                                      boolean[][] visited) {
            // 如果 p 从pIdex开始是空字符串了，那么 s 也必须从 sIndex 是空才能匹配上
            if (pIndex == p.length()) {
                return sIndex == s.length();
            }

            // 如果 s 从 sIndex 是空，那么p 必须全是 *
            if (sIndex == s.length()) {
                return allStar(p, pIndex);
            }

            if (visited[sIndex][pIndex]) {
                return memo[sIndex][pIndex];
            }

            char sChar = s.charAt(sIndex);
            char pChar = p.charAt(pIndex);
            boolean match;

            if (pChar == '*') {
                match = isMatchHelper(s, sIndex, p, pIndex + 1, memo, visited) ||
                        isMatchHelper(s, sIndex + 1, p, pIndex, memo, visited);
            } else {
                match = charMatch(sChar, pChar) &&
                        isMatchHelper(s, sIndex + 1, p, pIndex + 1, memo, visited);
            }

            visited[sIndex][pIndex] = true;
            memo[sIndex][pIndex] = match;
            return match;
        }

        private boolean charMatch(char sChar, char pChar) {
            return (sChar == pChar || pChar == '?');
        }

        private boolean allStar(String p, int pIndex) {
            for (int i = pIndex; i < p.length(); i++) {
                if (p.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }
    }


    /**
     * Same as Soltuion 2, written by myself
     */
    class Solution3 {
        public boolean isMatch2_me(String s, String p) {
            if (s == null || p == null) {
                return false;
            }

            boolean[][] visited = new boolean[s.length()][p.length()];
            boolean[][] mem = new boolean[s.length()][p.length()];

            return helper(s, 0, p, 0, visited, mem);
        }

        private boolean helper(String s, int sIdx, String p, int pIdx, boolean[][] visited, boolean[][] mem) {
            if (pIdx == p.length()) {
                return sIdx == s.length();
            }

            if (sIdx == s.length()) {
                return isAllStar(p, pIdx);
            }

            if (visited[sIdx][pIdx]) {
                return mem[sIdx][pIdx];
            }

            boolean match = false;
            char sChar = s.charAt(sIdx);
            char pChar = p.charAt(pIdx);

            if (pChar == '*') {
                /**
                 * !!!
                 * case 1 : '*' matches 1 or more chars, bypass the first char in s, recurse
                 * case 2 : '*' is used as empty char, move one position in p.
                 */
                match = helper(s, sIdx + 1, p, pIdx, visited, mem)
                        || helper(s, sIdx, p, pIdx + 1, visited, mem);
            } else {
                /**
                 * !!! Must have 'isMatch' here
                 */
                match = isMatch(sChar, pChar) && helper(s, sIdx + 1, p, pIdx + 1, visited, mem);
            }

            visited[sIdx][pIdx] = true;
            mem[sIdx][pIdx] = match;

            return match;
        }

        private boolean isMatch(char c1, char c2) {
            if (c1 == c2 || c2 == '?') {
                return true;
            }
            return false;
        }

        private boolean isAllStar(String p, int pIdx) {
            for (int i = pIdx; i < p.length(); i++) {
                if (p.charAt(i) != '*') {
                    return false;
                }
            }

            return true;
        }
    }
}
