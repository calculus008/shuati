package leetcode;

import common.TreeNode;

import java.util.*;

public class LE_863_All_Nodes_Distance_K_In_Binary_Tree {
    /**
         We are given a binary tree (with root node root), a target node, and an integer value K.

         Return a list of the values of all nodes that have a distance K from the target node.
         The answer can be returned in any order.

         Example 1:

         Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, K = 2

         Output: [7,4,1]

         Explanation:
         The nodes that are a distance 2 from the target node (with value 5)
         have values 7, 4, and 1.

         Note that the inputs "root" and "target" are actually TreeNodes.
         The descriptions of the inputs above are just serializations of these objects.

         Note:
         The given tree is non-empty.
         Each node in the tree has unique values 0 <= node.val <= 500.
         The target node is a node in the tree.
         0 <= K <= 1000.

         Medium
     */

    /**
     * http://zxi.mytechroad.com/blog/dynamic-programming/leetcode-329-longest-increasing-path-in-a-matrix/
     *
     * DFS + BFS
     *
     * 1.DFS to build a undirected graph
     * 2.BFS traverse the graph, collect all nodes K steps from target.
     *
     * Time  : O(n)
     * Space : O(n)
     */
    class Solution {
        public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
            Map<Integer, List<TreeNode>> graph = new HashMap<>();
            List<Integer> res = new ArrayList<>();

            buildGraph(root, null, graph);

            bfs(target, K, graph, res);

            return res;
        }

        /**
         * !!!
         * Build a graph (adjacent list) from binary tree.
         *
         * For binary tree, each node can have at most 3 neighbors.
         * We can go top down.
         */
        private void buildGraph(TreeNode node, TreeNode parent, Map<Integer, List<TreeNode>> graph) {
            if (node == null) {
                return;
            }

            if (!graph.containsKey(node.val)) {
                graph.put(node.val, new ArrayList<>());

                if (parent != null) {
                    graph.get(node.val).add(parent);
                    graph.get(parent.val).add(node);
                }

                buildGraph(node.left, node, graph);
                buildGraph(node.right, node, graph);
            }
        }

        private void bfs(TreeNode target, int K, Map<Integer, List<TreeNode>> graph, List<Integer> res) {
            Queue<TreeNode> q = new LinkedList<>();
            q.offer(target);

            Set<Integer> visited = new HashSet<>();
            visited.add(target.val);

            int steps = 0;

            while (!q.isEmpty() && steps <= K) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    TreeNode cur = q.poll();

                    if (steps == K) {
                        res.add(cur.val);
                    }

                    for (TreeNode t : graph.get(cur.val)) {
                        if (visited.contains(t.val)) {
                            continue;
                        }

                        q.offer(t);
                        visited.add(t.val);
                    }
                }
                steps++;
            }}
    }
}