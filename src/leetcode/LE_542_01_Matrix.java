package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class LE_542_01_Matrix {
    /**
     * Given a matrix consists of 0 and 1, find the dirs of the nearest 0 for each cell.
     *
     * The dirs between two adjacent cells is 1.
     *
     *
     *
     * Example 1:
     *
     * Input:
     * [[0,0,0],
     *  [0,1,0],
     *  [0,0,0]]
     *
     * Output:
     * [[0,0,0],
     *  [0,1,0],
     *  [0,0,0]]
     *
     * Example 2:
     *
     * Input:
     * [[0,0,0],
     *  [0,1,0],
     *  [1,1,1]]
     *
     * Output:
     * [[0,0,0],
     *  [0,1,0],
     *  [1,2,1]]
     *
     *
     * Note:
     * The number of elements of the given matrix will not exceed 10,000.
     * There are at least one 0 in the given matrix.
     * The cells are adjacent in only four directions: up, down, left and right.
     *
     * Medium
     */

    /**
     * https://leetcode.com/problems/01-matrix/discuss/101021/Java-Solution-BFS
     *
     * Same algorithm as LE_286_Walls_And_Gates
     *
     * Simpler than LE_286_Walls_And_Gates, no obstacle here.
     */
    class Solution {
        public int[][] updateMatrix(int[][] matrix) {
            if (matrix == null || matrix.length == 0) return new int[][]{};

            int m = matrix.length;
            int n = matrix[0].length;

            int[][] res = new int[m][n];

            Queue<int[]> q = new LinkedList<>();

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][j] == 0) {
                        q.offer(new int[]{i, j});
                    } else {
                        res[i][j] = Integer.MAX_VALUE;
                    }
                }
            }

            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            while (!q.isEmpty()) {
                int[] cur = q.poll();

                int x = cur[0];
                int y = cur[1];

                for (int i = 0; i < dirs.length; i++) {
                    int nx = x + dirs[i][0];
                    int ny = y + dirs[i][1];

                    if (nx < 0 || nx >= matrix.length || ny < 0 || ny >= matrix[0].length || res[nx][ny] <= res[x][y] + 1) continue;

                    q.offer(new int[]{nx, ny});
                    res[nx][ny] = res[x][y] + 1;
                }
            }

            return res;
        }
    }
}
