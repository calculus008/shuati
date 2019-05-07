package leetcode;

import java.util.*;

/**
 * Created by yuank on 3/16/18.
 */
public class LE_140_Word_Break_II {
    /**
        Given a non-empty string s and a dictionary wordDict containing a list of non-empty words,
        add spaces in s to construct a sentence where each word is a valid dictionary word.
        You may assume the dictionary does not contain duplicate words.

        Return all such possible sentences.

        For example, given
        s = "catsanddog",
        dict = ["cat", "cats", "and", "sand", "dog"].

        A solution is ["cats and dog", "cat sand dog"].
     */

    /**
        Divide and Conquer
        https://www.youtube.com/watch?v=JqOIRBC0_9c&t=539s
        https://zxi.mytechroad.com/blog/leetcode/leetcode-140-word-break-ii/

        Time complexity: O(2^n)

        Space complexity: O(2^n)

        Example:
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

    /**
     * Time complexity : O(n^3) Size of recursion tree can go up to n ^ 2. The creation of list takes nn time.
     *
     * Space complexity : O(n^3)  The depth of the recursion tree can go up to n and each activation record
     *                            can contains a string list of size n
     */
    public List<String> wordBreak(String s, List<String> wordDict) {
        return helper(s, new HashSet<String>(wordDict), new HashMap<>());
    }

    List<String> helper(String s, Set<String> dict, HashMap<String, List<String>> cache) {
        //cache containsKey
        if (cache.containsKey(s)) return cache.get(s);

        //dict contains
        List<String> cur = new ArrayList<>();
        if (dict.contains(s)) {
            cur.add(s);
            /**
             * since we want to find all possible answers,
             * we can't return here, must go to the next step.
             *
             * For example: Given:
             * "pineapplepenapple"
             * ["apple","pen","applepen","pine","pineapple"]
             *
             * Correct answer is :
             * ["pine apple pen apple","pineapple pen apple","pine applepen apple"]
             *
             * If we return here, answer turned out to be :
             * ["pine applepen apple","pineapple pen apple"]
             *
             * We missed "pine apple pen apple", because "applepen" is a valid word,
             * but it can be further divided into "apple" and "pen". If we return here,
             * we won't be able to get this part.
             *
             */
        }

        for (int i = 1; i < s.length(); i++) {
            String l = s.substring(0, i);
            String r = s.substring(i);

            if (dict.contains(r)) {
                //!!! helper(l, , ), "l", NOT "r"!!!
                List<String> words = helper(l, dict, cache);
                for (String word : words) {
                    cur.add(word + " " + r);
                }
            }
        }

        cache.put(s, cur);
        return cur;
    }

    /**
     * Variation :
     * Given :
     * list[superhighway, super, high, way, superhigh, way], superhighway
     *
     * return : [[superhighway,], [super, high, way], [superhigh, way]]
     */
}

