package Interviews.Karat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rectangles_In_Matrix_Practice {
    public static int[] rectangle1(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    int k = i;
                    while (k < m && matrix[k][j] == 0) {
                        k++;
                    }
                    k--;

                    int l = j;
                    while (l < n && matrix[i][l] == 0) {
                        l++;
                    }
                    l--;

                    return new int[]{i, j, k, l};
                }
            }
        }

        return new int[]{};
    }

    public static List<int[]> rectangle2(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        /**
         * it requires the input should not be modified,
         * so we need another 2D array to track which
         * cells are already counted as rectangles in
         * previous rounds
         */
        int[][] visited = new int[m][n];
        List<int[]> res = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                /**
                 * !!!
                 * Check any cell with 0 value in matrix[][]
                 * With visited[][], if it is 0, means it is not counted as
                 * part of rectangle in any of the previous rounds.
                 */
                if (matrix[i][j] == 0 && visited[i][j] == 0) {
                    int k = i;
                    while (k < m && matrix[k][j] == 0 && visited[k][j] == 0) {
                        /**
                         * !!!
                         * Can't just change values in visited[][] here since
                         * we only iterate part of the area. Must use fill()
                         * to fill the full area with 1 in visited[][]
                         */
//                        visited[k][j] = 1;
                        k++;
                    }
                    k--;

                    int l = j;
                    while (l < n && matrix[i][l] == 0 && visited[i][l] == 0) {
                        l++;
                    }
                    l--;

                    /**
                     * !!!
                     */
                    mark(visited, i, j, k, l);
                    res.add(new int[]{i, j, k, l});

//                    System.out.println(i + "," + j + "," + k + "," + l);
//                    System.out.println(Arrays.deepToString(visited));
                }
            }
        }

        return res;
    }

    public static void mark(int[][] visited, int i, int j, int k, int l) {
        for (int a = i; a <= k; a++) {
            for (int b = j; b <= l; b++) {
                visited[a][b] = 1;
            }
        }
    }

    public static List<List<int[]>> irregular(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] visited = new int[m][n];
        List<List<int[]>> res = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0 && visited[i][j] == 0) {
                    List<int[]> cur = new ArrayList<>();
                    dfs(matrix, visited, cur, i, j);
                    if (cur.size() > 0) {
                        res.add(cur);
                    }
                }
            }
        }

        return res;
    }



    public static void dfs(int[][] matrix, int[][] visited, List<int[]> cur, int x, int y) {
        if (x < 0 || x >= matrix.length || y < 0 || y >= matrix[0].length) return;

        if (matrix[x][y] == 1 || visited[x][y] == 1) return;

        cur.add(new int[]{x, y});

        visited[x][y] = 1;
        dfs(matrix, visited, cur, x + 1, y);
        dfs(matrix, visited, cur, x - 1, y);
        dfs(matrix, visited, cur, x, y + 1);
        dfs(matrix, visited, cur, x, y - 1);
        /**
         * !!!
         * No need to do backtrack, just dfs and mark all the cells visited
         */
    }

    private static void printArray(String title, int[] arr) {
        System.out.println(title);
        System.out.println(Arrays.toString(arr));
    }


    private static void printList(String title, List<int[]> list) {
        System.out.println(title);
        for (int[] e : list) {
            System.out.println(Arrays.toString(e));
        }
    }

    private static void printListOfList(String title, List<List<int[]>> lists) {
        System.out.println(title);
        for (List<int[]> list : lists) {
            System.out.println("--shape---");
            for (int[] e : list) {
                System.out.println(Arrays.toString(e));
            }
            System.out.println("------");
        }
    }

    public static void main(String args[]) {
        int[][] matrix = new int[][]{
                {1, 1, 1, 1, 1, 1},
                {0, 0, 1, 0, 1, 1},
                {0, 0, 1, 0, 1, 0},
                {1, 1, 1, 0, 1, 0},
                {1, 0, 0, 1, 1, 1}
        };

        int[][] matrix3 = new int[][]{
                {0, 1, 1, 1, 1, 1},
                {0, 1, 1, 0, 1, 1},
                {0, 1, 1, 0, 1, 0},
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

        /**
         * 1 0 2 1
         *
         * 1 3 3 3
         *
         * 2 5 3 5
         *
         * 4 1 4 2
         */
        printList("Multiple ", rectangle2(matrix));

        printListOfList("irregular", irregular(matrix2));
    }


}
