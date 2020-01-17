package src.leetcode;

public class LE_965_Univalued_Binary_Tree {
    /**
     * A binary tree is univalued if every node in the tree has the same value.
     *
     * Return true if and only if the given tree is univalued.
     *
     * Easy
     */

    class Solution {
        public boolean isUnivalTree(common.TreeNode root) {
            if (root == null) return true;

            if ((root.left != null && root.val != root.left.val)
                    || (root.right != null && root.val != root.right.val)) {
                return false;
            }

            return isUnivalTree(root.left) && isUnivalTree(root.right);
        }
    }
}
