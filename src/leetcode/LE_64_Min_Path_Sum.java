package leetcode;

/**
 * Created by yuank on 3/4/18.
 */
public class LE_64_Min_Path_Sum {
    /*
        Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right which minimizes the sum of all numbers along its path.

        Note: You can only move either down or right at any point in time.

        Example 1:
        [[1,3,1],
         [1,5,1],
         [4,2,1]]
        Given the above grid map, return 7. Because the path 1→3→1→1→1 minimizes the sum.
     */

    public static int minPathSum(int[][] grid) {
        if(null == grid || grid.length ==0) return 0;

        //!!! we leave out grid[0][0], since it is the start point
        for (int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if (i == 0 && j != 0) grid[i][j] += grid[i][j-1]; //first row
                if (i != 0 && j == 0) grid[i][j] += grid[i-1][j]; //forst column
                if (i != 0 && j != 0) grid[i][j] += Math.min(grid[i][j-1], grid[i-1][j]);
            }
        }

        return grid[grid.length - 1][grid[0].length - 1];
    }
}
