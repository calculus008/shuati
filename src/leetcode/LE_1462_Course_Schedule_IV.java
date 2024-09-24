package leetcode;

import java.util.*;

public class LE_1462_Course_Schedule_IV {
    /**
     * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an
     * array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course ai first if you want
     * to take course bi.
     *
     * For example, the pair [0, 1] indicates that you have to take course 0 before you can take course 1.
     * Prerequisites can also be indirect. If course a is a prerequisite of course b, and course b is a prerequisite
     * of course c, then course a is a prerequisite of course c.
     *
     * You are also given an array queries where queries[j] = [uj, vj]. For the jth query, you should answer whethe
     * r course uj is a prerequisite of course vj or not.
     *
     * Return a boolean array answer, where answer[j] is the answer to the jth query.
     *
     * Medium
     *
     * https://leetcode.com/problems/course-schedule-iv/description/
     */

    /**
     * DFS + memo
     *
     * Time Complexity:
     *
     * Building the graph takes O(E) where E is the number of prerequisites.
     * Each DFS runs in O(V + E) where V is the number of courses and E is the number of edges (prerequisites).
     * The overall complexity depends on the number of queries, but memoization helps reduce redundant work.
     */

    class Solution_DFS_1 {
        private Map<Integer, List<Integer>> graph = new HashMap<>();
        private Boolean[][] memo;

        public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
            for (int i = 0; i < numCourses; i++) {
                graph.put(i, new ArrayList<>());
            }

            for (int[] prerequisite : prerequisites) {
                graph.get(prerequisite[0]).add(prerequisite[1]);
            }

            memo = new Boolean[numCourses][numCourses];

            List<Boolean> result = new ArrayList<>();

            for (int[] query : queries) {
                int u = query[0];
                int v = query[1];
                result.add(dfs(u, v));
            }

            return result;
        }

        private boolean dfs(int u, int v) {
            if (memo[u][v] != null) return memo[u][v];

            if (graph.get(u).contains(v)) {
                memo[u][v] = true;
                return true;
            }

            for (int next : graph.get(u)) {
                if (dfs(next, v)) {
                    memo[u][v] = true;
                    return true;
                }
            }

            return memo[u][v] = false;
        }
    }


    class Solution_DFS_2 {
        public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
            List<Integer>[] graph = new List[numCourses];
            for (int i = 0; i < numCourses; i++) {
                graph[i] = new ArrayList<>();
            }

            for (int[] pre : prerequisites) {
                graph[pre[0]].add(pre[1]);
            }

            List<Boolean> res = new ArrayList<>();
            Boolean[][] memo = new Boolean[numCourses][numCourses];
            for (int[] query : queries) {
                res.add(dfs(graph, query[0], query[1], memo));
            }
            return res;
        }

        private boolean dfs(List<Integer>[] graph, int start, int end, Boolean[][] memo) {
            if (start == end) return true;
            if (memo[start][end] != null) return memo[start][end];

            for (int next : graph[start]) {
                if (dfs(graph, next, end, memo))
                    return memo[start][end] = true;
            }
            return memo[start][end] = false;
        }
    }


    // Floyd-Warshall, Time: O(n^3)
    class Solution {
        public List<Boolean> checkIfPrerequisite(int n, int[][] prerequisites, int[][] queries) {
            boolean[][] connected = new boolean[n][n];
            for (int[] p : prerequisites) {
                connected[p[0]][p[1]] = true; // p[0] -> p[1]
            }

            for (int k = 0; k < n; k++)
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++)
                        connected[i][j] = connected[i][j] || connected[i][k] && connected[k][j];

            List<Boolean> ans = new ArrayList<>();
            for (int[] q : queries) {
                ans.add(connected[q[0]][q[1]]);
            }
            return ans;
        }
    }


}
