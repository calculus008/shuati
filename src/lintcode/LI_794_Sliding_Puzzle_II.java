package lintcode;

import java.util.*;

/**
 * Created by yuank on 7/19/18.
 */
public class LI_794_Sliding_Puzzle_II {
    /**
         On a 3x3 board, there are 8 tiles represented by the integers 1 through 8, and an empty square represented by 0.

         A move consists of choosing 0 and a 4-directionally adjacent number and swapping it.

         Given an initial state of the puzzle board and final state, return the least number of moves required so that the initial state to final state.

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
     * 非常典型的BFS。注意这两个方法：
     *
     * matrixToString() : 把矩阵转换成String,这样才能比较他们，以判断是否到达终点。
     *
     * getNext() : 找到下一层的所有可能的状态。
     */

        /**
         * @param init_state: the initial state of chessboard
         * @param final_state: the final state of chessboard
         * @return: return an integer, denote the number of minimum moving
         */
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
