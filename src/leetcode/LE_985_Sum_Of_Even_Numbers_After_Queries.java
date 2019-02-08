package leetcode;

public class LE_985_Sum_Of_Even_Numbers_After_Queries {
    /**
     * We have an array A of integers, and an array queries of queries.
     *
     * For the i-th query val = queries[i][0], index = queries[i][1], we add val to A[index].
     * Then, the answer to the i-th query is the sum of the even values of A.
     *
     * (Here, the given index = queries[i][1] is a 0-based index, and each query permanently modifies the array A.)
     *
     * Return the answer to all queries.  Your answer array should have answer[i] as the answer to the i-th query.
     *
     * Example 1:
     *
     * Input: A = [1,2,3,4], queries = [[1,0],[-3,1],[-4,0],[2,3]]
     * Output: [8,6,2,4]
     * Explanation:
     * At the beginning, the array is [1,2,3,4].
     * After adding 1 to A[0], the array is [2,2,3,4], and the sum of even values is 2 + 2 + 4 = 8.
     * After adding -3 to A[1], the array is [2,-1,3,4], and the sum of even values is 2 + 4 = 6.
     * After adding -4 to A[0], the array is [-2,-1,3,4], and the sum of even values is -2 + 4 = 2.
     * After adding 2 to A[3], the array is [-2,-1,3,6], and the sum of even values is -2 + 6 = 4.
     *
     *
     * Note:
     * 1 <= A.length <= 10000
     * -10000 <= A[i] <= 10000
     * 1 <= queries.length <= 10000
     * -10000 <= queries[i][0] <= 10000
     * 0 <= queries[i][1] < A.length
     *
     * Easy
     */
    class Solution {
        public int[] sumEvenAfterQueries(int[] A, int[][] queries) {
            if (null == A || A.length == 0) return new int[]{};

            int sum = 0;
            for (int a : A) {
                /**
                 * !!!
                 * Only add even number
                 */
                if (a % 2 == 0) {
                    sum += a;
                }
            }

            int n = queries.length;
            int[] res = new int[n];

            for (int i = 0; i < n; i++) {
                int val = A[queries[i][1]];

                if (val % 2 == 0) {
                    sum -= val;
                }

                int newVal = val + queries[i][0];
                /**
                 * !!!
                 * Don't forget to set new value
                 */
                A[queries[i][1]] = newVal;

                if (newVal % 2 == 0) {
                    sum += newVal;
                }

                res[i] = sum;
            }

            return res;
        }
    }
}
