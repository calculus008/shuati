package leetcode;

import common.TreeNode;

public class LE_938_Range_Sum_Of_BST {
    /**
     * Given the root node of a binary search tree, return the sum of
     * values of all nodes with value between L and R (inclusive).
     *
     * The binary search tree is guaranteed to have unique values.
     *
     *
     * Example 1:
     * Input: root = [10,5,15,3,7,null,18], L = 7, R = 15
     * Output: 32
     *
     * Example 2:
     * Input: root = [10,5,15,3,7,13,18,1,null,6], L = 6, R = 10
     * Output: 23
     *
     *
     * Note:
     * The number of nodes in the tree is at most 10000.
     * The final answer is guaranteed to be less than 2^31.
     *
     * Easy
     */
    class Solution1 {
        int sum = 0;

        public int rangeSumBST(TreeNode root, int L, int R) {
            if (root == null) return 0;

            helper(root, L, R);

            return sum;
        }

        private void helper(TreeNode root, int L, int R) {
            if (root == null) return;

            helper(root.left, L, R);

            if (root.val >= L && root.val <= R) {
                sum += root.val;
            }

            helper(root.right, L, R);
        }
    }

    /**
     * Faster solution
     */
    class Solution2 {
        public int rangeSumBST(TreeNode root, int L, int R) {
            if (root == null) return 0;

            if (root.val < L) return rangeSumBST(root.right, L, R);
            if (root.val > R) return rangeSumBST(root.left, L, R);

            return root.val + rangeSumBST(root.left, L, R) + rangeSumBST(root.right, L, R);
        }
    }
}

