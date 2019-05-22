package leetcode;

public class LE_463_Island_Perimeter {
    /**
     * You are given a map in form of a two-dimensional integer grid where 1
     * represents land and 0 represents water.
     *
     * Grid cells are connected horizontally/vertically (not diagonally).
     * The grid is completely surrounded by water, and there is exactly one
     * island (i.e., one or more connected land cells).
     *
     * The island doesn't have "lakes" (water inside that isn't connected to
     * the water around the island). One cell is a square with side length 1.
     * The grid is rectangular, width and height don't exceed 100. Determine
     * the perimeter of the island.
     *
     * Example:
     *
     * Input:
     * [[0,1,0,0],
     *  [1,1,1,0],
     *  [0,1,0,0],
     *  [1,1,0,0]]
     *
     * Output: 16
     *
     * Explanation: The perimeter is the 16
     *
     * Easy
     */

    /**
     * DFS
     */
    class Solution1 {
        public int islandPerimeter(int[][] grid) {
            if (grid == null || grid.length == 0) return 0;

            int m = grid.length;
            int n = grid[0].length;
            int res = 0;
            boolean[][] visited = new boolean[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1 && !visited[i][j]) {
                        return helper(grid, m, n, i, j, visited);
                    }
                }
            }

            return 0;
        }

        private int helper(int[][] grid, int m, int n, int x, int y, boolean[][] visited) {
            /**
             * goes out of boundary, this deals with the case that island is
             * at the edge, even though question says island is surrounded by sea.
             */
            if (x < 0 || x >= m || y < 0 || y >= n) return 1;

            /**
             * it is part of the island
             */
            if (visited[x][y]) {
                return 0;
            }

            /**
             * goes from island to sea, perimeter increase by one.
             */
            if (grid[x][y] == 0) {
                return 1;
            }

            visited[x][y] = true;
            int res = 0;

            res += helper(grid, m, n, x + 1, y, visited);
            res += helper(grid, m, n, x - 1, y, visited);
            res += helper(grid, m, n, x, y + 1, visited);
            res += helper(grid, m, n, x, y - 1, visited);

            return res;
        }
    }

    /**
     * counting
     */
    class Solution2 {
        public int islandPerimeter(int[][] grid) {
            int m = grid.length;
            if (m == 0) return 0;
            int n = grid[0].length;
            int per = 0;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        if (i == 0 || grid[i - 1][j] == 0) per++;
                        if (i == m - 1 || grid[i + 1][j] == 0) per++;
                        if (j == 0 || grid[i][j - 1] == 0) per++;
                        if (j == n - 1 || grid[i][j + 1] == 0) per++;
                    }
                }
            }
            return per;
        }
    }
}
