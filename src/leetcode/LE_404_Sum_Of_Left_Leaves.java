package leetcode;

import common.TreeNode;

public class LE_404_Sum_Of_Left_Leaves {
    /**
         Find the sum of all left leaves in a given binary tree.

         Example:

             3
            / \
           9  20
          /  \
         15   7

         There are two left leaves in the binary tree, with values 9 and 15 respectively. Return 24.

         Easy
     */

    /**
     * https://zxi.mytechroad.com/blog/tree/leetcode-404-sum-of-left-leaves/
     *
     * Time and Space : O(n)
     */
    class Solution {
        public int sumOfLeftLeaves(TreeNode root) {
            if (root == null) {
                return 0;
            }

            /**
             * !!!
             * not only look at current node, but also two levels down (its left child and children of its left child)
             * to tell if it is a leave node.
             */
            if (root.left != null && root.left.left == null && root.left.right == null) {
                /**
                 * !!!
                 * "+ sumOfLeftLeaves(root.right)"
                 */
                return root.left.val + sumOfLeftLeaves(root.right);
            }

            return sumOfLeftLeaves(root.left) + sumOfLeftLeaves(root.right);
        }
    }
}