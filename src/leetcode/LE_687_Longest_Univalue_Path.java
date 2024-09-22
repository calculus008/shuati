package leetcode;

import common.TreeNode;

public class LE_687_Longest_Univalue_Path {
    /**
     * Given a binary tree, find the length of the longest path where each node in the
     * path has the same value. This path may or may not pass through the root.
     *
     * Note: The length of path between two nodes is represented by the number of
     * edges between them.
     *
     * Example 1:
     * Input:
     *
     *               5
     *              / \
     *             4   5
     *            / \   \
     *           1   1   5
     * Output:
     * 2
     *
     * Example 2:
     * Input:
     *
     *               1
     *              / \
     *             4   5
     *            / \   \
     *           4   4   5
     * Output:
     * 2
     *
     * Note: The given binary tree has not more than 10000 nodes.
     * The height of the tree is not more than 1000.
     *
     * Easy
     */

    /**
     * http://zxi.mytechroad.com/blog/tree/leetcode-687-longest-univalue-path/
     *
     * DFS (post order)
     * Time  : O(n)
     * Space : O(n)
     *
     * Similar Problem :
     * LE_124_Binary_Tree_Max_Path_Sum
     */

    class Solution_clean {
        int res;
        public int longestUnivaluePath(TreeNode root) {
            if (root == null) return 0;

            res = Integer.MIN_VALUE;
            helper(root);

            return res;
        }

        private int helper(TreeNode root) {
            if (root == null) return 0;

            int l = helper(root.left);
            int r = helper(root.right);

            int pl = 0;
            int pr = 0;
            if (root.left != null && root.left.val == root.val) {
                pl = l + 1;
            }
            if (root.right != null && root.right.val == root.val) {
                pr = r + 1;
            }
            res = Math.max(res, pl + pr);

            return Math.max(pl, pr);
        }
    }

    class Solution {
        int res;
        public int longestUnivaluePath(TreeNode root) {
            if (root == null) return 0;

            res = Integer.MIN_VALUE;
            helper(root);

            return res;
        }


        /**
         * !!!
         * Return value of this helper() should be the longest univalue path that
         * goes through root from left child OR right child.
         */
        private int helper(TreeNode root) {
            if (root == null) {
                return 0;
            }

            int l = helper(root.left);
            int r = helper(root.right);

            /**
             * !!!
             * pl : the univalue path length goes through root and comes from left child
             * pr : the univalue path length goes through root and comes from right child
             *
             * The length of path between two nodes is represented by the NUMBER OF EDGES between them.
             */
            int pl = 0;
            int pr = 0;
            if (root.left != null && root.left.val == root.val) {
                pl = l + 1;
            }

            if (root.right != null && root.right.val == root.val) {
                pr = r + 1;
            }

            /**
             * !!!
             * the length of the path is number of edges, not number of nodes
             * therefore, it's pl + pr, NOT pl + pr + 1
             */
            res = Math.max(res, pl + pr);

            return Math.max(pl, pr);
        }
    }
}