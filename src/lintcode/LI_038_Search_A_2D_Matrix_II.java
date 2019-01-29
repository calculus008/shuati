package lintcode;

/**
 * Created by yuank on 7/2/18.
 */
public class LI_038_Search_A_2D_Matrix_II {
    /**
         Write an efficient algorithm that searches for a value in an m x n matrix, return the occurrence of it.

         This matrix has the following properties:

         Integers in each row are sorted from left to right.
         Integers in each column are sorted from up to bottom.
         No duplicate integers in each row or column.
         Example
         Consider the following matrix:

         [
         [1, 3, 5, 7],
         [2, 4, 7, 8],
         [3, 5, 9, 10]
         ]
         Given target = 3, return 2.

         Challenge
         O(m+n) time and O(1) extra space
     **/


    public int searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int n = matrix.length;
        int m = matrix[0].length;

        //Starting from left bottom corner
        int x = n-1;
        int y = 0;
        int res = 0;

        while(x >= 0 && y < m) {
            if(target > matrix[x][y]) {
                y++;
            } else if(target < matrix[x][y]) {
                x--;
            } else {//!!! return number of occurrences
                res++;
                x--;
                y++;
            }
        }

        return res;
    }

    //另外一种问法，只要求返回是否找到target
    public static boolean searchMatrix2(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int n = matrix.length;
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
