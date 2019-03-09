package leetcode;

import common.TreeNode;

/**
 * Created by yuank on 4/6/18.
 */
public class LE_235_Lowest_Common_Ancestor_Of_BST {
    /**
        Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.

        According to the definition of LCA on Wikipedia:
        “The lowest common ancestor is defined between two nodes v and w as the
         lowest node in T that has both v and w as descendants (where we allow a node to be a descendant of itself).”

                _______6______
               /              \
            ___2__          ___8__
           /      \        /      \
           0      _4       7       9
                 /  \
                 3   5
        For example, the lowest common ancestor (LCA) of nodes 2 and 8 is 6. Another example is LCA of nodes 2 and 4 is 2,
        since a node can be a descendant of itself according to the LCA definition.
     */

    /**
     * Time  : O(n)
     * Space : O(n) : This is because the maximum amount of space utilized by the recursion stack would be
     *                N since the height of a skewed BST could be N.
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return root;//!!! return root

        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        }

        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        }

        return root;
    }

    /**
     * Iterative
     * Time : O(n)
     * Space : O(1), constant space
     */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        int pVal = p.val;
        int qVal = q.val;

        // Start from the root node of the tree
        TreeNode node = root;

        // Traverse the tree
        while (node != null) {
            int parentVal = node.val;

            if (pVal > parentVal && qVal > parentVal) {
                node = node.right;
            } else if (pVal < parentVal && qVal < parentVal) {
                node = node.left;
            } else {
                return node;
            }
        }

        return null;//!!!
    }
}
