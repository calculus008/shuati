package leetcode;

import java.util.PriorityQueue;

/**
 * Created by yuank on 10/15/18.
 */
public class LE_407_Trapping_Rain_Water_II {
    /**
         Given an m x n matrix of positive integers representing the height of each unit cell in a
         2D elevation map, compute the volume of water it is able to trap after raining.

         Note:
         Both m and n are less than 110. The height of each unit cell is greater than 0 and is less than 20,000.

         Example:

         Given the following 3x6 height map:
         [
             [1,4,3,1,3,2],
             [3,2,1,3,2,4],
             [2,3,3,2,3,1]
         ]

         Return 4.

         Hard
     */

    /**
     * 将矩阵周边的格子都放到堆里，这些格子上面是无法盛水的。
     * 每次在堆里挑出一个高度最小的格子 Element，把周围的格子加入到堆里。
     * 这些格子被加入堆的时候，计算他们上面的盛水量。
     *
     * 盛水量 = Element.height - 这个格子的高度
     * 当然如果这个值是负数，盛水量就等于 0。
     *
     * Time  : O((m * n)log(m + n))
     * Space : O(m * n)
     */

    public class Solution {
        class Element {
            int x, y, val;
            public Element(int x, int y, int val) {
                this.x = x;
                this.y = y;
                this.val = val;
            }
        }

        public int trapRainWater(int[][] heights) {
            if (heights == null || heights.length == 0 || heights[0].length == 0) return 0;

            int m = heights.length;
            int n = heights[0].length;
            int sum = 0;

            int[][] dir = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
            boolean[][] visited = new boolean[m][n];
            PriorityQueue<Element> pq = new PriorityQueue<>((a, b) -> a.val - b.val);

            for (int i = 0; i < n; i++) {
                pq.offer(new Element(0, i, heights[0][i]));
                visited[0][i] = true;
                pq.offer(new Element(m - 1, i, heights[m - 1][i]));
                visited[m - 1][i] = true;
            }

            for (int i = 1; i < m - 1; i++) {
                pq.offer(new Element(i, 0, heights[i][0]));
                visited[i][0] = true;
                pq.offer(new Element(i, n - 1, heights[i][n - 1]));
                visited[i][n - 1] = true;
            }

            while (!pq.isEmpty()) {
                Element cur = pq.poll();
                int x = cur.x;
                int y = cur.y;

                for (int i = 0; i < dir.length; i++) {
                    int nx = x + dir[i][0];
                    int ny = y + dir[i][1];
                    if (nx < 0 || nx >= m || ny < 0 || ny >= n || visited[nx][ny]) continue;

                    visited[nx][ny] = true;
                    /**
                     * Key insight : when "push outside wall" inside, the height value is not
                     * necessarily comes from the new  location, it's the max of the height
                     * value of the "cur" and the new location.
                     */
                    pq.offer(new Element(nx, ny, Math.max(cur.val, heights[nx][ny])));
                    /**
                     * Must be "cur.val", because the logic to add new Element above does not
                     * use the value of heights[nx][ny], it is the max between cur.val and
                     * heights[nx][ny], so amount of water is difference between the value of
                     * the Element that is just poped off pq and the value of the new location.
                     * **/
                    sum += Math.max(0, cur.val - heights[nx][ny]);
                }
            }

            return sum;
        }
    }
}
