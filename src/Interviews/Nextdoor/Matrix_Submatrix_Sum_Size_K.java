package Interviews.Nextdoor;

import java.util.*;

/**
 * Given an n x n square matrix, find sum of all sub-squares of size k x k where k is smaller than or equal to n.
 * Examples :
 *
 * Input:
 * n = 5, k = 3
 * arr[][] = { {1, 1, 1, 1, 1},
 *             {2, 2, 2, 2, 2},
 *             {3, 3, 3, 3, 3},
 *             {4, 4, 4, 4, 4},
 *             {5, 5, 5, 5, 5},
 *          };
 * Output:
 *        18  18  18
 *        27  27  27
 *        36  36  36
 *
 *
 * Input:
 * n = 3, k = 2
 * arr[][] = { {1, 2, 3},
 *             {4, 5, 6},
 *             {7, 8, 9},
 *          };
 * Output:
 *        12  16
 *        24  28
 *
 * https://www.geeksforgeeks.org/given-n-x-n-square-matrix-find-sum-sub-squares-size-k-x-k/
 *
 * ##
 * 名字貌似是perfectsquare。 要求是 给一个 M * M的 matrix, 再给一个 输入K. 这个 K 可以在矩阵里面构成很多歌 K* K的子矩阵。
 * 让我们做 三件事：
 * 1.算出所有K*K子矩阵的和。
 * 2.这些算出的和找到那个最大的和
 * 3. 找出 加起来是最大和的 所有matrix， 这些所有matrix 的所有的数值去重放在一个集合里面， 然后计算出这个集合的 所有数字的和。
 * 也就是说 如果三个 4 * 4子矩阵 他们各自的和 是最大的sum。那么 把 3 * 4 * 4 所有的数值 放在一个集合里面， 然后去重 求和。
 * 我的思路是 用一个HashMap<Integer, List<List<Integer>>> 计算所有子矩阵的和 并且把他们的成员包在list 里面。
 * 之后再用set去重。
 */
public class Matrix_Submatrix_Sum_Size_K {
    static int n = 5;

    /**
     * A O(n^2) function to find sum of all sub-squares of size k x k
     * in a given square matrix of size n x n
     **/
    static void printSumTricky(int matrix[][], int k) {
        if (k > n) return;

        /**
         * 1: PREPROCESSING : To store sums of all strips of size k x 1
         **/
        int stripSum[][] = new int[n][n];

        // Go column by column
        for (int j = 0; j < n; j++) {
            // Calculate sum of first k x 1
            // rectangle in this column
            int sum = 0;
            for (int i = 0; i < k; i++) {
                sum += matrix[i][j];
            }
            stripSum[0][j] = sum;

            // Calculate sum of remaining rectangles
            for (int i = 1; i < n - k + 1; i++) {
                sum += (matrix[i + k - 1][j] - matrix[i - 1][j]);
                stripSum[i][j] = sum;
            }
        }

        /**
         * 2: CALCULATE SUM of Sub-Squares using stripSum[][]
         **/
//        int max = Integer.MIN_VALUE;

        for (int i = 0; i < n - k + 1; i++) {
            // Calculate and print sum of first
            // subsquare in this row
            int sum = 0;
            for (int j = 0; j < k; j++) {
                sum += stripSum[i][j];
            }
            System.out.print(sum + " ");

            // Calculate sum of remaining squares
            // in current row by removing the
            // leftmost strip of previous sub-square
            // and adding a new strip
            for (int j = 1; j < n - k + 1; j++) {
                sum += (stripSum[i][j + k - 1] - stripSum[i][j - 1]);
                System.out.print(sum + " ");
            }
            System.out.println();
        }
    }

    static void printSumSimple(int mat[][], int k) {
        // k must be smaller than or
        // equal to n
        if (k > n) return;

        int max = Integer.MIN_VALUE;
        Set<Integer> set = new HashSet<>();
        Map<Integer, List<Integer>> map = new HashMap<>();

        // row number of first cell in
        // current sub-square of size k x k
        for (int i = 0; i < n - k + 1; i++) {
            // column of first cell in current
            // sub-square of size k x k
            for (int j = 0; j < n - k + 1; j++) {
                // Calculate and print sum of
                // current sub-square
                int sum = 0;
                set.clear();

                for (int p = i; p < k + i; p++) {
                    for (int q = j; q < k + j; q++) {
                        set.add(mat[p][q]);
                        sum += mat[p][q];
                    }
                }

                if (sum >= max) {
                    int val = 0;
                    for (int num : set) {
                        val += sum;
                    }

                    map.putIfAbsent(sum, new ArrayList<>());
                    map.get(sum).add(val);
                    max = sum;
                }


                System.out.print(sum + " ");
            }

            // Line separator for sub-squares
            // starting with next row
            System.out.println();
            System.out.println(Arrays.toString(map.get(max).toArray()));
        }
    }
    // Driver program to test above function
    public static void main(String[] args) {
        int mat[][] = {{1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2},
                {3, 3, 3, 3, 3},
                {4, 4, 4, 4, 4},
                {5, 5, 5, 5, 5},
        };
        int k = 3;

        printSumTricky(mat, k);
        printSumSimple(mat, k);
    }
}
