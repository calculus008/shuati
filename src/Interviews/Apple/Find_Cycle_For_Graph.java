package Interviews.Apple;

import java.util.*;

public class Find_Cycle_For_Graph {
    /**
     * 1. Cycle Detection in Directed Graph (using DFS):
     *    Use Depth-First Search (DFS) with a recursion stack.
     *    If a node is visited twice during the recursion (i.e., it's in the recursion stack), a cycle is detected.
     *
     *    Algorithm:
     *    Mark each node with 3 states: not visited, visiting, and visited.
     *    If you reach a node that's visiting, a cycle exists.
     */

    public class CycleInDirectedGraph {
        // Define the status constants
        private static final int NOT_VISITED = 0;
        private static final int VISITING = 1;
        private static final int VISITED = 2;

        public boolean hasCycle(int n, List<List<Integer>> graph) {
            int[] status = new int[n];  // Track the status of each node (0: not visited, 1: visiting, 2: visited)

            for (int i = 0; i < n; i++) {
                if (status[i] == NOT_VISITED) {
                    if (dfs(i, graph, status)) {
                        return true;  // Cycle detected
                    }
                }
            }
            return false;  // No cycle detected
        }

        // Helper DFS method
        private boolean dfs(int node, List<List<Integer>> graph, int[] status) {
            // If we're visiting a node that is already being visited, a cycle is detected
            if (status[node] == VISITING) {
                return true;
            }

            // Mark this node as visiting
            status[node] = VISITING;

            // Visit all neighbors
            for (int neighbor : graph.get(node)) {
                if (status[neighbor] != VISITED) {
                    if (dfs(neighbor, graph, status)) {
                        return true;
                    }
                }
            }

            // Mark this node as visited
            status[node] = VISITED;

            return false;
        }
    }

    /**
     * Cycle Detection in Undirected Graph (using DFS or Union-Find):
     * DFS: If you revisit a node that is not the parent of the current node, a cycle exists.
     * Union-Find: Check if two nodes being connected are already part of the same set.
     */

    public class CycleInUndirectedGraph {
        public boolean hasCycle(int n, List<List<Integer>> graph) {
            boolean[] visited = new boolean[n];

            // Loop through all nodes, as the graph might be disconnected
            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    // Start DFS from node `i`
                    if (dfs(i, -1, visited, graph)) {
                        return true;
                    }
                }
            }

            return false;  // No cycle found
        }

        // DFS to detect cycle in an undirected graph
        private boolean dfs(int current, int parent, boolean[] visited, List<List<Integer>> graph) {
            visited[current] = true;  // Mark current node as visited

            for (int neighbor : graph.get(current)) {
                if (!visited[neighbor]) {
                    // Recur for the unvisited neighbor
                    if (dfs(neighbor, current, visited, graph)) {
                        return true;  // Cycle detected
                    }
                } else if (neighbor != parent) {
                    // If the neighbor is visited and isn't the parent, it's a cycle
                    return true;
                }
            }

            return false;  // No cycle found
        }
    }

}
