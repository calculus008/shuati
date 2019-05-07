package leetcode;

import java.util.*;

public class LE_472_Concatenated_Words {
    /**
     * Given a list of words (without duplicates), please write a program
     * that returns all concatenated words in the given list of words.
     * A concatenated word is defined as a string that is comprised entirely
     * of at least two shorter words in the given array.
     *
     * Example:
     * Input: ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]
     *
     * Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]
     *
     * Explanation: "catsdogcats" can be concatenated by "cats", "dog" and "cats";
     *  "dogcatsdog" can be concatenated by "dog", "cats" and "dog";
     * "ratcatdogcat" can be concatenated by "rat", "cat", "dog" and "cat".
     *
     * The number of elements of the given array will not exceed 10,000
     * The length sum of elements in the given array will not exceed 600,000.
     * All the input string will only include lower case letters.
     * The returned elements order does not matter.
     *
     * Hard
     */

    /**
     * Recursion with mem
     *
     * Preferred Solution
     *
     * Exact the same algorithm as LE_139_Word_Break. "helper()" function is copied
     * from LE_139_Word_Break with no modification.
     *
     * Time  : O(k * n ^ 2), k is length words, n is average length of a single word.
     * Space : O(n ^ 2)
     *
     * 88 ms
     * 58 M
     */

    class Solution1 {
        public List<String> findAllConcatenatedWordsInADict(String[] words) {
            List<String> list = new ArrayList<String>();
            Set<String> set = new HashSet(Arrays.asList(words));

            for(String word : words) {
                set.remove(word);

                if (helper(word, set, new HashMap<>())) {
                    list.add(word);
                }

                set.add(word);
            }
            return list;
        }

        private boolean helper(String s, Set<String> dict, Map<String, Boolean> mem) {
            if (mem.containsKey(s)) return mem.get(s);

            if (dict.contains(s)) {
                mem.put(s, true);
                return true;
            }

            for (int i = 1; i < s.length(); i++) {
                String l = s.substring(0, i);
                String r = s.substring(i);

                if (dict.contains(r)) {
                    if (helper(l, dict, mem)) {
                        mem.put(s, true);
                        return true;
                    }
                }
            }

            mem.put(s, false);

            return false;
        }
    }

    /**
     * DP, same logic as DP solution for LE_139_Word_Break
     */
    class Solution2 {
        public List<String> findAllConcatenatedWordsInADict(String[] words) {
            List<String> list = new ArrayList<String>();
            Set<String> set = new HashSet(Arrays.asList(words));

            int maxLen = getMaxLen(set);

            for(String word : words) {
                set.remove(word);

                if (wordBreak(word, set, maxLen)) {
                    list.add(word);
                }

                set.add(word);
            }
            return list;
        }

        public boolean wordBreak(String s, Set<String> dict, int maxLen) {
            if (s == null || s.length() == 0) {
                return false;
            }

            int max = 0;
            if (s.length() == maxLen) {
                max = getMaxLen(dict);
            } else {
                max = maxLen;
            }

            int n = s.length();
            boolean[] dp = new boolean[n + 1];
            dp[0] = true;


            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= max && j <= i; j++) {
                    if (dp[i - j] && dict.contains(s.substring(i - j, i))) {
                        dp[i] = true;
                        break;
                    }
                }
            }

            return dp[n];
        }

        private int getMaxLen(Set<String> dict) {
            int max = 0;
            for (String word : dict) {
                max = Math.max(max, word.length());
            }
            return max;
        }
    }
}
