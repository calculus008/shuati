package Interviews.Google;

public class ValidMatrics {
    /**
     * Find valid matrix. Given matrix A of length N * M. Each element A can only be 0 or 1.
     * If a sub-matrix only contains 0, we call it valid. How many sub-matrices are there in A?
     *
     * Example:
     * input: A = [[1, 0, 0, 0, 1, 0],
     *             [1, 0, 0, 0, 0, 0],
     *             [0, 0, 0, 1, 1, 0]]
     * output: [(6+1) + (15) + (6+1)] + [(6+1) + (3+1) + (3+1)] = 29 + 15 = 44
     *
     * 2D variation of ValidSubarray
     */

    /**
     * 面试官提示要使用第一题。这个提示很重要。算法分为两个步骤：
     * 1）procedure 1. 统计single rows. 遍历每一行，每一行调用第一题的代码。得到一个数 part1。
     * 2）procedure 2. 统计multiple rows. 一个双层循环loop。外层 i 从第二行开始到最后一行，
     *    内层 j 从i-1到第一行。最里边对从第 i 行到 j 行做“collapse”，就是elementwise or。
     *    得到一个数组，传入第一问代码。这里总数是part2。
     * 3）返回 part1 + part2

     */
    public static int ValidMatrics(int[][] matrix) {
        int res = 0;
        for (int[] a : matrix) {
            res += validSubarray(a);
        }

        System.out.println("part1=" + res);

        int m = matrix.length;
        int n = matrix[0].length;

        for (int i = 1; i < m; i++) {
            int[] b = new int[n];

            for (int j = i - 1; j >= 0; j--) {
                for (int k = 0; k < n; k++) {
                    b[k] = matrix[i][k] | matrix[j][k];
                }
                res += validSubarray(b);
            }

        }

        return res;
    }

    /**
     * Copy from ValidSubarray
     */
    public static int validSubarray(int[] nums) {
        int count = 0;
        int res = 0;

        for (int num : nums) {
            if (num == 0) {
                count++;
                res += count;
            } else {
                count = 0;
            }
        }

        System.out.println(res);
        return res;
    }

    public static void main(String [] args) {
        int[][] test ={{1, 0, 0, 0, 1, 0},
                       {1, 0, 0, 0, 0, 0},
                       {0, 0, 0, 1, 1, 0}};
        int res = ValidMatrics(test);

        System.out.println(res);
    }

}
