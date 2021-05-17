package leetcode;

public class LE_1267_Count_Servers_That_Communicate {
    /**
     * You are given a map of a server center, represented as a m * n integer matrix grid,
     * where 1 means that on that cell there is a server and 0 means that it is no server.
     * Two servers are said to communicate if they are on the same row or on the same column.
     *
     * Return the number of servers that communicate with any other server.
     *
     * Example1:
     * Input: grid = [[1,0],[0,1]]
     * Output: 0
     * Explanation: No servers can communicate with others.
     *
     * Example2:
     * Input: grid = [[1,0],[1,1]]
     * Output: 3
     * Explanation: All three servers can communicate with at least one other server.
     *
     * Example3:
     * Input: grid = [[1,1,0,0],[0,0,1,0],[0,0,1,0],[0,0,0,1]]
     * Output: 4
     * Explanation: The two servers in the first row can communicate with each other.
     * The two servers in the third column can communicate with each other. The server
     * at right bottom corner can't communicate with any other server.
     *
     * Medium
     */

    /**
     * Logic:
     * 1.First get total number of servers, get row and col counts for reach server.
     * 2.Detect the server that has no communication, then decrease total server count.
     */
    class Solution {
        public int countServers(int[][] grid) {
            if (grid == null || grid.length == 0) return 0;

            int m = grid.length;
            int n = grid[0].length;
            int[] rowCount = new int[m];
            int[] colCount = new int[n];
            int count = 0;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        rowCount[i]++;
                        colCount[j]++;
                        count++;
                    }
                }
            }

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        /**
                         * if the col and row that current server resides both shows
                         * there's only one server, it means it has no communication
                         * with any other server. Decrease server count.
                         */
                        if (rowCount[i] == 1 && colCount[j] == 1) {
                            count--;
                        }
                    }
                }
            }

            return count;
        }
    }
}
