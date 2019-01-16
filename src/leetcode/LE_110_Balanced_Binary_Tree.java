package leetcode;

import common.TreeNode;

/**
 * Created by yuank on 3/11/18.
 */
public class LE_110_Balanced_Binary_Tree {
    /**
        Given a binary tree, determine if it is height-balanced.

        For this problem, a height-balanced binary tree is defined as:

        a binary tree in which the depth of the two subtrees of every node never differ by more than 1.

        Example 1:

        Given the following tree [3,9,20,null,null,15,7]:

            3
           / \
          9  20
            /  \
           15   7
        Return true.

        Example 2:

        Given the following tree [1,2,2,3,3,null,null,4,4]:

               1
              / \
             2   2
            / \
           3   3
          / \
         4   4
        Return false.
     */

    /**
     * Time and Space : O(n)
     * 2 ms, 58%
     */
    class Solution1 {
        public boolean isBalanced(TreeNode root) {
            if (root == null) return true;
            /**
             * Reason to use -1 : -1 is just a flag that tells
             * the tree is not balanced, depth() also returns
             * value >= 0 which represents the real depth of a
             * subtree
             */
            return depth(root) == -1 ? false : true;
        }

        public int depth(TreeNode root) {
            if (root == null) return 0;

            int l = depth(root.left);
            int r = depth(root.right);

            if (l == -1 || r == -1 || Math.abs(l - r) > 1) return -1;

            return Math.max(l, r) + 1;
        }
    }

    /**
     * Huahua's version
     * http://zxi.mytechroad.com/blog/leetcode/leetcode-110-balanced-binary-tree/
     *
     * 重点是是件复杂度分析。
     * Naive solution O(nlogn)
     *
     * Time and Space : O(n)
     *
     * 1 ms, 99%
     */
    class Solution2 {
        private boolean balanced;

        public boolean isBalanced(TreeNode root) {
            this.balanced = true;
            height(root);
            return this.balanced;
        }

        private int height(TreeNode root) {
            /**
             * !!!
             * Use balanced here to return early if we know the tree is not balanced,
             * make it much faster than Solution1.
             */
            if (root == null || !this.balanced) {
                return -1;
            }

            int l = height(root.left);
            int r = height(root.right);

            if (Math.abs(l - r) > 1) {
                this.balanced = false;
                return -1;
            }
            return Math.max(l, r) + 1;
        }
    }
}
