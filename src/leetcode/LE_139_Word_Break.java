package leetcode;

import java.util.*;

/**
 * Created by yuank on 3/16/18.
 */
public class LE_139_Word_Break {
    /**
        Given a non-empty string s and a dictionary wordDict containing a list of non-empty words,
        determine if s can be segmented into a space-separated sequence of one or more dictionary words.
        You may assume the dictionary does not contain duplicate words.

        For example, given
        s = "leetcode",
        dict = ["leet", "code"].

        Return true because "leetcode" can be segmented as "leet code".
     */

    /**
     * Time  : O(n ^ 2)
     * Space : O(n)
     *
     * Related LE_472_Concatenated_Words
     */
    class Solution_Practice_2 {
        public boolean wordBreak(String s, List<String> wordDict) {
            if (s == null || s.length() == 0 || wordDict == null || wordDict.size() == 0) {
                return false;
            }

            Set<String> set = new HashSet<>(wordDict);

            return helper(s, set, new HashMap<>());
        }

        private boolean helper(String s, Set<String> set, Map<String, Boolean> mem) {
            if (mem.containsKey(s)) return mem.get(s);

            if (s.equals("")) return true;
            //Or
            //if (set.contains(s)) return true;

            for (int i = 0; i < s.length(); i++) {
                String l = s.substring(0, i);
                String r = s.substring(i);

                if (set.contains(r)) {
                    if (helper(l, set, mem)) {
                        mem.put(s, true);//!!!
                        return true;
                    }
                }
            }

            mem.put(s, false);//!!!
            return false;
        }
    }

    /**
     * Solution 1 : DP

       i一直往右移动，i每移动一次，j就从0走到i,或者当发现dp[i]为TRUE,break.
       "leet", "code"
        开始：
          i
        l e e t c o d e
        j

        j = 0, i = 4
                i
        l e e t c o d e  dp[4] = true
        j

        j = 4, i = 7
                      i
        l e e t c o d e  dp[8] = true
                j

        If "wordDict.contains()" is O(1), Time is O(n ^ 2), if it is O(n), Time is O(n ^ 3).

        Space : O(1)
    */

    /**
     * Best solution, DP
     *
     * Fastest, 4ms, 95.55%
     *
     */
    class Solution_DP {
        public boolean wordBreak(String s, Set<String> dict) {
            if (s == null || s.length() == 0) {
                return true;
            }

            int n = s.length();
            /**
             * 长度为n的单词 有n + 1个切割点 比如: _l_i_n_t_
             *
             * dp[i] : if the first i chars in current string is valid
             * **/
            boolean[] dp = new boolean[n + 1];

            /**
             * 当s长度为0时, "" is valid
             * **/
            dp[0] = true;

            //find the max length of the word in dict
            int max = getMaxLen(dict);

            /**
             * The key improvement from wordBreak2 is in inner loop,
             * inner loop j is looping for the length of the max length of words in dict,
             * hence it no longer needs to loop, in worst case, from 0 to the length of s.
             * It only needs to loop from 1 to the max length of the words in dict and
             * avoids the possibility of MLE when s is huge.
             *
             * Key changes :
             * 1.for (int j = 1; j <= max && j <= i; j++)
             * 2.dp[i - j]
             * 3.s.substring(i - j, i)
             *
             */
            for (int i = 1; i <= n; i++) {//loop for length
                for (int j = 1; j <= max && j <= i; j++) {//loop for break point between i and j
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


        /**
         * This solution MLE on lintcode for the case that given s is very large
         * <p>
         * Here dp[i] means if chars from col 0 to i -1 can be broken into valid words
         */
        public boolean wordBreak2(String s, List<String> wordDict) {
            if (s == null || s.length() == 0) return false;

            int n = s.length();
            boolean[] dp = new boolean[n + 1];
            dp[0] = true;

            //!!! "i = 1", "i <= n"
            for (int i = 1; i <= n; i++) {
                for (int j = 0; j < i; j++) {
                    //!!!s.substring(j, i)
                    if (dp[j] && wordDict.contains(s.substring(j, i))) {
                        dp[i] = true;
                        break;//!!!
                    }
                }
            }

            return dp[n];//!!!
        }
    }


    /**
     * Solution 2 : Recursion with cache. Slower solution, takes 36 ms.
     *              AC at leetcode, but MLE(Memory Limit Exceed) at Lintcode (139)
     *
     * Huahua's version
     * http://zxi.mytechroad.com/blog/leetcode/leetcode-139-word-break/
     *
     * Time  : O(n^2), Size of recursion tree can go up to n^2
     *
     *         假設n=4, 則recursion tree為
     *        /       /           \                         \
     *       (0,4)   (1,3)        (2,2)                    (3,1)
     *              /             /    \               /     \        \
     *            (0,1)      (0,2)  (1,1)           (0,3)  (1,2)  (2,1)
     *                                  /                      /         /    \
     *                              (0,1)                 (0,1)  (0,2)  (1,1)
     *                                                                            /
     *                                                                         (0,1)
     *         可以看到, 以0為開頭, 共有4個子問題(0,4), (0,3), (0, 2), (0, 1), 以1為開頭, 共有3個子問題, 以2為開頭,
     *         共有2個子問題, 以3為開頭, 共有1個子問題. 因此, 如果是一個長度為n的字串,
     *         共有n+n-1+n-2+...+1=(n+1)*n/2個子問題, 因此時間複雜度是O(n^2)
     *
     *
     *
     * Space : O(n ^ 2), 因為每個子問題都要佔一個memo(true/false), 所以空間複雜度也是O(n^2)﻿
     *
     * very slow on leetcode
     */
    class Solution_Recursion_Woth_Mem {
        public boolean wordBreak3(String s, List<String> wordDict) {
            Set<String> dict = new HashSet<>(wordDict);
            Map<String, Boolean> cache = new HashMap<>();

            return isValid(s, dict, cache);
        }

        private boolean isValid(String s, Set<String> wordDict, Map<String, Boolean> cache) {
            //!!! This is wrong!!!, if use getOrDefault, it means that it will only return
            // true when cache has the key and value is true. The correct logic is that you need
            // to return the value (regardless it is true or false) if the key exists.
            //!!!
            // if(cache.getOrDefault(s, false)) return true;

            if (cache.containsKey(s)) return cache.get(s);

            if (wordDict.contains(s)) {
                cache.put(s, true);
                return true;//!!! #1 diff from 140
            }

            for (int i = 1; i < s.length(); i++) {
                String l = s.substring(0, i);
                String r = s.substring(i);

                if (isValid(l, wordDict, cache) && wordDict.contains(r)) {//!!! #2 diff from 140
                    cache.put(s, true);
                    return true;//!!! #3 diff from 140
                }
            }

            cache.put(s, false);
            return false;//!!! #4 diff from 140
        }
    }


    class Solution_practice_1{
            public boolean wordBreak(String s, List<String> wordDict) {
            if (s == null || s.length() == 0 || wordDict == null || wordDict.size() == 0) {
                return false;
            }

            Set<String> dict = new HashSet<>(wordDict);

            return helper(s, dict, new HashMap<>());
        }

        private boolean helper(String s, Set<String> dict, Map<String, Boolean> mem) {
            if (mem.containsKey(s)) return mem.get(s);

            if (dict.contains(s)) {
                /**
                 * !!!
                 * 千万不要忘记set mem
                 */
                mem.put(s, true);
                return true;
            }

            for (int i = 1; i < s.length(); i++) {
                String l = s.substring(0, i);
                String r = s.substring(i);

                if (dict.contains(r)) {
                    if (helper(l, dict, mem)) {
                        /**
                         * !!!
                         */
                        mem.put(s, true);
                        return true;
                    }
                }
            }

            /**
             * !!!
             */
            mem.put(s, false);

            return false;
        }
    }

    class Solution_Variation{
        /**
         * Just given wordDict, find the max length of the word in wordDict that can be
         * broken into words in the same wordDict
         *
         * Similar with LE_472_Concatenated_Words
         */

        int res = Integer.MIN_VALUE;

        public int wordBreak(List<String> wordDict) {
            if (wordDict == null || wordDict.size() == 0) {
                return 0;
            }

            Set<String> dict = new HashSet<>(wordDict);

            /**
             * Another way to prevent getting s itself from dict is
             * to first remove s from dict before helper(), then after
             * returning, add it back
             */
            for (String s : dict) {
                if (helper(s, s, dict, new HashMap<>())) {
                    res = Math.max(res, s.length());
                }
            }

            return res;
        }

        private boolean helper(String s, String org, Set<String> dict, Map<String, Boolean> mem) {
            if (mem.containsKey(s)) return mem.get(s);

            if (s.equals("")) return true;

            for (int i = 0; i < s.length(); i++) {
                String l = s.substring(0, i);
                String r = s.substring(i);

                /**
                 * make sure the current s is not the original one from the dictionary
                 */
                if (dict.contains(r) && !s.equals(org)) {
                    if (helper(l, org, dict, mem)) {
                        mem.put(s, true);
                        return true;
                    }
                }
            }

            mem.put(s, false);

            return false;
        }
    }
}
