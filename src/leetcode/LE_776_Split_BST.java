package leetcode;

import common.TreeNode;

public class LE_776_Split_BST {
    /**
     * Given a Binary Search Tree (BST) with root node root, and a target value V, split the tree into two subtrees
     * where one subtree has nodes that are all smaller or equal to the target value, while the other subtree has
     * all nodes that are greater than the target value.  It's not necessarily the case that the tree contains a node
     * with value V.
     *
     * Additionally, most of the structure of the original tree should remain.  Formally, for any child C with parent
     * P in the original tree, if they are both in the same subtree after the split, then node C should still have the parent P.
     *
     * You should output the root TreeNode of both subtrees after splitting, in any order.
     *
     * Median
     */

    /**
     * https://leetcode.com/problems/split-bst/discuss/114861/Java-Recursion-in-O(logn)
     *
     * Just think about the current root, after processing, it will need to return [smaller/eq, larger] subtrees
     *
     * if root.val <=V, all nodes under root.left(including root) will be in the smaller/eq tree(!!!!)
     * we then split the root.right subtree into smaller/eq, larger, the root will need to concat the smaller/eq
     * from the subproblem result (recursion).
     *
     * Similarly for the case root.val>V
     *
     * The runtime will be O(logn) if the input is balanced BST. Worst case is O(n) if it is not balanced.
     *
     * Time : O(logn)
     *
     * Trick part is that the value may not exist in BST, so we need to do SPLIT of the subtree.
     *
     * BST => Recursion, think locally, for current root:
     * if root.val <= V, its left subtree must be smaller than V, simply go to right side, do next level of recursion.
     * Get result back, result = {smaller/eql, bigger}
     * root.right = result[0] (smaller/eql)
     * return : {root, result[1]}
     *
     * if root.val > V, its right subtree must be bigger than V, simply go to left side, do next level of recursion.
     * Get result back, result = {smaller/eql, bigger}
     * root.left = result[1] (bigger)
     * return {result[0], root}
     *
     * Example 1 : V = 9
     * Root = 10, 10 > 9, go to left.
     * Root = 6, 6 < 9, go to right.
     * Root = 7, 7 < 9, go to right, get {null, null}, return {7, null}
     * Back to root = 6 from right, 6.right = 7, return {6, null}
     * Back to root = 10 from left, 10.left = null, return {6, 10}
     *
     * Example 2 : V = 15
     * Root = 10, 10 < 15, go to right
     * Root = 16, 16 > 15, go to left
     * Root = 13, 13 < 15, go to right, get{null, null}, return {null, 13}
     * Back to Root = 16 back from left, 16.left = 13, return {null, 16}
     * Back to Root = 10 back from right, 10.right = null, return {10, 16}
     *
     *           10
     *         /   \
     *       6      16
     *      / \    / \
     *     1  7   13  20
     */
    class Solution1 {
        public TreeNode[] splitBST(TreeNode root, int V) {
            if (root == null) return new TreeNode[]{null, null};

            TreeNode[] result = new TreeNode[2];
            if (root.val <= V) {
                TreeNode[] rsplit = splitBST(root.right, V);//back from right
                root.right = rsplit[0];
                result[0] = root;
                result[1] = rsplit[1];
            } else {
                TreeNode[] lsplit = splitBST(root.left, V);//back from left
                root.left = lsplit[1];
                result[0] = lsplit[0];
                result[1] = root;
            }

            return result;
        }
    }

    class Solution2 {
        public TreeNode[] splitBST(TreeNode root, int V) {
            if (root == null) return new TreeNode[]{null, null};

            TreeNode[] split = new TreeNode[2];
            if (root.val <= V) {
                split = splitBST(root.right, V);
                root.right = split[0];
                split[0] = root;
            } else {
                split = splitBST(root.left, V);
                root.left = split[1];
                split[1] = root;
            }

            return split;
        }
    }
}
