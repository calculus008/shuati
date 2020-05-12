package Interviews.Amazon;

import java.util.LinkedList;
import java.util.Queue;

public class Knight_Move {
    /**
     * Similar to LE_688_Knight_Probability_In_Chessboard
     * 给一个老将的位置，问走几步老马吃老将
     */

    public static int knightMove(int N, int x1, int y1, int x2, int y2) {
        if (!isValid(N, x1, y1) || !isValid(N, x2, y2)) return -1;
        if (x1 == x2 && y1 == y2) return 0;

        int[][] dirs = new int[][] {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {-1, 2}, {1, -2}, {-1, -2}};

        boolean[][] visited = new boolean[N][N];
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{x1, y1});
        visited[x1][y1] = true;
        int steps = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            steps++;

            for (int i = 0; i < size; i++) {
                int[] cur = q.poll();
                int x = cur[0];
                int y = cur[1];

                for (int[] d : dirs) {
                    int nx = x + d[0];
                    int ny = y + d[1];

                    if (nx < 0 || nx >= N || ny < 0 || ny >= N || visited[nx][ny]) continue;

                    if (x == x2 && y == y2) return steps;

                    visited[nx][ny] = true;
                    q.offer(new int[]{nx, ny});
                }
            }

        }

        return -1;
    }

    private static boolean isValid(int N, int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N;
    }

    public static void main(String[] args) {
        System.out.println(knightMove(6, 0, 0, 3, 1));
    }
}
