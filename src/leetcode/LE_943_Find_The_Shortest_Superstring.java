package leetcode;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created by yuank on 11/27/18.
 */
public class LE_943_Find_The_Shortest_Superstring {
    /**
         Given an array A of strings, find any smallest string that contains each string in A as a substring.

         We may assume that no string in A is substring of another string in A.


         Example 1:

         Input: ["alex","loves","leetcode"]
         Output: "alexlovesleetcode"
         Explanation: All permutations of "alex","loves","leetcode" would also be accepted.


         Example 2:

         Input: ["catg","ctaagt","gcta","ttca","atgcatc"]
         Output: "gctaagttcatgcatc"


         Note:

         1 <= A.length <= 12
         1 <= A[i].length <= 20

         Hard
     */

    /**
     * https://zxi.mytechroad.com/blog/searching/leetcode-943-find-the-shortest-superstring/
     *
     * Solution 1
     * Brutal Force, DFS + merging strings
     *
     * Time  : O(n!)
     * Space : O(n)
     */
    class Solution1 {
        int minLen;
        int[][] cost;
        int[] minPath;

        public String shortestSuperstring(String[] A) {
            int n = A.length;
            cost = new int[n][n];
            minPath = new int[n];
            minLen = Integer.MAX_VALUE;

            /**
             * Pre processing
             * Calculating cost[i][j] : cost of append A[j] to A[i]
             *
             * Example :
             * ["catg","ctaagt","gcta","ttca","atgcatc"]
             *
             * cost[0][1] : append "ctaagt" after "catg", no common prefix and postfix, 6
             * cost[2][4] : append "atgcatc" after "gcta", first char of A[4] is the same as last char of A[2],
             *              cost is A[4].length - 1 = 6
             *
             * cost[][]
             * [4, 6, 3, 4, 4]
             * [4, 6, 4, 3, 7]
             * [4, 3, 4, 4, 6]
             * [2, 6, 4, 4, 6]
             * [3, 5, 4, 4, 7]
             */
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    cost[i][j] = A[j].length();

                    for (int k = 1; k < Math.min(A[i].length(), A[j].length()); k++) {
                        if ((A[i].substring(A[i].length() - k).equals(A[j].substring(0, k)))) {
                            cost[i][j] = A[j].length() - k;
                        }
                    }
                }
            }

            dfs(A, new int[n], 0, 0, new boolean[n]);

            StringBuilder sb = new StringBuilder(A[minPath[0]]);
            for (int k = 1; k < minPath.length; k++) {
                int i = minPath[k - 1];
                int j = minPath[k];
                sb.append(A[j].substring(A[j].length() - cost[i][j]));
            }


            return sb.toString();
        }

        private void dfs(String[] A, int[] path, int pos, int curLen, boolean[] used) {
            if (curLen >= minLen) {
                return;
            }

            if (pos == A.length) {
                minLen = curLen;
                /**
                 * !!!
                 * Must use Arrays.copyOf, NOT "minPath = path", this will make
                 * minPath always point to path, lead to wrong state after recursion
                 * is finished
                 */
                minPath = Arrays.copyOf(path, path.length);
                return;
            }

            for (int i = 0; i < A.length; i++) {
                if (used[i]) {
                    continue;
                }

                path[pos] = i;
                used[i] = true;
                dfs(A, path, pos + 1,
                        pos == 0 ? A[i].length() : curLen + cost[path[pos - 1]][i],
                        used);

                used[i] = false;
            }
        }
    }

    /**
     * Solution 2
     * Travling Salesman Problem (TSP)
     *
     * DP
     *
     * g[i][j] is the cost of appending word[j] after word[i], or weight of edge[i][j].
     * We would like find the shortest path to visit each node from 0 to n – 1 once and only once this
     * is called the Travelling sells man’s problem which is NP-Complete.
     *
     * We can solve it with DP that uses exponential time.
     * dp[s][i] := min distance to visit nodes (represented as a binary state s) once and only once
     * and the path ends with node i.e.g. dp[7][1] is the min distance to visit nodes (0, 1, 2) and ends
     * with node 1, the possible paths could be (0, 2, 1), (2, 0, 1).
     *
     * Time complexity: O(n^2 * 2^n)
     * Space complexity: O(n * 2^n)
     */

    class Solution2 {
        public String shortestSuperstring(String[] A) {
            int n = A.length;
            int[][] graph = new int[n][n];
            // build the graph
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    graph[i][j] = calc(A[i], A[j]);
                    graph[j][i] = calc(A[j], A[i]);
                }
            }
            int[][] dp = new int[1 << n][n];
            int[][] path = new int[1 << n][n];
            int last = -1, min = Integer.MAX_VALUE;

            // start TSP DP
            for (int i = 1; i < (1 << n); i++) {
                Arrays.fill(dp[i], Integer.MAX_VALUE);
                for (int j = 0; j < n; j++) {
                    if ((i & (1 << j)) > 0) {
                        int prev = i - (1 << j);
                        if (prev == 0) {
                            dp[i][j] = A[j].length();
                        } else {
                            for (int k = 0; k < n; k++) {
                                if (dp[prev][k] < Integer.MAX_VALUE && dp[prev][k] + graph[k][j] < dp[i][j]) {
                                    dp[i][j] = dp[prev][k] + graph[k][j];
                                    path[i][j] = k;
                                }
                            }
                        }
                    }
                    if (i == (1 << n) - 1 && dp[i][j] < min) {
                        min = dp[i][j];
                        last = j;
                    }
                }
            }

            // build the path
            StringBuilder sb = new StringBuilder();
            int cur = (1 << n) - 1;
            Stack<Integer> stack = new Stack<>();
            while (cur > 0) {
                stack.push(last);
                int temp = cur;
                cur -= (1 << last);
                last = path[temp][last];
            }

            // build the result
            int i = stack.pop();
            sb.append(A[i]);
            while (!stack.isEmpty()) {
                int j = stack.pop();
                sb.append(A[j].substring(A[j].length() - graph[i][j]));
                i = j;
            }
            return sb.toString();
        }

        private int calc(String a, String b) {
            for (int i = 1; i < a.length(); i++) {
                if (b.startsWith(a.substring(i))) {
                    return b.length() - a.length() + i;
                }
            }
            return b.length();
        }
    }
}
