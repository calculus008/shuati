package leetcode;

import java.util.ArrayList;
import java.util.List;

public class LE_969_Pancake_Sorting {
    /**
     * Given an array A, we can perform a pancake flip: We choose some positive integer k <= A.length,
     * then reverse the order of the first k elements of A.  We want to perform zero or more pancake
     * flips (doing them one after another in succession) to sort the array A.
     *
     * Return the k-values corresponding to a sequence of pancake flips that sort A.  Any valid answer
     * that sorts the array within 10 * A.length flips will be judged as correct.
     *
     * Example 1:
     * Input: [3,2,4,1]
     * Output: [4,2,4,3]
     * Explanation:
     * We perform 4 pancake flips, with k values 4, 2, 4, and 3.
     * Starting state: A = [3, 2, 4, 1]
     * After 1st flip (k=4): A = [1, 4, 2, 3]
     * After 2nd flip (k=2): A = [4, 1, 2, 3]
     * After 3rd flip (k=4): A = [3, 2, 1, 4]
     * After 4th flip (k=3): A = [1, 2, 3, 4], which is sorted.
     *
     * Example 2:
     * Input: [1,2,3]
     * Output: []
     * Explanation: The input is already sorted, so there is no need to flip anything.
     * Note that other answers, such as [3, 3], would also be accepted.
     *
     *
     * Note:
     *
     * 1 <= A.length <= 100
     * A[i] is a permutation of [1, 2, ..., A.length] (!!!)
     *
     * Medium
     */

    /**
     * O(n ^ 2)
     *
     * Find the largest number
     * Flip twice to the tail
     *
     * [3,2,4,1]
     *      ^
     *
     * [4,2,3,1]    3
     * [1,3,2,4]    4
     *    ^
     *
     * [3,1,2,4]    2
     * [2,1,3,4]    3
     *  ^
     *
     * [2,1,3,4]    1
     * [1,2,3,4]    2
     *
     */
    class Solution {
        public List<Integer> pancakeSort(int[] A) {
            List<Integer> res = new ArrayList<>();
            int n = A.length;

            int target = n;
            /**
             * for n number, only need to process n - 1 times,the last one will be
             * in the right place
             */
            for (int i = 0; i < n - 1; i++) {
                int idx = find(A, target);

                flip(A, 0, idx);
                res.add(idx + 1);

                flip(A, 0, target - 1);
                res.add(target);
                target--;
            }

            return res;
        }

        private int find(int[] A, int k) {
            for (int i = 0; i < A.length; i++) {
                if (A[i] == k) {
                    return i;
                }
            }

            return -1;
        }

        private void flip(int[] A, int i, int j) {
            while (i < j) {
                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;
                i++;
                j--;
            }
        }
    }
}
