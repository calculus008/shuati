package leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class LE_786_Kth_Smallest_Prime_Fraction {
    /**
         A sorted list A contains 1, plus some number of primes.
         Then, for every p < q in the list, we consider the fraction p/q.

         What is the K-th smallest fraction considered?
         Return your answer as an array of ints, where answer[0] = p and answer[1] = q.

         Examples:
         Input: A = [1, 2, 3, 5], K = 3
         Output: [2, 5]
         Explanation:
         The fractions to be considered in sorted order are:
         1/5, 1/3, 2/5, 1/2, 3/5, 2/3.
         The third fraction is 2/5.

         Input: A = [1, 7], K = 1
         Output: [1, 7]
         Note:

         A will have length between 2 and 2000.
         Each A[i] will be between 1 and 30000.
         K will be between 1 and A.length * (A.length - 1) / 2.

         Hard
     */

    /**
     * LE_378_Kth_Smallest_Element_In_A_Sorted_Matrix
     * LE_719_Find_Kth_Smallest_Pair_Distance
     */

    /**
     * http://zxi.mytechroad.com/blog/two-pointers/leetcode-786-k-th-smallest-prime-fraction/
     *
     * Solution 1
     * Binary Search
     *
     * Find m (0 < m < 1) so that there are exact K pairs of (i, j) that A[i]/A[j] <= m
     *
     * Time  : O(nlog(max)), max is max element in A
     * Space : O(1)
     *
     * Virtual Matrix to speed up search process, example:
     *
     *  A = [1, 2, 3, 5]
     *
     *    decrease
     * -------------->  ^
     * 1/2   1/3   1/5  |
     *       2/3   2/5  | decrease
     *             3/5  |
     *
     *
     */
    public int[] kthSmallestPrimeFraction1(int[] A, int K) {
        double l = 0;
        double r = 1.0;
        int n = A.length;

        while (l < r) {
            double m = l + (r - l) / 2;
            double max = 0.0;

            int p = 0;
            int q = 0;
            int total = 0;
            int j = 1;

            /**
             * j is column in virtual matrix.
             * j value  is not reset in outer loop, so it keep increasing,
             * that's why time for the loop is O(n).
             *
             * In virtual matrix, for each column, from top to bottom,
             * value  increases, that's why we can keep j increasing,
             * since we for the same j, value in the next row (i + 1)
             * must be bigger than row i. We just need to go from j,
             * move to left, no need to go back to column 0.
             */
            for (int i = 0; i < n - 1; i++) {
                /**
                 * In virtual matrix, for every row, number value decreases from left to right.
                 * Here we try to find the first element in virtual matrix whose value is smaller
                 * than or equal to m.
                 */
                while (j < n && A[i] > m * A[j]) {
                    j++;
                }

                /**
                 * add number of elements in this row that is smaller than or equal to m.
                 */
                total += (n - j);

                /**
                 * n == j means there's no more elements that is smaller than m (n - j == 0),
                 * no need to go to the next row.
                 */
                if (n == j) {
                    break;
                }

                double f = (double)A[i] /A[j];
                if (f > max) {
                    p = i;
                    q = j;
                    max = f;
                }
            }

            if (total == K) {
                return new int[]{A[p], A[q]};
            } else if (total < K) {
                l = m;//!!! not "m + 1"
            } else {
                r = m;
            }
        }

        return new int[]{};
    }

    //Remove all comments
    public int[] kthSmallestPrimeFraction1_clean(int[] A, int K) {
        double l = 0;
        double r = 1.0;
        int n = A.length;

        while (l < r) {
            double m = l + (r - l) / 2;
            double max = 0.0;

            int p = 0;
            int q = 0;
            int total = 0;

            int j = 1;

            for (int i = 0; i < n - 1; i++) {
                while (j < n && A[i] > m * A[j]) {
                    j++;
                }
                if (n == j) {
                    break;
                }
                total += (n - j);

                double f = (double)A[i] /A[j];
                if (f > max) {
                    p = i;
                    q = j;
                    max = f;
                }
            }

            if (total == K) {
                return new int[]{A[p], A[q]};
            } else if (total < K) {
                l = m;//!!! not "m + 1"
            } else {
                r = m;
            }
        }

        return new int[]{};
    }

    /**
     * https://leetcode.com/problems/k-th-smallest-prime-fraction/discuss/115819/Summary-of-solutions-for-problems-%22reducible%22-to-LeetCode-378
     *
     * Solution 2
     * PriorityQueue
     * Time  : O((n + k)logn)
     * Space : O(n)
     */
    public int[] kthSmallestPrimeFraction2(int[] A, int K) {
        int n = A.length;

        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(A[a[0]] * A[n - 1 - b[1]], A[n - 1 - a[1]] * A[b[0]]);
            }
        });

        for (int i = 0; i < n; i++) {
            pq.offer(new int[] {i, 0});
        }

        while (--K > 0) {
            int[] p = pq.poll();

            if (++p[1] < n) {
                pq.offer(p);
            }
        }

        return new int[] {A[pq.peek()[0]], A[n - 1 - pq.peek()[1]]};
    }

    /**
     * https://leetcode.com/problems/k-th-smallest-prime-fraction/discuss/115486/Java-AC-O(max(nk)-*-logn)-Short-Easy-PriorityQueue
     *
     * This solution probably doesn't have the best runtime but it's really simple and easy to understand.
     *
     * Says if the list is [1, 7, 23, 29, 47], we can easily have this table of relationships
     *
     * 1/47  < 1/29    < 1/23 < 1/7
     * 7/47  < 7/29    < 7/23
     * 23/47 < 23/29
     * 29/47
     *
     * So now the problem becomes "find the kth smallest element of (n-1) sorted list" (!!!)
     *
     * PriorityQueue<int[]>, it saves the index pair in given array which the values form the fraction, for example:
     * [1, 7, 23, 29, 47], {0, n - 1} represents 1/47
     *
     * Following is my implementation using PriorityQueue, running time is O(max(n,k) * logn), space is O(n)
     */
    public int[] kthSmallestPrimeFraction3(int[] A, int k) {
        int[] res = new int[2];
        if (A.length < 2) {
            return new int[0];
        }

        int n = A.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>(A.length, (a, b) -> A[a[0]] * A[b[1]] - A[b[0]] * A[a[1]]);

        /**
         * add the starting value of each sorted list
         */
        for (int i = 0; i < n - 1; i++) {
            pq.offer(new int[]{i, n - 1});
        }

        while (--k > 0 && !pq.isEmpty()) {
            int[] cur = pq.poll();

            /**
             * add the next value which is in the same list as cur[]
             */
            if (cur[1] - 1 > cur[0]) {//cur[1] - cur[0] > 1, make sure 分母大于分子
                cur[1]--;
                pq.offer(cur);
            }
        }

        res[0] = A[pq.peek()[0]];
        res[1] = A[pq.peek()[1]];

        return res;
    }
}