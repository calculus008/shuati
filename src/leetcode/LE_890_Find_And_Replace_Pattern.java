package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LE_890_Find_And_Replace_Pattern {
    /**
     * You have a list of words and a pattern, and you want
     * to know which words in words matches the pattern.
     *
     * A word matches the pattern if there exists a permutation
     * of letters p so that after replacing every letter x in
     * the pattern with p(x), we get the desired word.
     *
     * (Recall that a permutation of letters is a bijection from
     * letters to letters: every letter maps to another letter,
     * and no two letters dist to the same letter.)
     *
     * Return a list of the words in words that match the given
     * pattern.
     *
     * You may return the answer in any order.
     *
     * Example 1:
     *
     * Input: words = ["abc","deq","mee","aqq","dkd","ccc"], pattern = "abb"
     * Output: ["mee","aqq"]
     * Explanation: "mee" matches the pattern because there is a permutation {a -> m, b -> e, ...}.
     * "ccc" does not match the pattern because {a -> c, b -> c, ...} is not a permutation,
     * since a and b dist to the same letter.
     *
     *
     * Note:
     *
     * 1 <= words.length <= 50
     * 1 <= pattern.length = words[i].length <= 20
     *
     * Same as LE_205_Isomorphic_Strings
     *
     * Medium
     */

    /**
     * Use solutions for LE_205_Isomorphic_Strings in isMatch()
     *
     */


    /**
     * Two Maps
     * https://leetcode.com/problems/find-and-replace-pattern/solution/
     *
     * Time  : O(N * K) , N - number of words, K - length of a word
     * Space : O(N * K)
     *
     * If say, the first letter of the pattern is "a", and the first letter
     * of the word is "x", then in the permutation, "a" must dist to "x".
     *
     * We can write this bijection using two maps: a forward dist \text{m1}
     * and a backwards dist \text{m2}.
     *
     * Then, if there is a contradiction later, we can catch it via one of
     * the two maps. For example, if the (word, pattern) is ("aa", "xy"),
     * we will catch the mistake in m1 :
     *
     * a -> x
     * a -> y : wrong
     *
     * Similarly, with (word, pattern) = ("ab", "xx"), we will catch the mistake
     * in m2.
     */
    class Solution1 {
        public List<String> findAndReplacePattern(String[] words, String pattern) {
            List<String> res = new ArrayList<>();
            for (String word : words) {
                if (isMatch(word, pattern)) {
                    res.add(word);
                }
            }

            return res;
        }

        private boolean isMatch(String word, String pattern) {
            if (word.length() != pattern.length()) return false;

            Map<Character, Character> map1 = new HashMap<>();
            Map<Character, Character> map2 = new HashMap<>();

            for (int i = 0; i < pattern.length(); i++) {
                char c1 = word.charAt(i);
                char c2 = pattern.charAt(i);

                if (!map1.containsKey(c1)) {
                    map1.put(c1, c2);
                }
                if (!map2.containsKey(c2)) {
                    map2.put(c2, c1);
                }

                if (map1.get(c1) != c2 || map2.get(c2) != c1) {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * https://leetcode.com/problems/find-and-replace-pattern/discuss/161266/JAVA-3ms-Clear-Code
     *
     * Time  : O(N * K)
     * Space : O(1)
     *
     * LE_205_Isomorphic_Strings
     */
    class Solution2 {
        public List<String> findAndReplacePattern(String[] words, String pattern) {
            List<String> res = new ArrayList<>();
            for (String word : words) {
                if (isMatch(word, pattern)) {
                    res.add(word);
                }
            }

            return res;
        }

        /**
         * pattern = "abb", word = "bcc"
         *
         * 'a' : p[0] = q[1] = i + 1 = 1
         * 'b' : p[1] = q[2] = i + 1 = 2
         * 'b' : p[1] = q[2] = i + 1 = 3
         *
         * pattern = "abb", word = "ccc"
         *
         * 'a' : p[0] = q[2] = i + 1 = 1
         * 'b' : p[1] = 0, q[2] = 1, return false;
         *
         * if use i:
         * 'a' : p[0] = q[2] = i = 0
         * 'b' : p[1] = q[2] = i = 1
         * 'b' : p[1] = q[2] = 2, wrong
         *
         */
        private boolean isMatch(String word, String pattern) {
            if (word.length() != pattern.length()) return false;

            int[] p = new int[26];
            int[] q = new int[26];

            for (int i = 0;i < word.length(); i++) {
                int c1 = word.charAt(i) - 'a';
                int c2 = pattern.charAt(i) - 'a';

                if (p[c1] != q[c2]) return false;

                /**
                 * !!!
                 * i + 1
                 */
                p[c1] = q[c2] = i + 1;
            }

            return true;
        }
    }
}
