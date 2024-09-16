package leetcode;

import java.util.Arrays;

public class LE_727_Minimum_Window_Subsequence {
    /**
     * Given strings S and T, find the minimum (contiguous) substring W of S,
     * so that T is a subsequence of W.
     *
     * If there is no such window in S that covers all characters in T, return
     * the empty string "". If there are multiple such minimum-length windows,
     * return the one with the left-most starting index.
     *
     * Example 1:
     *
     * Input:
     * S = "abcdebdde", T = "bde"
     * Output: "bcde"
     * Explanation:
     * "bcde" is the answer because it occurs before "bdde" which has the same length.
     * "deb" is not a smaller window because the elements of T in the window must occur in order.
     *
     *
     * Note:
     *
     * All the strings in the input will only contain lowercase letters.
     * The length of S will be in the range [1, 20000].
     * The length of T will be in the range [1, 100].
     *
     * Hard
     *
     * https://leetcode.com/problems/minimum-window-subsequence
     */

    /**
     * Sliding Window
     *
     * 1.Scan from left to right, find a window that has T
     * 2.Scan from right to left in this window, try to optimize the length
     *
     * 本质上是sliding window, 先找到一个满足要求的window， 然后优化。
     * 特殊的是，这里的优化不是移动左边界，而是往回扫描。
     *
     * 要注意两个坐标的调整。每当完成扫描时，当前坐标值已经越界，要调整回来。
     *
     * 扫描的过程就是判断是否是，这里，sIdx always move, tIdx only moves if a match happens.
     *
     * Time  : O(mn)
     * Space : O(1)
     *
     * 和 LE_76_Minimum_Window_Substring 不同的是，这里要求的是subsequence, 所以字符出现的先后顺序是要考虑的。
     */
    class Solution1 {
        public String minWindow(String S, String T) {
            if (S == null || T == null || S.length() < T.length()) return "";

            int sLen = S.length();
            int tLen = T.length();
            char[] s = S.toCharArray();
            char[] t = T.toCharArray();

            int min = Integer.MAX_VALUE;
            int start = -1;

            int sIdx = 0, tIdx = 0;

            while (sIdx < sLen) {
                if (s[sIdx] == t[tIdx]) {
                    tIdx++;

                    if (tIdx == tLen) { //找到了一个合乎要求的window
                        int end = sIdx;
                        tIdx--;

                        while (tIdx >= 0) {//然后往回扫描，找到最小的window长度
                            if (s[sIdx] == t[tIdx]) {
                                tIdx--;
                            }
                            sIdx--;
                        }

                        sIdx++;
                        tIdx++;

                        if (end - sIdx + 1 < min) {
                            min = end - sIdx + 1;
                            start = sIdx;
                        }
                    }
                }

                sIdx++;//!!!
            }

            return start == -1 ? "" : S.substring(start, start + min);
        }
    }

    class Solution1_Variation {
        /**
         * Instead of String, given strings that have words separated by space.
         *
         * 给你一个很长的文本，和一个query，找到文本中最短的，包含这个 query的 内容，
         * 比如，文本是:
         * This is a river and this river has a drowning dog.
         * query 是：this river,
         * 包含这个query的文本有 （this is a river,  this river），
         * 最短的是 this river
         */
        public String minWindow(String text, String target) {
            if (text == null || target == null || text.length() < target.length()) return "";

            String[] s = text.split(" ");
            String[] t = target.split(" ");

            if (s == null || t == null || s.length < t.length) return "";

            int sLen = s.length;
            int tLen = t.length;

            int min = Integer.MAX_VALUE;
            int start = -1;

            int sIdx = 0, tIdx = 0;

            while (sIdx < sLen) {
                if (s[sIdx].equals(t[tIdx])) {
                    tIdx++;

                    if (tIdx == tLen) {
                        int end = sIdx;
                        tIdx--;

                        while (tIdx >= 0) {
                            if (s[sIdx].equals(t[tIdx])) {
                                tIdx--;
                            }
                            sIdx--;
                        }

                        sIdx++;
                        tIdx++;

                        if (end - sIdx + 1 < min) {
                            min = end - sIdx + 1;
                            start = sIdx;
                        }
                    }
                }

                sIdx++;
            }

            return start == -1 ? "" : String.join(" ", Arrays.copyOfRange(s, start, start + min));
        }
    }

    /**
     * Same sliding window, but use String.indexOf()
     */
    class Solution2 {
        public String minWindow(String S, String T) {
            if (S == null || T == null || S.length() < T.length()) return "";

            char[] t = T.toCharArray();
            int i = -1;
            int min = Integer.MAX_VALUE;
            String res = "";

            while (true) {
                for (char c : t) {
                    i = S.indexOf(c, i + 1);

                    if (i == -1) return res;
                }

                int k = ++i;

                for (int j = t.length - 1; j >= 0; j--) {
                    i = S.lastIndexOf(t[j], i - 1);
                }

                if (res.equals("") || k - i< res.length()) {
                    res = S.substring(i, k);
                }
            }
        }
    }

    /**
     * https://leetcode.com/problems/minimum-window-subsequence/discuss/109362/Java-Super-Easy-DP-Solution-(O(mn))
     *
     * dp[i][j] stores the starting index of the substring where T has length i and S has length j.
     *
     * So dp[i][j would be:
     * if T[i - 1] == S[j - 1], this means we could borrow the start index from dp[i - 1][j - 1]
     * to make the current substring valid;
     * else, we only need to borrow the start index from dp[i][j - 1] which could either exist or not.
     *
     * Finally, go through the last row to find the substring with min length and appears first
     */
    class Solution_DP{
        public String minWindow(String S, String T) {
            int m = T.length(), n = S.length();
            int[][] dp = new int[m + 1][n + 1];
            for (int j = 0; j <= n; j++) {
                dp[0][j] = j + 1;
            }
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (T.charAt(i - 1) == S.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = dp[i][j - 1];
                    }
                }
            }

            int start = 0, len = n + 1;
            for (int j = 1; j <= n; j++) {
                if (dp[m][j] != 0) {
                    if (j - dp[m][j] + 1 < len) {
                        start = dp[m][j] - 1;
                        len = j - dp[m][j] + 1;
                    }
                }
            }
            return len == n + 1 ? "" : S.substring(start, start + len);
        }
    }
    /**
     * Brutal Force
     *
     * Time : O((n - m) * n)
     */
    class Solution_Mine {
        public String minWindow(String S, String T) {
            if (S == null || T == null || S.length() < T.length()) return "";

            int n = S.length();
            int m = T.length();
            char[] chs1 = S.toCharArray();
            char[] chs2 = T.toCharArray();

            int min = Integer.MAX_VALUE;
            int start = -1;

            for (int i = 0; i < n - m; i++) {
                while (i < n && chs1[i] != chs2[0]) {
                    i++;
                }

                int l = find(chs1, chs2, i);
                if (l < min && l != -1) {
                    start = i;
                    min = l;
                }
            }

            if (start == -1 || min == Integer.MAX_VALUE) return "";

            return S.substring(start, start + min);
        }

        private int find(char[] chs1, char[] chs2, int start) {
            int idx = 0;
            for (int i = start; i < chs1.length; i++) {
                if (chs1[i] == chs2[idx]) {
                    idx++;
                    if (idx == chs2.length) {
                        return i - start + 1;
                    }
                }
            }

            return -1;
        }
    }
}
