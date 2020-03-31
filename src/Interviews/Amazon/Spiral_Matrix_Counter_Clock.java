package Interviews.Amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Spiral_Matrix_Counter_Clock {
    /**
     * Given a 2D array, print it in counter-clock wise spiral form. See the following examples.
     * Examples :
     *
     * Input:
     * 1    2   3   4
     * 5    6   7   8
     * 9   10  11  12
     * 13  14  15  16
     * Output:
     * 1 5 9 13 14 15 16 12 8 4 3 2 6 10 11 7
     *
     * Input:
     * 1   2   3   4  5   6
     * 7   8   9  10  11  12
     * 13  14  15 16  17  18
     * Output:
     * 1 7 13 14 15 16 17 18 12 6 5 4 3 2 8 9 10 11
     */
    static int R = 4;
    static int C = 4;

    // function to print the
    // required traversal
    static void counterClockspiralPrint(int m, int n, int arr[][]) {
        int i, rowBegin = 0, colBegin = 0;

    /* rowBegin - starting row index
        m - ending row index
        colBegin - starting column index
        n - ending column index
        i - iterator */

        // initialize the count
        int cnt = 0;

        // total number of
        // elements in matrix
        int total = m * n;

        while (rowBegin < m && colBegin < n) {
            if (cnt == total)
                break;

            // Print the first column
            // from the remaining columns
            for (i = rowBegin; i < m; ++i) {
                System.out.print(arr[i][colBegin] + " ");
                cnt++;
            }
            colBegin++;

            if (cnt == total)
                break;

            // Print the last row from
            // the remaining rows
            for (i = colBegin; i < n; ++i) {
                System.out.print(arr[m - 1][i] + " ");
                cnt++;
            }
            m--;

            if (cnt == total)
                break;

            // Print the last column
            // from the remaining columns
            if (rowBegin < m) {
                for (i = m - 1; i >= rowBegin; --i) {
                    System.out.print(arr[i][n - 1] + " ");
                    cnt++;
                }
                n--;
            }

            if (cnt == total)
                break;

            // Print the first row
            // from the remaining rows
            if (colBegin < n) {
                for (i = n - 1; i >= colBegin; --i) {
                    System.out.print(arr[rowBegin][i] + " ");
                    cnt++;
                }
                rowBegin++;
            }
        }
    }

    public static List<Integer> spiralOrder1(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return res;
        }

        int rowBegin = 0;
        int rowEnd = matrix.length - 1;
        int colBegin = 0;
        int colEnd = matrix[0].length - 1;

        while (rowBegin <= rowEnd && colBegin <= colEnd) {
            for (int i = rowBegin; i <= rowEnd; i++) {
                res.add(matrix[i][colBegin]);
            }
            colBegin++;

            for (int i = colBegin; i <= colEnd; i++) {
                res.add(matrix[rowEnd][i]);
            }
            rowEnd--;

            if (colBegin <= colEnd) {
                for (int i = rowEnd; i >= rowBegin; i--) {
                    res.add(matrix[i][colEnd]);
                }
            }
            colEnd--;

            if (rowBegin <= rowEnd) {
                for (int i = colEnd; i >= colBegin; i--) {
                    res.add(matrix[rowBegin][i]);
                }
            }
            rowBegin++;
        }

        return res;
    }

    // Driver Code
    public static void main(String[] args) {
        int arr[][] = {{1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}};

        int arr1[][] = {
                {1,   2,   3,   4,  5,   6},
                {7,   8,   9,  10,  11,  12},
                {13,  14,  15, 16,  17,  18}
        };

        // Function calling
        counterClockspiralPrint(R, C, arr);

        System.out.println();
        System.out.println(Arrays.toString(spiralOrder1(arr1).toArray()));
    }
}
