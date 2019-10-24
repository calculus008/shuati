package lintcode;

import common.TreeNode;

public class LI_661_Convert_BST_To_Greater_Tree {
    /**
     * Given a Binary Search Tree (BST), convert it to a Greater Tree such that every key of the original BST is changed to the original key plus sum of all keys greater than the original key in BST.
     *
     * Example
     * Example 1:
     *
     * Input : {5,2,13}
     *               5
     *             /   \
     *            2     13
     * Output : {18,20,13}
     *              18
     *             /   \
     *           20     13
     * Example 2:
     *
     * Input : {5,3,15}
     *               5
     *             /   \
     *            3     15
     * Output : {20,23,15}
     *              20
     *             /   \
     *           23     15
     *
     * Easy
     */

    public class Solution {
        int sum = 0;
        public TreeNode convertBST(TreeNode root) {
            if (root == null) return root;

            convertBST(root.right);

            root.val += sum;
            sum = root.val;//!!!

            convertBST(root.left);

            return root;
        }
    }
}
