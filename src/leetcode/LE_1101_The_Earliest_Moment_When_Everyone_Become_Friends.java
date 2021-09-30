package leetcode;

import java.util.*;

public class LE_1101_The_Earliest_Moment_When_Everyone_Become_Friends {
    /**
     * There are n people in a social group labeled from 0 to n - 1. You are given an array logs where logs[i] =
     * [timestampi, xi, yi] indicates that xi and yi will be friends at the time timestampi.
     *
     * Friendship is symmetric. That means if a is friends with b, then b is friends with a. Also, person a is
     * acquainted with a person b if a is friends with b, or a is a friend of someone acquainted with b.
     *
     * Return the earliest time for which every person became acquainted with every other person. If there is no
     * such earliest time, return -1.
     *
     * Example 1:
     * Input: logs = [[20190101,0,1],[20190104,3,4],[20190107,2,3],[20190211,1,5],[20190224,2,4],[20190301,0,3],
     *                [20190312,1,2],[20190322,4,5]], n = 6
     * Output: 20190301
     * Explanation:
     * The first event occurs at timestamp = 20190101 and after 0 and 1 become friends we have the following friendship groups [0,1], [2], [3], [4], [5].
     * The second event occurs at timestamp = 20190104 and after 3 and 4 become friends we have the following friendship groups [0,1], [2], [3,4], [5].
     * The third event occurs at timestamp = 20190107 and after 2 and 3 become friends we have the following friendship groups [0,1], [2,3,4], [5].
     * The fourth event occurs at timestamp = 20190211 and after 1 and 5 become friends we have the following friendship groups [0,1,5], [2,3,4].
     * The fifth event occurs at timestamp = 20190224 and as 2 and 4 are already friends anything happens.
     * The sixth event occurs at timestamp = 20190301 and after 0 and 3 become friends we have that all become friends.
     *
     * Example 2:
     * Input: logs = [[0,2,0],[1,0,1],[3,0,3],[4,1,2],[7,3,1]], n = 4
     * Output: 3
     *
     * Constraints:
     * 2 <= n <= 100
     * 1 <= logs.length <= 104
     * logs[i].length == 3
     * 0 <= timestampi <= 109
     * 0 <= xi, yi <= n - 1
     * xi != yi
     * All the values timestampi are unique.
     * All the pairs (xi, yi) occur at most one time in the input.
     *
     * Medium
     *
     * https://leetcode.com/problems/the-earliest-moment-when-everyone-become-friends/
     */

    /**
     * Union Find
     *
     * sort logs array by timestamp
     * union: when two people know each other
     * when there is only one parent (one single connected component), return the timestamp as the final result.
     *
     * Time  : O(MlogM), where M is the number of logs
     * Space : O(N), where N is the number of people
     */
    class Solution {
        int[] parents;

        public int earliestAcq(int[][] logs, int n) {
            int count = n;
            parents = new int[n];

            for (int i = 0; i < n; i++) {
                parents[i] = i;
            }

            Arrays.sort(logs, (a, b) -> a[0] - b[0]);

            for (int[] log : logs) {
                int pa = find(log[1]);
                int pb = find(log[2]);

                if (pa != pb) {
                    parents[pa] = pb;
                    count--;
                    if (count == 1) return log[0];
                }
            }

            return -1;
        }

        private int find(int child) {
            if (parents[child] == child) {
                return child;
            }
            return find(parents[child]);
        }
    }
}
