package leetcode;

/**
 * Created by yuank on 3/5/18.
 */
public class LE_74_Search_In_2D_Matrix {
    /**
        Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:

        Integers in each row are sorted from left to right.
        The first integer of each row is greater than the last integer of the previous row.
        For example,

        Consider the following matrix:

        [
          [1,   3,  5,  7],
          [10, 11, 16, 20],
          [23, 30, 34, 50]
        ]
        Given target = 3, return true.
     **/

    /**
        Solution: Binary Search
        Convert matrix[x][y] to array: a[col * x + y]
        Convert a[k] to matrix : matrix[a / col][a % col]
        Time : O(log(n * m)), Space : O(1)
     **/
    public boolean searchMatrix1(int[][] matrix, int target) {
        //!!!
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;

        int row = matrix.length;
        int col = matrix[0].length;
        int left = 0;
        int right = row * col - 1;

        while (left <= right) {
            int mid = (right - left) / 2 + left;
            int val = matrix[mid / col][mid % col];
            if(target == val) {
                return true;
            } else if (target < val) {
                right = mid -1;
            } else {
                left = mid + 1;
            }
        }
        return false;
    }

    /**
        Follow up question:
        If the condition is that values in row and column both increase("The first integer of each row is greater than the FIRST integer of the previous row"),
        find another solution.
        Solution 1: Time : O(n + m).
     **/
    public static boolean searchMatrix2(int[][] matrix, int target) {
        int n = matrix.length;
        if(n == 0)
            return false;

        int m = matrix[0].length;

        //Starting from left bottom corner
        int x = n-1;
        int y = 0;

        while(x >= 0 && y < m) {
            if(target > matrix[x][y]) {
                y++;
            } else if(target < matrix[x][y]) {
                x--;
            } else {
                return true;
            }
        }

        return false;
    }
}
