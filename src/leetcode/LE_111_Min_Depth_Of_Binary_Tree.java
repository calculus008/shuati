package leetcode;

import common.TreeNode;

/**
 * Created by yuank on 3/11/18.
 */
public class LE_111_Min_Depth_Of_Binary_Tree {
    /**
        Given a binary tree, find its minimum depth.

        The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.
     */

    public int minDepth(TreeNode root) {
        if (root == null) return 0;

        /**
         * !!!
         **/
        if (root.left == null || root.right == null) {
            return Math.max(minDepth(root.left), minDepth(root.right)) + 1;
        }

        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }
}
