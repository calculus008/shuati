package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LE_1110_Delete_Nodes_And_Return_Forest {
    /**
     * Given the root of a binary tree, each node in the tree has a distinct value.
     *
     * After deleting all nodes with a value in to_delete, we are left with a forest (a disjoint union of trees).
     *
     * Return the roots of the trees in the remaining forest.  You may return the result in any order.
     *
     * Constraints:
     *
     * The number of nodes in the given tree is at most 1000.
     * Each node has a distinct value between 1 and 1000.
     * to_delete.length <= 1000
     * to_delete contains distinct values between 1 and 1000.
     *
     * Medium
     */

    /**
     * The key point is that we need to do two things here:
     * 1.Add un-deleted node into result list
     * 2.Doing the actual delete - set child pointer to the delete node as null
     */
    class Solution {
        Set<Integer> set = new HashSet<>();
        List<TreeNode> res = new ArrayList<>();

        public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
            if (root == null) return res;

            for (int num : to_delete) {
                set.add(num);
            }

            dfs(root, true);

            return res;
        }

        private TreeNode dfs(TreeNode node, boolean isRoot) {
            if (node == null) return null;

            boolean deleted = set.contains(node.val);

            /**
             * !!!
             */
            if (isRoot && !deleted) {
                res.add(node);
            }

            /**
             * !!!
             * "deleted"
             * if we delete current node, then its children will be root of a new tree
             */
            node.left = dfs(node.left, deleted);
            node.right = dfs(node.right, deleted);

            return deleted ? null : node;
        }

        /**
         * This version removes the links for deleted node to its children
         */
        private TreeNode dfs1(TreeNode root, boolean isRoot) {
            if (root == null) return null;

            boolean deleted = set.contains(root.val) ? true : false;

            if (isRoot && !deleted) {
                res.add(root);
            }

            TreeNode l = dfs(root.left, deleted);
            TreeNode r = dfs(root.right, deleted);

            root.left = deleted ? null : l;
            root.right = deleted ? null : r;

            return deleted ? null : root;
        }
    }
}
