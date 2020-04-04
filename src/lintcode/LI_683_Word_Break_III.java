package lintcode;

import java.util.*;

/**
 * Created by yuank on 8/6/18.
 */
public class LI_683_Word_Break_III {
    /**
         Give a dictionary of words and a sentence with all whitespace removed,
         return the number of sentences you can form by inserting whitespaces
         to the sentence so that each word can be found in the dictionary.

         Example
         Given a String CatMat
         Given a dictionary ["Cat", "Mat", "Ca", "tM", "at", "C", "Dog", "og", "Do"]
         return 3

         we can form 3 sentences, as follows:
         CatMat = Cat Mat
         CatMat = Ca tM at
         CatMat = C at Mat

         Hard
     */
    public class Solution {
        public int wordBreak3(String s, Set<String> dict) {
            Set<String> set = new HashSet<>();
            for (String word : dict) {
                set.add(word.toLowerCase());
            }

            return helper(s.toLowerCase(), set, new HashMap<>());
        }

        private int helper(String s, Set<String> dict, Map<String, Integer> mem) {
            if (mem.containsKey(s)) return mem.get(s);

            if (s.equals("")) {
                if (!mem.containsKey(s)) {
                    mem.put("", 1);
                }

                return mem.get("");
            }

            int res = 0;
            for (int i = 0; i < s.length(); i++) {
                String l = s.substring(0, i);
                String r = s.substring(i);

                if (dict.contains(r)) {
                    res += helper(l, dict, mem);
                }
            }
            mem.put(s, res);

            return res;
        }
    }

    /**
     * Solution 1 : DP
     */
    public int wordBreak3(String s, Set<String> dict) {
        int n = s.length();
        String lowerS = s.toLowerCase();

        Set<String> lowerDict = new HashSet<String>();
        for(String str : dict) {
            lowerDict.add(str.toLowerCase());
        }

        int[][] dp = new int[n][n];

        //right side
        for(int i = 0; i < n; i++){
            for(int j = i; j < n;j++){
                String sub = lowerS.substring(i, j + 1);
                if(lowerDict.contains(sub)){
                    dp[i][j] = 1;
                }
            }
        }

        for(int i = 0; i < n; i++){
            for(int j = i; j < n; j++){
                for(int k = i; k < j; k++){
                    dp[i][j] += (dp[i][k] * dp[k + 1][j]);
                }
            }
        }
        return dp[0][n - 1];
    }

    /**
     * Solution 2
     * Memoization(记忆化搜索的解法), 这个解法很类似于divide and conquer,
     * 不同的是当每个分支被计算完毕以后, 可以把当前的算过的值存下来, 这样就省去了重复计算的烦恼.
     * 在这个题里面, 我们可以记住每次处理的字符串中有多少个解法.
     */
    Map<String, Integer> memo = new HashMap<>();
    public int wordBreak3_JiuZhang(String s, Set<String> dict) {
        Set<String> lower_dict = new HashSet<>();

        for(String word : dict){
            lower_dict.add(word.toLowerCase());
        }

        s = s.toLowerCase();
        return dfs(s, lower_dict);
    }

    private int dfs(String s, Set<String> dict){
        if(memo.containsKey(s)){
            return memo.get(s);
        }

        if(s.equals("")){
            if(!memo.containsKey(s)){
                memo.put("", 1);
            }

            return memo.get("");
        }

        int res = 0;

        //!!! "i <= s.length()" -> produce "".
        for(int i = 1; i <= s.length(); i++){
            String prefix = s.substring(0, i);

            if(dict.contains(prefix)){
                String suffix = s.substring(i);

                res = res + dfs(suffix, dict);
            }
        }

        memo.put(s, res);

        return res;
    }

    /**
     * My version
     */
    public int wordBreak3_my(String s, Set<String> dict) {
        if (s == null || s.length() == 0) return 0;

        Set<String> lowerDict = new HashSet<>();
        for (String word : dict) {
            lowerDict.add(word.toLowerCase());
        }

        return helper1(s.toLowerCase(), lowerDict, new HashMap<>());
    }

    private int helper1(String s, Set<String> dict, Map<String, Integer> mem) {
        if (mem.containsKey(s)) {
            return mem.get(s);
        }

        /**!!!
         * "a", [a], in for loop, res = 0 + helper("") = 0 + 1 = 1
         */
        if (s.equals("")) {
            if (!mem.containsKey(s)) {
                mem.put("", 1);
            }
            return mem.get("");
        }

        int res = 0;
        for (int i = 1; i <= s.length(); i++) {
            String l = s.substring(0, i);

            if (dict.contains(l)) {
                String r = s.substring(i);
                res += helper1(r, dict, mem);
            }
        }

        mem.put(s, res);
        return res;
    }

    /**
     * Solution 3
     * Copy solution from LE_140_Word_Break_II, just return size of the final list, but it is MLE on lintcode
     */
    public int wordBreak3_MLE(String s, Set<String> wordDict) {
        Set<String> lowerDict = new HashSet<>();
        for (String word : wordDict) {
            lowerDict.add(word.toLowerCase());
        }

        List<String> res = helper(s.toLowerCase(), lowerDict, new HashMap<>());
        return res.size();
    }

    List<String> helper(String s, Set<String> dict, HashMap<String, List<String>> cache) {
        if (cache.containsKey(s)) return cache.get(s);

        List<String> cur = new ArrayList<>();
        if (dict.contains(s)) {
            cur.add(s);
        }

        for (int i = 1; i < s.length(); i++) {
            String l = s.substring(0, i);
            String r = s.substring(i);

            if (dict.contains(r)) {
                List<String> words = helper(l, dict, cache);
                for (String word : words) {
                    cur.add(word + " " + r);
                }
            }
        }

        cache.put(s, cur);
        return cur;
    }
}
