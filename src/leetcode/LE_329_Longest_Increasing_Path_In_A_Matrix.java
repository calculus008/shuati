package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 5/6/18.
 */
public class LE_329_Longest_Increasing_Path_In_A_Matrix {
    /**
         Given an integer matrix, find the length of the longest increasing path.

         From each cell, you can either move to four directions: left, right, up or down.
         You may NOT move diagonally or move outside of the boundary (i.e. wrap-around is not allowed).

         Example 1:

         nums = [
         [9,9,4],
         [6,6,8],
         [2,1,1]
         ]
         Return 4
         The longest increasing path is [1, 2, 6, 9].

         Example 2:

         nums = [
         [3,4,5],
         [3,2,6],
         [2,2,1]
         ]
         Return 4
         The longest increasing path is [3, 4, 5, 6]. Moving diagonally is not allowed.

         Hard


         Variation - Max_Zigzag_Paths
     */

    class Solution_DFS_Practice {
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        public int longestIncreasingPath(int[][] matrix) {
            if (null == matrix || matrix.length == 0) return 0;

            int m = matrix.length;
            int n = matrix[0].length;
            int[][] mem = new int[m][n];
            int res = 0;

            /**
             * 在这里含糊了一下：如果每个cell 都找一遍，那岂不是O(MN *MN) 吗？
             *
             * 关键是 - memoization. 用 mem[][]存贮访问过的节点，所以每个cell只计算一次路径。
             *
             * O(MN)
             */
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    res = Math.max(res, dfs(matrix, i, j, mem));
                }
            }
            return res;
        }

        public int dfs(int[][] matrix, int x, int y, int[][] mem) {
            if (mem[x][y] != 0) return mem[x][y];

            int m = matrix.length;
            int n = matrix[0].length;
            int ret = 0;

            for (int i = 0; i < 4; i++) {
                int nx = x + dirs[i][0];
                int ny = y + dirs[i][1];

                if (nx < 0 || nx >= m || ny < 0 || ny >= n || matrix[nx][ny] <= matrix[x][y]) {
                    continue;
                }

                ret = Math.max(ret, dfs(matrix, nx, ny, mem));
            }

            mem[x][y] = 1 + ret;

            return 1 + ret;
        }
    }

    /**
         http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-329-longest-increasing-path-in-a-matrix/

         DFS + Memorization
         Time and Space : O(m * n)

         Each vertex/cell will be calculated once and only once, and each edge will be visited once and only once.
         The total time complexity is then O(V+E). V is the total number of vertices and E is the total number of edges.
         In our problem, O(V) = O(mn), O(E) = O(4V) = O(mn)O(E)=O(4V)=O(mn).

         14 ms
     **/
    class Solution1 {
        public int longestIncreasingPath(int[][] matrix) {
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
                return 0;
            }

            int m = matrix.length;
            int n = matrix[0].length;
            int res = 0;
            int[][] mem = new int[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    int k = helper(matrix, mem, Integer.MIN_VALUE, i, j, m, n);
                    res = Math.max(res, k);
                }
            }

            return res;
        }

        public int helper(int[][] matrix, int[][] mem, int min, int i, int j, int m, int n) {
            if (i < 0 || j < 0 || i >= m || j >= n || matrix[i][j] <= min) return 0;
            if (mem[i][j] > 0) return mem[i][j];

            //!!! "+ 1"
            int a = helper(matrix, mem, matrix[i][j], i + 1, j, m, n) + 1;
            int b = helper(matrix, mem, matrix[i][j], i - 1, j, m, n) + 1;
            int c = helper(matrix, mem, matrix[i][j], i, j + 1, m, n) + 1;
            int d = helper(matrix, mem, matrix[i][j], i, j - 1, m, n) + 1;

            int ret = Math.max(Math.max(Math.max(a, b), c), d);
            mem[i][j] = ret;
            return ret;
        }
    }


    /**
     * 9 ms
     */
    class Solution2 {
        int[][] mem;
        int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        public int longestIncreasingPath(int[][] matrix) {
            if (null == matrix || matrix.length == 0 || matrix[0].length == 0) {
                return 0;
            }

            int m = matrix.length;
            int n = matrix[0].length;
            int res = Integer.MIN_VALUE;

            mem = new int[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    /**
                     * !!! "Integer.MIN_VALUE"
                     */
                    res = Math.max(res, helper(matrix, m, n, Integer.MIN_VALUE, i, j));
                }
            }

            return res;
        }

        private int helper(int[][] matrix, int m, int n, int last, int x, int y) {
            /**
             * !!! "matrix[x][y] <= last"
             */
            if (x < 0 || x >= m || y < 0 || y >= n || matrix[x][y] <= last) {
                return 0;
            }

            if (mem[x][y] != 0) {//!!!
                return mem[x][y];
            }

            int val = 0;
            for (int i = 0; i < dirs.length; i++) {
                int nx = x + dirs[i][0];
                int ny = y + dirs[i][1];

                val = Math.max(val, helper(matrix, m, n, matrix[x][y], nx, ny));
            }

            val++;//!!!
            mem[x][y] = val;//!!!

            return val;
        }
    }

    /**
     * DP
     * 用一个数组 len[][] 记录任意点的递增路径长度，
     * 再利用一个 visited[][] 数组记录当前位置是否遍历过，
     * 如果已经处理过该点，那么直接返回该点对应的路径长度即可。
     */
    class Solution3 {
        private int[] row = {-1, 1, 0, 0};
        private int[] col = {0, 0, -1, 1};

        public int longestIncreasingPath(int[][] matrix) {
            if (matrix.length == 0 || matrix[0].length == 0) {
                return 0;
            }
            boolean[][] visited = new boolean[matrix.length][matrix[0].length];
            int[][] len = new int[matrix.length][matrix[0].length];
            int max = 0;

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    max = Math.max(max, find(matrix, visited, len, i, j));
                }
            }
            return max;
        }

        private int find(int[][] matrix, boolean[][] visited, int[][] len, int x, int y) {
            if (visited[x][y]) {
                return len[x][y];
            }

            len[x][y] = 1;
            for (int i = 0; i < 4; i++) {
                int curX = x + row[i];
                int curY = y + col[i];
                if (curX >= 0 && curX < matrix.length && curY >= 0 && curY < matrix[0].length && matrix[curX][curY] < matrix[x][y]) {
                    len[x][y] = Math.max(len[x][y], find(matrix, visited, len, curX, curY) + 1);
                }
            }
            visited[x][y] = true;
            return len[x][y];
        }
    }

}
