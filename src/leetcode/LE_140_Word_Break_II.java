package leetcode;

import java.util.*;

/**
 * Created by yuank on 3/16/18.
 */
public class LE_140_Word_Break_II {
    /*
        Given a non-empty string s and a dictionary wordDict containing a list of non-empty words,
        add spaces in s to construct a sentence where each word is a valid dictionary word.
        You may assume the dictionary does not contain duplicate words.

        Return all such possible sentences.

        For example, given
        s = "catsanddog",
        dict = ["cat", "cats", "and", "sand", "dog"].

        A solution is ["cats and dog", "cat sand dog"].
     */

    /*

        c a t s a n d d o g

        try splitting s into two part at each position : l and r

        c | a t s a n d d o g
        c a | t s a n d d o g
        c a t | s a n d d o g
        c a t s | a n d d o g
        c a t s a | n d d o g
        c a t s a n | d d o g
        c a t s a n d | d o g
        c a t s a n d d | o g
        c a t s a n d d o | g
    */

    public List<String> wordBreak(String s, List<String> wordDict) {
        return helper(s, new HashSet<String>(wordDict), new HashMap<>());
    }

    List<String> helper(String s, Set<String> dict, HashMap<String, List<String>> cache) {
        if (cache.containsKey(s)) return cache.get(s);

        List<String> res = new ArrayList<>();
        if (dict.contains(s)) {
            res.add(s);
        }

        for (int i = 1; i < s.length(); i++) {
            String l = s.substring(0, i);
            String r = s.substring(i);

            if (dict.contains(r)) {
                List<String> words = helper(l, dict, cache);
                for (String word : words) {
                    res.add(word + " " + r);
                }
            }
        }

        cache.put(s, res);
        return res;
    }

}
