package leetcode;

import java.util.*;

public class LE_1284_Minimum_Number_Of_Flips_To_Convert_Binary_Matrix_To_Zero_Matrix {
    /**
     * Given a m x n binary matrix mat. In one step, you can choose one cell and flip it and all the four neighbors of
     * it if they exist (Flip is changing 1 to 0 and 0 to 1). A pair of cells are called neighbors if they share one edge.
     *
     * Return the minimum number of steps required to convert mat to a zero matrix or -1 if you cannot.
     *
     * A binary matrix is a matrix with all cells equal to 0 or 1 only.
     *
     * A zero matrix is a matrix with all cells equal to 0.
     *
     * Example 1:
     * Input: mat = [[0,0],[0,1]]
     * Output: 3
     * Explanation: One possible solution is to flip (1, 0) then (0, 1) and finally (1, 1) as shown.
     *
     * Example 2:
     * Input: mat = [[0]]
     * Output: 0
     * Explanation: Given matrix is a zero matrix. We do not need to change it.
     *
     * Example 3:
     * Input: mat = [[1,0,0],[1,0,0]]
     * Output: -1
     * Explanation: Given matrix cannot be a zero matrix.
     *
     * Constraints:
     * m == mat.length
     * n == mat[i].length
     * 1 <= m, n <= 3
     * mat[i][j] is either 0 or 1.
     *
     * Hard
     *
     * https://leetcode.com/problems/minimum-number-of-flips-to-convert-binary-matrix-to-zero-matrix/
     */

    /**
     * BFS
     *
     * BFS Template:
     *
     * q = [start]
     * seen = (start)
     * step = 0
     *
     * while q:
     *   size = q.size()
     *   for i in range(size)
     *     s = q.poll()
     *     if s == end : return steps
     *     for t in expand(s):
     *       if t not in seen:
     *         q.add(t)
     *         seen.add(t)
     *   step++
     *
     * return -1
     *
     * 容易犯糊涂的地方：
     * 1.BFS是对状态做BFS, 所以q和visited里面也相应存储的是representation of a state, 对该题，状态就是每次flip操作之后mat的样子。
     * 2.直接的做法可以是存储二维数组作为状态，但是这样，需要iterate整个数组(O(m * n))去验证：
     *   a.当前状态是否在visited里
     *   b.当前状态是否是要求的最终状态
     * 3.所以注意到给定的条件："1 <= m, n <= 3"，可以用bit operation把一个二维数组的状态压缩到一个Integer里。每次flip，只需要
     *   对相应的位做XOR，对状态的检查也可以变为对整数值的比较(O(1)).
     * 4.BFS中的expand:
     *   对应于每一个状态，从该状态出发，经过一次flip, 所有可能的合法状态：对于当前状态中的每一个单元和它的邻居，做flip, 如果这个新的
     *   状态不在visited中，加入q和visited.
     *
     * 如果mat的size太大，不能把状态压缩到一个整数里，在q里只能存贮数组作为状态。当检查状态是否被访问过的时候，可以用：
     *    String t = Arrays.deepToString(mat)
     *
     * visited里可以存这种String.
     *
     *
     * Time  : O(m * n * 2 ^ (m * n))
     * Space : O(2 ^ (m * n)).
     */
    class Solution {
        public int minFlips(int[][] mat) {
            /**
             * Add {0, 0} so that the flip for the cell itself can be included in the for loop with the flip operations
             * for all of its neighbours.
             */
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {0, 0}};

            int m = mat.length;
            int n = mat[0].length;
            int start = 0;

            /**
             * Calculate start state
             */
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    start |= mat[i][j] << (i * n + j);
                }
            }

            Queue<Integer> q = new LinkedList<>();
            q.offer(start);
            Set<Integer> visited = new HashSet<>();
            visited.add(start);

            int res = 0;
            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int cur = q.poll();

                    if (cur == 0) return res;

                    for (int j = 0; j < m; j++) {
                        for (int k = 0; k < n; k++) {
                            /**
                             * !!!
                             */
                            int next = cur;

                            for (int l = 0; l < dirs.length; l++) {
                                int nx = j + dirs[l][0];
                                int ny = k + dirs[l][1];

                                if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;

                                next ^= (1 << (nx * n + ny));
                            }

                            if (visited.contains(next)) continue;

                            /**
                             * !!!
                             */
                            q.offer(next);
                            visited.add(next);
                        }
                    }
                }

                /**
                 * !!!
                 */
                res++;
            }

            return -1;
        }
    }
}
