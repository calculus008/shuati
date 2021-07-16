package leetcode;

import java.util.*;

public class LE_884_Uncommon_Words_From_Two_Sentences {
    /**
     * A sentence is a string of single-space separated words where each word consists only of lowercase letters.
     *
     * A word is uncommon if it appears exactly once in one of the sentences, and does not appear in the other sentence.
     *
     * Given two sentences s1 and s2, return a list of all the uncommon words. You may return the answer in any order.
     *
     * Example 1:
     * Input: s1 = "this apple is sweet", s2 = "this apple is sour"
     * Output: ["sweet","sour"]
     *
     * Example 2:
     * Input: s1 = "apple apple", s2 = "banana"
     * Output: ["banana"]
     *
     * Constraints:
     * 1 <= s1.length, s2.length <= 200
     * s1 and s2 consist of lowercase English letters and spaces.
     * s1 and s2 do not have leading or trailing spaces.
     * All the words in s1 and s2 are separated by a single space.
     *
     * Easy
     */

    /**
     * Two HashMp
     *
     * The only tricky part:
     * We need to use HashMap because we need to keep the count of number of appearances of a word in one sentence.
     *
     * If we use set, this case will fail:
     *
     * s1 : "s z z z s"
     * s2 : "s z ejt"
     *
     * 1st pass goes through s1, then set1 is ["z"]. So the answer will be ["s", "ejt"], but the correct answer is ["ejt"]
     * (https://leetcode.com/submissions/detail/523581892/)
     */
    class Solution1 {
        public String[] uncommonFromSentences(String s1, String s2) {
            Map<String, Integer> map1 = new HashMap<>();
            String[] words1 = s1.split(" ");
            for (String word : words1) {
                map1.put(word, map1.getOrDefault(word, 0) + 1);
            }

            Map<String, Integer> map2 = new HashMap<>();
            String[] words2 = s2.split(" ");
            for (String word : words2) {
                map1.put(word, map1.getOrDefault(word, 0) + 1);
            }

            List<String> res = new ArrayList<>();
            for (String key : map1.keySet()) {
                if (map2.containsKey(key) || map1.get(key) > 1) continue;
                res.add(key);
            }
            for (String key : map2.keySet()) {
                if (map1.containsKey(key) || map2.get(key) > 1) continue;
                res.add(key);
            }

            return res.toArray(new String[0]);
        }
    }

    /**
     * One HashMap, cantatenate two sentences together so we treat it as one sentence, then the word with count 1 is the answer.
     */
    class Solution2 {
        public String[] uncommonFromSentences(String A, String B) {
            Map<String, Integer> count = new HashMap<>();
            for (String w : (A + " " + B).split(" ")) {
                count.put(w, count.getOrDefault(w, 0) + 1);
            }
            ArrayList<String> res = new ArrayList<>();
            for (String w : count.keySet()) {
                if (count.get(w) == 1) res.add(w);
            }

            return res.toArray(new String[0]);
        }
    }
}
