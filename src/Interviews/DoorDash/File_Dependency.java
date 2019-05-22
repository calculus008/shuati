package Interviews.DoorDash;

import java.util.*;

public class File_Dependency {
    /**
     * copy from LE_210_Course_Schedule_II
     */
    static Map<Integer, List<Integer>> map;
    static int totalNumber;

    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        if (numCourses < 0 || prerequisites == null) {
            return new int[]{};
        }

        int[] res = new int[numCourses];

        totalNumber = numCourses;
        map = new HashMap<>();
        for (int i = 0; i < numCourses; i++) {
            map.put(i, new ArrayList<>());
        }

        int[] indegree = new int[numCourses];

        for (int[] p : prerequisites) {
            /**
             * !!!
             * Test cases use number starting from 1,
             * here the index is zero based, so we
             * need to map from 1-based to 0-based.
             **/
            int from = p[1] - 1;
            int to = p[0] - 1;

            indegree[to]++;
            map.get(from).add(to);
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < indegree.length; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }

        int count = 0;
        while (!q.isEmpty()) {
            int cur = q.poll();
            res[count++] = cur + 1;

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
    private static boolean isCyclic() {
        /**
         * Mark all the vertices as not visited and not part of recursion stack
         **/
        boolean[] visited = new boolean[totalNumber];
        boolean[] visiting = new boolean[totalNumber];


        /**
         * Call the recursive helper function to detect cycle in different DFS trees
         **/
        for (int i = 0; i < totalNumber; i++) {
            Map<Integer, Integer> path = new HashMap<>();
            if (isCyclicUtil(i, visited, visiting, path)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isCyclicUtil(int i, boolean[] visited, boolean[] visiting, Map<Integer, Integer> path) {
        if (visiting[i]) {
            printCycle(path, i);
            return true;
        }

        if (visited[i]) {
            return false;
        }

//            visited[i] = true;
        visiting[i] = true;

        List<Integer> children = map.get(i);

        for (Integer c : children) {
            path.put(c, i);
            if (isCyclicUtil(c, visited, visiting, path)) {
                return true;
            }
            path.remove(c);
        }

        visited[i] = true;
        visiting[i] = false;

        return false;
    }

    private static void printCycle(Map<Integer, Integer> path, int start) {
        StringBuilder sb = new StringBuilder();
        int cur = start;

        sb.append(cur + 1).append("-").append(path.get(cur) + 1);//map to 1-based
        cur = path.get(cur);

        while (cur != start) {
            cur = path.get(cur);
            sb.append("-").append(cur + 1);//map to 1-based
        }

        System.out.println(sb.reverse().toString());
     }

    public static void main(String[] args) {
        int[][] input = {{2, 1}, {3, 2}, {1, 4}, {5, 4}, {4, 6}, {5, 6}};
        int[] res = findOrder(6, input);
        System.out.println(Arrays.toString(res));
        System.out.println(isCyclic());

        map.clear();
        int[][] input1 = {{2, 1}, {3, 2}, {1, 4}, {4, 5}, {6, 4}, {5, 6}};
        int[] res1 = findOrder(6, input1);
        System.out.println(Arrays.toString(res1));
        System.out.println(isCyclic());

    }
}
