package leetcode;

public class LE_395_Longest_Substring_With_At_Least_K_Repeating_Characters {
    /**
     * Find the length of the longest substring T of a given string
     * (consists of lowercase letters only) such that every character
     * in T appears no less than k times.
     *
     * Example 1:
     *
     * Input:
     * s = "aaabb", k = 3
     *
     * Output:
     * 3
     *
     * The longest substring is "aaa", as 'a' is repeated 3 times.
     * Example 2:
     *
     * Input:
     * s = "ababbc", k = 2
     *
     * Output:
     * 5
     *
     * The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
     *
     * Medium
     */

    /**
     * Sliding Window
     *
     * Time : O(n)
     */
    public int longestSubstring(String s, int k) {
        if (s == null || s.length() == 0 || k <= 0) return 0;

        int res = 0;
        char[] chs = s.toCharArray();

        for (int i = 1; i <= 26; i++) {
            res = Math.max(res, helper(chs, k, i));
        }

        return res;
    }

    /**
     * find longest substring which has l unique characters and each of those
     * character appears at least k times.
     */
    public int helper(char[] chs, int k, int l) {
        int unique = 0;
        int atLeastK = 0;
        int[] count = new int[26];

        int res = 0;

        for (int i = 0, j = 0; i < chs.length; i++) {
            int idx = chs[i] - 'a';
            if (count[idx] == 0) {
                unique++;
            }
            count[idx]++;
            if (count[idx] == k) {
                atLeastK++;
            }

            while (unique > l) {
                int idx1 = chs[j] - 'a';

                if (count[idx1] == k) {
                    atLeastK--;
                }
                count[idx1]--;
                if (count[idx1] == 0) {
                    unique--;
                }
                j++;
            }

            if (unique == l && unique == atLeastK) {
                res = Math.max(res, i - j + 1);
            }
        }

        return res;
    }
}
