package Interviews.Nextdoor;

import java.util.ArrayList;
import java.util.Collections;

public class Diagonals_Sort {
    /**
     * 把一个 matrix 按斜线顺序重排
     */
    /**
     *    8     4      1            4       1       1
     *    4     4      1    -----   4       8       4
     *    4     8      9            4       8       9
     */

    public static void diagonal_sort(int[][] matrix, int dir) {
        int m = matrix.length;
        int n = matrix[0].length;

        if (dir == 1) {
            // from top-left to bottom-right
            for (int i = n - 1; i >= 0; i--) {
                int row = 0;
                int col = i;

                ArrayList<Integer> tmp = new ArrayList();

                while (row < m && col < n) {
                    System.out.println("Add matrix["+row+"]["+col+"]");
                    tmp.add(matrix[row++][col++]);
                }

                System.out.println("sort");
                Collections.sort(tmp);

                row = 0;
                col = i;

                for (Integer num : tmp) {
                    matrix[row++][col++] = num;
                }
            }

            for (int i = 1; i < m; i++) {
                int row = 0;
                int col = i;

                ArrayList<Integer> tmp = new ArrayList();

                while (row < n && col < m) {
                    tmp.add(matrix[col++][row++]);
                }

                Collections.sort(tmp);

                row = 0;
                col = i;

                for (Integer num : tmp) {
                    matrix[col++][row++] = num;
                }
            }
            //System.out.println("@@");
            //peek(matrix);
        } else {
            for (int i = n - 1; i > 0; i--) {
                int j = 0;
                int i_tmp = i;

                ArrayList<Integer> tmp = new ArrayList();

                while (j < m && i_tmp >= 0) {
                    tmp.add(matrix[j++][i_tmp--]);
                }

                Collections.sort(tmp);

                j = 0;
                i_tmp = i;

                for (Integer num : tmp) {
                    matrix[j++][i_tmp--] = num;
                }
            }
            for (int i = 1; i < m; i++) {
                int j = n - 1;
                int i_tmp = i;

                ArrayList<Integer> tmp = new ArrayList();

                while (j >= 0 && i_tmp < m) {
                    tmp.add(matrix[i_tmp++][j--]);
                }

                Collections.sort(tmp);

                j = n - 1;
                i_tmp = i;

                for (Integer num : tmp) {
                    matrix[i_tmp++][j--] = num;
                }
            }
        }
    }

    public static void peek(int[][] matrix) {
        for (int[] row : matrix) {
            for (int num : row) {
                System.out.print(num + "\t");
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = (int) (Math.random() * 10.0);
            }
        }
        peek(matrix);
        diagonal_sort(matrix, 1);
        System.out.println("@@");
        peek(matrix);
//        System.out.println("Hello World!");
    }
}
