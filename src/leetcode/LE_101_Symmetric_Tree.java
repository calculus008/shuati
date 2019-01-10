package leetcode;

import common.TreeNode;

/**
 * Created by yuank on 3/11/18.
 */
public class LE_101_Symmetric_Tree {
    /*
        Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).

        For example, this binary tree [1,2,2,3,4,4,3] is symmetric:

            1
           / \
          2   2
         / \ / \
        3  4 4  3
        But the following [1,2,2,null,3,null,3] is not:
            1
           / \
          2   2
           \   \
           3    3
        Note:
        Bonus points if you could solve it both recursively and iteratively.

     */

    public static boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return helper(root.left, root.right);
    }

    public static boolean helper(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;

        if (p.val != q.val) return false;
        return helper(p.left, q.right) && helper(p.right, q.left);
    }
}
