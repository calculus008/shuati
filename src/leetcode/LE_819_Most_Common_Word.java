package leetcode;

import java.util.*;

public class LE_819_Most_Common_Word {
    /**
     * Given a paragraph and a list of banned words, return the most frequent word
     * that is not in the list of banned words.  It is guaranteed there is at least
     * one word that isn't banned, and that the answer is unique.
     *
     * Words in the list of banned words are given in lowercase, and free of punctuation.
     * Words in the paragraph are not case sensitive.  The answer is in lowercase.
     *
     *
     *
     * Example:
     *
     * Input:
     * paragraph = "Bob hit a ball, the hit BALL flew far after it was hit."
     * banned = ["hit"]
     * Output: "ball"
     * Explanation:
     * "hit" occurs 3 times, but it is a banned word.
     * "ball" occurs twice (and no other word does), so it is the most frequent non-banned word in the paragraph.
     * Note that words in the paragraph are not case sensitive,
     * that punctuation is ignored (even if adjacent to words, such as "ball,"),
     * and that "hit" isn't the answer even though it occurs more because it is banned.
     *
     *
     * Note:
     *
     * 1 <= paragraph.length <= 1000.
     * 0 <= banned.length <= 100.
     * 1 <= banned[i].length <= 10.
     * The answer is unique, and written in lowercase (even if its occurrences in paragraph may have uppercase symbols, and even if it is a proper noun.)
     * paragraph only consists of letters, spaces, or the punctuation symbols !?',;.
     * There are no hyphens or hyphenated words.
     * Words only consist of letters, never apostrophes or other punctuation symbols.
     *
     * Easy
     */

    /**
     * 1.Put banned words into set
     * 2.split given paragrah into words
     * 3.Iterate through words, count frequency in a map if the word is not banned.
     * 4.Iterate through count map to get the max frequency
     */
    public String mostCommonWord(String paragraph, String[] banned) {
        Set<String> set = new HashSet<>(Arrays.asList(banned));

        /**
         * !!!
         * "\\W" means matches the non-word characters.
         */
        String[] words = paragraph.toLowerCase().split("\\W+");

        Map<String, Integer> map = new HashMap<>();

        for (String word : words) {
            if (!set.contains(word)) {
                map.put(word, map.getOrDefault(word, 0) + 1);
            }
        }

        String res = "";
        int max = 0;

        for(String key : map.keySet()) {
            if (map.get(key) > max) {
                max = map.get(key);
                res = key;
            }
        }

        return res;
    }
}
