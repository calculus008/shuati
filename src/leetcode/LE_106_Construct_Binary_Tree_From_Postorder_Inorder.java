package leetcode;

import common.TreeNode;

/**
 * Created by yuank on 3/12/18.
 */
public class LE_106_Construct_Binary_Tree_From_Postorder_Inorder {
    /**
     * Given inorder and postorder traversal of a tree, construct the binary tree.
     *
     * Note:
     * You may assume that duplicates do not exist in the tree.
     *
     * For example, given
     *
     * inorder = [9,3,15,20,7]
     * postorder = [9,15,7,20,3]
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

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return helper1(postorder, 0, postorder.length - 1, inorder, 0, inorder.length - 1);
    }

    /**
     * Same method as LE_105 : get root from postorder, find root in inorder,
     * calculate length of the left subtree string, then recurse...
     **/
    private TreeNode helper1(int[] postorder, int poststart, int postend,  int[] inorder, int instart, int inend) {
        if(instart > inend || poststart > postend) {
            return null;
        }

        TreeNode root = new TreeNode(postorder[postend]);
        int i = 0;
        while (inorder[i] != root.val) {
            i++;
        }

        root.left = helper1(postorder,  poststart, poststart + (i - instart) - 1, inorder, instart, i - 1);
        root.right = helper1(postorder, poststart + (i - instart), postend - 1, inorder, i + 1, inend);

        return root;
    }


    /**
     * Only difference from helper1(), only pass "postend", no need to pass "poststart"
     * calculate length of the RIGHT subtree string, then recurse...
     **/
    private TreeNode helper2(int[] postorder, int postend,  int[] inorder, int instart, int inend) {
        if(instart > inend || postend < 0)
            return null;

        TreeNode root = new TreeNode(postorder[postend]);
        int i=0;
        while(inorder[i] != root.val) {
            i++;
        }

        //inend - (i + 1) + 1
        int lenRT = inend - i;
        root.left = helper2(postorder, postend - 1 - lenRT ,  inorder, instart, i-1);
        root.right = helper2(postorder, postend - 1, inorder, i+1,     inend);

        return root;
    }
}
