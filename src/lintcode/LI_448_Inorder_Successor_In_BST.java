package lintcode;

import common.TreeNode;

/**
 * Created by yuank on 7/26/18.
 */
public class LI_448_Inorder_Successor_In_BST {
    /**
         Given a binary search tree (See Definition) and a node in it, find the in-order successor of that node in the BST.

         If the given node has no in-order successor in the tree, return null.

         Example
         Given tree = [2,1] and node = 1:

         2
         /
         1
         return node 2.

         Given tree = [2,1,3] and node = 2:

         2
         / \
         1   3
         return node 3.

         Challenge
         O(h), where h is the height of the BST.

         Medium
     */

    /**
     * Modified from LI_915_Inorder_Predecessor_In_BST
     *
     * In stead of remembering the last node turning right, we remember last node turning left
     */
    TreeNode turnLeft = null;
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null) {
            return null;
        }

        if (p.val > root.val) {
            return inorderSuccessor(root.right, p);
        } else if (p.val < root.val) {
            turnLeft = root;
            return inorderSuccessor(root.left, p);
        } else {
            if (root.right != null) {
                TreeNode cur = root.right;
                while (cur.left != null) {
                    cur = cur.left;
                }

                return cur;
            } else {
                return turnLeft;
            }

        }
    }
}
