package lintcode;

import java.util.HashMap;
import java.util.Map;

public class LI_639_Word_Abbreviation {
    /**
     * Given an array of n distinct non-empty strings, you need to generate minimal
     * possible abbreviations for every word following rules below.
     * <p>
     * Begin with the first character and then the number of characters abbreviated,
     * which followed by the last character.
     * <p>
     * If there are any conflict, that is more than one words share the same abbreviation,
     * a longer prefix is used instead of only the first character until making the map
     * from word to abbreviation become unique. In other words, a final abbreviation cannot
     * map to more than one original words.
     * <p>
     * If the abbreviation doesn't make the word shorter, then keep it as original.
     * <p>
     * Both n and the length of each word will not exceed 400.
     *
     * The length of each word is greater than 1.
     *
     * The words consist of lowercase English letters only.
     *
     * The return answers should be in the same order as the original array.
     *
     * Compare with LE_288_Unique_Word_Abbreviation
     */

    public class Solution {
        public String[] wordsAbbreviation(String[] dict) {
            int len = dict.length;
            String[] ans = new String[len];

            /**
             * !!!
             * For ith element in dict, the length of prefix used (in order to make unique in hashmap)
             */
            int[] prefix = new int[len];

            /**
             * HashMap : count number of times a key that has appeared
             */
            Map<String, Integer> count = new HashMap<>();

            for (int i = 0; i < len; i++) {
                prefix[i] = 1;
                ans[i] = getAbbr(dict[i], 1);
                count.put(ans[i], count.getOrDefault(ans[i], 0) + 1);
            }

            while (true) {
                boolean unique = true;
                for (int i = 0; i < len; i++) {
                    if (count.get(ans[i]) > 1) {
                        /**
                         * !!!
                         * Not unique, increase length of prefix and re-generate abbreviation
                         */
                        prefix[i]++;
                        ans[i] = getAbbr(dict[i], prefix[i]);

                        /**
                         * only add new key to hashmap, not deleting the original one
                         */
                        count.put(ans[i], count.getOrDefault(ans[i], 0) + 1);
                        unique = false;
                    }
                }
                if (unique) {
                    break;
                }
            }
            return ans;
        }

        String getAbbr(String s, int p) {
            if (p + 2 >= s.length()) {
                return s;
            }

            String ans;
            ans = s.substring(0, p) + (s.length() - 1 - p) + s.charAt(s.length() - 1);
            return ans;
        }

    }
}
