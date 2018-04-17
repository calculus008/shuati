package leetcode;

/**
 * Created by yuank on 3/4/18.
 */
public class LE_63_Unique_Path_II {
    /*
        Follow up for "Unique Paths":

        Now consider if some obstacles are added to the grids. How many unique paths would there be?

        An obstacle and empty space is marked as 1 and 0 respectively in the grid.

        For example,
        There is one obstacle in the middle of a 3x3 grid as illustrated below.

        [
          [0,0,0],
          [0,1,0],
          [0,0,0]
        ]
        The total number of unique paths is 2.

        Note: m and n will be at most 100.
     */

     public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
         if(obstacleGrid[0][0]==1) return 0;
         int m = obstacleGrid.length;
         int n = obstacleGrid[0].length;
         int[] a = new int[n];
         a[0] = 1; //!!! if entry point is "1", we already return "0", so here we set a[0] to 1.


         for(int i=0; i<m; i++) {
             for(int j=0; j<n; j++) {
                 //int array is initialized with default value of "0", once a "1" appears in the first column and first row
                 //, the rest will also be 0
                 if(obstacleGrid[i][j] == 1) {
                     a[j] = 0;
                 } else if(j>0) {
                     a[j] += a[j-1];
                 }
             }
         }

         return a[n-1];
     }
}
