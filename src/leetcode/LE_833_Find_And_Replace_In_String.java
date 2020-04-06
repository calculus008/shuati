package src.leetcode;

import java.util.HashMap;
import java.util.Map;

public class LE_833_Find_And_Replace_In_String {
    /**
     * To some string S, we will perform some replacement operations that
     * replace groups of letters with new ones (not necessarily the same size).
     *
     * Each replacement operation has 3 parameters: a starting index i, a
     * source word x and a target word y.  The rule is that if x starts at
     * position i in the original string S, then we will replace that
     * occurrence of x with y.  If not, we do nothing.
     *
     * For example, if we have S = "abcd" and we have some replacement operation
     * i = 2, x = "cd", y = "ffff", then because "cd" starts at position 2 in
     * the original string S, we will replace it with "ffff".
     *
     * Using another example on S = "abcd", if we have both the replacement
     * operation i = 0, x = "ab", y = "eee", as well as another replacement
     * operation i = 2, x = "ec", y = "ffff", this second operation does
     * nothing because in the original string S[2] = 'c', which doesn't
     * match x[0] = 'e'.
     *
     * All these operations occur simultaneously.  It's guaranteed that
     * there won't be any overlap in replacement: for example, S = "abc",
     * indexes = [0, 1], sources = ["ab","bc"] is not a valid test case.
     *
     * Example 1:
     *
     * Input: S = "abcd", indexes = [0,2], sources = ["a","cd"], targets = ["eee","ffff"]
     * Output: "eeebffff"
     * Explanation: "a" starts at index 0 in S, so it's replaced by "eee".
     * "cd" starts at index 2 in S, so it's replaced by "ffff".
     * Example 2:
     *
     * Input: S = "abcd", indexes = [0,2], sources = ["ab","ec"], targets = ["eee","ffff"]
     * Output: "eeecd"
     * Explanation: "ab" starts at index 0 in S, so it's replaced by "eee".
     * "ec" doesn't starts at index 2 in the original S, so we do nothing.
     * Notes:
     *
     * 0 <= indexes.length = sources.length = targets.length <= 100
     * 0 < indexes[i] < S.length <= 1000
     * All characters in given inputs are lowercase letters.
     *
     * Medium
     */

    /**
     * Time  : O(n * (m * n)), n - length of S, m - number of replacement
     * Space : O(n)
     */
    class Solution1 {
        public String findReplaceString(String S, int[] indexes, String[] sources, String[] targets) {
            if (S == null || S.length() == 0) return S;

            int n = indexes.length;
            /**
             * Map
             * key : index of in S that a replace will hapen
             * val : index of the change list - which change will happen
             */
            Map<Integer, Integer> map = new HashMap<>();

            /**
             * !!!
             * First pass to record all changes and the indexes.
             * We have to do this because the indexes in indexes[]
             * is not in any order, could be like {5, 7, 1}
             */
            for (int i = 0; i < n; i++) {
                /**
                 * !!!
                 * "startsWith()", it takes O(n)
                 */
                if (S.startsWith(sources[i], indexes[i])) {
                    map.put(indexes[i], i);
                }
            }

            /**
             * Second pass to do replacement using dist
             */
            StringBuilder sb = new StringBuilder();
            char[] chs = S.toCharArray();

            int i = 0;
            while (i < chs.length) {
                if (map.containsKey(i)) {
                    int idx = map.get(i);
                    sb.append(targets[idx]);
                    i += sources[idx].length();
                } else {
                    sb.append(chs[i]);
                    i++;
                }
            }

            return sb.toString();
        }
    }
}
