package Interviews.Lyft.lc;

import java.util.Arrays;

public class Matrix_Transform {
    /**
     * From 面经
     * "3*3 matrix has True or False values. Given api Toggle(i, j)， 可以把ith row and jth col的值x变成!x.
     * 给定input matrix and output matrix, 最少需要调用多少次Toggle API可以达成转换"
     *
     * https://stackoverflow.com/questions/1310590/how-to-do-matrix-conversions-by-row-and-columns-toggles
     * https://stackoverflow.com/questions/14169450/converting-a-binary-matrix-to-0s-by-toggling-rows-and-columns
     * https://stackoverflow.com/questions/1489441/transforming-a-nxn-binary-matrix-to-a-zero-matrix-using-minimum-row-and-column-t
     *
     * I have got a square matrix consisting of elements either 1 or 0.
     * An ith row toggle toggles all the ith row elements (1 becomes 0 and vice versa) and jth column toggle
     * toggles all the jth column elements. I have got another square matrix of similar size. I want to change
     * the initial matrix to the final matrix using the minimum number of toggles. For example
     *
     * |0 0 1|
     * |1 1 1|
     * |1 0 1|
     *
     * to
     *
     * |1 1 1|
     * |1 1 0|
     * |1 0 0|
     *
     * would require a toggle of the first row and of the last column.
     *
     * We've reduced the problem to converting a matrix to the zero matrix via toggles, if possible.
     * Start by noting that, if we want a minimal answer, we'll never toggle a row or column more than
     * once, since toggling a row/column twice is the same as not toggling to begin with -- a consequence
     * of the identity (P XOR 1) XOR 1 = P.
     *
     * Let's look at the first row. Every 1 in the first row must be toggled to 0. We can do that by
     * toggling each column with a 1 in the first row, or we can toggle the first row, swapping the 1's
     * and 0's, and then toggle each new 1 back to a 0. And (assuming no toggle pairs) those are the only
     * two sets of operations that result in the first row being reduced to all 0's.
     *
     * At this point, look at the other rows. If any row has a mixture of 0's or 1's, you're done and
     * the problem is insoluble; there is no way to make the row all 0's without a column toggle, and
     * that destroys a 0 in the first row. OTOH, if every other row is all 0's or all 1's, then you
     * just have row toggles remaining.
     *
     * The final step is a consequence of the fact that there are 2N possible toggles, and no toggle
     * will be part of both solutions. That should be immediately clear from the above for column toggles;
     * for row toggles, note that a row that is all 0's after one set of column toggles will be all 1's
     * after the other set of column toggles. So, after computing one set of K toggles, the size of the other
     * set will be 2N - K toggles.
     */

     public static int toggleMatrix(boolean[][] matrix, boolean[][] result) {
         int m = matrix.length;
         int n = matrix[0].length;

         boolean[][] temp = new boolean[m][n];
         for (int i = 0; i < m; i++) {
             for (int j = 0; j < n; j++) {
                 temp[i][j] = matrix[i][j] ^ result[i][j];
             }
         }

         if (isAllFalse(temp)) return 0;

         printMatrix(temp);

         int steps = 0;
         for (int j = 0; j < n; j++) {
             if (temp[0][j]) {
                 toggleCol(temp, j);
                 printMatrix(temp);

                 steps++;
             }
         }

         if (!isRowUniform(temp)) return -1;

         steps += numberOfAllTrueRows(temp);

         System.out.println("steps = " + steps);

         return Math.min(steps, m + n - steps);
     }

     private static void printMatrix(boolean[][] m) {
         for (boolean[] a : m) {
             System.out.println(Arrays.toString(a));
         }
         System.out.println("");
     }

     private static boolean isRowUniform(boolean[][] m) {
         for (int i = 1; i < m.length; i++) {
             for (int j = 1; j < m[0].length; j++) {
                 if (m[i][j - 1] != m[i][j]) return false;
             }
         }

         return true;
     }

     private static int numberOfAllTrueRows(boolean[][] m) {
         int count = 0;
         for (int i = 1; i < m.length; i++) {
             boolean allTrue = true;
             for (int j = 0; j < m[0].length; j++) {
                 if (!m[i][j]) {
                     allTrue = false;
                 }
             }
             if (allTrue) count++;
         }

         return count;
     }

     private static boolean isAllFalse(boolean[][] m) {
         for (int i = 0; i < m.length; i++) {
             for (int j = 0; j < m[0].length; j++) {
                 if (m[i][j]) return false;
             }
         }

         return true;
     }

     private static void toggleRow(boolean[][] m, int row) {
         for (int col = 0; col < m[0].length; col++) {
             m[row][col] ^= true;
         }
     }

     private static void toggleCol(boolean[][] m ,int col) {
         for (int row = 0; row < m.length; row++) {
             m[row][col] ^= true;
         }
     }

     public static void main(String[] args) {
         boolean[][] input = {
                 {false, false, true},
                 {true, true, true},
                 {true, false, true}
         };

         boolean[][] output = {
                 {true, true, true},
                 {true, true, false},
                 {true, false, false}

         };

         System.out.println(toggleMatrix(input, output));
     }
}
