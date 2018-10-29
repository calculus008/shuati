package lintcode;

import java.util.List;

/**
 * Created by yuank on 10/28/18.
 */
public class LI_91_Minimum_Adjustment_Cost {
    /**
         Given an integer array, adjust each integers so that the difference
         of every adjacent integers are not greater than a given number target.

         If the array before adjustment is A, the array after adjustment is B,
         you should minimize the sum of |A[i]-B[i]|

         Example
         Given [1,4,2,3] and target = 1, one of the solutions is [2,3,2,3],
         the adjustment cost is 2 and it's minimal.

         Return 2.

         Notice
         You can assume each number in the array is a positive integer and not greater than 100.

         Medium
     */

    /**
     * if each number has a range < 100
     */
    public class Solution1 {

        public int MinAdjustmentCost(List<Integer> A, int target) {
            /**
                DP: each number has a range < 100, so just enumerate each position from 0 to 100
                Kind of build up a new List B, and compare every B to A, find out the min
                and sum up the difference in each position comparing to A[i], then pick up the min one.
                for final result, we just loop the last row or last dp loop, to find out the min one

                F[j] defines min cost for position i, if we pick j  (0 <= j <= 100) as its number, then
                F[j] = (PrevF[0....100] + abs(j - A[i])
             **/

            //using 2 rows to do rolling.
            int[][] F = new int[2][101];

            for (int i = 0; i < A.size(); i++)
            {
                //for any postion i, just enumerate j from 0 to 100, then compute each min cost for each j
                for (int j = 0; j <= 100; j++)
                {
                    int curr = (i+1) % 2;
                    int prev = i % 2;
                    F[curr][j] = Integer.MAX_VALUE;
                    for (int k = 0; k <= 100; k++)
                    {
                        if (Math.abs(j-k) <= target)
                        {
                            F[curr][j] = Math.min(F[curr][j], F[prev][k] + Math.abs(j - A.get(i)));
                        }
                    }
                }
            }

            //loop curr row which is at A.size() % 2.
            int res = Integer.MAX_VALUE;
            for (int j = 0; j <= 100; j++)
            {
                res = Math.min(res, F[A.size() % 2][j]);
            }
            return res;
        }
    }

    /**
     * Generalize to number that is not limited by range < 100
     */
    public class Solution2 {
        int MIN = 0x7fffffff, MAX = -0x80000000;

        public int MinAdjustmentCost(List<Integer> A, int target) {
            int n = A.size(), ans = 0x7fffffff, pre = 0, cur = 1;
            Integer[] AA = A.toArray(new Integer[n]);
            for (int a : A) {
                MIN = Math.min(MIN, a);
                MAX = Math.max(MAX, a);
            }
            int[][] f = new int[2][MAX - MIN + 1];

            for (int i = 0; i < n; i++) {
                cur = pre;
                pre = 1 - cur;
                for (int j = MIN; j <= MAX; j++) {
                    if (i == 0) {
                        f[cur][j - MIN] = Math.abs(j - AA[i]);
                        continue;
                    }
                    f[cur][j - MIN] = 0x7fffffff;
                    for (int k = -target; k <= target; k++) {
                        if (j + k < MIN || j + k > MAX) continue;
                        f[cur][j - MIN] = Math.min(f[pre][j + k - MIN] + Math.abs(j - AA[i]), f[cur][j - MIN]);
                    }
                }
            }
            for (int j = MIN; j <= MAX; j++)
                ans = Math.min(ans, f[cur][j - MIN]);
            return ans;
        }
    }
}
