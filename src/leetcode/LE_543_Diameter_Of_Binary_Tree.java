package leetcode;

import common.TreeNode;

public class LE_543_Diameter_Of_Binary_Tree {
    /**
     * Given a binary tree, you need to compute the length of the diameter of the tree.
     * The diameter of a binary tree is the length of the longest path between any two
     * nodes in a tree. This path may or may not pass through the root.
     *
     * Example:
     * Given a binary tree
     *           1
     *          / \
     *         2   3
     *        / \
     *       4   5
     * Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].
     *
     * Note:
     * The length of path between two nodes is represented by the number of edges between them.
     *
     * Easy
     */

    /**
     * http://zxi.mytechroad.com/blog/tree/leetcode-543-diameter-of-binary-tree/
     *
     * Time and Space : O(n)
     *
     * Same type as :
     * LE_124_Binary_Tree_Max_Path_Sum
     * LE_687_Longest_Univalue_Path
     */
    class Solution {
        int res;
        public int diameterOfBinaryTree(TreeNode root) {
            if (root == null) {
                return 0;
            }

            res = Integer.MIN_VALUE;
            helper(root);
            return res;
        }

        private int helper(TreeNode root) {
            if (root == null) {
                return -1;
            }

            int l = helper(root.left) + 1;
            int r = helper(root.right) + 1;

            res = Math.max(res, l + r);

            return Math.max(l, r);
        }
    }
}