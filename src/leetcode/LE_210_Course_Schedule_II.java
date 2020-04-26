package leetcode;

import java.util.*;

/**
 * Created by yuank on 3/26/18.
 */
public class LE_210_Course_Schedule_II {
    /**
        There are a total of n courses you have to take, labeled from 0 to n - 1.

        Some courses may have prerequisites, for example to take course 0 you have to first take course 1,
        which is expressed as a pair: [0,1]

        Given the total number of courses and a list of prerequisite pairs, return the ordering of courses
        you should take to finish all courses.

        There may be multiple correct orders, you just need to return one of them. If it is impossible to
        finish all courses, return an empty array.

        For example:

        2, [[1,0]]
        There are a total of 2 courses to take. To take course 1 you should have finished course 0.
        So the correct course order is [0,1]

        4, [[1,0],[2,0],[3,1],[3,2]]
        There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2.
        Both courses 1 and 2 should be taken after you finished course 0. So one correct course order is [0,1,2,3].
        Another correct ordering is[0,2,1,3].

        Note:
        The input prerequisites is a graph represented by a list of edges, not adjacency matrices.
        Read more about how a graph is represented.
        You may assume that there are no duplicate edges in the input prerequisites.
     */

    /**
     * BFS topological sort with HashMap
     *
     * Follow up, detect cycle
     * https://www.geeksforgeeks.org/detect-cycle-in-a-graph/
     */
    class Solution1 {
        Map<Integer, List<Integer>> map;
        int totalNumber;

        public int[] findOrder(int numCourses, int[][] prerequisites) {
            if (numCourses < 0 || prerequisites == null) {
                return new int[]{};
            }

            /**
             * #1.Build Graph (adjacent list) and init indegree[]
             */
            int[] res = new int[numCourses];

            totalNumber = numCourses;
            map = new HashMap<>();
            for (int i = 0; i < numCourses; i++) {
                map.put(i, new ArrayList<>());
            }

            int[] indegree = new int[numCourses];

            for (int[] p : prerequisites) {
                int from = p[1];
                int to = p[0];
                indegree[to]++;
                map.get(from).add(to);
            }

            /**
             * #2.BFS
             */
            Queue<Integer> q = new LinkedList<>();
            for (int i = 0; i < indegree.length; i++) {
                if (indegree[i] == 0) {
                    q.offer(i);
                }
            }

            int count = 0;
            while (!q.isEmpty()) {
                int cur = q.poll();
                res[count++] = cur;

                List<Integer> list = map.get(cur);
                for (int num : list) {
                    indegree[num]--;
                    if (indegree[num] == 0) {
                        q.offer(num);
                    }
                }
            }

            return count == numCourses ? res : new int[]{};
        }

        /**
         * Returns true if the graph contains a cycle, else false.
         *
         * Depth First Traversal can be used to detect a cycle in a Graph. DFS for a connected
         * graph produces a tree. There is a cycle in a graph only if there is a back edge present
         * in the graph. A back edge is an edge that is from a node to itself (self-loop) or one
         * of its ancestor in the tree produced by DFS.
         *
         * For a disconnected graph, we get the DFS forest as output. To detect cycle, we can check
         * for a cycle in individual trees by checking back edges.
         *
         * To detect a back edge, we can keep track of vertices(or nodes) currently in recursion stack of
         * function for DFS traversal. If we reach a vertex that is already in the recursion stack,
         * then there is a cycle in the tree. The edge that connects current vertex to the vertex
         * in the recursion stack is a back edge. We have used recStack[] array to keep track of
         * vertices in the recursion stack.
         *
         * Time : O(V + E)
         **/
        private boolean isCyclic() {
            /**
             * Mark all the vertices as not visited and not part of recursion stack
             **/
            boolean[] visited = new boolean[totalNumber];
            boolean[] visiting = new boolean[totalNumber];


            /**
             * Call the recursive helper function to detect cycle in different DFS trees
             **/
            for (int i = 0; i < totalNumber; i++) {
                if (isCyclicUtil(i, visited, visiting)) {
                    return true;
                }
            }

            return false;
        }

        private boolean isCyclicUtil(int i, boolean[] visited, boolean[] visiting) {
            if (visiting[i]) {
                return true;
            }

            if (visited[i]) {
                return false;
            }

//            visited[i] = true;
            visiting[i] = true;

            List<Integer> children = map.get(i);

            for (Integer c : children) {
                if (isCyclicUtil(c, visited, visiting)) {
                    return true;
                }
            }

            visited[i] = true;
            visiting[i] = false;

            return false;
        }
    }



    /**
     * Same BFS Topological sort as Solution1, use array of List.
     *
     * Similar to LE_207_Course_Schedule,
     *
     * This solution works because it builds the neighbors list (neighbours[])
     * so that we don't need to iterate through all input data to update neighbors in degree
     */
    class Solution2 {
        public int[] findOrder_JiuZhang(int numCourses, int[][] prerequisites) {
            int[] res = new int[numCourses];
            int[] indegree = new int[numCourses];
            int count = 0;

            /**
             * define as generic ArrayList array
             * or can use HashMap
             */
            ArrayList[] neighbors = new ArrayList[numCourses];
            for (int i = 0; i < numCourses; i++) {
                neighbors[i] = new ArrayList<>();
            }

            for (int[] pair : prerequisites) {
                indegree[pair[0]]++;
                neighbors[pair[1]].add(pair[0]);
            }

            Queue<Integer> queue = new LinkedList<>();
            for (int i = 0; i < numCourses; i++) {
                if (indegree[i] == 0) {
                    queue.offer(i);
                }
            }

            while (!queue.isEmpty()) {
                int cur = queue.poll();
                res[count++] = cur;//!!!

                /**
                 * cast as ArrayList for Integer
                 */
                ArrayList<Integer> neighbours = neighbors[cur];
                for (int neighbour : neighbours) {
                    indegree[neighbour]--;
                    if (indegree[neighbour] == 0) {
                        queue.offer(neighbour);
                    }
                }
            }

            return count == numCourses ? res : new int[]{};
        }
    }

    /**
     * TLE for large input data set., just for reference
     *
     * Almost the same as LE_207_Course_Schedule, same conditions, only return result is different.
     * Here we need to return one possible ordering of courses to finish all courses.
     *
     * Time : O(V + E), Space : O(n)
     * https://www.youtube.com/watch?v=6BCRaQcx0Ng&list=PLvyIyKZVcfAkKE4fx9dz12HnEn-uzxrIK&index=10
     **/
    class Solution3 {
        public int[] findOrder(int numCourses, int[][] prerequisites) {
            int[] indegree = new int[numCourses];
            int[] res = new int[numCourses];
            int k = 0;

            //Get indgree for all nodes
            for (int[] pair : prerequisites) {
                indegree[pair[0]]++;
            }

            //Find starting nodes which have 0 indgree
            Queue<Integer> queue = new LinkedList<>();
            for (int i = 0; i < numCourses; i++) {
                if (indegree[i] == 0) {
                    queue.offer(i);
                    res[k++] = i;//!!!
                }
            }

            while (!queue.isEmpty()) {
                int cur = queue.poll();
                for (int[] pair : prerequisites) {
                    if (pair[1] == cur) {
                        indegree[pair[0]]--;
                        if (indegree[pair[0]] == 0) {
                            queue.offer(pair[0]);
                            res[k++] = pair[0];//!!!
                        }
                    }
                }
            }

            //!!!
            return k == numCourses ? res : new int[0];
        }
    }
}
