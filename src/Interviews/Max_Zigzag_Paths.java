package Interviews;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Max_Zigzag_Paths {
        /**
         * Snapchat 面经题
         *
         * 题目是给你一个2D Matrix然后输出所有最长的zigzag path
         * Example:
         * [1 2 3 4 5]
         * [1 2 2 2 2]
         * [1 2 3 4 4]
         * [1 2 2 3 2]
         * [1 2 2 4 4]
         * <p>
         * 比如 2->3->2->3->2->3->2 就是一个valid path, 要求返回所有最长的path
         */
        static int[][] mem;
        static boolean[][] visited;
        static int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        public static List<List<Integer>> maxZigzagPath(int[][] matrix) {
            List<List<Integer>> res = new ArrayList<>();
            if (null == matrix || matrix.length == 0 || matrix[0].length == 0) {
                return res;
            }

            int m = matrix.length;
            int n = matrix[0].length;
            mem = new int[m][n];
            visited = new boolean[m][n];

            int len = longestZigzagPath(matrix);

            System.out.println("maxlen = " + len);

            for (int[] k : mem) {
                System.out.println(Arrays.toString(k));
            }

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (mem[i][j] == len) {
                        getMaxPath(res, new ArrayList<>(), matrix, i, j, m, n, len + 1);
                    }
                }
            }

            return res;
        }

        public static void getMaxPath(List<List<Integer>> res, List<Integer> temp, int[][] matrix, int x, int y, int m, int n, int last) {
            if (x < 0 || x >= m || y < 0 || y >= n) {
                return;
            }

            System.out.println("1.last = " + last + ", mem[" + x +  "][" + y + "] = " + mem[x][y]);

            if (mem[x][y] != last - 1) {//!!!
                return;
            }

            if (mem[x][y] == 1) {//end of path;
                res.add(new ArrayList<>(temp));
                return;
            }

            System.out.println("2.last = " + last + ", mem[" + x +  "][" + y + "] = " + mem[x][y]);

            int val = 0;
            for (int i = 0; i < dirs.length; i++) {
                int nx = x + dirs[i][0];
                int ny = y + dirs[i][1];

                temp.add(matrix[x][y]);
                getMaxPath(res, temp, matrix, nx, ny, m, n, mem[x][y]);
                temp.remove(temp.size() - 1);
            }
        }


        public static int longestZigzagPath(int[][] matrix) {
            if (null == matrix || matrix.length == 0 || matrix[0].length == 0) {
                return 0;
            }

            int m = matrix.length;
            int n = matrix[0].length;
            int res = Integer.MIN_VALUE;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
//                    res = Math.max(res,
//                            Math.max(helper(matrix, m, n, Integer.MIN_VALUE, i, j, true),
//                                    helper(matrix, m, n, Integer.MAX_VALUE, i, j, false)));
//                    res = Math.max(res, helper(matrix, m, n, Integer.MIN_VALUE, i, j, true));
                    res = Math.max(res, helper(matrix, m, n, Integer.MAX_VALUE, i, j, false));

//                    helper(matrix, m, n, Integer.MIN_VALUE, i, j, true);
                }
            }

            return res;
        }

        private static int helper(int[][] matrix, int m, int n, int last, int x, int y, boolean increase) {
            /**
             * !!! "matrix[x][y] <= last"
             */
            if (x < 0 || x >= m || y < 0 || y >= n) {
                return 0;
            }


            if (increase) {
                if (matrix[x][y] <= last) {
                    return 0;
                }
            } else {
                if (matrix[x][y] >= last) {
                    return 0;
                }
            }


            if (mem[x][y] != 0) {//!!!
                return mem[x][y];
            }


            if (visited[x][y]) {
                return 0;
            }

            System.out.println("x = " + x + ", y = " + y + ", last = " + last + ", cur = " + matrix[x][y] + ", increase = " + increase);

            int val = 0;
            for (int i = 0; i < dirs.length; i++) {
                int nx = x + dirs[i][0];
                int ny = y + dirs[i][1];

                visited[x][y] = true;
                val = Math.max(val, helper(matrix, m, n, matrix[x][y], nx, ny, increase ? false : true));
                visited[x][y] = false;
            }

            val++;//!!!
            mem[x][y] = val;//!!!


            return val;
        }

//    static int[][][] dp;

//    public static int longestZigzagPath_DP(int[][] matrix) {
//            int m = matrix.length;
//            int n = matrix[0].length;
//            int res = 0;
//            dp = new int[m][n][2];
//
//            for (int i = 0; i < m; i++) {
//                for (int j = 0; j < n; j++) {
//                    dp[i][j][0] = dp[i][j][1] = 1;
//                }
//            }
//
//            for (int i = 0; i < m; i++) {
//                for (int j = 0; j < n; j++) {
//                    int[] k = findMaxPathInNeighbors(matrix, i, j, m, n);
//                }
//            }
//
//            return res;
//        }

//        private static int[] findMaxPathInNeighbors(int[][] matrix, int x, int y, int m, int n) {
//            int[] len = {0, 0};
//            int val = 0;
//            for (int i = 0; i < dirs.length; i++) {
//                int nx = x + dirs[i][0];
//                int ny = y + dirs[i][1];
//
//                if (nx < 0 || nx >= m || ny < 0 || ny >= n) {
//                    continue;
//                }
//
//                if (matrix[x][y] < matrix[nx][ny]) {
//
//                } else if (matrix[x][y] > matrix[nx][ny]) {
//
//                }
//                len[0] = Math.max(len[0], dp[nx][ny][0]);
//                len[1] = Math.max(len[1], dp[nx][ny][1]);
//
//
//            }
//        }

        public static void main(String[] args) {
            int[][] matrix = new int[][] {{1, 2, 3, 4, 5}, {1, 2, 2, 2, 2}, {1, 2, 3, 4, 4}, {1, 2, 2, 3, 2}, {1, 2, 2, 4, 4}};
            List<List<Integer>> res = maxZigzagPath(matrix);


//            for (List<Integer> l : res) {
//                System.out.println(Arrays.toString(l.toArray()));
//            }
        }

}
