package leetcode;

public class LE_949_Largest_Time_For_Given_Digits {
    /**
     * Given an array of 4 digits, return the largest 24 hour time that can be made.
     *
     * The smallest 24 hour time is 00:00, and the largest is 23:59.  Starting from 00:00,
     * a time is larger if more time has elapsed since midnight.
     *
     * Return the answer as a string of length 5.  If no valid time can be made, return a
     * n empty string.
     *
     * Example 1:
     *
     * Input: [1,2,3,4]
     * Output: "23:41"
     * Example 2:
     *
     * Input: [5,5,5,5]
     * Output: ""
     *
     *
     * Note:
     *
     * A.length == 4
     * 0 <= A[i] <= 9
     *
     * Easy
     */

    /**
     * The inner most loop at most iterates 4 * 4 * 4 = 64 times.
     * A[i], A[j], A[k], & A[l] are the 4 elements of A, where i, j, k & l are the permutation of 0, 1, 2, & 3.
     * Therefore, since i + j + k + l = 0 + 1 + 2 + 3 = 6, we have l = 6 - i - j - k.
     */
    class Solution {
        public String largestTimeFromDigits(int[] A) {
            String res = "";
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    for (int k = 0; k < 4; k++) {
                        if (i == j || j == k || i == k) continue;

                        String h = "" + A[i] + A[j];
                        String m = "" + A[k] + A[6 - i - j - k];
                        String t = h + ":" + m;

                        if (h.compareTo("24") < 0 && m.compareTo("60") < 0 && res.compareTo(t) < 0) {
                            res = t;
                        }
                    }
                }
            }

            return res;
        }
    }
}
