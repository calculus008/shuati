package leetcode;

public class LE_905_Sort_Array_By_Parity {
    /**
     * Given an array A of non-negative integers, return an array consisting
     * of all the even elements of A, followed by all the odd elements of A.
     *
     * You may return any answer array that satisfies this condition.
     *
     *
     *
     * Example 1:
     *
     * Input: [3,1,2,4]
     * Output: [2,4,3,1]
     *
     * The outputs [4,2,3,1], [2,4,1,3], and [4,2,1,3] would also be accepted.
     */

    /**
     * In-place
     *
     * Time  : O(n)
     * Space : O(1)
     */
    public int[] sortArrayByParity(int[] A) {
        int l = 0;
        int r = A.length - 1;

        while (l < r) {
            if (A[l] % 2 > A[r] % 2) {
                int temp = A[l];
                A[l] = A[r];
                A[r] = temp;
            }

            if (A[l] % 2 == 0) {
                l++;
            }
            if (A[r] % 2 == 1) {
                r--;
            }
        }

        return A;
    }
}
