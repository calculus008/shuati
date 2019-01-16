package leetcode;

import common.TreeNode;

/**
 * Created by yuank on 3/11/18.
 */
public class LE_104_Max_Depth_Of_Binary_Tree {
    /**
        Given a binary tree, find its maximum depth.

        The maximum depth is the number of nodes along
        the longest path from the root node down to the farthest leaf node.

        For example:
        Given binary tree [3,9,20,null,null,15,7],

            3
           / \
          9  20
            /  \
           15   7
     */

    //Time and Space : O(n)
    public static int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }
}
