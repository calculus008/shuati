package leetcode;

import java.util.*;

/**
 * Created by yuank on 4/17/18.
 */
public class LE_279_Perfect_Squares {
    /**
     * Given a positive integer n, find the least number of perfect square numbers
     * (for example, 1, 4, 9, 16, ...) which sum to n.
     *
     * For example, given n = 12, return 3 because 12 = 4 + 4 + 4; given n = 13, return 2 because 13 = 4 + 9.
     */


    /**
     DP : Time  : O(n * sqrt(n))
                  In main step, we have a nested loop, where the outer loop is of nn iterations and in
                  the inner loop it takes at maximum sqrt(n) iterations.

          Space : O(n)

     Given n = 8:
     i = 0, dp[0] = 0;
     i = 1, j = 1, Math.max(dp[1], dp[1 - 1 * 1] + 1) => dp[1] = 1
     i = 2, j = 1, Math.max(dp[2], dp[2 - 1 * 1] + 1) => dp[2] = 2
     i = 3, j = 1, Math.max(dp[3], dp[3 - 1 * 1] + 1) => dp[3] = 3

     i = 4, j = 1, Math.max(dp[4], dp[4 - 1 * 1] + 1) => dp[4] = 4
            j = 2, Math.max(dp[4], dp[4 - 2 * 2] + 1) => dp[4] = 1

     i = 5, j = 1, Math.max(dp[5], dp[5 - 1 * 1] + 1) => dp[5] = 2
            j = 2, Math.max(dp[5], dp[5 - 2 * 2] + 1) => dp[5] = 2

     i = 6, j = 1, Math.max(dp[6], dp[6 - 1 * 1] + 1) => dp[6] = 3
            j = 2, Math.max(dp[6], dp[6 - 2 * 2] + 1) => dp[6] = 3

     i = 7, j = 1, Math.max(dp[7], dp[7 - 1 * 1] + 1) => dp[7] = 4
            j = 2, Math.max(dp[7], dp[7 - 2 * 2] + 1) => dp[7] = 4

     i = 8, j = 1, Math.max(dp[8], dp[8 - 1 * 1] + 1) => dp[6] = 4
            j = 2, Math.max(dp[8], dp[8 - 2 * 2] + 1) => dp[4] = 2

     i = 9, j = 1, Math.max(dp[9], dp[9 - 1 * 1] + 1) => dp[9] = 5  (dp[7] = 4, [2, 1, 1, 1] => dp[9] = 5, [2, 1, 1, 1, 1])
            j = 2, Math.max(dp[9], dp[9 - 2 * 2] + 1) => dp[9] = 3  (dp[5] = 2 ,[2, 1] => dp[9] = 3, [2, 1, 2], 2 * 2 + 1 + 2 * 2)
            j = 3, Math.max(dp[9], dp[9 - 3 * 3] + 1) => dp[9] = 1

     */
    class Solution_DP {
        public int numSquares1(int n) {
            int[] dp = new int[n + 1];
            Arrays.fill(dp, Integer.MAX_VALUE);
            dp[0] = 0;

            for (int i = 0; i <= n; i++) {
                for (int j = 1; j * j <= i; j++) {
                    dp[i] = Math.min(dp[i], dp[i - j * j] + 1); //这里加的1就是指的用j (j * j)
                }
            }

            return dp[n];
        }

        /**
         * improved from numSquares1 by adding pre processing square numbers
         */
        public int numSquares2(int n) {
            int dp[] = new int[n + 1];
            Arrays.fill(dp, Integer.MAX_VALUE);
            dp[0] = 0;

            // pre-calculate the square numbers.
            int max_square_index = (int) Math.sqrt(n) + 1;
            int square_nums[] = new int[max_square_index];
            for (int i = 1; i < max_square_index; ++i) {
                square_nums[i] = i * i;
            }

            for (int i = 1; i <= n; ++i) {
                for (int j = 1; j < max_square_index; ++j) {
                    if (i < square_nums[j]) break;
                    dp[i] = Math.min(dp[i], dp[i - square_nums[j]] + 1);
                }
            }
            return dp[n];
        }
    }

    /**
     * https://leetcode.com/articles/perfect-squares/
     * Approach 4
     *
     * Time : O(n ^ (h / 2))
     * Space : O((sqrt(n) ^ h)
     *
     * h is the height of the N-array tree
     */
    class Solution_BFS {
        public int numSquares(int n) {
            Queue<Integer> q = new LinkedList<>();
            Set<Integer> visited = new HashSet<>();

            q.offer(0);
            visited.add(0);

            int depth = 0;

            while (!q.isEmpty()) {
                int size = q.size();
                depth++;

                while (size-- > 0) {
                    int cur = q.poll();
                    for (int i = 1; i * i <= n; i++) {
                        int next = cur + i * i;
                        if (next == n) {
                            return depth;
                        }

                        if (next > n) {
                            break;
                        }

                        if (!visited.contains(next)) {
                            q.offer(next);
                            visited.add(next);
                        }
                    }
                }
            }

            return depth;
        }
    }
}
