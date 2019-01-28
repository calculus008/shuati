package leetcode;

public class LE_982_Triples_With_Bitwise_AND_Equal_To_Zero {
    /**
     * Given an array of integers A, find the number of triples of indices (i, j, k) such that:
     *
     * 0 <= i < A.length
     * 0 <= j < A.length
     * 0 <= k < A.length
     * A[i] & A[j] & A[k] == 0, where & represents the bitwise-AND operator.
     *
     *
     * Example 1:
     *
     * Input: [2,1,3]
     * Output: 12
     * Explanation: We could choose the following i, j, k triples:
     * (i=0, j=0, k=1) : 2 & 2 & 1
     * (i=0, j=1, k=0) : 2 & 1 & 2
     * (i=0, j=1, k=1) : 2 & 1 & 1
     * (i=0, j=1, k=2) : 2 & 1 & 3
     * (i=0, j=2, k=1) : 2 & 3 & 1
     * (i=1, j=0, k=0) : 1 & 2 & 2
     * (i=1, j=0, k=1) : 1 & 2 & 1
     * (i=1, j=0, k=2) : 1 & 2 & 3
     * (i=1, j=1, k=0) : 1 & 1 & 2
     * (i=1, j=2, k=0) : 1 & 3 & 2
     * (i=2, j=0, k=1) : 3 & 2 & 1
     * (i=2, j=1, k=0) : 3 & 1 & 2
     *
     *
     * Note:
     *
     * 1 <= A.length <= 1000
     * 0 <= A[i] < 2^16
     *
     * Hard
     */

    /**
     * https://zxi.mytechroad.com/blog/bit/leetcode-982-triples-with-bitwise-and-equal-to-zero/
     *
     * Brutal Force time is O(n ^ 3)
     *
     * Optimize it by pre-computing all AND value between elements in A.
     * count[] is actually used as hashmap, its value is the result of AND operation between
     * 2 numbers in A, its value is the number of appearance of this reult.
     *
     * Loop A, replace inner loop in brutal force with for loop from 0 to max value in A.
     *
     * Time  : O(n ^ 2 + n * max)
     * Space : O(max)
     */
    class Solution {
        public int countTriplets(int[] A) {
            int max = Integer.MIN_VALUE;
            for (int a : A) {
                max = Math.max(a, max);
            }

            int[] count = new int[max + 1];

            for (int a : A) {
                for (int b : A) {
                    count[a & b]++;
                }
            }

            int res = 0;
            for (int a : A) {
                for (int i = 0; i <= max; i++) {
                    if ((a & i) == 0) {
                        res += count[i];
                    }
                }
            }

            return res;
        }
    }
}
