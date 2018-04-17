package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuank on 2/26/18.
 */
public class LE_30_SubstrConactWords {
    /*
        You are given a string, s, and a list of words, words, that are all of the same length. Find all starting indices of substring(s) in s that
        is a concatenation of each word in words exactly once and without any intervening characters.

        For example, given:
        s: "barfoothefoobarman"
        words: ["foo", "bar"]

        You should return the indices: [0,9].
        (order does not matter).
     */

    //O(n ^ 2) Solution,
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        if (s == null || s.length() == 0) return res;

        int n = words.length;
        int m = words[0].length(); //!!! words[0] is a String, so "length()"!!!
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        //!!! "<= s.length() - n * m"
        for (int i = 0; i <= s.length() - n * m; i++) {
            Map<String, Integer> copy = new HashMap<>(map);
            int j = i;
            int k = n;
            while (k > 0) {
                String cur = s.substring(j, j + m);
                //!!! faster in execution
                if (!copy.containsKey(cur) || copy.get(cur) < 1) break;

                k--;
                j += m;
                copy.put(cur, copy.get(cur) - 1);
            }

            if (k == 0) {
                res.add(i);
            }
        }

        return res;
    }

    public List<Integer> findSubstring2(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        if (s == null || s.length() == 0) return res;

        int n = words.length;
        int m = words[0].length(); //!!! words[0] is a String, so "length()"!!!
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        //!!! "<= s.length() - n * m"
        for (int i = 0; i <= s.length() - n * m; i++) {
            Map<String, Integer> copy = new HashMap<>(map);
            int j = i;
            int k = n;
            while (k > 0) {
                String cur = s.substring(j, j + m);
                //!!! faster in execution
                if (!copy.containsKey(cur) || copy.get(cur) < 1) break;

                k--;
                j += m;
                copy.put(cur, copy.get(cur) - 1);
            }

            if (k == 0) {
                res.add(i);
            }
        }

        return res;
    }
}
