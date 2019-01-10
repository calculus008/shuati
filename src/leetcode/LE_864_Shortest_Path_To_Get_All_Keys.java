package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class LE_864_Shortest_Path_To_Get_All_Keys {
    /**
         We are given a 2-dimensional grid. "." is an empty cell, "#" is a wall,
         "@" is the starting point, ("a", "b", ...) are keys,
         and ("A", "B", ...) are locks.

         We start at the starting point, and one move consists of walking one space in one of the 4 cardinal directions.
         We cannot walk outside the grid, or walk into a wall.  If we walk over a key, we pick it up.  We can't walk over
         a lock unless we have the corresponding key.

         For some 1 <= K <= 6, there is exactly one lowercase and one uppercase letter of the first K letters of the English
         alphabet in the grid.  This means that there is exactly one key for each lock, and one lock for each key; and also
         that the letters used to represent the keys and locks were chosen in the same order as the English alphabet.

         Return the lowest number of moves to acquire all keys.  If it's impossible, return -1.

         Example 1:

         Input: ["@.a.#","###.#","b.A.B"]
         Output: 8

         Example 2:

         Input: ["@..aA","..B#.","....b"]
         Output: 6

         Note:

         1 <= grid.length <= 30
         1 <= grid[0].length <= 30
         grid[i][j] contains only '.', '#', '@', 'a'-'f' and 'A'-'F'
         The number of keys is in [1, 6].  Each key has a different letter and opens exactly one lock.

         Hard
     **/

    /**
     * Huahua version
     * https://zxi.mytechroad.com/blog/searching/leetcode-864-shortest-path-to-get-all-keys/
     *
     * Time  : O(m * n * 64), 64 = 2 ^ number of keys (max is 6)
     *
     * Space : O(m * n * 64)
     **/

    class Solution {
        public int shortestPathAllKeys(String[] grid) {
            int m = grid.length;
            int n = grid[0].length();
            int allKeys = 0;
            char[][] chars = new char[m][n];

            boolean[][][] visited = new boolean[m][n][64];

            Queue<Integer> q = new LinkedList<>();

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    char c = grid[i].charAt(j);
                    chars[i][j] = c;

                    if (c == '@') {//startint point
                        q.offer((j << 16) | (i << 8));
                        visited[i][j][0] = true;
                    } else if (c >= 'a' && c <= 'f') {//add key
                        allKeys |= (1 << (c - 'a'));
                    }
                }
            }

            int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            int steps = 0;
            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int cur = q.poll();
                    int x = cur >> 16;
                    int y = (cur >> 8) & 0xFF;//!!!
                    int keys = cur & 0xFF;//!!!

                    if (keys == allKeys) {
                        return steps;
                    }

                    /**
                     i maps to y, j mapx x
                     **/
                    for (int j = 0; j < dirs.length; j++) {
                        int newKeys = keys;
                        int newX = x + dirs[j][0];
                        int newY = y + dirs[j][1];

                        if (newX < 0 || newX >= n || newY < 0 || newY >= m) {//!!!
                            continue;
                        }

                        char c = chars[newY][newX];
                        if (c == '#') {//wall
                            continue;
                        }

                        if (c >= 'A' && c <= 'F' && (keys & (1 << (c - 'A'))) == 0) {//room, but no key
                            continue;
                        }

                        if (c >= 'a' && c <= 'f') {//pick up key
                            newKeys |= (1 << (c - 'a'));
                        }

                        if (visited[newY][newX][newKeys]) {//prevent duplicate path
                            continue;
                        }

                        q.offer((newX << 16) | (newY << 8) | newKeys);
                        visited[newY][newX][newKeys] = true;
                    }
                }
                steps++;
            }

            return -1;
        }
    }

    /**
     * Leetcode solution
     *
     * https://leetcode.com/problems/shortest-path-to-get-all-keys/discuss/146827/JAVA-%2B-BFS-%2B-21ms
     *
     * Not using bit operation to combine x, y and keys into a single integer,
     * instead, use Node class to store them.
     *
     * Only using bit operation to save keys (allKeys and current keys).
     *
     * More concise and no more confusion with mapping between x, y and i, j
     */
    class Solution2 {
        class Node {
            int x;
            int y;
            int keys;

            public Node(int x, int y, int keys) {
                this.x = x;
                this.y = y;
                this.keys = keys;
            }
        }

        public int shortestPathAllKeys(String[] grid) {
            int m = grid.length;
            int n = grid[0].length();
            boolean[][][] visited = new boolean[m][n][64];
            int allKeys = 0;
            char[][] chars = new char[m][n];

            Queue<Node> q = new LinkedList<>();

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    char c = grid[i].charAt(j);
                    chars[i][j] = c;

                    if (c == '@') {
                        q.offer(new Node(i, j, 0));
                        visited[i][j][0] = true;
                    } else if (c >= 'a' && c <= 'f') {
                        allKeys |= (1 << (c - 'a'));
                    }
                }
            }

            int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            int steps = 0;
            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    Node cur = q.poll();
                    int x = cur.x;
                    int y = cur.y;//!!!
                    int keys = cur.keys;//!!!

                    if (keys == allKeys) {
                        return steps;
                    }

                    for (int j = 0; j < dirs.length; j++) {
                        int newKeys = keys;
                        int newX = x + dirs[j][0];
                        int newY = y + dirs[j][1];

                        if (newX < 0 || newX >= m || newY < 0 || newY >= n) {//!!!
                            continue;
                        }

                        char c = chars[newX][newY];
                        if (c == '#') {//wall
                            continue;
                        }

                        if (c >= 'A' && c <= 'F' && (keys & (1 << (c - 'A'))) == 0) {//room, but no key
                            continue;
                        }

                        if (c >= 'a' && c <= 'f') {//pick up key
                            newKeys |= (1 << (c - 'a'));
                        }

                        if (visited[newX][newY][newKeys]) {//prevent duplicate path
                            continue;
                        }

                        q.offer(new Node(newX, newY, newKeys));
                        visited[newX][newY][newKeys] = true;
                    }
                }
                steps++;
            }

            return -1;
        }
    }
}