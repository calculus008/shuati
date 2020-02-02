package leetcode;

import common.TreeNode;

/**
 * Created by yuank on 3/12/18.
 */
public class LE_105_Construct_Binary_Tree_From_Preorder_Inorder {
    /**
     * Given preorder and inorder traversal of a tree, construct the binary tree.
     *
     * Note:
     * You may assume that duplicates do not exist in the tree.
     *
     * For example, given
     *
     * preorder = [3,9,20,15,7]
     * inorder = [9,3,15,20,7]
     * Return the following binary tree:
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     *
     * Medium
     */

    /**
       preorder = [root][...left...][...right...]
             preStart  preStart + 1  preStart + d + 1

       inorder = [...left...][root][...right...]       (len of left subtree string) d = (k - 1) - inStart + 1 = k - inStart
               inStart    k-1   k   k+1       inEnd
    */

    //Time and Space : O(n)
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTree(preorder, 0, inorder, 0, inorder.length - 1);
    }

    private TreeNode buildTree(int[] preorder, int prestart, int[] inorder, int instart, int inend) {
        /**
         * !!!
         * check range of inorder and preorder, if it's not valid, return null.
         */
        if (instart > inend || prestart > preorder.length - 1) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[prestart]);

        /**
         * find left subtree boundary in inorder
         */
        int i = 0;
        while (inorder[i] != root.val) {
            i++;
        }

        root.left = buildTree(preorder, prestart + 1, inorder, instart, i - 1);

        /**
         * next prestart for right substree = cur prestart + length of left subtree + 1
         *
         * In inorder[]:
         * lenLT = (i - 1) - instart + 1 = i - instart
         **/
        root.right = buildTree(preorder, prestart + ((i - 1) - instart + 1) + 1, inorder, i + 1, inend);

        return root;
    }
}
