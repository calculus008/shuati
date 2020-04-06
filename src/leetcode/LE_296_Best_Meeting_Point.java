package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 4/22/18.
 */
public class LE_296_Best_Meeting_Point {
    /**
         A group of two or more people wants to meet and minimize the total travel distance.
         You are given a 2D grid of values 0 or 1, where each 1 marks the home of someone in the group.
         The distance is calculated using Manhattan Distance, where distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.

         For example, given three people living at (0,0), (0,4), and (2,2):

         1 - 0 - 0 - 0 - 1
         |   |   |   |   |
         0 - 0 - 0 - 0 - 0
         |   |   |   |   |
         0 - 0 - 1 - 0 - 0
         The point (0,2) is an ideal meeting point, as the total travel distance of 2+2+2=6 is minimal. So return 6.

        Hard
     */

    /**
     Key : it is NOT steps between 2 points, so no need to use BFS
     1.Distance on x and y can be calculated separately. 降维。
     2.One a one dimension line, given 2 points A, B, the point that has the min combined distance to A and B
       must between A and B.
     3.The min combined distance is B - A. With same logic, add two more points C and D, the min combined distance to all 4
       points must be between C and D. And the min combined distance is (B-A)+(D-C).

     A---C---------D--------B

     Time :  O(m * n)
     Space : O(n)
     */

    /**
     * For example given in th question:
     *
     * a : {0, 0, 2} , row in sorted order
     * b : {0, 2, 4} , col in sorted order
     *
     * min() on a:
     * 2 - 0 = 2
     *
     * min() on b:
     * 4 - 0 = 4
     *
     * res = 4 + 2 = 6
     */
    public int minTotalDistance(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

        int m = grid.length;
        int n = grid[0].length;

        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();

        /**
         * Get locations on x axle
         */
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    a.add(i);
                }
            }
        }

        /**
         !!! 第二个双重循环要把上一个的内外循环调换位置
             Get locations on y axle
         */
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                if (grid[i][j] == 1) {
                    b.add(j);
                }
            }
        }

        return min(a) + min(b);
    }

    /**
     * two pointers to find min combined distance
     */
    public int min(List<Integer> list) {
        int res = 0;
        int i = 0;
        int j = list.size() - 1;

        while (i < j) {
            res += list.get(j--) - list.get(i++);
        }

        return res;
    }
}
