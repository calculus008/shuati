package Interviews.DoorDash;

import java.util.*;

public class Word_Break_III {
    /**
     * Variation of LE_140_Word_Break_II
     *
     * return max length of the broken words lists
     */

    public int wordBreak(String s, List<String> wordDict) {
        List<String> list =  helper(s, new HashSet<String>(wordDict), new HashMap<>());

        int maxLen = 0;
        for (String words : list) {
            maxLen = Math.max(maxLen, words.split(" ").length);
        }

        return maxLen;
    }

    List<String> helper(String s, Set<String> dict, HashMap<String, List<String>> cache) {
        //cache containsKey
        if (cache.containsKey(s)) return cache.get(s);

        //dict contains
        List<String> cur = new ArrayList<>();
        if (dict.contains(s)) {
            cur.add(s);
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

        return cur;
    }
}
