package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LE_967_Numbers_With_Same_Consecutive_Differences {
    /**
         Return all non-negative integers of length N such that the absolute difference between every
         two consecutive digits is K.

         Note that every number in the answer must not have leading zeros except for the number 0 itself.
         For example, 01 has one leading zero and is invalid, but 0 is valid.

         You may return the answer in any order.

         Example 1:

         Input: N = 3, K = 7
         Output: [181,292,707,818,929]
         Explanation: Note that 070 is not a valid number, because it has leading zeroes.


         Example 2:

         Input: N = 2, K = 1
         Output: [10,12,21,23,32,34,43,45,54,56,65,67,76,78,87,89,98]


         Note:

         1 <= N <= 9
         0 <= K <= 9

         Medium
     */

    /**
     * https://zxi.mytechroad.com/blog/searching/967-numbers-with-same-consecutive-differences/
     *
     * DFS
     *
     * 坑1 ： K = 0， when k is 0， need to skip one of the two branches，otherwise there are duplicate numbers。
     * 坑2 ： N = 1， when N is 1， answer will always be {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
     *
     * Time  : O(2 ^ n), since branching factor is 2, for DFS, recursion depth is n, so time complexity is 2 ^ n
     * Space : O(n)
     */
    class Solution1 {
        public int[] numsSameConsecDiff(int N, int K) {
            List<Integer> res = new ArrayList<>();

            if (N == 1) {
                //Or
                //return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                res.add(0);
            }

            for (int i = 1; i <= 9; i++) {
                helper(N - 1, K, i, res);
            }

            int i = 0;
            int[] ans = new int[res.size()];
            for (int n : res) {
                ans[i++] = n;
            }

            return ans;
        }

        private void helper(int n, int k, int temp, List<Integer> res) {
            if (n == 0) {
                res.add(temp);
                return;
            }

            int cur = temp % 10;

            if (cur + k < 10) {
                helper(n - 1, k, temp * 10 + cur + k, res);
            }

            if (k != 0 && cur - k >= 0) {
                helper(n - 1, k, temp * 10 + cur - k, res);
            }
        }
    }

    /**
     * BFS
     *
     * Time  : O(2 ^ n)
     * Space : O(2 ^ n)
     */
    class Solution2 {
        public int[] numsSameConsecDiff(int N, int K) {
            List<Integer> res = new ArrayList<>();

            if (N == 1) {
                return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            }

            Queue<Integer> q = new LinkedList<>();
            for (int i = 1; i <= 9; i++) {
                q.offer(i);
            }
            int level = 1;

            while (!q.isEmpty() && level < N) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int cur = q.poll();
                    int d = cur % 10;

                    if (d + K < 10) {
                        q.offer(cur * 10 + d + K);
                    }

                    if (K != 0 && d - K >= 0) {
                        q.offer(cur * 10 + d - K);
                    }
                }

                level++;
            }

            int[] ans = new int[q.size()];
            int i = 0;
            for (int n : q) {
                ans[i++] = n;
            }

            return ans;
        }
    }
}