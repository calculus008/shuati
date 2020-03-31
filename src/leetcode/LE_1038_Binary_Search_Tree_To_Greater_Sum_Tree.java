package leetcode;

import common.TreeNode;

public class LE_1038_Binary_Search_Tree_To_Greater_Sum_Tree {
    /**
     * Given the root of a binary search tree with distinct values, modify it so
     * that every node has a new value equal to the sum of the values of the original
     * tree that are greater than or equal to node.val.
     *
     * As a reminder, a binary search tree is a tree that satisfies these constraints:
     *
     * The left subtree of a node contains only nodes with keys less than the node's key.
     * The right subtree of a node contains only nodes with keys greater than the node's key.
     * Both the left and right subtrees must also be binary search trees.
     *
     * Medium
     */

    /**
     * Reversed in-order traversal
     */
    class Solution {
        int sum = 0;

        public TreeNode bstToGst(TreeNode root) {
            if (root == null) return root;

            helper(root);

            return root;
        }

        private void helper(TreeNode node) {
            if (node == null) return;

            helper(node.right);
            node.val += sum;
            sum = node.val;
            helper(node.left);
        }
    }

    /**
     * follow up让我实现add()，就是生成完sum tree之后要在original tree上再加一个值，
     * update sum tree accordingly。
     * 我说那就是update node的时候要存一下original value，用hashmap
     * 他说不用extra memory要怎么写，我解释了一下用减法，他说可以
     */
}
