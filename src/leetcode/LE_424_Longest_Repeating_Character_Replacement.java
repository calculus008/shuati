package leetcode;

public class LE_424_Longest_Repeating_Character_Replacement {
    /**
     * Given a string s that consists of only uppercase English letters,
     * you can perform at most k operations on that string.
     *
     * In one operation, you can choose any character of the string and
     * change it to any other uppercase English character.
     *
     * Find the length of the longest sub-string containing all repeating
     * letters you can get after performing the above operations.
     *
     * Note:
     * Both the string's length and k will not exceed 104.
     *
     * Example 1:
     * Input:
     * s = "ABAB", k = 2
     *
     * Output:
     * 4
     *
     * Explanation:
     * Replace the two 'A's with two 'B's or vice versa.
     *
     *
     * Example 2:
     * Input:
     * s = "AABABBA", k = 1
     *
     * Output:
     * 4
     *
     * Explanation:
     * Replace the one 'A' in the middle with 'B' and form "AABBBBA".
     * The substring "BBBB" has the longest repeating letters, which is 4.
     *
     * Medium
     *
     * https://leetcode.com/problems/longest-repeating-character-replacement
     */

    /**
     * Sliding Window
     *
     * i : end idx of the sliding window
     * j : start idx of the sliding window
     * i - j + 1 : current sliding window size
     * max : max frequency in current sliding window
     *
     * i - j + 1 == max + k : it means the current window can be changed to continuous
     *                        repeating chars by making k changes. The char is the one
     *                        has the max frequency in the window before changes.
     * For example:
     * "XXXYZ", k = 2 : 'X' has max frequency (3), we can make 2 changes so it becomes "XXXXXX"
     *
     * Apply sliding window algorithm :
     * Calculate res when i - j + 1 <= max + k, whenever i - j + 1 >= max + k, move j in while loop
     * to bring it back to valid condition.
     *
     * !!!
     * The key here is that we only care max count itself, as for which char has the max count, we
     * don't care.
     *
     */
    class Solution {
        public int characterReplacement(String s, int k) {
            if (s == null || s.length() == 0) return 0;

            char[] chs = s.toCharArray();
            int[] count = new int[26];
            int max = 0;
            int res = 0;

            for (int i = 0, j = 0; i < chs.length; i++) {
                count[chs[i] - 'A']++;
                max = Math.max(max, count[chs[i] - 'A']);

                while (i - j + 1 > max + k) {//move start of the sliding window to right by one
                    count[chs[j] - 'A']--;
                    j++;
                }

                res = Math.max(res, i - j + 1);
            }

            return res;
        }
    }
}
