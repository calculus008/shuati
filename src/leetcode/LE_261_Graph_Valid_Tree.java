package leetcode;

import common.UnionFindSet;

import java.util.*;

/**
 * Created by yuank on 4/23/18.
 */
public class LE_261_Graph_Valid_Tree {
    /**
         Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
         write a function to check whether these edges make up a valid tree.

         For example:

         Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.

         Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.

         Note: you can assume that no duplicate edges will appear in edges. Since all edges are undirected,
         [0, 1] is the same as [1, 0] and thus will not appear together in edges.

        Medium
     */

    class Solution_editorial_DFS_with_cycle_detection {

        private List<List<Integer>> adjacencyList = new ArrayList<>();
        private Set<Integer> visited = new HashSet<>();

        public boolean validTree(int n, int[][] edges) {
            if (edges.length != n - 1) return false;

            for (int i = 0; i < n; i++) {
                adjacencyList.add(new ArrayList<>());
            }
            for (int[] edge : edges) {
                adjacencyList.get(edge[0]).add(edge[1]);
                adjacencyList.get(edge[1]).add(edge[0]);
            }

            // We return true iff no cycles were detected,
            // AND the entire graph has been reached.
            return dfs(0, -1) && visited.size() == n;
        }

        public boolean dfs(int node, int parent) {
            if (visited.contains(node)) return false; //has been visited, cycle detected, return false
            visited.add(node);
            for (int neighbour : adjacencyList.get(node)) {
                if (parent != neighbour) { // avoid "trivial cycle"
                    boolean result = dfs(neighbour, node);
                    if (!result) return false;
                }
            }
            return true;
        }
    }

    class Solution_editorial_DFS {
        private List<List<Integer>> adjacencyList = new ArrayList<>();
        private Set<Integer> visited = new HashSet<>();

        public boolean validTree(int n, int[][] edges) {
            if (edges.length != n - 1) return false; //!!!For the graph to be a valid tree, it must have exactly n - 1 edges

            for (int i = 0; i < n; i++) {// Make the adjacency list.
                adjacencyList.add(new ArrayList<>());
            }
            for (int[] edge : edges) {
                adjacencyList.get(edge[0]).add(edge[1]);
                adjacencyList.get(edge[1]).add(edge[0]);
            }

            dfs(0);

            return visited.size() == n;// Inspect result and return the verdict.
        }

        public void dfs(int node) {
            if (visited.contains(node)) return;
            visited.add(node);
            for (int neighbour : adjacencyList.get(node)) {
                dfs(neighbour);
            }
        }
    }

    public class Solution_DFS_detect_cycle {
        public boolean validTree(int n, int[][] edges) {
            List<List<Integer>> adjList = new ArrayList<List<Integer>>(n);  // initialize adjacency list

            for (int i = 0; i < n; i++) {
                adjList.add(i, new ArrayList<Integer>());
            }

            for (int i = 0; i < edges.length; i++) {// add edges, undirected, so add two ways
                int u = edges[i][0], v = edges[i][1];
                adjList.get(u).add(v);
                adjList.get(v).add(u);
            }

            boolean[] visited = new boolean[n];

            if (hasCycle(adjList, 0, visited, -1)) { // make sure there's no cycle, DFS, starting from node 0
                return false;
            }

            for (int i = 0; i < n; i++) {// make sure all vertices are connected
                if (!visited[i])
                    return false;
            }

            return true;
        }

        boolean hasCycle(List<List<Integer>> adjList, int u, boolean[] visited, int parent) {
            visited[u] = true;

            for (int i = 0; i < adjList.get(u).size(); i++) {
                int v = adjList.get(u).get(i);

                if ((visited[v] && parent != v) || (!visited[v] && hasCycle(adjList, v, visited, u)))
                    return true;
            }

            return false;
        }
    }

    /**
     * Uion Find
     *
     * Time : O(E + V)
     * Space : O(n)
     *
     * As a tree is connected, all nodes should point to a single parent eventually
     */

    class Solution1 {
        public boolean validTree(int n, int[][] edges) {
            /**
             * !!!
             */
            if (n == 1 && edges.length == 0) return true;
            /**!!!
             *  if there's no loop, number of edges equals number of nodes minus 1
             **/
            if (n < 1 || edges.length != n - 1) return false;

            UnionFindSet ufs = new UnionFindSet(n);
            for (int[] edge : edges) {
                /**if two nodes on the same edge are already in the same cluster - meaning the edges forms a loop,
                 * union() will return false
                 *
                 * By identifying two connected nodes (which has an edge between them) has the same parents,
                 * you will know if there is a circle in the tree. If so, the tree is not valid.
                 * **/
                if (!ufs.union(edge[0], edge[1])) {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * Union Find solution without using UnionFindSet class
     */
    public class Solution2 {
        public boolean validTree(int n, int[][] edges) {
            // initialize n isolated islands
            int[] nums = new int[n];
            Arrays.fill(nums, -1);

            // perform union find
            for (int i = 0; i < edges.length; i++) {
                int x = find(nums, edges[i][0]);
                int y = find(nums, edges[i][1]);

                // if two vertices happen to be in the same set
                // then there's a cycle
                if (x == y) return false;

                // union
                nums[x] = y;
            }

            return edges.length == n - 1;
        }

        int find(int nums[], int i) {
            if (nums[i] == -1) return i;
            return find(nums, nums[i]);
        }
    }

    /**
     * Graph DFS solution with adjacency list
     */
    public class Solution3 {
        public boolean validTree(int n, int[][] edges) {
            // initialize adjacency list
            List<List<Integer>> adjList = new ArrayList<List<Integer>>(n);

            // initialize vertices
            for (int i = 0; i < n; i++) {
                adjList.add(i, new ArrayList<Integer>());
            }

            // add edges
            for (int i = 0; i < edges.length; i++) {
                int u = edges[i][0], v = edges[i][1];
                adjList.get(u).add(v);
                adjList.get(v).add(u);
            }

            /**
             * !!!
             */
            boolean[] visited = new boolean[n];

            // make sure there's no cycle, DFS, starting from node 0
            if (hasCycle(adjList, 0, visited, -1)) {
                return false;
            }

            /**!!!
             * make sure all vertices are connected
             */
            for (int i = 0; i < n; i++) {
                if (!visited[i])
                    return false;
            }

            return true;
        }

        /**
         * !!!
         * check if an undirected graph has cycle started from vertex u
         *
         * Important, will be in followup questions.
         * **/
        boolean hasCycle(List<List<Integer>> adjList, int u, boolean[] visited, int parent) {
            visited[u] = true;

            for (int i = 0; i < adjList.get(u).size(); i++) {
                int v = adjList.get(u).get(i);

                if ((visited[v] && parent != v) || (!visited[v] && hasCycle(adjList, v, visited, u)))
                    return true;
            }

            return false;
        }
    }

    class Solution_Practice {
        /**
         * UFS without merge by rank
         */
        class UnionFindSet {
            int[] parents;

            public UnionFindSet(int size) {
                parents = new int[size + 1];
                /**
                 * !!!
                 * init parents[]
                 */
                for (int i = 0; i < parents.length; i++) {
                    parents[i] = i;
                }
            }

            public int find(int n) {
                while (parents[n] != n) {
                    parents[n] = parents[parents[n]];//path compression
                    n = parents[n];
                }
                /**
                 * !!!
                 * return n
                 */
                return n;
            }

            public boolean union(int u, int v) {
                int x = find(u);
                int y = find(v);

                if (x == y) {
                    return false;
                }

                parents[x] = y;//!!! no merge by rank logic here

                return true;
            }
        }

        public boolean validTree(int n, int[][] edges) {
            if (n == 1 && edges.length == 0) {
                return true;
            }

            if (n < 1 || edges.length != n - 1) {
                return false;
            }

            UnionFindSet ufs = new UnionFindSet(n);
            for (int[] edge : edges) {
                if (!ufs.union(edge[0], edge[1])) {
                    return false;
                }
            }

            return true;
        }
    }
}
