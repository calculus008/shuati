package leetcode;

/**
 * Created by yuank on 3/9/18.
 */
public class LE_98_Validate_BST {
    /*
        Given a binary tree, determine if it is a valid binary search tree (BST).

        Assume a BST is defined as follows:

        The left subtree of a node contains only nodes with keys less than the node's key.
        The right subtree of a node contains only nodes with keys greater than the node's key.
        Both the left and right subtrees must also be binary search trees.
        Example 1:
            2
           / \
          1   3
        Binary tree [2,1,3], return true.
        Example 2:
            1
           / \
          2   3
        Binary tree [1,2,3], return false.
     */

    //Time and Space : O(n)
    public static boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        return helper(root, null, null);
    }

    //!!! pass min and max as object Integer
    public static boolean helper(TreeNode root, Integer min, Integer max) {
        if (root == null) return true;
        if (min != null && root.val <= min) return false;
        if (max != null && root.val >= max) return false;
        return (helper(root.left, min, root.val) && helper(root.right, root.val, max));
    }
}
