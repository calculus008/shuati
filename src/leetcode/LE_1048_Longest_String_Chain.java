package leetcode;

import java.util.*;

public class LE_1048_Longest_String_Chain {
    /**
     * Given a list of words, each word consists of English lowercase letters.
     *
     * Let's say word1 is a predecessor of word2 if and only if we can add exactly
     * one letter anywhere in word1 to make it equal to word2.  For example, "abc"
     * is a predecessor of "abac".
     *
     * A word chain is a sequence of words [word_1, word_2, ..., word_k] with k >= 1,
     * where word_1 is a predecessor of word_2, word_2 is a predecessor of word_3,
     * and so on.
     *
     * Return the longest possible length of a word chain with words chosen from the
     * given list of words.
     *
     *
     * Example 1:
     *
     * Input: ["a","b","ba","bca","bda","bdca"]
     * Output: 4
     * Explanation: one of the longest word chain is "a","ba","bda","bdca".
     *
     *
     * Note:
     *
     * 1 <= words.length <= 1000
     * 1 <= words[i].length <= 16
     * words[i] only consists of English lowercase letters.
     *
     * Medium
     */

    /**
     * https://leetcode.com/problems/longest-string-chain/discuss/294890/C%2B%2BJavaPython-DP-Solution
     *
     * Sort the words by word's length. (also can apply bucket sort)
     * For each word, loop on all possible previous word with 1 letter missing.
     * If we have seen this previous word, update the longest chain for the current word.
     * Finally return the longest word chain.
     *
     * Time Complexity
     * Assume there are N words, and on average each word's length is L, we should consider
     * about the time when calling function substring as well. Therefore, the total time
     * complexity of your resolution should be O(NlogN + NL^2)
     *
     * Space : O(NL)
     *
     * Java String.substring(int index):
     * IndexOutOfBoundsException - if beginIndex is negative or larger than the length of this String object.
     * So the index can be  "the length of this String object", for example:
     *
     * S = "a", S.substring(1) = ""
     */
    class Solution {
        public int longestStrChain(String[] words) {
            if (null == words || words.length == 0) return 0;

            Map<String, Integer> map = new HashMap<>();
            Arrays.sort(words, (a, b) -> a.length() - b.length());
            int res = 0;

            /**
             * After sorting, word is in order from smallest length to the largest length
             */
            for (String word : words) {
                int best = 0;
                for (int i = 0; i < word.length(); i++) {
                    /**
                     * Iterate all possible strings by removing one char from the word.
                     * Those strings are candidate of the previous word.
                     */
                    String pre = word.substring(0, i) + word.substring(i + 1);

                    /**
                     * if we find previous word in map, then length of chain for this previous word
                     * should be increase by 1.
                     * Find the max value of the length of the chain for the current word.
                     */
                    best = Math.max(best, map.getOrDefault(pre, 0) + 1);
                }

                /**
                 * now we have the max possible length of chain that ends with current word, put it in map
                 */
                map.put(word, best);

                /**
                 * keep the max chain length in final solution
                 */
                res = Math.max(res, best);
            }

            return res;
        }
    }

    class Solution_DFS {
        public int longestStrChain(String[] words) {
            int ans = 0;
            Set<String> set = new HashSet<>();
            Map<String, Integer> map = new HashMap<>();

            for (String word : words) {
                set.add(word);
            }

            for (String word : words) {
                ans = Math.max(ans, helper(map, set, word));
            }

            return ans;
        }

        private int helper(Map<String, Integer> map, Set<String> set, String word) {
            if (map.containsKey(word)) return map.get(word);
            int cnt = 0;

            for (int i = 0; i < word.length(); i++) {
                String next = word.substring(0, i) + word.substring(i + 1);
                if (set.contains(next)) {
                    cnt = Math.max(cnt, helper(map, set, next));
                }
            }

            map.put(word, 1 + cnt);
            return 1 + cnt;
        }
    }
}
