package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LE_417_Pacific_Atlantic_Water_Flow {
    /**
     * Given an m x n matrix of non-negative integers representing the height of
     * each unit cell in a continent, the "Pacific ocean" touches the left and top
     * edges of the matrix and the "Atlantic ocean" touches the right and bottom edges.
     *
     * Water can only flow in four directions (up, down, left, or right) from a cell
     * to another one with height equal or lower.
     *
     * Find the list of grid coordinates where water can flow to both the Pacific and
     * Atlantic ocean.
     *
     * Note:
     * The order of returned grid coordinates does not matter.
     * Both m and n are less than 150.
     * Example:
     *
     * Given the following 5x5 matrix:
     *
     *   Pacific ~   ~   ~   ~   ~
     *        ~  1   2   2   3  (5) *
     *        ~  3   2   3  (4) (4) *
     *        ~  2   4  (5)  3   1  *
     *        ~ (6) (7)  1   4   5  *
     *        ~ (5)  1   1   2   4  *
     *           *   *   *   *   * Atlantic
     *
     * Return:
     *
     * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]]
     * (positions with parentheses in above matrix).
     *
     * Medium
     */

    public class Solution_DFS_Practice {
        public List<List<Integer>> pacificAtlantic(int[][] matrix) {
            List<List<Integer>> res = new ArrayList<>();
            if (matrix == null || matrix.length == 0) return res;

            int m = matrix.length;
            int n = matrix[0].length;
            boolean[][] visited1 = new boolean[m][n];
            boolean[][] visited2 = new boolean[m][n];

            for (int i = 0; i < m; i++) {
                dfs(matrix, visited1, i, 0);
                dfs(matrix, visited2, i, n - 1);
            }

            for (int i = 0; i < n; i++) {
                dfs(matrix, visited1, 0, i);
                dfs(matrix, visited2, m - 1, i);
            }

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (visited1[i][j] && visited2[i][j]) {
                        List<Integer> elm = new ArrayList<>();
                        elm.add(i);
                        elm.add(j);
                        res.add(elm);
                    }
                }
            }

            return res;
        }

        public void dfs(int[][] matrix, boolean[][] visited, int x, int y) {
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            visited[x][y] = true;
            int m = matrix.length;
            int n = matrix[0].length;

            for (int i = 0; i < 4; i++) {
                int nx = x + dirs[i][0];
                int ny = y + dirs[i][1];

                if (nx < 0 || nx >= m || ny < 0 || ny >= n || matrix[x][y] > matrix[nx][ny] || visited[nx][ny]) {
                    continue;
                }

                dfs(matrix, visited, nx, ny);
            }
        }
    }

    /**
     * Time  : O(m + n + m * n)
     * Space : O(m * n)
     *
     * 从海洋反向向里推
     */
    class Solution {
        public List<int[]> pacificAtlantic(int[][] matrix) {
            List<int[]> res = new ArrayList<>();
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
                return res;
            }

            int m = matrix.length;
            int n = matrix[0].length;

            boolean[][] visited1 = new boolean[m][n];
            boolean[][] visited2 = new boolean[m][n];

            for (int i = 0; i < m; i++) {
                dfs(matrix, i, 0, visited1);
                dfs(matrix, i, n - 1, visited2);
            }

            for (int j = 0; j < n; j++) {
                dfs(matrix, 0, j, visited1);
                dfs(matrix, m - 1, j, visited2);
            }

            /**
             * !!!
             * How to get the cells that can reach both oceans?
             * Use two boolean arrays, then get the ones that are true in both.
             */
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (visited1[i][j] && visited2[i][j]) {
                        res.add(new int[]{i, j});
                    }
                }
            }

            return res;
        }

        int[][] dirs = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

        private void dfs(int[][] matrix, int x, int y, boolean[][] visited) {
            visited[x][y] = true;

            for (int[] dir : dirs) {
                int nx = x + dir[0];
                int ny = y + dir[1];

                if (isValid(nx, ny, matrix.length, matrix[0].length)
                        && matrix[nx][ny] >= matrix[x][y] && !visited[nx][ny]) {
                    dfs(matrix, nx, ny, visited);
                }
            }
        }

        private boolean isValid(int x, int y, int m, int n) {
            return (x >= 0 && x < m && y >= 0 && y < n);
        }
    }

    /**
     * 题目是给一个二维矩阵代表高度，假设某一个点开始下雨，水往等高或更低的点流，输出是否水能流到边界点
     *
     * Time  : O(m + n)
     * Space : O(m * n)
     */
    class Solution_Variation {
        public boolean canFlow(int[][] matrix, int x, int y) {
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
                return false;
            }

            if (x == 0 || y == 0) return true;

            int m = matrix.length;
            int n = matrix[0].length;

            boolean[][] visited = new boolean[m][n];

            for (int i = 0; i < m; i++) {
                if (dfs(matrix, i, 0, x, y, visited)) return true;
                if (dfs(matrix, i, n - 1, x, y, visited)) return true;
            }

            for (int j = 0; j < n; j++) {
                if (dfs(matrix, 0, j, x, y, visited)) return true;
                if (dfs(matrix, m - 1, j, x, y, visited))return true;
            }

            return false;
        }

        int[][] dirs = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

        private boolean dfs(int[][] matrix, int x, int y, int sx, int sy, boolean[][] visited) {
            if (x == sx && y == sy) {
                return true;
            }

            visited[x][y] = true;

            for (int[] dir : dirs) {
                int nx = x + dir[0];
                int ny = y + dir[1];

                if (isValid(nx, ny, matrix.length, matrix[0].length)
                        && matrix[nx][ny] >= matrix[x][y] && !visited[nx][ny]) {
                    if (dfs(matrix, nx, ny, sx, sy, visited)) {
                        return true;
                    }
                }
            }

            return false;
        }

        private boolean isValid(int x, int y, int m, int n) {
            return (x >= 0 && x < m && y >= 0 && y < n);
        }
    }

    public class Solution_BFS {
        public List<List<Integer>> pacificAtlantic(int[][] matrix) {
            List<List<Integer>> res = new ArrayList<>();
            if (matrix == null || matrix.length == 0) return res;

            int m = matrix.length;
            int n = matrix[0].length;
            boolean[][] visited1 = new boolean[m][n];
            boolean[][] visited2 = new boolean[m][n];

            Queue<int[]> q1 = new LinkedList<>();
            Queue<int[]> q2 = new LinkedList<>();

            for (int i = 0; i < m; i++) {
                q1.offer(new int[]{i, 0});
                visited1[i][0] = true;//!!!
                q2.offer(new int[]{i, n - 1});
                visited2[i][n - 1] = true;//!!!
            }

            for (int i = 0; i < n; i++) {
                q1.offer(new int[]{0, i});
                visited1[0][i] = true;//!!!
                q2.offer(new int[]{m - 1, i});
                visited2[m - 1][i] = true;//!!!
            }

            bfs(matrix, q1, visited1, m, n);
            bfs(matrix, q2, visited2, m, n);

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (visited1[i][j] && visited2[i][j]) {
                        List<Integer> elm = new ArrayList<>();
                        elm.add(i);
                        elm.add(j);
                        res.add(elm);
                    }
                }
            }

            return res;
        }

        public void bfs(int[][] matrix, Queue<int[]> q, boolean[][] visited, int m, int n) {
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            while (!q.isEmpty()) {
                int[] cur = q.poll();

                for (int i = 0; i < 4; i++) {
                    int x = cur[0];
                    int y = cur[1];
                    int nx = x + dirs[i][0];
                    int ny = y + dirs[i][1];

                    /**
                     * !!!
                     * "visited[nx][ny]"
                     */
                    if (nx < 0 || nx >= m || ny < 0 || ny >= n || matrix[x][y] > matrix[nx][ny] || visited[nx][ny]) {
                        continue;
                    }

                    visited[nx][ny] = true;
                    q.offer(new int[]{nx, ny});
                }
            }
        }
    }
}
