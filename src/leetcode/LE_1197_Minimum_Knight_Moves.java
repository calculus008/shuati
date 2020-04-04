package leetcode;

import java.util.*;

public class LE_1197_Minimum_Knight_Moves {
    /**
     * In an infinite chess board with coordinates from -infinity to +infinity,
     * you have a knight at square [0, 0].
     *
     * A knight has 8 possible moves it can make, as illustrated below.
     * Each move is two squares in a cardinal direction, then one square in an orthogonal direction.
     *
     * Return the minimum number of steps needed to move the knight to the square [x, y].
     * It is guaranteed the answer exists.
     *
     * Example 1:
     * Input: x = 2, y = 1
     * Output: 1
     * Explanation: [0, 0] → [2, 1]
     *
     * Example 2:
     * Input: x = 5, y = 5
     * Output: 4
     * Explanation: [0, 0] → [2, 1] → [4, 2] → [3, 4] → [5, 5]
     *
     * Constraints:
     * |x| + |y| <= 300
     *
     * Medium
     */

    /**
     * BFS
     *
     * Tricky part of board is INFINIT
     */
    class Solution1 {
        public int minKnightMoves(int x, int y) {
            /**
             * !!!
             * 因为棋盘无限大，（x， y）在4个象限是对称的， 我们只用考虑第1象限。
             * 这样，只考虑1/4的空间，大大减少运算时间。
             */
            x = Math.abs(x);
            y = Math.abs(y);

            Queue<int[]> q = new LinkedList<>();
            q.offer(new int[]{0, 0});
            int steps = 0;

            int[][] dirs = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {-1, 2}, {1, -2}, {-1, -2}};

            /**
             * 把坐标转换成STRING作为唯一key。
             */
            Set<String> visited = new HashSet<>();
            visited.add("0, 0");

            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int[] cur = q.poll();
                    if (cur[0] == x && cur[1] == y) {
                        return steps;
                    }

                    for (int[] dir : dirs) {
                        int nx = cur[0] + dir[0];
                        int ny = cur[1] + dir[1];

                        String s = nx + "," + ny;

                        /**
                         * !!!
                         * "nx >= -1 && ny >= -1"
                         *
                         * The co-or in general to compute the knight moves are: (x-2, y-1) (x-2, y+1),
                         * (x-1, y-2) ... where for all x,y>=2 the next "move" will always be >=0
                         * (ie in the first quadrant). Only for x=1/y=1, the next move may fall in the
                         * negative quad example (x-2,y-1) or (x-1, y-2), and hence x=-1 y=-1 boundary is
                         * considered.
                         *
                         * For example, to reach (1,1) from (0, 0), the best way is to get (2, -1) or (-1, 2)
                         * first, then (1,1) (two steps). If we eliminate all coordinates with negative
                         * numbers, then we can't reach (1,1) from (0, 0) within two steps.
                         */
                        if (!visited.contains(s) && nx >= -1 && ny >= -1) {
                            q.offer(new int[]{nx, ny});
                            visited.add(s);
                        }
                    }
                }

                steps++;
            }

            return -1;
        }
    }

    /**
     * DP
     *
     * The key observation is that we do not need to distinguish x and y,
     * and we don't care whether x and y are positive or negative at all.
     *
     * In 1st quandran, move towards (0, 0), so we only need to consider
     * two directions, {-2, -1} and {-1, -2}. Only those 2 directions will
     * have shortest path to (0, 0)
     *
     * Much faster than BFS, only takes 20ms (BFS uses 583ms)
     */
    class Solution2 {
        public int minKnightMoves(int x, int y) {
            x = Math.abs(x);
            y = Math.abs(y);
            Map<String, Integer> memo = new HashMap<>();
            return helper(x, y, memo);
        }

        private int helper(int x, int y, Map<String, Integer> memo) {
            String key = x + ":" + y;

            if (memo.containsKey(key)) {
                return memo.get(key);
            }

            if (x + y == 0) {
                return 0;
            } else if (x + y == 2) {
                return 2;
            }

            int min = Math.min(helper(Math.abs(x - 1), Math.abs(y - 2), memo),
                    helper(Math.abs(x - 2), Math.abs(y - 1), memo)) + 1;
            memo.put(key, min);

            return min;
        }
    }
}
