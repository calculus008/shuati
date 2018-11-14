package leetcode;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by yuank on 11/13/18.
 */
public class LE_873_Length_Of_Longest_Fibonacci_Subsequence {
    /**
         A sequence X_1, X_2, ..., X_n is fibonacci-like if:

         n >= 3
         X_i + X_{i+1} = X_{i+2} for all i + 2 <= n

         Given a strictly increasing array A of positive integers forming a sequence,
         find the length of the longest fibonacci-like subsequence of A.  If one does
         not exist, return 0.

         (Recall that a subsequence is derived from another sequence A by deleting any
         number of elements (including none) from A, without changing the order of the
         remaining elements.  For example, [3, 5, 8] is a subsequence of [3, 4, 5, 6, 7, 8].)



         Example 1:
         Input: [1,2,3,4,5,6,7,8]
         Output: 5
         Explanation:
         The longest subsequence that is fibonacci-like: [1,2,3,5,8].

         Example 2:
         Input: [1,3,7,11,12,14,18]
         Output: 3
         Explanation:
         The longest subsequence that is fibonacci-like:
         [1,11,12], [3,11,14] or [7,11,18].


         Note:

         3 <= A.length <= 1000
         1 <= A[0] < A[1] < ... < A[A.length - 1] <= 10^9
         (The time limit has been reduced by 50% for submissions in Java, C, and C++.)

         Medium
     */

    //https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-873-length-of-longest-fibonacci-subsequence/

    /**
     * Solution 1
     * HashTable
     * Time  : O(n ^ 3)
     * Space : O(n)
     * 78 ms
     */
    public int lenLongestFibSubseq1(int[] A) {
        if (null == A || A.length == 0) {
            return 0;
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        int n = A.length;
        int res = 0;

        /**
         * put all number in a hashmap so we can check a value in O(1)
         */
        for (int i = 0; i < n; i++) {
            map.put(A[i], i);
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int a = A[i];
                int b = A[j];
                int c = a + b;

                /**
                 * !!!
                 * check each starting position i and j, see how far it can go
                 * to form a fibonacci sequence, find the max
                 */
                int len = 2;

                while (map.containsKey(c)) {
                    a = b;
                    b = c;
                    c = a + b;
                    len++;
                    res = Math.max(res, len);
                }
            }
        }

        return res;
    }

    /**
         Solution 2
         DP with HashMap
         Time  : O(n ^ 2)
         Space : O(n ^ 2)

         dp[i][j] : max length of fibonacci sequence that ends with index i and j,
                    in other words, the last 2 values of the longest fibonacci
                    sequence is A[i] and A[j]

         So for index k (i < j < k), if A[i] + A[j] = A[k], then dp[j, k] = dp[i][j] + 1
         (one more number into the sequence),


         A ={1,2,3,4,5}

          After init:
              1 2 3 4 5
           1  2 2 2 2 2
           2  2 2 2 2 2
           3  2 2 2 2 2
           4  2 2 2 2 2
           5  2 2 2 2 2

         k = 2, j = 1, A[2] = 3, A[1] = 2, A[2] - A[1] = 1, 1 exists in A, index is 0 (i),
         dp[j][k] = dp[1][2] = dp[i][j] + 1 = dp[0][1] + 1 = 3

      idx      0 1 2 3 4
               1 2 3 4 5
         0  1  2 2 2 2 2
         1  2  2 2 3 2 2
         2  3  2 2 2 2 2
         3  4  2 2 2 2 2
         4  5  2 2 2 2 2

         k = 3, j = 2, A[3] = 4, A[2] = 3, A[3] - A[2] = 1, 1 exists in A, index is 0 (i),
         dp[j][k] = dp[2][3] = dp[i][j] + 1 = dp[0][2] + 1 = 3

      idx      0 1 2 3 4
               1 2 3 4 5
         0  1  2 2 2 2 2
         1  2  2 2 3 2 2
         2  3  2 2 2 3 2
         3  4  2 2 2 2 2
         4  5  2 2 2 2 2

         k = 4, j = 2, A[4] = 5, A[2] = 3, A[3] - A[2] = 2, 2 exists in A, index is 1 (i),
         dp[j][k] = dp[2][4] = dp[i][j] + 1 = dp[1][2] + 1 = 4

     idx       0 1 2 3 4
               1 2 3 4 5
         0  1  2 2 2 2 2
         1  2  2 2 3 2 2
         2  3  2 2 2 3 4
         3  4  2 2 2 2 2
         4  5  2 2 2 2 2
     **/
    public int lenLongestFibSubseq(int[] A) {
        if (null == A || A.length == 0) {
            return 0;
        }

        int n = A.length;

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(A[i], i);
        }

        /**
         * !!!
         * init dp with 2, 2 is not a valid length for fabonacci sequence
         */
        int[][] dp = new int[n][n];
        for (int[] a : dp) {
            Arrays.fill(a, 2);
        }


        int res = 0;
        for (int j = 0; j < n; j++) {
            for (int k = j + 1; k < n; k++) {
                int c = A[k] - A[j];

                /**
                 * !!!
                 * Pruning, if c >= A[j], it's not possible to form fibonacci sequence with C, A[j] and A[k]
                 * since A is strictly increasing. After current k value, k keeps increasing and c will increase,
                 * so no need to continue current k loop.
                 */
                if (c >= A[j]) {
                    break;
                }

                if (map.containsKey(c)) {
                    int i = map.get(c);
                    dp[j][k] = dp[i][j] + 1;
                    res = Math.max(res, dp[j][k]);
                }
            }
        }

        return res;
    }
}
