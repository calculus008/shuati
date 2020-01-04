package Interviews.Paypal;

import common.TreeNode;

public class CountNoneLeaveNodeBT {
    /**
     * Given a Binary tree, count total number of non-leaf nodes in the tree
     *
     * https://www.geeksforgeeks.org/count-non-leaf-nodes-binary-tree/
     */


    public int count(TreeNode root) {
        if (root == null || root.left == null && root.right == null) return 0;

        return 1 +  count(root.left) + count(root.right);
    }
}
