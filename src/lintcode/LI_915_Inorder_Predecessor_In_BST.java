package lintcode;

import leetcode.TreeNode;

/**
 * Created by yuank on 7/26/18.
 */
public class LI_915_Inorder_Predecessor_In_BST {
    /**
         Given a binary search tree and a node in it, find the in-order predecessor of that node in the BST.

         Example
         Given root = {2,1,3}, p = 1, return null.

         Medium
     */

    /**
     *设计一个全局变量turnRight ，代表上一个有右拐的点，开始为Null
     * 然后如果是左拐，对这个遍量不作处理，右拐的话，刷新这个全局变量为当前root
     * 找到p,如果p有左子树，那么返回左子树，没有的话，就返回它前面上一个右拐的点
     */
    TreeNode turnRight = null;
    public TreeNode inorderPredecessor(TreeNode root, TreeNode p) {
        if (root == null) {
            return null;
        }

        if (p.val > root.val) {
            turnRight = root;
            return inorderPredecessor(root.right, p);
        } else if (p.val < root.val) {
            return inorderPredecessor(root.left, p);
        } else {
            if (root.left != null) {
                TreeNode cur = root.left;
                while (cur.right != null) {
                    cur = cur.right;
                }
                return cur;
            } else {
                return turnRight;
            }
        }
    }
}
