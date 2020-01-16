package leetcode;

/**
 * Created by yuank on 11/9/18.
 */
public class LE_361_Bomb_Enemy {
    /**
         Given a 2D grid, each cell is either a wall 'W', an enemy 'E' or empty '0' (the number zero),
         return the maximum enemies you can kill using one bomb. The bomb kills all the enemies in the same
         row and column from the planted point until it hits the wall since the wall is too strong to be destroyed.

         Note: You can only put the bomb at an empty cell.

         Example:

         Input: [["0","E","0","0"],["E","0","W","E"],["0","E","0","0"]]
         Output: 3
         Explanation: For the given grid,

         0 E 0 0
         E 0 W E
         0 E 0 0

         Placing a bomb at (1,1) kills 3 enemies.
     */

    /**
     * Time  : O(mn)
     * Space : O(n)
     *
     * Key insights :
     * 对于每一行和列，对于grid[i][j]=='0', 统计hits只需要在一定的边界内。
     * 如果一行内没有‘W'，则任何在该行内的值为'0'的点都有一样的hits.
     * 如果有'W'，则要统计在边界和'W'之间以及'W'和'W'之间的hits, 例如：
     *
     * 以'|'为边界的3个区间内的hits分别为 2， 1， 0。
     *
     * |     2    |    1  |  0 |
     *  0 0 E 0 E W 0 E 0 W 0 0
     *
     * 对列同理。
     *
     * 所以对每个点，不用把行和列都从新扫描，只有当从第一行和第一列，以及同列上一行和
     * 同行左别的列为‘W'时，才需要把hits reset 为 0， 统计从当前位置到下一个边界的hits.
     *
     * There are 3 values in grid : "0", "E", "W". When iterating grid :
     * "W" - do nothing, skip
     * "0" or "E" - run count logic for row and col
     *
     * 所以等于做了2次扫描 - 2 x mn -> O(mn)
     *
     */
    class Solution {
        public int maxKilledEnemies(char[][] grid) {
            if (null == grid || grid.length == 0 || grid[0].length == 0) {
                return 0;
            }

            int m = grid.length;
            int n = grid[0].length;
            int res = 0;
            int[] col = new int[n];

            for (int i = 0; i < m; i++) {
                int row = 0;
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 'W') {
                        continue;
                    }

                    if (j == 0 || grid[i][j - 1] == 'W') {
                        row = getRowHits(grid, i, j);
                    }

                    if (i == 0 || grid[i - 1][j] == 'W') {
                        col[j] = getColHits(grid, i, j);
                    }

                    if (grid[i][j] == '0') {
                        res = Math.max(res, row + col[j]);
                    }
                }
            }

            return res;
        }

        private int getRowHits(char[][] grid, int i, int j) {
            int count = 0;
            while (j < grid[0].length && grid[i][j] != 'W') {
                if (grid[i][j] == 'E') {
                    count++;
                }
                j++;
            }
            return count;
        }

        private int getColHits(char[][] grid, int i, int j) {
            int count = 0;
            while (i < grid.length && grid[i][j] != 'W') {
                if (grid[i][j] == 'E') {
                    count++;
                }
                i++;
            }
            return count;
        }
    }

    class Solution_Practice {
        public int maxKilledEnemies(char[][] grid) {
            if (null == grid || grid.length == 0) return 0;

            int res = 0;
            int m = grid.length;
            int n = grid[0].length;

            int[] col = new int[n];

            for (int i = 0; i < m; i++) {
                int row = 0;
                for (int j = 0; j < n; j++) {
                    /**
                     * !!!
                     * Must do this way, otherwise, the logic next to check if
                     * previous col or row element is 'W' will not work properly
                     *
                     * 开始COUNT的trigger是：
                     * 1.i = 0, start count current col
                     * 2.j = 0, start count current row
                     * 3.Previous element in the same row is 'W', start count current row.
                     * 4.Previous element in the same col is 'W, start count current col.
                     */
                    if (grid[i][j] == 'W') continue;

                    if (i == 0 || grid[i - 1][j] == 'W') {
                        col[j] = countCol(grid, i, j);
                    }

                    if (j == 0 || grid[i][j - 1] == 'W') {
                        row = countRow(grid, i, j);
                    }

                    if (grid[i][j] == '0') {
                        int kill = col[j] + row;
                        res = Math.max(res, kill);
                    }
                }
            }

            return res;
        }

        private int countCol(char[][] grid, int i, int j) {
            int res = 0;
            int m = grid.length;
            while (i < m && grid[i][j] != 'W') {
                if (grid[i][j] == 'E') res++;
                i++;
            }

            return res;
        }

        private int countRow(char[][] grid, int i, int j) {
            int res = 0;
            int n = grid[0].length;
            while (j < n && grid[i][j] != 'W') {
                if (grid[i][j] == 'E') res++;
                j++;
            }

            return res;
        }
    }
}
