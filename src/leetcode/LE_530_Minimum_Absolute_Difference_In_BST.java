package leetcode;

import common.TreeNode;

public class LE_530_Minimum_Absolute_Difference_In_BST {
    /**
     * Given a binary search tree with non-negative values,
     * find the minimum absolute difference between values of any two nodes.
     *
     * Example:
     *
     * Input:
     *
     *    1
     *     \
     *      3
     *     /
     *    2
     *
     * Output:
     * 1
     *
     * Explanation:
     * The minimum absolute difference is 1, which is the difference between 2 and 1 (or between 2 and 3).
     *
     *
     * Note: There are at least two nodes in this BST.
     *
     * Easy
     */

    /**
     * http://zxi.mytechroad.com/blog/tree/leetcode-530-minimum-absolute-difference-in-bst/
     *
     * Sorting via inorder traversal gives us sorted values, compare current one with previous
     * one to reduce space complexity from O(n) to O(h).
     *
     * Time : O(n)
     * Space : O(h)
     */

    class Solution {
        int res = Integer.MAX_VALUE;
        TreeNode last = null;

        public int getMinimumDifference(TreeNode root) {
            if (root == null) return 0;
            helper(root);
            return res;
        }

        public void helper(TreeNode root) {
            if (root == null) return;

            helper(root.left);

            /**
             * !!!
             * must check last
             */
            if (last != null) {
                res = Math.min(root.val - last.val, res);
            }
            last = root;

            helper(root.right);

        }


    }
}