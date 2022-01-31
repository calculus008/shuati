package leetcode;

import java.util.*;

public class LE_1624_Largest_Substring_Between_Two_Equal_Characters {
    /**
     * Given a string s, return the length of the longest substring between two equal characters, excluding the two
     * characters. If there is no such substring return -1.
     *
     * A substring is a contiguous sequence of characters within a string.
     *
     * Example 1:
     * Input: s = "aa"
     * Output: 0
     * Explanation: The optimal substring here is an empty substring between the two 'a's.
     *
     * Example 2:
     * Input: s = "abca"
     * Output: 2
     * Explanation: The optimal substring here is "bc".
     *
     * Example 3:
     * Input: s = "cbzxy"
     * Output: -1
     * Explanation: There are no characters that appear twice in s.
     *
     * Constraints:
     * 1 <= s.length <= 300
     * s contains only lowercase English letters.
     *
     * Easy
     *
     * https://leetcode.com/problems/largest-substring-between-two-equal-characters/
     */

    /**
     * Save the index of a char for its earliest appearance in HashMap, when re-appearing, calculate distance and
     * get the max value.
     *
     * Solution1 : HashMap
     * Solution2 : int[] array
     */
    class Solution1 {
        public int maxLengthBetweenEqualCharacters(String s) {
            Map<Character, Integer> map = new HashMap<>();

            char[] chars = s.toCharArray();
            int res = -1;

            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (!map.containsKey(c)) {
                    map.put(c, i);
                    continue;
                }

                res = Math.max(res, i - map.get(c) - 1);
            }

            return res;
        }
    }

    class Solution2 {
        public int maxLengthBetweenEqualCharacters(String s) {
            int[] map = new int[26];

            char[] chars = s.toCharArray();
            int res = -1;
            Arrays.fill(map, -1);

            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                int idx = c - 'a';
                if (map[idx] == -1) {
                    map[idx] = i;
                    continue;
                }

                res = Math.max(res, i - map[idx] - 1);
            }

            return res;
        }
    }
}
