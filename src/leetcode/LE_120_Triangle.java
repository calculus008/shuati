package leetcode;

import java.util.List;

/**
 * Created by yuank on 3/13/18.
 */
public class LE_120_Triangle {
    /**
        Given a triangle, find the minimum path sum from top to bottom. Each step you may move to adjacent numbers on the row below.

        For example, given the following triangle
        [
             [2],
            [3,4],
           [6,5,7],
          [4,1,8,3]
        ]
        The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).

        Note:
        Bonus point if you are able to do this using only O(n) extra space, where n is the total number of rows in the triangle.
     */

    /**
        1.For ith level, jth element, in i+1 level, its adjacent elements are at position j and j+1
        2.If total level is n, then the last level has n elements.
        3.DP from bottom to top

                 [2],
                [3,4],
               [6,5,7],
              [4,1,8,3]

        Can be seen like :

         [2],
         [3,4],
         [6,5,7],
         [4,1,8,3]

        dp[]:
        init : [0,0,0,0,0]
        i = 3 :[4,1,8,3,0]
        i = 2 :[7,6,10,3,0]
        i = 1 :[9,10,10,3,0]
        i = 0 :[11,10,10.3,0]

        Time : O(n ^ 2), Space : O(n)

        Similar problem : LE_931_Minimum_Falling_Path_Sum

    */
    public static int minimumTotal(List<List<Integer>> triangle) {
        int[] dp = new int[triangle.size() + 1];

        for (int i = triangle.size() - 1; i >= 0; i--) {
            /**
             * !!!
             * triangle.get(i).size()
             */
            for (int j = 0; j < triangle.get(i).size(); j++) {
                /**
                 * !!!
                 * 当前元素加上相邻两条路径的累加的最小值 (in dp[])
                 */
                dp[j] = triangle.get(i).get(j) + Math.min(dp[j], dp[j + 1]);
            }
        }

        return dp[0];
    }
}
