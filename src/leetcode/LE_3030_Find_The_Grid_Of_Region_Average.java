package leetcode;

public class LE_3030_Find_The_Grid_Of_Region_Average {
    /**
     * ou are given m x n grid image which represents a grayscale image, where image[i][j] represents a pixel with
     * intensity in the range [0..255]. You are also given a non-negative integer threshold.
     *
     * Two pixels are adjacent if they share an edge.
     *
     * A region is a 3 x 3 subgrid where the absolute difference in intensity between any two adjacent pixels is less
     * than or equal to threshold.
     *
     * All pixels in a region belong to that region, note that a pixel can belong to multiple regions.
     *
     * You need to calculate a m x n grid result, where result[i][j] is the average intensity of the regions to which
     * image[i][j] belongs, rounded down to the nearest integer. If image[i][j] belongs to multiple regions, result[i][j]
     * is the average of the rounded-down average intensities of these regions, rounded down to the nearest integer.
     * If image[i][j] does not belong to any region, result[i][j] is equal to image[i][j].
     *
     * Return the grid result.
     *
     * Medium
     *
     * https://leetcode.com/problems/find-the-grid-of-region-average
     */

    class Solution {
        public int[][] resultGrid(int[][] a, int threshold) {
            int m = a.length;
            int n = a[0].length;
            int[][] result = new int[m][n];
            int[][] cnt = new int[m][n];

            for (int i = 2; i < m; i++) {
                next:
                for (int j = 2; j < n; j++) {
                    // 检查左右相邻格子
                    for (int x = i - 2; x <= i; x++) {
                        if (Math.abs(a[x][j - 2] - a[x][j - 1]) > threshold || Math.abs(a[x][j - 1] - a[x][j]) > threshold) {
                            continue next; // 不合法，下一个
                        }
                    }

                    // 检查上下相邻格子
                    for (int y = j - 2; y <= j; y++) {
                        if (Math.abs(a[i - 2][y] - a[i - 1][y]) > threshold || Math.abs(a[i - 1][y] - a[i][y]) > threshold) {
                            continue next; // 不合法，下一个
                        }
                    }

                    // 合法，计算 3x3 子网格的平均值
                    int avg = 0;
                    for (int x = i - 2; x <= i; x++) {
                        for (int y = j - 2; y <= j; y++) {
                            avg += a[x][y];
                        }
                    }
                    avg /= 9;

                    // 更新 3x3 子网格内的 result
                    for (int x = i - 2; x <= i; x++) {
                        for (int y = j - 2; y <= j; y++) {
                            result[x][y] += avg; // 先累加，最后再求平均值
                            cnt[x][y]++;
                        }
                    }
                }
            }

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (cnt[i][j] == 0) { // (i,j) 不属于任何子网格
                        result[i][j] = a[i][j];
                    } else {
                        result[i][j] /= cnt[i][j]; // 求平均值
                    }
                }
            }
            return result;
        }
    }

    /**
     * 遍历所有  3×3 的子网格。
     * 遍历网格内的所有左右相邻格子和上下相邻格子，如果存在差值超过  threshold 的情况，则枚举下一个子网格。
     * 如果合法，计算子网格的平均值  avg，等于子网格的元素和除以  9下取整。
     * 更新子网格内的result[i][j]，由于需要计算平均值，我们先把  avg加到result[i][j] 中，同时用一个  cnt 矩阵统计(i,j) 在多少个合法子网格内。
     * 最后返回答案。
     */
}
