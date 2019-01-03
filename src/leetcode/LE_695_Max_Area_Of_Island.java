package leetcode;

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
     *
     * Time : O(m * n)
     * Space ; O(1)
     */

    class Solution {
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

        public int dfs(int[][] grid, int x, int y) {
            if (x < 0 || y < 0 || x >= grid.length || y >= grid[0].length || grid[x][y] == 0) return 0;

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
}