package Interviews.Karat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Treasure_Practice {
    /**
     * Given a start point and an end point, shortest path seems to suggest to use BFS.
     * But here we are not checking shortest path from start to end, we need to find the
     * shortest path among all paths that go from start to end and pass all treasure
     * points.
     *
     * Also BFS is to find the number of shortest path, here, we need to record each point
     * along the path, therefore BFS won't work here.
     *
     * So the solution is to do DFS to get all valid paths, then find the shortest one.
     */
    public static List<int[]> treasurePath(int[][] matrix, int[] start, int[] end) {
        List<int[]> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return res;

        int m = matrix.length;
        int n = matrix[0].length;
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    count++;
                }
            }
        }

        if (count == 0) return res;

        List<List<int[]>> all = new ArrayList<>();
        List<int[]> temp = new ArrayList<>();

        dfs(matrix, new boolean[m][n], end, all, temp, count, start[0], start[1]);

        int min = Integer.MAX_VALUE;
        for (List<int[]> list : all) {
            printList(list);
            if (list.size() < min) {
                min = list.size();
                res = list;
            }
        }

        return res;
    }

    private static int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static void dfs(int[][] matrix, boolean[][] visited, int[] end, List<List<int[]>> all, List<int[]> temp, int count, int x, int y) {
        if (x < 0 || x  >= matrix.length || y < 0 || y >= matrix[0].length) return;

        if (visited[x][y] || matrix[x][y] == -1) return;

        if (matrix[x][y] == 1) count--;
        visited[x][y] = true;
        temp.add(new int[]{x, y});

        if (x == end[0] && y == end[1] && count == 0) {
            all.add(new ArrayList<>(temp));

            /**
             * !!!
             * Need to remove end point here, because
             * we reach the end, will NOT move any further
             * and return.
             *
             * Since we don't move any further, count will
             * no longer be passed into next level of recursion,
             * therefore no need to set it back if the end point
             * is also 1
             */
            temp.remove(temp.size() - 1);
            visited[x][y] = false;

            return;
        }

        for (int i = 0; i < dirs.length; i++) {
            int nx = x + dirs[i][0];
            int ny = y + dirs[i][1];

            dfs(matrix, visited, end, all, temp, count, nx, ny);
        }


        visited[x][y] = false;
//        if (matrix[x][y] == 1) count++;
        temp.remove(temp.size() - 1);
    }

    public static void printList(List<int[]> path) {
        StringBuilder sb = new StringBuilder();
        for (int[] point : path) {
            sb.append(Arrays.toString(point)).append("->");
        }

        System.out.println(sb.toString());
    }

    public static void main(String args[]) {
        int[][] matrix = new int[][]{
                {0, -1, -1, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        };

        int[][] matrix1 = new int[][]{
                    {1,  0,  0, 0, 0},
                    {0, -1, -1, 0, 0},
                    {1, -1,  0, 1, 0},
                    {-1, 0,  0, 0, 0},
                    {0,  1, -1, 0, 0},
                    {0,  0,  0, 0, 0 },
                };

//        printList(treasurePath(matrix, new int[]{0, 0}, new int[]{2, 3}));

        /**
         * [(5, 1), (4, 1), (3, 1), (3, 2), (2, 2), (2, 3), (1, 3), (0, 3), (0, 2),
         *  (0, 1), (0, 0), (1, 0), (2, 0)]
         */
        printList(treasurePath(matrix1, new int[]{5, 1}, new int[]{2, 0}));
    }
}
