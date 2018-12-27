package leetcode;

import java.util.*;

public class LE_773_Sliding_Puzzle {
    /**
         On a 2x3 board, there are 5 tiles represented by the integers 1 through 5, and an empty square represented by 0.

         A move consists of choosing 0 and a 4-directionally adjacent number and swapping it.

         The state of the board is solved if and only if the board is [[1,2,3],[4,5,0]].

         Given a puzzle board, return the least number of moves required so that the state of the board is solved.
         If it is impossible for the state of the board to be solved, return -1.

         Examples:

         Input: board = [[1,2,3],[4,0,5]]
         Output: 1
         Explanation: Swap the 0 and the 5 in one move.
         Input: board = [[1,2,3],[5,4,0]]
         Output: -1
         Explanation: No number of moves will make the board solved.
         Input: board = [[4,1,2],[5,0,3]]
         Output: 5

         Explanation: 5 is the smallest number of moves that solves the board.
         An example path:
         After move 0: [[4,1,2],[5,0,3]]
         After move 1: [[4,1,2],[0,5,3]]
         After move 2: [[0,1,2],[4,5,3]]
         After move 3: [[1,0,2],[4,5,3]]
         After move 4: [[1,2,0],[4,5,3]]
         After move 5: [[1,2,3],[4,5,0]]
         Input: board = [[3,2,4],[1,5,0]]
         Output: 14
         Note:

         board will be a 2 x 3 array as described above.
         board[i][j] will be a permutation of [0, 1, 2, 3, 4, 5].

         Hard
     **/

    /**
     * http://zxi.mytechroad.com/blog/searching/leetcode-773-sliding-puzzle/
     *
     * Time complexity: O(6!)
     * Generally,O(R ∗ C ∗ (R ∗ C)!), where R, C are the number of rows and columns in board.
     * There are O((R * C)!) possible board states.
     *
     * Space complexity: O(6!)
     * O(R ∗ C ∗ (R ∗ C)!)
     */
    class Solution {
        public int slidingPuzzle(int[][] board) {
            if (null == board || board.length == 0) {
                return 0;
            }

            int m = board.length;
            int n = board[0].length;

            /**
             * !!! "Arrays.deepToString()"
             * Convert to string in format - [[1,2], [3,4]]. With this method, we can hash the 2D array into a String
             *
             * replace(char oldChar, char newChar)
             * Returns a new string resulting from replacing all occurrences of oldChar in this string with newChar.
             *
             * replaceAll(String regex, String replacement)
             * Replaces each substring of this string that matches the given regular expression with the given replacement.
             */
            String start = Arrays.deepToString(board).replace("[", "").replace("]", "").replace(",", "").replace(" ","");

            String target = "123450";//hard-coded based on given condition

            Queue<String> q = new LinkedList<>();
            q.offer(start);
            Set<String> visited = new HashSet<>();
            visited.add(start);

            int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            int steps = 0;
            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    String cur = q.poll();

                    if (cur.equals(target)) {
                        return steps;
                    }

                    int idx = cur.indexOf('0');
                    int x = idx / n;
                    int y = idx % n;

                    for (int j = 0; j < dirs.length; j++) {
                        int nx = x + dirs[j][0];
                        int ny = y + dirs[j][1];

                        if (nx < 0 || nx >= m || ny < 0 || ny >= n) {
                            continue;
                        }

                        String next = swap(cur.toCharArray(), idx, nx * n + ny);

                        if (visited.contains(next)) {
                            continue;
                        }

                        /**
                         * !!!
                         * Never forget to put it in visited, otherwise, it will TLE
                         */
                        visited.add(next);
                        q.offer(next);
                    }
                }
                /**
                 * !!!
                 * Need to do it after "return steps", since after it,
                 * we expand to the next level - meaning we move one more steps
                 */
                steps++;
            }

            return -1;
        }

        private String swap(char[] chars, int idx, int nIdx) {
            char temp = chars[idx];
            chars[idx] = chars[nIdx];
            chars[nIdx] = temp;
            return String.valueOf(chars);
        }
    }
}