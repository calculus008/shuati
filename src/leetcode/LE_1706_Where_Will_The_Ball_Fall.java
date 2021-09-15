package leetcode;

public class LE_1706_Where_Will_The_Ball_Fall {
    /**
     * You have a 2-D grid of size m x n representing a box, and you have n balls. The box is open on the top and bottom sides.
     *
     * Each cell in the box has a diagonal board spanning two corners of the cell that can redirect a ball to the right or to the left.
     *
     * A board that redirects the ball to the right spans the top-left corner to the bottom-right corner and is represented in the grid as 1.
     * A board that redirects the ball to the left spans the top-right corner to the bottom-left corner and is represented in the grid as -1.
     * We drop one ball at the top of each column of the box. Each ball can get stuck in the box or fall out of the bottom.
     * A ball gets stuck if it hits a "V" shaped pattern between two boards or if a board redirects the ball into either wall of the box.
     *
     * Return an array answer of size n where answer[i] is the column that the ball falls out of at the bottom after dropping the
     * ball from the ith column at the top, or -1 if the ball gets stuck in the box.
     *
     * Constraints:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 100
     * grid[i][j] is 1 or -1.
     *
     * Medium
     */

    /**
     * Recursion
     *
     * Time O(mn)
     * Space O(n)
     */
    class Solution1 {
        public int[] findBall(int[][] grid) {
            int n = grid[0].length;
            int[] res = new int[n];

            for (int i = 0; i < n; i++) {
                res[i] = helper(grid, 0, i);
            }

            return res;
        }

        private int helper(int[][] grid, int x, int y) {
            /**
             * !!! NOT "x == grid.length - 1". This represents the ball fells to the bottom.
             */
            if (x == grid.length) {
                return y;
            }

            /**
             * Checking out of boundary cases.
             * Since x starts from 0 and keeps increasing, no need to check x < 0.
             * Since x == grid.length is checked frist in helper(), no need to check x >= grid.length case.
             */
            if (y < 0 || y >= grid[0].length) return -1;


            if (grid[x][y] == 1 && y + 1 < grid[0].length && grid[x][y + 1] == 1) {
                return helper(grid, x + 1, y + 1);
            } else if (grid[x][y] == -1 && y - 1 >= 0 && grid[x][y - 1] == -1) {
                return helper(grid, x + 1, y - 1);
            } else {
                return -1;
            }
        }
    }

    /**
     * Iterative solution
     * Time O(mn)
     * Space O(n)
     */
    class Solution2 {
        public int[] findBall(int[][] grid) {
            if (grid == null || grid.length == 0) return new int[0];

            int[] res = new int[grid[0].length];

            // Each loop computes the result for when be drop a ball in column i.
            for (int i = 0; i < grid[0].length; i++) {
                int x = 0;
                int y = i;

                while (x < grid.length) {
                    // We go to the right if the current value and the value to the right are both equal to 1.
                    if (grid[x][y] == 1 && y + 1 < grid[0].length && grid[x][y + 1] == 1) {
                        y++;
                        x++;
                        // We go to the left if the current value and the value to the left are both equal to -1.
                    } else if (grid[x][y] == -1 && y - 1 >= 0 && grid[x][y - 1] == -1) {
                        y--;
                        x++;
                    } else {
                        break;  // If we can't move to the left, and we can't move to the right, then the ball is stuck because there is no other way to move.
                    }
                }
                res[i] = x == grid.length ? y : -1;   // Store -1 if the ball got stuck.
            }
            return res;
        }
    }
}
