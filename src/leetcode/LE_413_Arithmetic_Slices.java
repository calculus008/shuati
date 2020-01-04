package leetcode;

public class LE_413_Arithmetic_Slices {
    /**
     * A sequence of number is called arithmetic if it consists of at least three elements
     * and if the difference between any two consecutive elements is the same.
     *
     * For example, these are arithmetic sequence:
     *
     * 1, 3, 5, 7, 9
     * 7, 7, 7, 7
     * 3, -1, -5, -9
     * The following sequence is not arithmetic.
     *
     * 1, 1, 2, 5, 7
     *
     * A zero-indexed array A consisting of N numbers is given. A slice of that array is
     * any pair of integers (P, Q) such that 0 <= P < Q < N.
     *
     * A slice (P, Q) of array A is called arithmetic if the sequence:
     * A[P], A[p + 1], ..., A[Q - 1], A[Q] is arithmetic. In particular, this means
     * that P + 1 < Q.
     *
     * The function should return the number of arithmetic slices in the array A.
     *
     * Example:
     *
     * A = [1, 2, 3, 4]
     *
     * return: 3, for 3 arithmetic slices in A: [1, 2, 3], [2, 3, 4] and [1, 2, 3, 4] itself.
     */

    /**
     * 等差数列长度 >= 3的个数
     *
     * 1 2 3  ： 1
     *
     * 1 2 3 4 ： 1 + 2 （ 1 2 3， 1 2 3 4）
     *
     * 1 2 3 4 5 ： 1 + 2 + 3  （3 4 5， 2 3 4 5， 1 2 3 4 5）
     *
     * ......
     *
     * 所以在长度为n的等差数列中，长度 >= 3的等差数列的个数：
     *
     * 1 + 2 + 3 .... + n = n * (n + 1) / 2
     *
     * Variation : ValidSubarray
     */

    class Solution {
        public int numberOfArithmeticSlices(int[] A) {
            if (null == A || A.length < 3) return 0;

            int res = 0;
            int count = 0;

            /**
             * !!!
             * 我们需要往回看两个前面相邻的元素，所以从i=2开始循环
             */
            for (int i = 2; i < A.length; i++) {
                if (A[i] - A[i - 1] == A[i - 1] - A[i - 2]) {
                    count++;
                    /**
                     * 1 + 2 + 3....
                     */
                    res += count;
                } else {
                    count = 0;
                }
            }

            return res;
        }
    }
}
