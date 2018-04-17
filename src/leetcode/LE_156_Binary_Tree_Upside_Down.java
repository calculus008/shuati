package leetcode;

/**
 * Created by yuank on 3/20/18.
 */
public class LE_156_Binary_Tree_Upside_Down {
    /*
        Given a binary tree where all the right nodes are either leaf nodes with a sibling (a left node that shares the same parent node) or empty, flip it upside down and turn it into a tree where the original right nodes turned into left leaf nodes. Return the new root.

        For example:
        Given a binary tree {1,2,3,4,5},
            1
           / \
          2   3
         / \
        4   5
        return the root of the binary tree [4,5,2,#,#,3,1].
           4
          / \
         5   2
            / \
           3   1
     */

    /*

            1
           / \
          2   3
         / \
        4   5

            1
           /
          2 - 3
         /
        4 - 5

     */

    //Time and Space : O(n)
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null || root.left == null && root.right == null) return root;

        TreeNode newRoot = upsideDownBinaryTree(root.left);
        root.left.left = root.right;
        root.left.right = root;
        root.left = null;
        root.right = null;

        return newRoot;
    }
}
