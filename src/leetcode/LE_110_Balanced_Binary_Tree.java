package leetcode;

/**
 * Created by yuank on 3/11/18.
 */
public class LE_110_Balanced_Binary_Tree {
    /*
        Given a binary tree, determine if it is height-balanced.

        For this problem, a height-balanced binary tree is defined as:

        a binary tree in which the depth of the two subtrees of every node never differ by more than 1.

        Example 1:

        Given the following tree [3,9,20,null,null,15,7]:

            3
           / \
          9  20
            /  \
           15   7
        Return true.

        Example 2:

        Given the following tree [1,2,2,3,3,null,null,4,4]:

               1
              / \
             2   2
            / \
           3   3
          / \
         4   4
        Return false.
     */

    public static boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        return depth(root) == -1 ? false : true;
    }

    public static int depth(TreeNode root) {
        if (root == null) return 0;

        int l = depth(root.left);
        int r = depth(root.right);

        if (l == -1 || r == -1 || Math.abs(l - r) > 1) return -1;

        return Math.max(l, r) + 1;
    }
}
