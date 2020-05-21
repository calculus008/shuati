package Interviews.Nextdoor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

public class Find_Min_In_Matrix {
    /**
     * 给int n, m，想象n*m的矩阵M, M[i,j] = (i+1)*(j+1)，0-ba​​​​​​​​​​​​​​​​​​​sed 一系列query，有三种类型，
     * 第一 种是查询矩阵中最小的元素，第二、三分别是禁用某一行、列。
     *
     * 给一个matrix， 一个整数数组， matrix只有三列，[l,r,target], ​​​​​​​​​​​​​​​​​​​要求在整数数组中寻找下标从l 到 r
     * 中包含几个target，找到+1，最后输出个数总和。
     */
    public static void peek(int[][] matrix) {
        for (int[] row : matrix) {
            for (int num : row) {
                System.out.print(num + "\t");
            }
            System.out.println("");
        }
    }

    public static List<Integer> find_min_in_matrix(int m, int n, int[][] query) {
        int[][] matrix = new int[m][n];

        /**
         * Use TreeMap with 2 purposes:
         * 1.Sort value in matrix so that we can find min value in O(1)
         * 2.Values in treemap is frequency of the key, so we can disable values as input suggests
         */
        TreeMap<Integer, Integer> map = new TreeMap();
        HashSet<Integer> removed_row = new HashSet();
        HashSet<Integer> removed_col = new HashSet();
        List<Integer> result = new ArrayList();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = (i + 1) * (j + 1);
                map.put(matrix[i][j], map.getOrDefault(matrix[i][j], 0) + 1);
            }
        }

        //peek(matrix);
        for (int[] q : query) {
            if (q[0] == 0) {
                // case: get min element from array
                result.add(map.firstKey());
            } else if (q[0] == 1) {
                // case: remove row if element
                int r = q[1];
                for (int i = 0; i < n; i++) {
//                    if (removed_col.contains(i)) {//??
//                        continue;
//                    }
                    int val = matrix[r][i];
                    if (map.get(val) == 1) {
                        map.remove(val);
                    } else {
                        map.put(val, map.get(val) - 1);
                    }
                }
            } else {
                // case: remove col
                int c = q[1];
                for (int i = 0; i < m; i++) {
//                    if (removed_row.contains(i)) {
//                        continue;
//                    }
                    int val = matrix[i][c];
                    if (map.get(val) == 1) {
                        map.remove(val);
                    } else {
                        map.put(val, map.get(val) - 1);
                    }
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        List<Integer> result = find_min_in_matrix(3, 3, null);
        System.out.println("Hello World!");
    }
}
