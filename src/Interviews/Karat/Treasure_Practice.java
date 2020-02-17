package Interviews.Karat;

import java.util.*;

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

    /**
     * Question 1
     */
    public static List<int[]> nextMove(int[][] matrix, int x, int y) {
        List<int[]> res = new ArrayList<>();
        if (!isValid(matrix, x, y)) return res;

        int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : dirs) {
            int nx = x + dir[0];
            int ny = y + dir[1];

            if (isValid(matrix, nx, ny)) {
                res.add(new int[]{nx, ny});
            }
        }

        return res;
    }

    public static boolean isValid(int[][] matrix, int x, int y) {
        if (x < 0 || x >= matrix.length || y < 0 || y >= matrix[0].length) return false;
        if (matrix[x][y] == -1) return false;
        return true;
    }

    /**
     * Question 2
     * Time  : O(mn)
     * Space : O(mn)
     *
     * Walk from destination to other 0 value cell, then check if
     * there's any cell is not reachable.
     */
    public static boolean isReachable(int[][] board, int x, int y) {
        if (!isValid(board, x, y)) return false;

        int m = board.length;
        int n = board[0].length;

        int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{x, y});
        boolean[][] visited = new boolean[m][n];
        visited[x][y] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            for (int[] dir : dirs) {
                int nx = cur[0] + dir[0];
                int ny = cur[1] + dir[1];

                if (!isValid(board, nx, ny)) continue;

                /**
                 * !!!
                 */
                if (visited[nx][ny]) continue;

                q.offer(new int[]{nx, ny});
                visited[nx][ny] = true;
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n ; j++) {
                if (board[i][j] == 0 && !visited[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Question 3
     * Time : O(2 ^ (mn)) ??
     * Space : O(mn)
     *
     * LE_980_Unique_Paths_III
     *
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

        if (x == end[0] && y == end[1]) {
            if (count == 0) {
                all.add(new ArrayList<>(temp));
            }

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
        if (matrix[x][y] == 1) count++;
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
                {0, 0, 0, 0},
                {0, -1, 0, 0}
        };

        int[][] matrix2 = new int[][]{
                {0,  0,  0, 0, 0},
                {0, -1, -1, 0, 0},
                {-1, -1,  0, 0, 0},
                {-1, 0,  0, 0, 0},
                {0,  0, -1, 0, 0},
                {0,  0,  0, 0, 0 },
        };

        int[][] matrix3 = new int[][]{
                {0,  -1,  0, 0, 0},
                {0, -1, -1, 0, 0},
                {-1, -1,  0, 0, 0},
                {-1, 0,  0, 0, 0},
                {0,  -1, -1, 0, 0},
                {0,  -1,  0, 0, 0 },
        };

        int[][] matrix1 = new int[][]{
                    {1,  0,  0, 0, 0},
                    {0, -1, -1, 0, 0},
                    {1, -1,  0, 1, 0},
                    {-1, 0,  0, 0, 0},
                    {0,  1, -1, 0, 0},
                    {0,  0,  0, 0, 0 },
                };

        printList(nextMove(matrix, 0, 3));
        printList(nextMove(matrix, 1, 1));
//        printList(treasurePath(matrix, new int[]{0, 0}, new int[]{2, 3}));

        System.out.println(isReachable(matrix2, 2,4));
        System.out.println(isReachable(matrix3, 1,1));
        System.out.println(isReachable(matrix3, 4,0));
        /**
         * [(5, 1), (4, 1), (3, 1), (3, 2), (2, 2), (2, 3), (1, 3), (0, 3), (0, 2),
         *  (0, 1), (0, 0), (1, 0), (2, 0)]
         */
        printList(treasurePath(matrix1, new int[]{5, 1}, new int[]{2, 0}));
    }
}
