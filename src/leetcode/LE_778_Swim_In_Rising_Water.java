package leetcode;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class LE_778_Swim_In_Rising_Water {
    /**
     * On an N x N grid, each square grid[i][j] represents the elevation at that point (i,j).
     *
     * Now rain starts to fall. At time t, the depth of the water everywhere is t. You can swim
     * from a square to another 4-directionally adjacent square if and only if the elevation of
     * both squares individually are at most t. You can swim infinite distance in zero time.
     * Of course, you must stay within the boundaries of the grid during your swim.
     *
     * You start at the top left square (0, 0). What is the least time until you can reach the
     * bottom right square (N-1, N-1)?
     *
     * Example 1:
     * Input: [[0,2],[1,3]]
     * Output: 3
     * Explanation:
     * At time 0, you are in grid location (0, 0).
     * You cannot go anywhere else because 4-directionally adjacent neighbors have a higher
     * elevation than t = 0.
     *
     * You cannot reach point (1, 1) until time 3.
     * When the depth of water is 3, we can swim anywhere inside the grid.
     *
     *
     * Example 2:
     * Input: [[0,1,2,3,4],[24,23,22,21,5],[12,13,14,15,16],[11,17,18,19,20],[10,9,8,7,6]]
     * Output: 16
     * Explanation:
     *  0  1  2  3  4
     * 24 23 22 21  5
     * 12 13 14 15 16
     * 11 17 18 19 20
     * 10  9  8  7  6
     *
     * The final route is marked in bold.
     * We need to wait until time 16 so that (0, 0) and (4, 4) are connected.
     * Note:
     *
     * 2 <= N <= 50.
     * grid[i][j] is a permutation of [0, ..., N*N - 1].
     *
     * Hard
     */

    /**
     * https://zxi.mytechroad.com/blog/heap/leetcode-778-swim-in-rising-water/
     *
     * Dijstra variation : PriorityQueue
     *
     * Translation of the question : For all paths that starting from [0, 0] and ending at [n - 1, n - 1],
     * find the smallest of the max value in each path.
     *
     * !!!
     * Let's keep a priority queue of which square we can walk in next. We always walk in the smallest one
     * that is 4-directionally adjacent to ones we've visited.
     *
     * When we reach the target, the largest number we've visited so far is the answer.
     *
     * Time complexity: O(n^2*log(n^2)) -> O(n^2*logn)
     * Space complexity: O(n^2)
     */

    class Solution1 {
        public int swimInWater(int[][] grid) {
            int n = grid.length;
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
            boolean[][] visited = new boolean[n][n];
            pq.offer(new int[]{0, 0, grid[0][0]});
            visited[0][0] = true;//!!!

            int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

            /**
             * pq to pick the square that has the smallest value, which we can swim to
             */
            while (!pq.isEmpty()) {
                int[] cur = pq.poll();//poll
                int x = cur[0];
                int y = cur[1];
                int val = cur[2];

                for (int[] dir : dirs) {
                    int nx = x + dir[0];
                    int ny = y + dir[1];

                    if (nx < 0 || nx >= n || ny < 0 || ny >= n || visited[nx][ny]) continue;

                    int max = Math.max(val, grid[nx][ny]);
                    if (nx == n - 1 && ny == n - 1) return max;
                    /**
                     * !!!
                     * 实际上是记住{nx, ny}所在路径上的最大值
                     */
                    pq.offer(new int[]{nx, ny, max});
                    visited[nx][ny] = true;
                }
            }

            return -1;
        }
    }

    /**
     * Binary Search + DFS
     * https://leetcode.com/problems/swim-in-rising-water/discuss/113765/PythonC%2B%2B-Binary-Search
     * https://leetcode.com/problems/swim-in-rising-water/solution/
     * Approach 2
     */
    private int[][] dirs = {{0, -1}, {0, 1}, {1, 0}, {-1, 0}};

    public int swimInWater4(int[][] grid) {

        int N = grid.length;

        int lo = 0;
        int hi = N * N - 1;
        while (lo < hi) {
            int middle = (lo + hi) >>> 1;
            boolean[][] visited = new boolean[N][N];

            if (hasPath(grid, 0, 0, middle, visited)) {
                hi = middle;
            } else {
                lo = middle + 1;
            }
        }

        return lo;
    }

    private boolean hasPath(int[][] grid, int i, int j, int time, boolean[][] visited) {
        int N = grid.length;
        if (i == N - 1 && j == N - 1) return true;
        visited[i][j] = true;

        for (int[] dir : dirs) {
            int newI = i + dir[0];
            int newJ = j + dir[1];
            if (newI >= 0 && newJ >= 0 && newI < N && newJ < N && !visited[newI][newJ] && grid[i][j] <= time && grid[newI][newJ] <= time) {
                if (hasPath(grid, newI, newJ, time, visited)) return true;
            }
        }

        return false;
    }
}
