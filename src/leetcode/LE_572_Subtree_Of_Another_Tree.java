package leetcode;

import common.TreeNode;

public class LE_572_Subtree_Of_Another_Tree {
    /**
     * Given two non-empty binary trees s and t, check whether tree t has exactly
     * the same structure and node values with a subtree of s. A subtree of s is
     * a tree consists of a node in s and all of this node's descendants. The tree
     * s could also be considered as a subtree of itself.
     *
     * Example 1:
     * Given tree s:
     *
     *      3
     *     / \
     *    4   5
     *   / \
     *  1   2
     * Given tree t:
     *    4
     *   / \
     *  1   2
     * Return true, because t has the same structure and node values with a subtree of s.
     *
     * Example 2:
     * Given tree s:
     *
     *      3
     *     / \
     *    4   5
     *   / \
     *  1   2
     *     /
     *    0
     * Given tree t:
     *    4
     *   / \
     *  1   2
     * Return false.
     *
     * Easy
     */

    /**
     * Key:
     * It has two levels of recursion - isSubtree() and isSame()
     *
     * At isSubtree() level, first check if given s and t is the same tree
     * using isSame(), if not, then calling isSubtree() on left/right children of s and t
     */
    class Solution {
        public boolean isSubtree(TreeNode s, TreeNode t) {
            if (s == null || t == null) return false;

            if (isSame(s, t)) return true;

            return isSubtree(s.left, t) || isSubtree(s.right, t);
        }

        private boolean isSame(TreeNode s, TreeNode t) {
            if (s == null && t == null) return true;
            if (s == null || t == null) return false;

            if (s.val == t.val) {
                return isSame(s.left, t.left) && isSame(s.right, t.right);
            }

            return false;
        }
    }
}
