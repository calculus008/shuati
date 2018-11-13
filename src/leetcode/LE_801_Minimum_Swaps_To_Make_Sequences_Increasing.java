package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 11/11/18.
 */
public class LE_801_Minimum_Swaps_To_Make_Sequences_Increasing {
    /**
         We have two integer sequences A and B of the same non-zero length.

         We are allowed to swap elements A[i] and B[i].  Note that both elements
         are in the same index position in their respective sequences.

         At the end of some number of swaps, A and B are both strictly increasing.
         (A sequence is strictly increasing if and only if A[0] < A[1] < A[2] < ... < A[A.length - 1].)

         Given A and B, return the minimum number of swaps to make both sequences strictly increasing.
         It is guaranteed that the given input always makes it possible.

         Example:
         Input: A = [1,3,5,4], B = [1,2,3,7]
         Output: 1

         Explanation:
         Swap A[3] and B[3].  Then the sequences are:
         A = [1, 3, 5, 7] and B = [1, 2, 3, 4]
         which are both strictly increasing.
         Note:

         A, B are arrays with the same length, and that length will be in the range [1, 1000].
         A[i], B[i] are integer values in the range [0, 2000].
     */

    /**
     * Solution 1
     * DFS
     * Time : O(2 ^ n)
     * TLE
     */
    class Solution1 {
        int res = Integer.MAX_VALUE;
        public int minSwap(int[] A, int[] B) {
            if (A == null || B == null || A.length == 0 || B.length == 0) {
                return 0;
            }

            helper(A, B, 0, 0);
            return res;
        }

        private void helper(int[] A, int[] B, int i, int cur) {
            if (cur >= res) {
                return;
            }

            if (i == A.length) {
                res = Math.min(cur, res);
                return;
            }

            if (i == 0 || (A[i] > A[i - 1] && B[i] > B[i - 1])) {
                helper(A, B, i + 1, cur);
            }

            if (i == 0 || (A[i] > B[i - 1] && B[i] > A[i - 1])) {
                swap(A, B, i);
                helper(A, B, i + 1, cur + 1);
                swap(A, B, i);
            }
        }

        private void swap(int[] A, int[] B, int i) {
            int temp = A[i];
            A[i] = B[i];
            B[i] = temp;
        }
    }

    /**
     * Solution 2
     * DP
     * Time : O(n)
     *
     * Use two states array
     * keep[i] : min swaps to make A[0] ~ A[i] and B[0] ~ B[i] strictly increase without swapping at index i (not swap A[i] and B[i])
     * swap[i] : min swaps to make A[0] ~ A[i] and B[0] ~ B[i] strictly increase with swapping at index i (swap A[i] and B[i])
     *
     * if A[i] > A[i - 1] && B[i] > B[i - 1]
     * #1.No Swap
     * keep[i] = keep[i - 1]
     *
     * #2.Swap both i and i - 1
     * swap[i] = swap[i - 1] + 1
     *
     * if A[i] > B[i - 1] && B[i] > A[i - 1]
     * #3.Only Swap i
     * swap[i] = min(swap[i], keep[i - 1] + 1)
     *
     * $4.Only Swap i - 1
     * keep[i] = min(keep[i], swap[i - 1])
     */
    class Solution2 {
        int res = Integer.MAX_VALUE;

        public int minSwap(int[] A, int[] B) {
            if (A == null || B == null || A.length == 0 || B.length == 0) {
                return 0;
            }

            int n = A.length;
            int[] keep = new int[n];
            int[] swap = new int[n];
            Arrays.fill(keep, Integer.MAX_VALUE);
            Arrays.fill(swap, Integer.MAX_VALUE);
            keep[0] = 0;
            swap[0] = 1;

            for (int i = 1; i < n; i++) {
                if (A[i] > A[i - 1] && B[i] > B[i - 1]) {
                    keep[i] = keep[i - 1];
                    swap[i] = swap[i - 1] + 1;
                }

                if (A[i] > B[i - 1] && B[i] > A[i - 1]) {
                    swap[i] = Math.min(swap[i], keep[i - 1] + 1);
                    keep[i] = Math.min(keep[i], swap[i - 1]);
                }
            }

            return Math.min(keep[n - 1], swap[n - 1]);
        }
    }

}
