package leetcode;

import common.TreeNode;

public class LE_1080_Insufficient_Nodes_In_Root_To_Leaf_Paths {
    /**
     * Given the root of a binary tree, consider all root to leaf paths: paths from the root to any leaf.
     * (A leaf is a node with no children.)
     *
     * A node is insufficient if every such root to leaf path intersecting this node has sum strictly
     * less than limit.
     *
     * Delete all insufficient nodes simultaneously, and return the root of the resulting binary tree.
     *
     * Note:
     *
     * The given tree will have between 1 and 5000 nodes.
     * -10^5 <= node.val <= 10^5
     * -10^9 <= limit <= 10^9
     *
     * Medium
     */

    /**
     * https://leetcode.com/problems/insufficient-nodes-in-root-to-leaf-paths/discuss/308326/JavaC%2B%2BPython-Easy-and-Concise-Recursion
     *
     * Key points:
     * 1.Don't get tangled with idea with compute path sum and compare sum on the way. The node
     *   value can be negative, so no point comparing sum results along the way.
     * 2.Convert sum compare to compare remaining value with the last node (leaf node) on a path.
     * 3.Start thinking about the base case - what happened if we only have a leaf node?
     * 4."return root.left == null && root.right == null ? null : root;"
     *   Base on definition - "A node is insufficient if every such root to leaf path intersecting this node
     *                         has sum strictly less than limit"
     *   Both root.left and root.right are now computed recursively, null value means they are both "insufficient".
     *   Therefore root itself is also "insufficient".
     *
     * Lesson:
     * For tree problem, go recursive, think of local case, base-case.
     */
    class Solution {
        public TreeNode sufficientSubset(TreeNode root, int limit) {
            if (root == null) return null;

            if (root.left == null && root.right == null) {
                return root.val < limit ? null : root;
            }

            root.left = sufficientSubset(root.left, limit - root.val);
            root.right = sufficientSubset(root.right, limit - root.val);

            return root.left == null && root.right == null ? null : root;
        }
    }
}
