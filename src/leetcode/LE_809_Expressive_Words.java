package src.leetcode;

import java.util.ArrayList;
import java.util.List;

public class LE_809_Expressive_Words {
    /**
     * Sometimes people repeat letters to represent extra feeling,
     * such as "hello" -> "heeellooo", "hi" -> "hiiii".  In these
     * strings like "heeellooo", we have groups of adjacent letters
     * that are all the same:  "h", "eee", "ll", "ooo".
     *
     * For some given string S, a query word is stretchy if it can
     * be made to be equal to S by any number of applications of
     * the following extension operation: choose a group consisting
     * of characters c, and add some number of characters c to the
     * group so that the size of the group is 3 or more.
     *
     * For example, starting with "hello", we could do an extension
     * on the group "o" to get "hellooo", but we cannot get "helloo"
     * since the group "oo" has size less than 3.  Also, we could do
     * another extension like "ll" -> "lllll" to get "helllllooo".
     * If S = "helllllooo", then the query word "hello" would be
     * stretchy because of these two extension operations:
     * query = "hello" -> "hellooo" -> "helllllooo" = S.
     *
     * Given a list of query words, return the number of words that are stretchy.
     *
     *
     * Example:
     * Input:
     * S = "heeellooo"
     * words = ["hello", "hi", "helo"]
     * Output: 1
     * Explanation:
     * We can extend "e" and "o" in the word "hello" to get "heeellooo".
     * We can't extend "helo" to get "heeellooo" because the group "ll" is not size 3 or more.
     *
     *
     * Notes:
     *
     * 0 <= len(S) <= 100.
     * 0 <= len(words) <= 100.
     * 0 <= len(words[i]) <= 100.
     * S and all words in words consist only of lowercase letters
     *
     * Medium
     */

    class Solution1 {
        public int expressiveWords(String S, String[] words) {
            if (S == null || S.length() == 0) return 0;

            int res = 0;
            for (String word : words) {
                if (isMatch(S, word)) {
                    res++;
                }
            }
            return res;
        }

        private boolean isMatch(String S, String word) {
            if (word == null) return false;

            int i = 0, j = 0;

            /**
             * two pointers, 边走边比较，不用先处理S
             */
            while (i < S.length() && j < word.length()) {
                if (S.charAt(i) == word.charAt(j)) {
                    int len1 = getLen(S, i);
                    int len2 = getLen(word, j);

                    /**
                     * 1."len1 < 3 && len1 != len2)", example:
                     *   S : hellooo
                     *   word : helo
                     *
                     * 2."(len1 >= 3 && len1 < len2))", example:
                     *   S : helloooo
                     *   word : helloooooooo
                     */
                    if ((len1 < 3 && len1 != len2) || (len1 >= 3 && len1 < len2)) {
                        return false;
                    }

                    i += len1;
                    j += len2;
                } else {
                    return false;
                }
            }

            /**
             * !!!
             */
            return i == S.length() && j == word.length();
        }

        private int getLen(String s, int start) {
            int idx = start;
            while (idx < s.length() && s.charAt(start) == s.charAt(idx)) {
                idx++;
            }

            return idx - start;
        }
    }

    /**
     * https://leetcode.com/problems/expressive-words/solution/
     *
     * Run length compression
     */
    class Solution2 {
        public int expressiveWords(String S, String[] words) {
            RLE R = new RLE(S);
            int ans = 0;

            search:
            for (String word : words) {
                RLE R2 = new RLE(word);
                if (!R.key.equals(R2.key)) continue;
                for (int i = 0; i < R.counts.size(); ++i) {
                    int c1 = R.counts.get(i);
                    int c2 = R2.counts.get(i);
                    if (c1 < 3 && c1 != c2 || c1 < c2)
                        continue search;
                }
                ans++;
            }
            return ans;
        }
    }

    class RLE {
        String key;
        List<Integer> counts;

        public RLE(String S) {
            StringBuilder sb = new StringBuilder();
            counts = new ArrayList();

            char[] ca = S.toCharArray();
            int N = ca.length;
            int prev = -1;
            for (int i = 0; i < N; ++i) {
                if (i == N - 1 || ca[i] != ca[i + 1]) {
                    sb.append(ca[i]);
                    counts.add(i - prev);
                    prev = i;
                }
            }

            key = sb.toString();
        }
    }
}
