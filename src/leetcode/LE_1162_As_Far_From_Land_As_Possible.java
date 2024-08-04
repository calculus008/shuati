package leetcode;

import java.util.*;

public class LE_1162_As_Far_From_Land_As_Possible {
    /**
     * Given an n x n grid containing only values 0 and 1, where 0 represents water and 1 represents land, find a water
     * cell such that its distance to the nearest land cell is maximized, and return the distance. If no land or water
     * exists in the grid, return -1.
     *
     * The distance used in this problem is the Manhattan distance: the distance between two cells (x0, y0) and (x1, y1)
     * is |x0 - x1| + |y0 - y1|.
     *
     * Example 1:
     * Input: grid = [[1,0,1],[0,0,0],[1,0,1]]
     * Output: 2
     * Explanation: The cell (1, 1) is as far as possible from all the land with distance 2.
     *
     * Example 2:
     * Input: grid = [[1,0,0],[0,0,0],[0,0,0]]
     * Output: 4
     * Explanation: The cell (2, 2) is as far as possible from all the land with distance 4.
     *
     * Constraints:
     * n == grid.length
     * n == grid[i].length
     * 1 <= n <= 100
     * grid[i][j] is 0 or 1
     *
     * Medium
     *
     * https://leetcode.com/problems/as-far-from-land-as-possible/
     */

    /**
     * BFS
     *
     * "find a water cell('0') such that its distance to the nearest land cell('1') is maximized"
     * 就是说，从水中的一个点到陆地的最远距离。这意味着，所走的"路"是"水"上的，只要一粘陆地，就算到了。
     *
     * 所以，把该问题转换成为"多点集合为出发点的BFS"。从陆地出发，把所有陆地放入BFS的Queue中作为出发状态，BFS可以想象为陆地一层层往外扩展，
     * 直到没有水为止，看一共走了多少部。例如：
     *
     * In example 1, 初始：
     * steps = 0，  q = {{0,0}, {0,2}, {2,0}, {2,2}}
     * 1 0 1
     * 0 0 0
     * 1 0 1
     *
     * steps = 1    q = {{0,1}, {1,0}, {1,2}, {2,1}}
     * 1 1 1
     * 1 0 1
     * 1 1 1
     *
     * steps = 2    q = {{1,1}}
     * 1 1 1
     * 1 1 1
     * 1 1 1
     *
     * 注意：当达到这一步时， 尽管已经没有水了，但是，Queue里仍然有一个元素， 就是最后一个变成水的元素坐标，{1, 1}, 所以，BFS还会再走一步，
     *      尽管grid这时已经不会有变化了。
     *
     * steps = 3    q = {}
     * 1 1 1
     * 1 1 1
     * 1 1 1
     *
     * 然后，Queue才变为空，while循环结束。
     *
     * 所以，应该返回 steps - 1
     *
     * 题目要求用"Manhattan distance"，实际上是为了要求陆地扩展的方向只能是上下左右，不能走对角。
     *
     * 所以，BFS不能只和"shortest path"关联，这里反而用它来找max distance (走的最多的步数或距离).
     *
     * Time:  O(n * n). We process an individual cell only once (or twice).
     * Space: O(n) for the queue.
     */

    class Solution1 {
        public int maxDistance(int[][] grid) {
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            Queue<int[]> q = new LinkedList<>();
            int n = grid.length;
            int steps = 0;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        q.offer(new int[]{i, j});
                    }
                }
            }

            if (q.size() == 0 || q.size() == n * n) return -1;

            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int[] cur = q.poll();

                    for (int[] dir : dirs) {
                        int x = cur[0] + dir[0];
                        int y = cur[1] + dir[1];

                        if (x < 0 || y < 0 || x >= n || y >= n || grid[x][y] == 1) continue;

                        grid[x][y] = 1;
                        q.offer(new int[]{x, y});

                    }
                }
                steps++;
            }

            return steps - 1;
        }
    }

    /**
     * Same algorithm as Solution1, use a separate visited array instead of changing values in the input array.
     */
    class Solution2 {
        public int maxDistance(int[][] grid) {
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            Queue<int[]> q = new LinkedList<>();
            int n = grid.length;
            int res = 0;

            boolean[][] visited = new boolean[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        q.offer(new int[]{i, j});
                        /**
                         * !!!
                         */
                        visited[i][j] = true;
                    }
                }
            }

            if (q.size() == 0 || q.size() == n * n) return -1;


            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int[] cur = q.poll();

                    for (int[] dir : dirs) {
                        int x = cur[0] + dir[0];
                        int y = cur[1] + dir[1];

                        /**
                         * !!!
                         */
                        if (x < 0 || y < 0 || x >= n || y >= n || visited[x][y]) continue; //!!!

                        /**
                         * !!!
                         */
                        visited[x][y] = true; //!!!
                        q.offer(new int[]{x, y});

                    }
                }
                res++;
            }

            return res - 1;
        }
    }
}
