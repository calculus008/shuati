package Interviews.Karat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Rectangles_In_Matrix {
    /**
     * 1.给一个矩阵，矩阵里的每个元素是1，但是其中分布着一些长方形区域， 这些长方形区域中的元素为0.
     * 要求输出每个长方形的位置（用长方形的左上角元素坐标和右下角元素坐标表示, 输⼊入⼀一定有效，
     * 保证有⼀一个满⾜足要求的矩形）。
     * <p>
     * example：
     * input:
     * [
     * [1,1,1,1,1,1],
     * [0,0,1,0,1,1],
     * [0,0,1,0,1,0],
     * [1,1,1,0,1,0],
     * [1,0,0,1,1,1]
     * ]
     * output:
     * [
     * [1,0,2,1],
     * [1,3,3,3],
     * [2,5,3,5],
     * [4,1,4,2]
     * ]
     *
     * 2.如果 Matrix 中有多个由0组成的长方体，请返回多套值（前提每两个长方体之间是不会连接的，所以放心）. 不改变输入的做法
     *
     * 3.不过还有第三问，就是connected components
     * 第三问 基本上就是leetcode connected components,只不过是返回一个list of lists，每个list是一个component的所有点坐标
     * 那个图是1,0组成的矩阵，0组成的就是各种图形。
     *
     * 跟前面关系的确不大,如果矩阵里有多个不规则的形状，返回这些形状。这里需要自己思考并定义何谓“返回这些形状”
     */

    static int[] rectangle1(int[][] matrix) {
        int[] res = new int[4];
        int row = matrix.length, col = matrix[0].length;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == 0) {
                    int iRight = i, jDown = j;
                    while (iRight < row) {
                        if (matrix[iRight][j] == 0) {
                            iRight++;
                        } else {
                            break;
                        }
                    }

                    while (jDown < col) {
                        if (matrix[i][jDown] == 0) {
                            jDown++;
                        } else {
                            break;
                        }
                    }

                    return new int[]{i, j, iRight - 1, jDown - 1};
                }
            }
        }

        return res;
    }

    static List<int[]> rectangleMulti(int[][] matrix) {
        List<int[]> res = new ArrayList<>();
        int row = matrix.length, col = matrix[0].length;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == 0) {
                    int iRight = i, jDown = j;
                    while (iRight < row) {
                        if (matrix[iRight][j] == 0) {
                            iRight++;
                        } else {
                            break;
                        }
                    }

                    while (jDown < col) {
                        if (matrix[i][jDown] == 0) {
                            jDown++;
                        } else {
                            break;
                        }
                    }

                    res.add(new int[]{i, j, iRight - 1, jDown - 1});

                    /**
                     * 这个是改变输入的算法，如果不改变输入，需要用另外一个
                     * visited[][] 记录已经访问过的单元。
                     */
                    fill(i, j, iRight - 1, jDown - 1, matrix);
                }
            }
        }
        printListArray("", res);
        return res;
    }

    static List<List<int[]>> irregular(int[][] matrix) {
        List<List<int[]>> res = new ArrayList<>();
        int row = matrix.length, col = matrix[0].length;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == 0) {
                    List<int[]> cur = new ArrayList<>();
                    dfs(matrix, i, j, row, col, cur);
                    res.add(cur);
                }
            }
        }
        for (List<int[]> r : res) {
            printListArray("", r);
        }
        return res;
    }

    static void dfs(int[][] matrix, int i, int j, int row, int col, List<int[]> cur) {
        int[][] dir = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        if (i < 0 || i >= row || j < 0 || j >= col || matrix[i][j] == 1) {
            return;
        }
        cur.add(new int[]{i, j});
        matrix[i][j] = 1;
        for (int[] k : dir) {
            int i2 = i + k[0], j2 = j + k[1];
            dfs(matrix, i2, j2, row, col, cur);
        }
    }

    static void fill(int i1, int j1, int i2, int j2, int[][] matrix) {
        for (int i = i1; i <= i2; i++) {
            for (int j = j1; j <= j2; j++) {
                matrix[i][j] = 1;
            }
        }
    }

    static void printArray(String s, int[] array) {
        System.out.println(s);
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    static void printSet(String s, Set<String> set) {
        System.out.println(s);
        for (String i : set) {
            System.out.println(i + " ");
        }
        System.out.println();
    }

    static void printList(String s, List<String> list) {
        System.out.println(s);
        for (String i : list) {
            System.out.println(i + " --> ");
        }
        System.out.println();
    }

    static void printListArray(String s, List<int[]> list) {
        System.out.println(s);
        for (int[] i : list) {
            printArray("", i);
        }
        System.out.println();
    }

    static void printListInt(String s, List<Integer> list) {
        System.out.println(s);
        for (Integer i : list) {
            System.out.println(i + " --> ");
        }
        System.out.println();
    }

    static void printMap(String s, Map<String, List<Integer>> map) {
        System.out.println(s);
        for (String ss : map.keySet()) {
            printListInt(ss, map.get(ss));
        }
        System.out.println();
    }

    // Driver Code
    public static void main(String args[]) {
        int[][] matrix = new int[][]{
                {1, 1, 1, 1, 1, 1},
                {0, 0, 1, 0, 1, 1},
                {0, 0, 1, 0, 1, 0},
                {1, 1, 1, 0, 1, 0},
                {1, 0, 0, 1, 1, 1}
        };

        int[][] matrix2 = new int[][]{
                {1, 1, 1, 1, 1, 1},
                {0, 0, 1, 0, 0, 1},
                {0, 0, 1, 0, 1, 0},
                {0, 1, 0, 0, 1, 0},
                {1, 0, 0, 1, 0, 1}
        };

        printArray("only one ", rectangle1(matrix));
        rectangleMulti(matrix);
        irregular(matrix2);
    }
}
