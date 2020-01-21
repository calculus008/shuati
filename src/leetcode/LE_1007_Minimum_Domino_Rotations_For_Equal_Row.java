package src.leetcode;

public class LE_1007_Minimum_Domino_Rotations_For_Equal_Row {
    /**
     * In a row of dominoes, A[i] and B[i] represent the top and bottom halves of
     * the i-th domino.  (A domino is a tile with two numbers from 1 to 6 - one
     * on each half of the tile.)
     *
     * We may rotate the i-th domino, so that A[i] and B[i] swap values.
     *
     * Return the minimum number of rotations so that all the values in A are the
     * same, or all the values in B are the same.
     *
     * If it cannot be done, return -1.
     *
     *
     *
     * Example 1:
     *
     *
     *
     * Input: A = [2,1,2,4,2,2], B = [5,2,6,2,3,2]
     * Output: 2
     * Explanation:
     * The first figure represents the dominoes as given by A and B: before we do
     * any rotations.
     * If we rotate the second and fourth dominoes, we can make every value in the
     * top row equal to 2, as indicated by the second figure.
     * Example 2:
     *
     * Input: A = [3,5,1,2,3], B = [3,6,3,3,4]
     * Output: -1
     * Explanation:
     * In this case, it is not possible to rotate the dominoes to make one row of
     * values equal.
     *
     *
     * Note:
     *
     * 1 <= A[i], B[i] <= 6
     * 2 <= A.length == B.length <= 20000
     *
     * Medium
     */

    /**
     * Count the occurrence of all numbers in A and B,
     * and also the number of domino with two same numbers.
     *
     * Try all possibilities from 1 to 6.
     * If we can make number i in a whole row,
     * it should satisfy that countA[i] + countB[i] - same[i] = n
     *
     * Take example of
     * A = [2,1,2,4,2,2]
     * B = [5,2,6,2,3,2]
     *
     * countA[2] = 4, as A[0] = A[2] = A[4] = A[5] = 2
     * countB[2] = 3, as B[1] = B[3] = B[5] = 2
     * same[2] = 1, as A[5] = B[5] = 2
     *
     * We have countA[2] + countB[2] - same[2] = 6,
     * so we can make 2 in a whole row.
     *
     * Time O(N), Space O(1)
     */
    class Solution {
        public int minDominoRotations(int[] A, int[] B) {
            int n = A.length;
            int[] countA = new int[7];
            int[] countB = new int[7];
            int[] same = new int[7];

            for (int i = 0; i < n; i++) {
                countA[A[i]]++;
                countB[B[i]]++;
                if (A[i] == B[i]) {
                    same[A[i]]++;
                }
            }

            for (int i = 1; i <= 6; i++) {
                if (countA[i] + countB[i] - same[i] == n) {
                    return n - Math.max(countA[i], countB[i]);
                }
            }
            return -1;
        }
    }
}
