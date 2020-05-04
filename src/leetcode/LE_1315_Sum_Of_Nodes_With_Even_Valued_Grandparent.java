package leetcode;

import common.TreeNode;

public class LE_1315_Sum_Of_Nodes_With_Even_Valued_Grandparent {
    /**
     * Given a binary tree, return the sum of values of nodes with even-valued grandparent.
     * (A grandparent of a node is the parent of its parent, if it exists.)
     *
     * If there are no nodes with an even-valued grandparent, return 0.
     *
     * Constraints:
     *
     * The number of nodes in the tree is between 1 and 10^4.
     * The value of nodes is between 1 and 100.
     *
     * Medium
     */

    class Solution {
        int sum = 0;
        public int sumEvenGrandparent(TreeNode root) {
            if (root == null) return 0;

            helper(root, new int[]{-1, -1});

            return sum;
        }

        private void helper(TreeNode node, int[] vals) {
            if (node == null) return;

            if (vals[0] > 0 && (vals[0] & 1) == 0) {
                sum += node.val;
            }

            helper(node.left, new int[]{vals[1], node.val});
            helper(node.right, new int[]{vals[1], node.val});
        }
    }
}
