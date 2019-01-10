package lintcode;

import common.TreeNode;

/**
 * Created by yuank on 7/22/18.
 */
public class LI_596_Minimum_Subtree {
    /**
     Given a binary tree, find the subtree with minimum sum. Return the root of the subtree.

     LintCode will print the subtree which root is your return node.
     It's guaranteed that there is only one subtree with minimum sum and the given binary tree is not an empty tree.

     Example
     Given a binary tree:

            1
          /   \
        -5     2
       / \   /  \
     0   2 -4  -5
     return the node 1.
     */

    int sum = Integer.MAX_VALUE;
    TreeNode node = null;

    public TreeNode findSubtree(TreeNode root) {
        helper(root);
        return node;
    }

    public int helper(TreeNode root) {
        if (root == null) {
            return 0;
        }

        /**
         * postorder
         */
        int cur = helper(root.left) + helper(root.right) + root.val;
        if (cur < sum) {
            sum = cur;
            node = root;
        }

        return cur;
    }

    // version 2: Pure divide conquer
    class ResultType {
        public TreeNode minSubtree;
        public int sum, minSum;
        public ResultType(TreeNode minSubtree, int minSum, int sum) {
            this.minSubtree = minSubtree;
            this.minSum = minSum;
            this.sum = sum;
        }
    }

    public TreeNode findSubtree_JiuZhang_2(TreeNode root) {
        ResultType result = helper_2(root);
        return result.minSubtree;
    }

    public ResultType helper_2(TreeNode node) {
        if (node == null) {
            return new ResultType(null, Integer.MAX_VALUE, 0);
        }

        ResultType leftResult = helper_2(node.left);
        ResultType rightResult = helper_2(node.right);

        ResultType result = new ResultType(
                node,
                leftResult.sum + rightResult.sum + node.val,
                leftResult.sum + rightResult.sum + node.val
        );

        if (leftResult.minSum <= result.minSum) {
            result.minSum = leftResult.minSum;
            result.minSubtree = leftResult.minSubtree;
        }

        if (rightResult.minSum <= result.minSum) {
            result.minSum = rightResult.minSum;
            result.minSubtree = rightResult.minSubtree;
        }

        return result;
    }
}
