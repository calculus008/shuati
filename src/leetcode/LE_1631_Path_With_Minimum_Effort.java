package leetcode;

import java.util.*;

public class LE_1631_Path_With_Minimum_Effort {
    /**
     * You are a hiker preparing for an upcoming hike. You are given heights, a 2D array of size rows x columns, where
     * heights[row][col] represents the height of cell (row, col). You are situated in the top-left cell, (0, 0), and
     * you hope to travel to the bottom-right cell, (rows-1, columns-1) (i.e., 0-indexed). You can move up, down, left,
     * or right, and you wish to find a route that requires the minimum effort.
     *
     * A route's effort is the MAXIMUM absolute difference in heights between two consecutive cells of the route.
     *
     * Return the MINIMUM effort required to travel from the top-left cell to the bottom-right cell.
     *
     * Example 1:
     * Input: heights = [[1,2,2],[3,8,2],[5,3,5]]
     * Output: 2
     * Explanation: The route of [1,3,5,3,5] has a maximum absolute difference of 2 in consecutive cells.
     * This is better than the route of [1,2,2,2,5], where the maximum absolute difference is 3.
     *
     * Example 2:
     * Input: heights = [[1,2,3],[3,8,4],[5,3,5]]
     * Output: 1
     * Explanation: The route of [1,2,3,4,5] has a maximum absolute difference of 1 in consecutive cells, which is
     * better than route [1,3,5,3,5].
     *
     * Example 3:
     * Input: heights = [[1,2,1,1,1],[1,2,1,2,1],[1,2,1,2,1],[1,2,1,2,1],[1,1,1,2,1]]
     * Output: 0
     * Explanation: This route does not require any effort.
     *
     * Constraints:
     * rows == heights.length
     * columns == heights[i].length
     * 1 <= rows, columns <= 100
     * 1 <= heights[i][j] <= 10 ^ 6
     *
     * Medium
     */

    /**
     * PriorityQueue
     *
     * If we observe, this problem is to find the shortest path from a source cell (0, 0) to a destination cell (m-1, n-1).
     * Here, the shortest path is the one with minimum "distance", and "distance" is defined as maximum absolute difference
     * in heights between two consecutive cells of the path.
     *
     * Thus, we could use Dijikstra's algorithm which is used to find the shortest path in a WEIGHTED graph with a slight
     * modification of criteria for the shortest path, which costs O(E log V), where E is number of edges E = 4*M*N, V is
     * number of veritices V = M * N
     *
     * My understanding of why we can use Dijkstra here:
     * 1.here, the total path cost is the maximum absolute difference, its a different kind of cost function compared
     *   to the vanilla adding all costs together along the path
     * 2.for Dijkstra, edge weight cannot be negative, but what does that really mean. It effectively means total path
     *   cost cannot go down when a new edge joins the path. In the vanilla Dijkstra, it's in the form of negative edge
     *   weight. If we translate that to this problem, we can see that the total path cost (maximum absolute difference)
     *   will never go down when a new edge joins the path
     *
     * Complexity:
     * Time: O(E * logV) = O(M * N * log(M * N)), where M is the number of rows and N is the number of columns in the matrix.
     * Space: O(M * N)
     */
    class Solution1 {
        public int minimumEffortPath(int[][] heights) {
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            int m = heights.length;
            int n = heights[0].length;

            /**
             * Neat trick to use Integer[][], instead of int[][].
             * 1.Avoid initialization of filling up int[][] with max value and shortens the code. But it doesn't affect
             *   the overall time complexity that greatly.
             * 2.More importantly, it can deal with the edge case of Integer.MAX_VALUE
             */
            Integer[][] minDist = new Integer[m][n];
            minDist[0][0] = 0;

            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[2], b[2]));
            pq.offer(new int[]{0, 0, 0});

            while (!pq.isEmpty()) {
                int[] cur = pq.poll();
                int x = cur[0];
                int y = cur[1];
                int curEffort = cur[2];

                if (x == m - 1 && y == n - 1) {
                    return curEffort; //or return mindDist[x][y]
                }

                for (int[] dir : dirs) {
                    int nx = x + dir[0];
                    int ny = y + dir[1];

                    if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;

                    int diff = Math.abs(heights[x][y] - heights[nx][ny]);
                    /**
                     * !!!
                     * By definition "A route's effort is the MAXIMUM absolute difference in heights between two
                     * consecutive cells of the route." So here, "Math.max()" is to satisfy this requirement.
                     */
                    int effort = Math.max(curEffort, diff);

                    /**
                     * !!!
                     * "minDist[nx][ny] == null || minDist[nx][ny] > effort"
                     * Satisfy requirement - "Return the MINIMUM effort required to travel..."
                     *
                     * Always save the min value of effort in a cell - the min effort for all paths that travel from
                     * starting point to current cell.
                     */
                    if (minDist[nx][ny] == null || minDist[nx][ny] > effort) {
                        minDist[nx][ny] = effort;
                        pq.offer(new int[]{nx, ny, effort});
                    }
                }
            }

            return -1;
        }
    }

    /**
     * Binary Search + BFS
     *
     * Given condition "1 <= heights[i][j] <= 10 ^ 6", for effort, lower bound is 0, upper bound is 10 ^ 6.
     * So we can do binary search, each time "guess" an effort value, see if there's a path from starting point
     * to end point and the effort of the path does not exceed the guessed effort value.
     *
     * Complexity Analysis
     * Let M be the number of rows and N be the number of columns for the matrix "heights".
     *
     * Time Complexity : O(M * N)
     * We do a binary search to calculate the mid values and then do BFSh on the matrix for each of those values.
     * 1.Binary Search : To perform Binary search on numbers in range (0.. 10 ^ 6), the time taken would be O(log(10 ^ 6)).
     * 2.BFS : The time complexity for the Breadth First Search for vertices V and edges E is O(V + E). Thus, in the matrix
     *   of size M * N, with M * N vertices and M * N edges,  the time complexity to perform BFS would be O(M * N + M * N)
     *   = O(M * N).
     *
     * This gives us total time complexity as O(log(10 ^ 6) * (M * N)), which is equivalent to O(M * N).
     *
     * Space Complexity: O(M * N), as we use a queue and visited array of size M * N.
     */
    class Solution2 {
        private int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        public int minimumEffortPath(int[][] heights) {
            int l = 0;
            int r = 1000000;

            while (l < r) {
                int m = l + (r - l) / 2;

                if (isValid_BFS(heights, m)) {
                    r = m;
                } else {
                    l = m + 1;
                }
            }

            return l;
        }

        private boolean isValid_BFS(int[][] heights, int max) {
            int m = heights.length;
            int n = heights[0].length;
            boolean[][] visited = new boolean[m][n];
            visited[0][0] = true;//!!!

            Queue<int[]> q = new LinkedList<>();
            q.offer(new int[]{0, 0});//!!!

            while (!q.isEmpty()) {
                int[] cur = q.poll();
                int x = cur[0];
                int y = cur[1];

                if (x == m - 1 && y == n - 1) return true;

                for (int[] dir : dirs) {
                    int nx = x + dir[0];
                    int ny = y + dir[1];

                    if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue; //bypass coordinates out of the matrix

                    int diff = Math.abs(heights[x][y] - heights[nx][ny]);

                    if (diff > max) continue; //bypass cell that has bigger effort value than the given one.

                    if (!visited[nx][ny]) {
                        q.offer(new int[]{nx, ny});
                        visited[nx][ny] = true;
                    }
                }
            }

            return false;
        }
    }

    /**
     * Binary Search + DFS
     * Same as Solution2, only now we use DFS to check if the guessed effort is valid.
     *
     * Time and Space : O(M * N)
     */
    class Solution3 {
        private int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        public int minimumEffortPath(int[][] heights) {
            int l = 0;
            int r = 1000000;

            while (l < r) {
                int m = l + (r - l) / 2;

                if (isValid_DFS(heights, m)) {
                    r = m;
                } else {
                    l = m + 1;
                }
            }

            return l;
        }

        private boolean isValid_DFS(int[][] heights, int max) {
            int m = heights.length;
            int n = heights[0].length;
            boolean[][] visited = new boolean[m][n];

            return isPathExist(heights, max, m, n, 0, 0, visited);
        }

        private boolean isPathExist(int[][] heights, int max, int m, int n, int x, int y, boolean[][] visited) {
            if (x == m - 1 && y == n - 1) return true;

            visited[x][y] = true;

            boolean res = false;
            for (int[] dir : dirs) {
                int nx = x + dir[0];
                int ny = y + dir[1];

                if (nx < 0 || nx >= m || ny < 0 || ny >= n || visited[nx][ny]) continue;

                int diff = Math.abs(heights[x][y] - heights[nx][ny]);
                if (diff > max) continue;

                if (isPathExist(heights, max, m, n, nx, ny, visited)) return true;
            }

            return false;
        }
    }

    /**
     * Brutal Force with DFS backtraking, will TLE
     *
     * The backtracking algorithms consists of the following steps,
     * 1.Choose: Choose the potential candidate. For any given cell A, we must choose the adjacent cells in all 4 directions
     *   (up, down, left, right) as a potential candidate.
     * 2.Constraint: Define a constraint that must be satisfied by the chosen candidate. In this case, a chosen cell is
     *   valid if it is within the boundaries of the matrix and it is not visited before.
     * 3.Goal: We must define the goal that determines if we have found the required solution and we must backtrack. Here,
     *   our goal is achieved once we have reached the destination cell. On reaching the destination cell, we must track
     *   the maximum absolute difference in that path and backtrack.
     *
     * Complexity Analysis
     * Let M be the number of rows and N be the number of columns in the matrix "heights".
     *
     * Time Complexity : O(3 ^ (M * N))
     * The total number of cells in the matrix is given by M * N. For the backtracking, there are at most 4 possible
     * directions to explore, but further, the choices are reduced to 3 (since we won't go back to where we come from).
     * Thus, considering 3 possibilities for every cell in the matrix the time complexity would be O(3 ^ (M * N)).
     *
     * The time complexity is exponential, hence this approach is exhaustive and results in Time Limit Exceeded (TLE).
     *
     * Space Complexity: O(M * N) This space will be used to store the recursion stack. As we recursively move to the
     * adjacent cells, in the worst case there could be M * N cells in the recursive call stack.
     */
    class Solution4 {
        int dirs[][] = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        /**
         * To make the algorithm more efficient, once we find any path from source to destination, we track the maximum
         * absolute difference of all adjacent cells in that path in a variable maxSoFar. With this, we can avoid going
         * into other paths in the future where effort is greater than or equal to maxSoFar.
         *
         * In other words, if we have already found a path to reach the destination cell with maxSoFar, then, we would
         * only explore other paths if it takes efforts less than maxSoFar.
         */
        int maxSoFar = Integer.MAX_VALUE;

        public int minimumEffortPath(int[][] heights) {
            int m = heights.length;
            int n = heights[0].length;
            boolean[][] visited = new boolean[m][n];

            return helper(0, 0, heights, m, n, 0, visited);
        }

        int helper(int x, int y, int[][] heights, int m, int n, int max, boolean[][] visited) {
            if (x == m - 1 && y == n - 1) {
                maxSoFar = Math.min(maxSoFar, max); //!!!
                return max;
            }

            visited[x][y] = true;

            int minEffort = Integer.MAX_VALUE;

            for (int[] dir : dirs) {
                int nx = x + dir[0];
                int ny = y + dir[1];

                if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;

                int diff = Math.abs(heights[nx][ny] - heights[x][y]);

                /**
                 * The maxDifference keeps track of the maximum absolute difference seen so far in the current path.
                 * On every move to the adjacent cell, we must update the maxDifference if it is lesser than the "diff"
                 */
                int maxCurrentDifference = Math.max(max, diff);

                /**
                 * pruning using maxSoFar
                 */
                if (maxCurrentDifference >= maxSoFar) continue;

                int result = helper(nx, ny, heights, m, n, maxCurrentDifference, visited);
                minEffort = Math.min(minEffort, result);
            }

            /**
             * This is necessary because the cell must be allowed to be visited again for other paths
             */
            visited[x][y] = false;

            return minEffort;
        }
    }
}
