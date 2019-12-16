package Interviews.Paypal;

import common.TreeNode;

public class SumOfLeaveNodes {
    /**
     * Given a binary tree, find the sum of all the leaf nodes.
     *
     * Examples:
     *
     * Input :
     *         1
     *       /   \
     *      2     3
     *     / \   / \
     *    4   5 6   7
     *           \
     *            8
     * Output :
     * Sum = 4 + 5 + 8 + 7 = 24
     *
     * https://www.geeksforgeeks.org/sum-leaf-nodes-binary-tree/
     */

    int sum = 0;
    public void sum(TreeNode root) {
        if (root == null)  return;

        if (root.left == null && root.right == null) {
            sum += root.val;
            return;
        }

        sum(root.left);
        sum(root.right);
    }
}
