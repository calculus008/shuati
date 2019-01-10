package leetcode;

import common.TreeNode;

/**
 * Created by yuank on 4/19/18.
 */
public class LE_285_Inorder_Successor_In_BST {
    /**
     * Given a binary search tree and a node in it, find the in-order successor of that node in the BST.

       Note: If the given node has no in-order successor in the tree, return null.
     */

    //Solution 1 : Iterative
    public TreeNode inorderSuccessor1(TreeNode root, TreeNode p) {
        TreeNode res = null;
        while (root != null) {
            //!!! "<=", for "If the given node has no in-order successor in the tree, return null"
            if (root.val <= p.val) {//This if logic uses the property of BST
                root = root.right;
            } else {//inorder, the successor is the left most node in the right subtree
                res = root;
                root = root.left;
            }
        }
        return res;
    }

    //Solution 2 : Recursion
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null) return null;
        if (root.val <= p.val) {
            return inorderSuccessor(root.right, p);
        } else {
            TreeNode temp = inorderSuccessor(root.left, p);
            return temp != null ? temp : root; //!!! check if temp is null
        }
    }
}
