package leetcode;

public class LE_922_Sort_Array_By_Parity_II {
    /**
     * Given an array A of non-negative integers, half of the integers in A are odd,
     * and half of the integers are even.
     *
     * Sort the array so that whenever A[i] is odd, i is odd;
     * and whenever A[i] is even, i is even.
     *
     * You may return any answer array that satisfies this condition.
     *
     * Example 1:
     *
     * Input: [4,2,5,7]
     * Output: [4,5,2,7]
     * Explanation: [4,7,2,5], [2,5,4,7], [2,7,4,5] would also have been accepted.
     *
     *
     * Note:
     *
     * 2 <= A.length <= 20000
     * A.length % 2 == 0
     * 0 <= A[i] <= 1000
     */

    public int[] sortArrayByParityII(int[] A) {
        for (int i = 0, j = 1; i < A.length; i += 2) {
            if (A[i] % 2 == 1) {
                while (j < A.length && A[j] % 2 == 1) {
                    j += 2;
                }

                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            }
        }

        return A;
    }

    /**
     * 变形题
     *
     * 一个未排序整数数组，有正负数，重新排列使负数排在正数前面，并且要求不改变原来的正负数之间相对顺序，
     * 比如： input: 1,7,-5,9,-12,15 ans: -5,-12,1,7,9,15
     *
     * 要求时间复杂度O(n),空间O(1)。
     */


}
