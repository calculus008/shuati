package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class LE_695_Max_Area_Of_Island {
    /**
     Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land)
     connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are
     surrounded by water.

     Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)

     Example 1:

     [[0,0,1,0,0,0,0,1,0,0,0,0,0],
     [0,0,0,0,0,0,0,1,1,1,0,0,0],
     [0,1,1,0,1,0,0,0,0,0,0,0,0],
     [0,1,0,0,1,1,0,0,1,0,1,0,0],
     [0,1,0,0,1,1,0,0,1,1,1,0,0],
     [0,0,0,0,0,0,0,0,0,0,1,0,0],
     [0,0,0,0,0,0,0,1,1,1,0,0,0],
     [0,0,0,0,0,0,0,1,1,0,0,0,0]]

     Given the above grid, return 6. Note the answer is not 11, because the island must be connected 4-directionally.

     Example 2:

     [[0,0,0,0,0,0,0,0]]

     Given the above grid, return 0.

     Note: The length of each dimension in the given grid does not exceed 50.

     Medium
     */

    /**
     * DFS
     * <p>
     * Time : O(m * n)
     * Space ; O(1)
     */

    class Solution_DFS {
        public int maxAreaOfIsland(int[][] grid) {
            if (null == grid || grid.length == 0) return 0;
            int n = grid.length;
            int m = grid[0].length;
            int res = 0;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (grid[i][j] == 1) {
                        int ans = dfs(grid, i, j);
                        res = Math.max(res, ans);
                    }
                }
            }

            return res;
        }

        /**
         * dfs returns area value
         */
        public int dfs(int[][] grid, int x, int y) {
            if (x < 0 || y < 0 || x >= grid.length || y >= grid[0].length || grid[x][y] == 0) return 0;

            //!!!
            int val = 1;
            grid[x][y] = 0;

            //!!! "+="
            val += dfs(grid, x + 1, y);
            val += dfs(grid, x - 1, y);
            val += dfs(grid, x, y + 1);
            val += dfs(grid, x, y - 1);

            return val;
        }
    }

    class Solution_DFS_Practice {
        public int maxAreaOfIsland(int[][] grid) {
            if (grid == null || grid.length == 0) return 0;

            int m = grid.length;
            int n = grid[0].length;
            int res = 0;
            boolean[][] visited = new boolean[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1 && !visited[i][j]) {
                        res = Math.max(res, helper(grid, m, n, i, j, visited));
                    }
                }
            }

            return res;
        }

        private int helper(int[][] grid, int m, int n, int x, int y, boolean[][] visited) {
            if (x < 0 || x >= m || y < 0 || y >= n || grid[x][y] != 1 || visited[x][y]) {
                return 0;
            }

            int res = 1;
            visited[x][y] = true;

            res += helper(grid, m, n, x + 1, y, visited);
            res += helper(grid, m, n, x - 1, y, visited);
            res += helper(grid, m, n, x, y + 1, visited);
            res += helper(grid, m, n, x, y - 1, visited);

            return res;
        }
    }

    public class Solution_BFS {
        private int[][] DIRECTIONS = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        // BFS
        public int maxAreaOfIsland(int[][] grid) {
            if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

            int M = grid.length;
            int N = grid[0].length;
            boolean[][] visited = new boolean[M][N];
            int res = 0;

            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    if (grid[i][j] == 1 && !visited[i][j]) {
                        res = Math.max(res, bfs(grid, visited, i, j));
                    }
                }
            }
            return res;
        }

        private int bfs(int[][] grid, boolean[][] visited, int i, int j) {
            Queue<int[]> q = new LinkedList<>();
            q.add(new int[]{i, j});
            visited[i][j] = true;
            int res = 0;

            while (!q.isEmpty()) {
                int[] curr = q.poll();
                /**
                 * !!!
                 * 每次从queue里取出一个就把面积加一。
                 */
                res++;

                for (int[] dir : DIRECTIONS) {
                    int x = curr[0] + dir[0];
                    int y = curr[1] + dir[1];

                    /**
                     * !!!
                     * compare with DFS, validation happens when branching to 4 directions,
                     * before adding element into q and mark visited
                     */
                    if (x < 0 || y < 0 || x >= grid.length || y >= grid[0].length || visited[x][y] || grid[x][y] != 1)
                        continue;


                    q.add(new int[]{x, y});
                    visited[x][y] = true;
                }
            }
            return res;
        }

    }
}