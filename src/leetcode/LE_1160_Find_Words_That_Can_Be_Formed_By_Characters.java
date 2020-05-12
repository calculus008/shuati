package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
     * !!!
     * "each character can only be used once":
     * This actually means each character can only be used once when creating one word,
     * in other words, when creating one word, we can use the chars existing in given
     * chars, when we start creating the next word, we still use all chars in given chars.
     *
     * In Example 1 above:
     * Input: words = ["cat","bt","hat","tree"], chars = "atach",
     * The strings that can be formed are "cat" and "hat".
     *
     * NOTICE that there's one 't' in chars, it is used in both 'cat' and 'hat'.
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
                 * just clone map
                 */
                int[] count = Arrays.copyOf(map, map.length);
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

    /**
     * If the chars are unicode or interviewer explicitly asks to use HashMap instead of array.
     * there's a trap here.
     *
     * You can't use map.clone(), becasue it does a shallow copy, it will not make a object for the key
     * and value, the value and key in cloned map will still point to the original object, we can't use
     * it here since we need to modify the values in the cloned map object.
     */
    class Solution_HashMap_1 {
        public int countCharacters(String[] words, String chars) {
            Map<Character, Integer> map = new HashMap<>();
            for (char c : chars.toCharArray()) {
                map.put(c, map.getOrDefault(c, 0) + 1);
            }

            int res = 0;

            for (String word : words) {
                /**
                 * Conventional loop through deep-copy
                 * Must do "new Integer(map.get(key))" to create a new Integer obj!!!
                 */
                 Map<Character, Integer> count = new HashMap<>();
                 for (Character key : map.keySet()) {
                     count.put(key, new Integer(map.get(key)));
                 }

                /**
                 * Deep copy by using Java 8 stream API
                 */
//                Map<Character, Integer> count = map.entrySet().stream()
//                        .collect(Collectors.toMap(e -> e.getKey(), e -> new Integer(e.getValue())));

                int idx = 0;

                for (;idx < word.length(); idx++) {
                    char c = word.charAt(idx);
                    if (!count.containsKey(c) || count.get(c) == 0) break;

                    count.put(c, count.get(c) - 1);
                }

                if (idx == word.length()) {
                    res += idx;
                }
            }

            return res;
        }
    }

    /**
     * This one is probably not useful,it assumes we still deal with 'a' - 'z' but uses
     * hashmap. Just for reference
     */
    class Solution_HashMap_2 {
        public int countCharacters(String[] words, String chars) {
            Map<Character, Integer> map = new HashMap<>();
            for (char c : chars.toCharArray()) {
                map.put(c, map.getOrDefault(c, 0) + 1);
            }

            int res = 0;
            for (String word : words) {
                char[] table = new char[26];
                int temp = word.length();

                for (char c : word.toCharArray()) {
                    table[c - 'a']++;
                }

                for (int i = 0; i < 26; i++) {
                    if (map.getOrDefault((char) (i + 'a'), 0) < table[i]) {
                        temp = 0;
                        break;
                    }
                }

                res += temp;
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
