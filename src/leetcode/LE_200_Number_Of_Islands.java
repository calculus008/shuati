package leetcode;

/**
 * Created by yuank on 3/24/18.
 */
public class LE_200_Number_Of_Islands {
    /*
        Given a 2d grid map of '1's (land) and '0's (water), count the number of islands.
        An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
        You may assume all four edges of the grid are all surrounded by water.

        Example 1:

        11110
        11010
        11000
        00000
        Answer: 1

        Example 2:

        11000
        11000
        00100
        00011
        Answer: 3
     */

    //Time and Space : O(m * n)
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;

        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    helper(grid, i, j);
                    res++;
                }
            }
        }

        return res;
    }

    private void helper(char[][] grid, int x, int y) {
        if (x < 0 || y < 0 || x >= grid.length || y >= grid[0].length || grid[x][y] != '1') {
            return;
        }

        /**
         * 千万别忘了这一步!!!
         */
        grid[x][y] = '0';
        helper(grid, x + 1, y);
        helper(grid, x - 1, y);
        helper(grid, x, y + 1);
        helper(grid, x, y - 1);
    }

    public int numIslands_JiuZhang(boolean[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int m = grid.length;
        int n = grid[0].length;
        int res = 0;

        for (int i = 0 ; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j]) {
                    helper(grid, i, j, m, n);
                    res++;
                }
            }
        }

        return res;
    }

    private void helper(boolean[][] grid, int x, int y, int m, int n) {
        if (x > m - 1 || x < 0 || y > n - 1 || y < 0 || !grid[x][y]) {
            return;
        }

        /**
         * 千万别忘了这一步!!!
         */
        grid[x][y] = false;

        helper(grid, x + 1, y, m, n);
        helper(grid, x, y + 1, m ,n);
        helper(grid, x - 1, y, m ,n);
        helper(grid, x, y - 1, m ,n);
    }
}
