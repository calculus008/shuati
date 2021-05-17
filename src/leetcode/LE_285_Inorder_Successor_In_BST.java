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

    /**
     * Naive way :
     * 1.Find p in BST
     * 2.Successor of p is the smallest element in its right subtree
     *
     * Then those 2 steps are combined.
     *
     * When we decide which subtree to go, use "<=". "=" means
     * we find p, then we still need to go to its right side.
     *
     * If its right side child R is null, return null, there's no such successor.
     *
     * If its right side child R is not null, its value must bigger than p.val,
     * now we move to its left side.
     *
     * Here, if the left is null, then R is the answer.
     *       if the left is not null, keep recursing, until it hits null, we find the last parent of a left child.
     *
     * example : find successor
     *
     *           3
     *          / \        3.return 4
     *         2  5
     *           / \       2.return  4
     *          4   6
     *          \          1.return null
     *          null
     *
     *
     *          3
     *         / \         2.return 4
     *        2  4
     *          /          1.return null
     *         null
     */
    /**
     * inorder, the successor is the left most node in the right subtree
     *
     * 走之字形。
     */
    //Solution 1 : Iterative
    public TreeNode inorderSuccessor1(TreeNode root, TreeNode p) {
        TreeNode res = null;

        while (root != null) {
            //!!! "<=", for "If the given node has no in-order successor in the tree, return null"
            if (root.val <= p.val) {//This "if" logic uses the property of BST
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
        if (root == null) {
            return null;
        }

        /**
         * !!!
         * "root.val <= p.val", it's "<=", not "<"
         */
        if (root.val <= p.val) {
            return inorderSuccessor(root.right, p);
        } else {
            TreeNode temp = inorderSuccessor(root.left, p);

            /**
             * check returned node, "temp == null" is the case that
             * p is the left child of root.
             */
            return temp != null ? temp : root; //!!! check if temp is null
        }
    }

    /**
     * Find Predecessor
     *
     * public TreeNode predecessor(TreeNode root, TreeNode p) {
     *   if (root == null)
     *     return null;
     *
     *   if (root.val >= p.val) {
     *     return predecessor(root.left, p);
     *   } else {
     *     TreeNode right = predecessor(root.right, p);
     *     return (right != null) ? right : root;
     *   }
     * }
     */
}
