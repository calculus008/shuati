package lintcode;

/**
 * Created by yuank on 11/12/18.
 */
public class LI_574_Build_Post_Office {
    /**
      Given a 2D grid, each cell is either an house 1 or empty 0 (the number zero, one),
      find the place to build a post office, the distance that post office to all the
      house sum is smallest. Return the smallest distance. Return -1 if it is not possible.

      Example
      Given a grid:

      0 1 0 0
      1 0 1 1
      0 1 0 0

      return 6. (Placing a post office at (1,1), the distance that post office to all the
      house sum is smallest.)

      Notice
      You can pass through house and empty. (!!!)

      You only build post office on an empty.

      Hard

      Compare with LE_317_Shortest_Distance_From_All_Buildings, there's no wall in the matrix (value '2')
     */

    /**
         Time : O(n)

         简单来说：

         计算当前row 到其他row的距离的和
         计算当前col 到其他col的距离的和

         判断每一个0的距离，找最小值

         计算和的时候：
         当前row到目标row的距离的和 = 目标row上1的个数 * 当前row到目标row的距离

         col同理

         时间复杂度 O(n)

         !!!
         In LE_317_Shortest_Distance_From_All_Buildings , when grid[i][j] == 1, it is not able to be passed,
         you need to go around it, for this problem, you can go across grid[i][j] == 1. So simply copy the
         Solution of LE_317_Shortest_Distance_From_All_Buildings won't work for this problem.
     */
    public class Solution {
        public int shortestDistance(int[][] grid) {
            if (grid.length == 0 || grid[0].length == 0)
                return 0;

            //get number of 1s in each row and col
            int[] rowCount = new int[grid.length];
            int[] colCount = new int[grid[0].length];

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 1) {
                        rowCount[i]++;
                        colCount[j]++;
                    }
                }
            }

            int[] rowDistance = new int[grid.length];
            int[] colDistance = new int[grid[0].length];

            getDistances(rowCount, grid.length, rowDistance);
            getDistances(colCount, grid[0].length, colDistance);

            int ans = Integer.MAX_VALUE;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 0) {
                        int temp = rowDistance[i] + colDistance[j];
                        ans = Math.min(ans, temp);
                    }
                }
            }

            return ans;
        }

        private void getDistances(int[] ary, int size, int[] ans) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    ans[i] += ary[j] * (Math.abs(j - i));
                }
            }
        }
    }
}
