package lintcode;

import common.TreeNode;

/**
 * Created by yuank on 7/25/18.
 */
public class LI_597_Subtree_With_Maximum_Average {
    /**
         Given a binary tree, find the subtree with maximum average. Return the root of the subtree.

         Example
         Given a binary tree:

              1
            /   \
          -5     11
          / \   /  \
         1   2 4    -2
         return the node 11.

         Easy
     */

    /**
     * For average, need to have sum and count of nodes in each recursion,
     * therefore, has to create class Result which can contain multiple member variables
     */
    class Result {
        public int sum;
        public int count;
        public Result (int sum, int count) {
            this.sum = sum;
            this.count = count;
        }
    }

    public class Solution {
        /**
         * @param root: the root  binary tree
         * @return: the root of the maximum average of subtree
         */
        private Result res = null;
        private TreeNode node = null;

        public TreeNode findSubtree2(TreeNode root) {
            helper(root);
            return node;
        }

        private Result helper(TreeNode root) {
            if (root == null) {
                return new Result(0, 0);
            }

            Result left = helper(root.left);
            Result right = helper(root.right);

            Result a = new Result(left.sum + right.sum + root.val,
                    left.count + right.count + 1);

            /**
             * !!!
             * instead of "s1 / c1 > s2 / c2", use "s1 * c2 > s2 * c1",
             * avoid accuracy issue caused by division
             **/
            if (res == null || ((a.sum * res.count) > (res.sum * a.count))) {
                res = a;
                node = root;
            }

            return a;
        }
    }
}
