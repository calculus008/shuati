package leetcode;

import common.TreeNode;

import java.util.Stack;

/**
 * Created by yuank on 3/13/18.
 */
public class LE_112_Path_Sum {
    /**
        Given a binary tree and a sum, determine if the tree has a root-to-leaf path
        such that adding up all the values along the path equals the given sum.

        For example:
        Given the below binary tree and sum = 22,

                      5
                     / \
                    4   8
                   /   / \
                  11  13  4
                 /  \      \
                7    2      1
        return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.

        Easy

        https://leetcode.com/problems/path-sum

        Related:
            LE_112_Path_Sum
            LE_113_Path_Sum_II
            LE_437_Path_Sum_III
     **/

    //Time and Space : O(n)

    //Solution 1 : Recursion
    public boolean hasPathSum1(TreeNode root, int sum) {
        if (root == null) return false;

        if (root.left == null && root.right == null) {
            return sum == root.val;
        }

        return hasPathSum1(root.left, sum - root.val) || hasPathSum1(root.right, sum - root.val);
    }

    public boolean hasPathSum2(TreeNode root, int sum) {
        if (root == null) return false;

        sum -= root.val;
        if ((root.left == null) && (root.right == null)) return (sum == 0);
        return hasPathSum2(root.left, sum) || hasPathSum2(root.right, sum);
    }

    //Solution 2 : preorder and stack
    public boolean hasPathSum3(TreeNode root, int sum) {
        if (root == null) return false;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            if (cur.left == null && cur.right == null) {
                if (sum == cur.val) return true;
            }

            /**
             * changes val in each node along the way
             */
            if (cur.left != null) {
                stack.push(cur.left);
                cur.left.val += cur.val;
            }
            if (cur.right != null) {
                stack.push(cur.right);
                cur.right.val += cur.val;
            }
        }

        return false;
    }
}
