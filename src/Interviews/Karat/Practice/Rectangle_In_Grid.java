package Interviews.Karat.Practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rectangle_In_Grid {
    public static int[] getRectangle(int[][] grid) {
        if (grid == null || grid.length == 0) return new int[]{};

        int m = grid.length;
        int n = grid[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    int k = i;
                    while (k < m && grid[k][j] == 0) {
                        k++;
                    }
                    k--;

                    int l = j;
                    while (l < n && grid[i][l] == 0) {
                        l++;
                    }
                    l--;

                    return new int[]{i, j, k, l};
                }
            }
        }

        return new int[]{};
    }

    public static List<int[]> getRectangles(int[][] grid) {
        List<int[]> res = new ArrayList<>();
        if (grid == null || grid.length == 0) return res;

        int m = grid.length;
        int n = grid[0].length;

        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                /**
                 * !!!
                 */
                if (grid[i][j] == 0 && !visited[i][j]) {
                    int k = i;
                    while (k < m && grid[k][j] == 0) {
                        k++;
                    }
                    k--;

                    int l = j;
                    while (l < n && grid[i][l] == 0) {
                        l++;
                    }
                    l--;

                    res.add(new int[]{i, j, k, l});
                    mark(visited, i, j, k, l);
                }
            }
        }

        return res;
    }

    public static void mark(boolean[][] visited, int x, int y, int k, int l) {
        for (int i = x; i <= k; i++) {
            for (int j = y; j <= l; j++) {
                visited[i][j] = true;
            }
        }
    }


    public static List<List<int[]>> getShapes(int[][] grid) {
        List<List<int[]>> res = new ArrayList<>();
        if (grid == null || grid.length == 0) return res;

        int m = grid.length;
        int n = grid[0].length;

        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0 && !visited[i][j]) {
                    List<int[]> cur = new ArrayList<>();
                    dfs(grid, visited, cur, i, j);

                    if (cur.size() > 0) {
                        res.add(cur);
                    }
                }
            }
        }

        return res;
    }

    public static void dfs(int[][] grid, boolean[][] visited, List<int[]> temp, int x, int y) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] != 0 || visited[x][y]) return;

        temp.add(new int[]{x, y});
        visited[x][y] = true;

        dfs(grid, visited, temp, x + 1, y);
        dfs(grid, visited, temp, x - 1, y);
        dfs(grid, visited, temp, x, y + 1);
        dfs(grid, visited, temp, x, y - 1);

        /**
         * !!!
         * Just like number of islands.
         * No need to do backtrack, just dfs and mark all the cells visited
         */
    }

    public static void printRes(String title, List<int[]> l) {
        System.out.println(title);
        for (int[] elem : l) {
            System.out.println(Arrays.toString(elem));
        }
    }

    public static void printShapes(String title, List<List<int[]>> shapes) {
        System.out.println(title);
        for (List<int[]> shape : shapes) {
            StringBuilder sb = new StringBuilder();
            for (int[] elem : shape) {
                sb.append(Arrays.toString(elem)).append(",");
            }
            sb.setLength(sb.length() - 1);
            System.out.println(sb.toString());
        }
    }

    public static void main(String[] args) {
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

        int[][] matrix3 = new int[][]{
                {0, 1, 1, 1, 1, 1},
                {0, 1, 1, 0, 1, 1},
                {0, 1, 1, 0, 1, 0},
                {1, 1, 1, 0, 1, 0},
                {1, 0, 0, 1, 1, 1}
        };

        int[] res = getRectangle(matrix);
        System.out.println(Arrays.toString(res));

        printRes("===Mutiple Rectangles===", getRectangles(matrix));

        printRes("===Mutiple Rectangles===", getRectangles(matrix3));

        printShapes("===Shapes===", getShapes(matrix2));


    }
}
