package leetcode;

import common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class LE_671_Second_Minimum_Node_In_A_Binary_Tree {
    /**
     * Given a non-empty special binary tree consisting of nodes with the non-negative value,
     * where each node in this tree has exactly two or zero sub-node. If the node has two
     * sub-nodes, then this node's value is the smaller value among its two sub-nodes.
     *
     * Given such a binary tree, you need to output the second minimum value in the set
     * made of all the nodes' value in the whole tree.
     *
     * If no such second minimum value exists, output -1 instead.
     *
     * Example 1:
     * Input:
     *     2
     *    / \
     *   2   5
     *      / \
     *     5   7
     *
     * Output: 5
     * Explanation: The smallest value is 2, the second smallest value is 5.
     *
     * Example 2:
     * Input:
     *     2
     *    / \
     *   2   2
     *
     * Output: -1
     * Explanation: The smallest value is 2, but there isn't any second smallest value.
     *
     * Easy
     */

    /**
     * http://zxi.mytechroad.com/blog/leetcode/leetcode-671-second-minimum-node-in-a-binary-tree/
     *
     * DFS
     * Time and Space : O(n)
     *
     * 特性：
     * 1.给定的Root的值一定是最小的。
     * 2.Children nodes的值一定大于等于parent node 的值。
     *
     * pre-order
     *
     * Time and Space : O(n)
     */
    class Solution_DFS_1 {
        public int findSecondMinimumValue(TreeNode root) {
            if (root == null) {
                return -1;
            }

            return dfs(root, root.val);
        }

        private int dfs(TreeNode root, int s1) {
            if (root == null) {
                return -1;
            }

            /**
             * !!!
             * s1 is the the value of the parent for current node.
             *
             * Since children's values are no less than root's value,
             * so we already find 2nd min element, no need to go further
             * into its branches.
             */
            if (root.val > s1) {
                return root.val;
            }

            int l = dfs(root.left, s1);
            int r = dfs(root.right, s1);

            if (l == -1) return r;
            if (r == -1) return l;

            return Math.min(l, r);
        }
    }

    class Solution_DFS_2 {
        int min1;
        long ans = Long.MAX_VALUE;

        public int findSecondMinimumValue(TreeNode root) {
            min1 = root.val;
            dfs(root);
            return ans < Long.MAX_VALUE ? (int) ans : -1;
        }

        public void dfs(TreeNode root) {
            if (root == null) return;

            if (min1 < root.val && root.val < ans) {
                ans = root.val;
            } else if (min1 == root.val) {
                dfs(root.left);
                dfs(root.right);
            }
        }
    }

    /**
     * BFS
     */
    class Solution2 {
        public int findSecondMinimumValue(TreeNode root) {
            if (root == null) return -1;

            int s1 = root.val;
            int s2 = Integer.MAX_VALUE;

            boolean found = false;
            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);

            while(!q.isEmpty()) {
                TreeNode cur = q.poll();
                if (cur.val > s1 && cur.val < s2) {
                    s2 = cur.val;
                    found = true;
                    continue;
                }

                if (cur.left == null) {
                    continue;
                }

                q.offer(cur.left);
                q.offer(cur.right);
            }

            return found ? s2 : -1;
        }
    }

    /**
     * If we try to find kth min, we probably need to traverse the whole tree.
     *
     * Use a TreeMap<Integer, Integer>, key is node.val, value is number of times it appears in tree.
     *
     * Then go over the TreeMap to find kth min.
     *
     * Also see brutal force solution (use set)  in https://leetcode.com/articles/second-minimum-node-in-a-binary-tree/
     *
     */
}