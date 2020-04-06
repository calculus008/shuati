package leetcode;

import java.util.Arrays;

public class LE_1160_Find_Words_That_Can_Be_Formed_By_Characters {
    /**
     * You are given an array of strings words and a string chars.
     *
     * A string is good if it can be formed by characters from chars (each character can only be used once).
     *
     * Return the sum of lengths of all good strings in words.
     *
     * Example 1:
     * Input: words = ["cat","bt","hat","tree"], chars = "atach"
     * Output: 6
     * Explanation:
     * The strings that can be formed are "cat" and "hat" so the answer is 3 + 3 = 6.
     *
     * Example 2:
     * Input: words = ["hello","world","leetcode"], chars = "welldonehoneyr"
     * Output: 10
     * Explanation:
     * The strings that can be formed are "hello" and "world" so the answer is 5 + 5 = 10.
     *
     * Note:
     * 1 <= words.length <= 1000
     * 1 <= words[i].length, chars.length <= 100
     * All strings contain lowercase English letters only.
     *
     * Easy
     */

    /**
     * A cleaner version modified from Soluion1
     */
    class Solution2 {
        public int countCharacters(String[] words, String chars) {
            int[] map = new int[26];
            for (char c : chars.toCharArray()) {
                map[c - 'a']++;
            }

            int res = 0;

            for (String word : words) {
                /**
                 * just clone dist
                 */
                int[] count = map.clone();
                int i = 0;

                for (char c : word.toCharArray()) {
                    int idx = c - 'a';
                    /**
                     * just decrease value in count and check if it is smaller than 0
                     */
                    count[idx]--;
                    if (count[idx] < 0) break;
                    i++;
                }

                /**
                 * check if we get to the end of word
                 */
                if (i == word.length()) {
                    res += word.length();
                }
            }

            return res;
        }
    }

    class Solution1 {
        public int countCharacters(String[] words, String chars) {
            int[] map = new int[26];
            for (char c : chars.toCharArray()) {
                map[c - 'a']++;
            }

            int[] count = new int[26];
            int res = 0;

            for (String word : words) {
                boolean valid = true;
                Arrays.fill(count, 0);

                for (char c : word.toCharArray()) {
                    int idx = c - 'a';

                    if (map[idx] > 0) {
                        count[idx]++;
                        if (count[idx] > map[idx]) {
                            valid = false;
                            break;
                        }
                    } else {
                        valid = false;
                        break;
                    }
                }

                if (valid) {
                    res += word.length();
                }
            }

            return res;
        }
    }

}
