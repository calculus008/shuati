package leetcode;

import java.util.HashMap;
import java.util.Map;

public class LE_1278_Palindrome_Partitioning_III {
    /**
     * You are given a string s containing lowercase letters and an integer k. You need to :
     *
     * First, change some characters of s to other lowercase English letters.
     * Then divide s into k non-empty disjoint substrings such that each substring is palindrome.
     * Return the minimal number of characters that you need to change to divide the string.
     *
     * Example 1:
     * Input: s = "abc", k = 2
     * Output: 1
     * Explanation: You can split the string into "ab" and "c", and change 1 character in "ab" to make it palindrome.
     *
     * Example 2:
     * Input: s = "aabbc", k = 3
     * Output: 0
     * Explanation: You can split the string into "aa", "bb" and "c", all of them are palindrome.
     *
     * Example 3:
     * Input: s = "leetcode", k = 8
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= k <= s.length <= 100.
     * s only contains lowercase English letters.
     *
     * Hard
     */

    class Solution {
        public int palindromePartition(String s, int k) {
            Map<String, Integer> dp = new HashMap<>();
            return dfs(s, dp, 0, k);
        }

        private int dfs(String s, Map<String, Integer> dp, int start, int k) {
            String key = start + "$" + k;

            if (dp.containsKey(key)) {
                return dp.get(key);
            }

            if (s.length() - start == k) {
                return 0;
            }

            if (k == 1) {
                return cost(s, start, s.length() - 1);
            }

            int res = Integer.MAX_VALUE;

            for (int i = start + 1; i <= s.length() - k + 1; i++) {
                res = Math.min(res, dfs(s, dp, i, k - 1) + cost(s, start, i - 1));
            }

            dp.put(key, res);
            return res;
        }

        private int cost(String s, int i, int j) {
            int res = 0;

            while (i < j) {
                if (s.charAt(i) != s.charAt(j)) {
                    res++;
                }
                i++;
                j--;
            }

            return res;
        }
    }

}
