package leetcode;

import common.TreeNode;

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
     * Time and Space : O(n)
     */
    class Solution {
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
}