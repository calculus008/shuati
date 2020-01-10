package src.leetcode;

public class LE_821_Shortest_Distance_To_A_Character {
    /**
     * Given a string S and a character C, return an array of integers
     * representing the shortest distance from the character C in the string.
     *
     * Example 1:
     *
     * Input: S = "loveleetcode", C = 'e'
     * Output: [3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0]
     *
     *
     * Note:
     *
     * S string length is in [1, 10000].
     * C is a single character, and guaranteed to be in string S.
     * All letters in S and C are lowercase.
     *
     * Easy
     */

    /**
     * Loop twice on the string S.
     * First forward pass to find shortest distant to character on left.
     * Second backward pass to find shortest distant to character on right.
     *
     * Input: S = "loveleetcode", C = 'e'
     *
     * After first pass:
     * res = {12, 13, 14, 0, 1, 0, 0, 1, 2, 3, 4, 0}
     *
     * After second pass:
     * res = {3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0}
     */
    class Solution1 {
        public int[] shortestToChar(String S, char C) {
            int n = S.length();
            int[] res = new int[n];

            int pos = -n;

            for (int i = 0; i < n; i++) {
                if (C == S.charAt(i)) {
                    pos = i;
                } else {
                    res[i] = i - pos;
                }
            }

            for (int i = n - 1; i >= 0; i--) {
                if (C == S.charAt(i)) {
                    pos = i;
                } else {
                    res[i] = Math.min(res[i], Math.abs(i - pos));
                }
            }

            return res;
        }
    }

    class Solution2 {
        public int[] shortestToChar(String S, char C) {
            int N = S.length();
            int[] ans = new int[N];
            int prev = Integer.MIN_VALUE / 2;

            for (int i = 0; i < N; ++i) {
                if (S.charAt(i) == C) prev = i;
                ans[i] = i - prev;
            }

            prev = Integer.MAX_VALUE / 2;
            for (int i = N-1; i >= 0; --i) {
                if (S.charAt(i) == C) prev = i;
                ans[i] = Math.min(ans[i], prev - i);
            }

            return ans;
        }
    }
}
