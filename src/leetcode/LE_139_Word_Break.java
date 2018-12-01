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
     */
    public boolean wordBreak(String s, Set<String> dict) {
        if (s == null || s.length() == 0) {
            return true;
        }

        int n = s.length();
        // 长度为n的单词 有n + 1个切割点 比如: _l_i_n_t_
        boolean[] dp = new boolean[n + 1];
        // 当s长度为0时
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
     *
     * Here dp[i] means if chars from idx 0 to i -1 can be broken into valid words
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


    /**
     Solution 2 : Recurssion with cache. Slower solution, takes 36 ms.
                  AC at leetcode, but MLE(Memory Limit Exceed) at Lintcode (139)
     */
     public boolean wordBreak3(String s, List<String> wordDict) {
         Set<String> dict = new HashSet<>(wordDict);
         Map<String, Boolean> cache = new HashMap<>();

         return isValid(s, dict, cache);
     }

     private boolean isValid(String s, Set<String> wordDict, Map<String, Boolean> cache ) {
         //!!! This is wrong!!!, if use getOrDefault, it means that it will only return
         // true when cache has the key and value is true. The correct logic is that you need
         // to return the value (regardless it is ture or false) if the key exists.
         //!!!
         // if(cache.getOrDefault(s, false)) return true;

         if(cache.containsKey(s)) return cache.get(s);

         if(wordDict.contains(s)) {
             cache.put(s, true);
             return true;//!!! #1 diff from 140
         }

         for(int i=1; i<s.length(); i++) {
             String l = s.substring(0, i);
             String r = s.substring(i);

             if(isValid(l, wordDict, cache) && wordDict.contains(r)) {//!!! #2 diff from 140
                 cache.put(s, true);
                 return true;//!!! #3 diff from 140
             }
         }

         cache.put(s, false);
         return false;//!!! #4 diff from 140
     }
}
