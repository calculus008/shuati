package leetcode;

import java.util.PriorityQueue;

public class LE_1102_Path_With_Maximum_Minimum_Value {
    /**
     * Given a matrix of integers A with R rows and C columns, find the maximum
     * score of a path starting at [0,0] and ending at [R-1,C-1].
     *
     * The score of a path is the minimum value in that path.  For example,
     * the value of the path 8 →  4 →  5 →  9 is 4.
     *
     * A path moves some number of times from one visited cell to any neighbouring
     * unvisited cell in one of the 4 cardinal directions (north, east, west, south).
     *
     * Note:
     * 1 <= R, C <= 100
     * 0 <= A[i][j] <= 10^9
     *
     * Medium
     */

    /**
     * As long as the square is added to the PQ, it has been reachable through the previous visited squares.
     * No matter what sequence the squares are popped from the PQ, when we reach the destination, it ensures
     * a continues path. There will be squares that are popped out and not necessarily on the effective path
     * ("which is not in the immediate neighborhood of curr sq", but note here in this logic, we do not really
     * have used the concept of a "Current Square"), but those squares have bigger values so it would not matter.
     *
     * [
     * [8,6,4],
     * [7,5,3],
     * [3,2,3]
     * ]
     *
     * Actually, the problem doesn't ask for tracking the path leading to solution (and there may be more than
     * one such path), it only asks to find the maximum score, which is 3 for your inputs. Of course, it is
     * possible to track the path with a slight algorithm modification.
     *
     * The order of popping for your inputs is 8->7->6->5->4->3->3->3, which corresponds to the path 8->7->5->3->3.
     *
     *
     * Time  : O(mnlogmn)
     * Space : O(mn)
     */
    class Solution {
        public int maximumMinimumPath(int[][] A) {
            int m = A.length;
            int n = A[0].length;

            int res = Integer.MAX_VALUE;
            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            boolean[][] visited = new boolean[m][n];
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> A[b[0]][b[1]] - A[a[0]][a[1]]);

            pq.offer(new int[]{0, 0});
            visited[0][0] = true;

            while (!pq.isEmpty()) {
                int[] cur = pq.poll();
                res = Math.min(res, A[cur[0]][cur[1]]);

                /**
                 * !!!
                 */
                if (cur[0] == m - 1 && cur[1] == n - 1 ) {
                    return res;
                }

                for (int[] dir : dirs) {
                    int nx = cur[0] + dir[0];
                    int ny = cur[1] + dir[1];

                    if (nx < 0 || nx >= m || ny < 0 || ny >= n || visited[nx][ny]) {
                        continue;
                    }

                    pq.offer(new int[]{nx, ny});
                    visited[nx][ny] = true;
                }
            }

            throw new IllegalStateException("Unable to reach end cell");
        }
    }
}
