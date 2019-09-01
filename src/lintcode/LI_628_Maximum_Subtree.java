package lintcode;

import common.TreeNode;

public class LI_628_Maximum_Subtree {
    /**
     * Given a binary tree, find the subtree with maximum sum. Return the root of the subtree.
     *
     * Example 1:
     *
     * Input:
     * {1,-5,2,0,3,-4,-5}
     * Output:3
     * Explanation:
     * The tree is look like this:
     *      1
     *    /   \
     *  -5     2
     *  / \   /  \
     * 0   3 -4  -5
     * The sum of subtree 3 (only one node) is the maximum. So we return 3.
     * Example 2:
     *
     * Input:
     * {1}
     * Output:1
     * Explanation:
     * The tree is look like this:
     *    1
     * There is one and only one subtree in the tree. So we return 1.
     *
     * Easy
     */

    int max = Integer.MIN_VALUE;
    TreeNode node;

    public TreeNode findSubtree(TreeNode root) {
        if (root == null) return null;
        helper(root);
        return node;
    }

    public int helper(TreeNode root) {
        if (root == null) return 0;

        int sum = helper(root.left) + helper(root.right) + root.val;
        if (sum > max) {
            max = sum;
            node = root;
        }

        return sum;
    }
}
