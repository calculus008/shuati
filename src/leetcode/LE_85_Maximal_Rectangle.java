package leetcode;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created by yuank on 3/7/18.
 */
public class LE_85_Maximal_Rectangle {
    /**
        Given a 2D binary matrix filled with 0's and 1's,
        find the largest rectangle containing only 1's and return its area.

        For example, given the following matrix:

        1 0 1 0 0
        1 0 1 1 1
        1 1 1 1 1
        1 0 0 1 0
        Return 6.
     */

    //Solution 1: Use histogram from LE_84
    public static int maximalRectangle1(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;

        int m = matrix.length;
        int n = matrix[0].length;
        int[] nums = new int[n];
        int res = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //注意 ：输入是char类型的二维数组，不是int
                nums[j] = matrix[i][j] == '0' ? 0 : nums[j] + 1;
            }
            int area = histogram(nums);
            res = Math.max(res, area);
        }

        return res;
    }

    public static int histogram(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int res = 0;

        for (int i = 0; i <= nums.length; i++) {
            int h = i == nums.length ? 0 : nums[i];
            while (!stack.isEmpty() && h < nums[stack.peek()]) {
                int height = nums[stack.pop()];
                int start = stack.isEmpty() ? -1 : stack.peek();
                int area = height * (i - start - 1);
                res = Math.max(res, area);
            }
            stack.push(i);
        }

        return res;
    }


    /**
        Solution 2 : DP
        Time: O(m *n), Space : O(n)

        https://leetcode.com/problems/maximal-rectangle/discuss/29054/Share-my-DP-solution

        Let the maximal rectangle area at row i and column j be computed by
        [right(i,j) - left(i,j)] * height(i,j). All the 3 variables left, right, and height can be determined
        by the information from previous row, and also information from the current row

        Transition equations:

         left(i,j) = max(left(i-1,j), cur_left), cur_left can be determined from the current row
         right(i,j) = min(right(i-1,j), cur_right), cur_right can be determined from the current row
         height(i,j) = height(i-1,j) + 1, if matrix[i][j]==‘1’;
         height(i,j) = 0, if matrix[i][j]==‘0’


         height[] : from top to this position, there are how many ‘1’.

         left[] : At current position(i, j), what is the index of left bound of the rectangle with height[j].
                  0 means at this position, no rectangle. (现在这个矩形的左边的下标)
                  !!!从左到右，连续出现‘1’的string的第一个坐标。

         right[] : Right bound index of this rectangle. ‘n’ means no rectangle.
                   !!!从右到左，连续出现‘1’的string的第一个坐标。

         matrix
         0 0 0 1 0 0 0
         0 0 1 1 1 0 0
         0 1 1 1 1 1 0

         height
         0 0 0 1 0 0 0
         0 0 1 2 1 0 0
         0 1 2 3 2 1 0

         left
         0 0 0 3 0 0 0
         0 0 2 3 2 0 0
         0 1 2 3 2 1 0

         right
         7 7 7 4 7 7 7
         7 7 5 4 5 7 7
         7 6 5 4 5 6 7

             result
         0 0 0 1 0 0 0
         0 0 3 2 3 0 0
         0 5 6 3 6 5 0

     */

     public static int maximalRectangle2(char[][] matrix) {
         if (null == matrix || matrix.length == 0 || matrix[0].length == 0) return 0;

         int m = matrix.length;
         int n = matrix[0].length;
         int[] left = new int[n];
         int[] right = new int[n];
         int[] height = new int[n];

         Arrays.fill(right, n);
         int res = 0;

         for (int i = 0; i < m; i++) {
             int curLeft = 0, curRight = n;

             for (int j = 0; j < n; j++) {
                 if (matrix[i][j] == '1') {
                     height[j]++;
                 } else {
                     height[j] = 0;
                 }
             }

             for (int j = 0; j < n; j++) {
                 if (matrix[i][j] == '1') {
                     left[j] = Math.max(left[j], curLeft);
                 } else {
                     //current val is 0, so the left boundary could be next one : j + 1
                     curLeft = j + 1;
                     left[j] = 0;
                 }
             }

             for (int j = n - 1; j >= 0; j--) {
                 if (matrix[i][j] == '1') {
                     right[j] = Math.min(right[j], curRight);
                 } else {
                     right[j] = n;
                     curRight = j;
                 }
             }

             for (int j = 0; j < n; j++) {
                 res = Math.max(res, (right[j] - left[j]) * height[j]);
             }
         }

         return res;
     }
}
