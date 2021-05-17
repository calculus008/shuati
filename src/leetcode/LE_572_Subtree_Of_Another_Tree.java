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
     * It has two levels of recursion - isSubtree() and isSameTree()
     *
     * At isSubtree() level, first check if given s and t is the same tree
     * using isSameTree(), if not, then calling isSubtree() on left/right children of s and t
     *
     * Traverses over the given tree ss and treats every node as the root of the subtree currently being considered.
     * It also checks the two subtrees currently being considered for their equality. In order to check the equality
     * of the two subtrees, we make use of isSame(x,y) function, which takes x and y, which are the roots of the two
     * subtrees to be compared as the inputs and returns True or False depending on whether the two are equal or not.
     * It compares all the nodes of the two subtrees for equality. Firstly, it checks whether the roots of the two
     * trees for equality and then calls itself recursively for the left subtree and the right subtree.
     *
     * Time : O(m*n) In worst case(skewed tree) traverse function takes O(m*n) time.
     *
     * Space : O(n). The depth of the recursion tree can go up to n. n refers to the number of nodes in s.
     */
    class Solution {
        public boolean isSubtree(TreeNode s, TreeNode t) {
            if (s == null || t == null) return false;

            if (isSameTree(s, t)) return true;

            return isSubtree(s.left, t) || isSubtree(s.right, t);
        }

        /**
         * This is the same logic as in LE_100_Same_Tree
         */
        private boolean isSameTree(TreeNode s, TreeNode t) {
            if (s == null && t == null) return true;
            if (s == null || t == null) return false;

            if (s.val != t.val) return false;

            return isSameTree(s.left, t.left) && isSameTree(s.right, t.right);
        }

        /**
         *    From LE_100_Same_Tree
         *
         *    public static boolean isSameTree(TreeNode p, TreeNode q) {
         *         if (p == null && q == null) return true;
         *         if (p == null || q == null) return false;
         *
         *         if (p.val != q.val) return false;
         *         return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
         *     }
         */
    }
}
