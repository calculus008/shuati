package leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class LE_527_Word_Abbreviation {
    /**
     * Given an array of n distinct non-empty strings, you need to generate
     * minimal possible abbreviations for every word following rules below.
     *
     * 1.Begin with the first character and then the number of characters abbreviated,
     * which followed by the last character.
     *
     * 2.If there are any conflict, that is more than one words share the same abbreviation,
     * a longer prefix is used instead of only the first character until making the map
     * from word to abbreviation become unique. In other words, a final abbreviation
     * cannot map to more than one original words.
     *
     * 3.If the abbreviation doesn't make the word shorter, then keep it as original.
     *
     * Example:
     * Input: ["like", "god", "internal", "me", "internet", "interval", "intension", "face", "intrusion"]
     * Output: ["l2e","god","internal","me","i6t","interval","inte4n","f2e","intr4n"]
     *
     * Note:
     * Both n and the length of each word will not exceed 400.
     * The length of each word is greater than 1.
     * The words consist of lowercase English letters only.
     * The return answers should be in the same order as the original array.
     *
     * Hard
     */

    /**
     * "prefix[i]" records starting index for abbreviation for ith word
     */
    public List<String> wordsAbbreviation(List<String> dict) {
        int len = dict.size();
        String[] ans = new String[len];
        int[] prefix = new int[len];

        for (int i = 0; i < len; i++) {
            prefix[i] = 1;
            ans[i] = makeAbbr(dict.get(i), 1); // make abbreviation for each string
        }

        for (int i = 0; i < len; i++) {
            while (true) {//!!!
                /**
                 * Set saves index for words have the same abbreviation
                 */
                HashSet<Integer> set = new HashSet<>();

                for (int j = i + 1; j < len; j++) {
                    if (ans[j].equals(ans[i])) {
                        set.add(j); // check all strings with the same abbreviation
                    }
                }

                if (set.isEmpty()) {
                    break;
                }

                set.add(i);
                for (int k : set) {
                    ans[k] = makeAbbr(dict.get(k), ++prefix[k]); // increase the prefix
                }
            }
        }
        return Arrays.asList(ans);
    }

    /**
     * Make abbreviate word, abbreviation starts from index k
     */
    private String makeAbbr(String s, int k) {
        if (k + 2 >= s.length()) {
            return s;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(s.substring(0, k));
        builder.append(s.length() - 1 - k);
        builder.append(s.charAt(s.length() - 1));

        return builder.toString();
    }
}
