package leetcode;

import common.TreeNode;

import java.util.*;

public class LE_742_Closest_Leaf_In_A_Binary_Tree {
    /**
         Given a binary tree where every node has a unique value, and a target key k,
         find the value of the nearest leaf node to target k in the tree.

         Here, nearest to a leaf means the least number of edges travelled on the binary tree
         to reach any leaf of the tree. Also, a node is called a leaf if it has no children.

         In the following examples, the input tree is represented in flattened form row by row.
         The actual root tree given will be a TreeNode object.

         Example 1:
         Input:
         root = [1, 3, 2], k = 1
         Diagram of binary tree:
           1
          / \
         3   2

         Output: 2 (or 3)

         Explanation: Either 2 or 3 is the nearest leaf node to the target of 1.


         Example 2:
         Input:
         root = [1], k = 1
         Output: 1

         Explanation: The nearest leaf node is the root node itself.
         Example 3:

         Input:
         root = [1,2,3,4,null,null,null,5,null,6], k = 2
         Diagram of binary tree:
                 1
                / \
               2   3
              /
             4
            /
           5
          /
         6

         Output: 3
         Explanation: The leaf node with value 3 (and not the leaf node with value 6)
         is nearest to the node with value 2.


         Note:
         root represents a binary tree with at least 1 node and at most 1000 nodes.
         Every node has a unique node.val in range [1, 1000].
         There exists some node in the given binary tree for which node.val == k.

         Medium
     */

    /**
     * http://zxi.mytechroad.com/blog/graph/leetcode-802-find-eventual-safe-states/
     *
     * Convert binary tree into graph, BFS find the first leaf node
     *
     * Time and Space : O(n)
     */
    class Solution {
        //Must be glbal here
        TreeNode start;

        public int findClosestLeaf(TreeNode root, int k) {
            Map<Integer, List<TreeNode>> graph = new HashMap<>();
            buildGraph(root, k, null, graph);

            Queue<TreeNode> q = new LinkedList<>();
            q.offer(start);
            Set<Integer> visited = new HashSet<>();
            visited.add(k);

            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    TreeNode cur = q.poll();
                    int key = cur.val;

                    if (cur.left == null && cur.right == null) {
                        return key;
                    }

                    for (TreeNode next : graph.getOrDefault(key, new ArrayList<>())) {
                        if (visited.contains(next.val)) {
                            continue;
                        }
                        visited.add(next.val);
                        q.offer(next);
                    }
                }
            }

            return -1;
        }

        /**
         * DFS Build graph for a binary tree
         */
        private void buildGraph(TreeNode node, int k, TreeNode parent, Map<Integer, List<TreeNode>> graph) {
            if (null == node) {
                return;
            }

            if (node.val == k) {
                start = node;
            }

            if (parent != null) {
                if (!graph.containsKey(node.val)) {
                    graph.put(node.val, new ArrayList<>());
                }
                graph.get(node.val).add(parent);
                if (!graph.containsKey(parent.val)) {
                    graph.put(parent.val, new ArrayList<>());
                }
                graph.get(parent.val).add(node);
            }

            buildGraph(node.left,  k, node, graph);
            buildGraph(node.right, k, node, graph);
        }
    }

    class Solution_Practice{
        Map<Integer, List<TreeNode>> map;
        TreeNode start;

        public int findClosestLeaf(TreeNode root, int k) {
            map = new HashMap<>();
            dfs(root, null, k);
            return bfs();
        }

        /**
         * Build graph from a binary tree, same as LE_863_All_Nodes_Distance_K_In_Binary_Tree,
         * only difference is that we also need to find the starting TreeNode here.
         */
        private void dfs(TreeNode root, TreeNode parent, int k) {
            if (root == null) return;

            if (root.val == k) {
                start = root;
            }

            if (!map.containsKey(root.val)) {
                map.put(root.val, new ArrayList<>());

                if (parent != null) {
                    map.get(root.val).add(parent);
                    map.get(parent.val).add(root);
                }

                dfs(root.left, root, k);
                dfs(root.right, root, k);
            }
        }

        private int bfs() {
            Queue<TreeNode> q = new LinkedList<>();
            q.offer(start);

            Set<Integer> visited = new HashSet<>();
            visited.add(start.val);

            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    TreeNode cur = q.poll();

                    if (cur.left == null && cur.right == null) {
                        return cur.val;
                    }

                    for (TreeNode node : map.get(cur.val)) {
                        if (visited.contains(node.val)) continue;

                        /**
                         * !!!
                         */
                        visited.add(node.val);
                        q.offer(node);
                    }
                }
            }

            return 0;
        }
    }
}