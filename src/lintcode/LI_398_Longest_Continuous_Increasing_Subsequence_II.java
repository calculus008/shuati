package lintcode;

/**
 * Created by yuank on 10/23/18.
 */
public class LI_398_Longest_Continuous_Increasing_Subsequence_II {
    /**
         Give you an integer matrix (with row size n, column size m)，find the longest
         increasing continuous subsequence in this matrix. (The definition of the longest
         increasing continuous subsequence here can start at any row or column and go
         up/down/right/left any direction).

         Example
         Given a matrix:

         [
         [1 ,2 ,3 ,4 ,5],
         [16,17,24,23,6],
         [15,18,25,22,7],
         [14,19,20,21,8],
         [13,12,11,10,9]
         ]

         return 25

         Challenge
         O(nm) time and memory.
     */


    /**
     * DSF + memory
     * Time and Space : O(nm)
     */


    /**
     * 找递增
     *
     * 一个2d array就足够了 因为找的是increasing sequence，所以在这个条件下是不会回头访问之前访问过的格子的.
     */
    public class Solution {
        public int longestContinuousIncreasingSubsequence2(int[][] matrix) {
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;

            int m = matrix.length;
            int n = matrix[0].length;
            int[][] mem = new int[m][n];

            int max = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    int length = helper(matrix, i, j, mem);
                    max = Math.max(max, length);
                }
            }

            return max;
        }

        private int helper(int[][] matrix, int x, int y, int[][] mem) {
            if (mem[x][y] != 0) {
                return mem[x][y];
            }

            int m = matrix.length;
            int n = matrix[0].length;
            int max = 0;

            int[][] neighbors = new int[][]{{1,0},{-1,0},{0,1},{0,-1}};

            for(int[] neighbor:neighbors){
                int nx = x + neighbor[0];
                int ny = y + neighbor[1];

                if (isValid(nx, ny, m, n) && matrix[x][y] < matrix[nx][ny]) {
                    int length = helper(matrix, nx, ny, mem);
                    max = Math.max(max, length);
                }
            }

            /**
             * 这个max是以当前cell(matrix[x][y])的四个邻居为起点的最长递增序列的长度，
             * 所以，作为以当前cell(matrix[x][y])为起点的最长递增序列的长度，应该加一。
             */
            mem[x][y] = max + 1;//!!!

            return mem[x][y];
        }

        private boolean isValid(int x, int y, int m, int n) {
            return (x >= 0 && x < m && y >= 0 && y < n);
        }
    }
}
