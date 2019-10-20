package lintcode;

import java.util.*;

/**
 * Created by yuank on 7/19/18.
 */
public class LI_794_Sliding_Puzzle_II {
    /**
         On a 3x3 board, there are 8 tiles represented by the integers 1 through 8, and an empty square represented by 0.

         A move consists of choosing 0 and a 4-directionally adjacent number and swapping it.

         Given an initial state of the puzzle board and final state, return the least number of moves required so that
         the initial state to final state.

         If it is impossible to move from initial state to final state, return -1.

         Example
         Given an initial state:

         [
         [2,8,3],
         [1,0,4],
         [7,6,5]
         ]
         and a final state:

         [
         [1,2,3],
         [8,0,4],
         [7,6,5]
         ]
         Return 4
         Explanation:

         [                 [
         [2,8,3],          [2,0,3],
         [1,0,4],   -->    [1,8,4],
         [7,6,5]           [7,6,5]
         ]                 ]

         [                 [
         [2,0,3],          [0,2,3],
         [1,8,4],   -->    [1,8,4],
         [7,6,5]           [7,6,5]
         ]                 ]

         [                 [
         [0,2,3],          [1,2,3],
         [1,8,4],   -->    [0,8,4],
         [7,6,5]           [7,6,5]
         ]                 ]

         [                 [
         [1,2,3],          [1,2,3],
         [0,8,4],   -->    [8,0,4],
         [7,6,5]           [7,6,5]
         ]                 ]

         Challenge
         How to optimize the memory?
         Can you solve it with A* algorithm?

         Hard
     */

    /**
     * BFS
     *
     * "华容道"
     *
     * 非常典型的BFS。注意这两个方法：
     *
     * matrixToString() : 把矩阵转换成String,这样才能比较他们，以判断是否到达终点。
     *
     * getNext() : 找到下一层的所有可能的状态。
     */
     class Solution {
        public int minMoveStep_JiuZhang(int[][] init_state, int[][] final_state) {
            String source = matrixToString(init_state);
            String target = matrixToString(final_state);

            Queue<String> queue = new LinkedList<>();
            Set<String> visited = new HashSet<>();

            queue.offer(source);
            visited.add(source);

            int step = 0;
            while (!queue.isEmpty()) {
                int size = queue.size();

                for (int i = 0; i < size; i++) {
                    String curt = queue.poll();
                    if (curt.equals(target)) {
                        return step;
                    }

                    for (String next : getNext(curt)) {
                        if (visited.contains(next)) {
                            continue;
                        }
                        queue.offer(next);
                        visited.add(next);
                    }
                }
                step++;
            }

            return -1;
        }

        public String matrixToString(int[][] state) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    sb.append(state[i][j]);
                }
            }
            return sb.toString();
        }

        public List<String> getNext(String state) {
            List<String> states = new ArrayList<>();
            int[] dx = {0, 1, -1, 0};
            int[] dy = {1, 0, 0, -1};

            /**
             * 因为每次都是交换‘0’和周围的位置：
             * 1.先找到‘0’的位置，计算出在矩阵中的坐标。
             * 2.往4个方向移动，判断是否合法。
             * 3.If yes, 在Char Array 中交换位置。
             *
             */
            int zeroIndex = state.indexOf('0');
            int x = zeroIndex / 3;
            int y = zeroIndex % 3;

            for (int i = 0; i < 4; i++) {
                int x_ = x + dx[i];
                int y_ = y + dy[i];
                if (x_ < 0 || x_ >= 3 || y_ < 0 || y_ >= 3) {
                    continue;
                }

                char[] chars = state.toCharArray();
                chars[x * 3 + y] = chars[x_ * 3 + y_];
                chars[x_ * 3 + y_] = '0';
                states.add(new String(chars));
            }

            return states;
        }
    }

    public class Solution_Practice {
        public int minMoveStep(int[][] init_state, int[][] final_state) {
            if (init_state == null || final_state == null) return -1;

            String start = convert(init_state);
            String end = convert(final_state);

            if (start.length() != end.length()) return -1;

            Set<String> visited = new HashSet<>();
            Queue<String> q = new LinkedList<>();
            q.offer(start);
            visited.add(start);

            int steps = 0;

            while (!q.isEmpty()) {
                steps++;
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    String cur = q.poll();

                    List<String> list = getNext(cur);
                    for (String next : list) {
                        if (end.equals(next)) {
                            return steps;
                        }

                        if (visited.contains(next)) continue;

                        visited.add(next);
                        q.offer(next);
                    }
                }
            }

            return -1;
        }

        public String convert(int[][] board) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    sb.append(board[i][j]);
                }
            }

            return sb.toString();
        }

        public List<String> getNext(String cur) {
            int pos = cur.indexOf('0');
            int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
            List<String> res = new ArrayList<>();

            for (int j = 0; j < 4; j++) {
                int x = pos / 3;
                int y = pos % 3;

                int nx = x + dirs[j][0];
                int ny = y + dirs[j][1];

                if (nx < 0 || nx >= 3 || ny < 0 || ny >= 3) {
                    continue;
                }

                int nPos = nx * 3 + ny;

                char[] chars = cur.toCharArray();
                chars[pos] = chars[nPos];
                chars[nPos] = '0';
                String next = new String(chars);

                res.add(next);
            }

            return res;
        }
    }
}
