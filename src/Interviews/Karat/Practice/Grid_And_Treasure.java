package Interviews.Karat.Practice;

import java.util.*;

/**
 * Improve speed
 *
 * 1.Each function has to be "static"
 * 2.
 */
public class Grid_And_Treasure {
    private static boolean isValid(int[][] grid, int x, int y) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] == -1) {
            return false;
        }
        return true;
    }

    public static List<int[]> next(int[][] grid, int x, int y) {
        List<int[]> res = new ArrayList<>();
        if (!isValid(grid, x, y)) return res;

        int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : dirs) {
            /**
             * !!!
             * nx, ny!!!
             */
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (isValid(grid, nx, ny)) {
                res.add(new int[]{nx, ny});
            }
        }

        return res;
    }


    public static void printRes(String title, List<int[]> list) {
        if (list.size() == 0) {
            System.out.println("impossible to move");
        } else {
            for (int[] elem : list) {
                System.out.println(Arrays.toString(elem));
            }
        }
    }

    public static boolean isReachable(int[][] grid, int x, int y) {
        /**
         * !!!
         */
        if (grid[x][y] == -1) return false;

        int m = grid.length;
        int n = grid[0].length;

        boolean[][] visited = new boolean[m][n];
        int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{x, y});

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            /**
             * !!!
             */
            visited[cur[0]][cur[1]] = true;

            for (int[] dir : dirs) {
                int nx = cur[0] + dir[0];
                int ny = cur[1] + dir[1];

                if (isValid(grid, nx, ny) && !visited[nx][ny]) {//!!!
                    q.offer(new int[]{nx, ny});
                }
            }
        }

        for (int i = 0; i < m ;i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0 && !visited[i][j]) {//!!!
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * make those global
     */
    static List<int[]> res;
    static int minSize;
    public static List<int[]> treasurePath(int[][] grid, int x1, int y1, int x2, int y2) {
        res = new ArrayList<>();
        minSize = Integer.MAX_VALUE;

        if (!isValid(grid, x1, y1) || !isValid(grid, x2, y2)) return res;

        int m = grid.length;
        int n = grid[0].length;

        boolean[][] visited = new boolean[m][n];

        /**
         * First count number of treasures
         */
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) count++;
            }
        }

        dfs(grid, new ArrayList<>(), visited, count, x1, y1, x2, y2);

        return res;

    }

    private static void dfs(int[][] grid, List<int[]> temp, boolean[][] visited, int count, int x, int y, int x2, int y2) {
        if (!isValid(grid, x, y)) return;
        if (visited[x][y]) return;

        if (grid[x][y] == 1) count--;
        visited[x][y] = true;
        temp.add(new int[]{x, y});

        if (x == x2 && y == y2) {
            if (count == 0) {
                /**
                 * !!!
                 */
                if (temp.size() < minSize) {
                    res = new ArrayList<>(temp);
                    minSize = res.size();
                }
            }

            /**
             * !!!
             */
            visited[x][y] = false;
            temp.remove(temp.size() - 1);

            return;
        }

        dfs(grid, temp, visited, count, x + 1, y, x2, y2);
        dfs(grid, temp, visited, count, x - 1, y, x2, y2);
        dfs(grid, temp, visited, count, x, y - 1, x2, y2);
        dfs(grid, temp, visited, count, x, y + 1, x2, y2);

        visited[x][y] = false;
        temp.remove(temp.size() - 1);
    }

    private static void printTreasurePath(List<int[]> path) {
        StringBuilder sb = new StringBuilder();
        for (int[] elem : path) {
            sb.append(Arrays.toString(elem)).append("->");
        }

        sb.setLength(sb.length() - 2);

        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {0, -1, -1, 0},
                {0,  0,  0, -1},
                {0, -1,  0, 0}
        };

        printRes("---next----", next(matrix, 0, 3));

        int[][] matrix2 = new int[][]{
                {0,  0,  0, 0, 0},
                {0, -1, -1, 0, 0},
                {-1, -1,  0, 0, 0},
                {-1, 0,  0, 0, 0},
                {0,  0, -1, 0, 0},
                {0,  0,  0, 0, 0 },
        };

        System.out.println(isReachable(matrix2, 2, 4));
        System.out.println(isReachable(matrix2, 1, 1));

        int[][] matrix3 = new int[][]{
                {0,  -1,  0, 0, 0},
                {0, -1, -1, 0, 0},
                {-1, -1,  0, 0, 0},
                {-1, 0,  0, 0, 0},
                {0,  -1, -1, 0, 0},
                {0,  -1,  0, 0, 0 },
        };

        System.out.println(isReachable(matrix3, 1, 3));

        int[][] matrix1 = new int[][]{
                {1,  0,  0, 0, 0},
                {0, -1, -1, 0, 0},
                {1, -1,  0, 1, 0},
                {-1, 0,  0, 0, 0},
                {0,  1, -1, 0, 0},
                {0,  0,  0, 0, 0 },
        };

        printTreasurePath(treasurePath(matrix1, 5, 1, 2, 0));
    }

}
