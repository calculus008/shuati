package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by yuank on 3/18/18.
 */
public class LE_145_Binary_Tree_Postorder_Traversal {
    /**
        Given a binary tree, return the postorder traversal of its nodes' values.

        For example:
        Given binary tree [1,null,2,3],

           1
            \
             2
            /
           3


        return [3,2,1].

        Note: Recursive solution is trivial, could you do it iteratively?
     */

    //LE_94  Inorder
    //LE_144 Preorder
    //LE_145 Postorder
    //LE_102 Levelorder
    //LE_107 Levelorder II
    //LE_103 Levelorder Zigzag
    //LE_314 Verticalorder

    //Recurssion
    public List<Integer> postorderTraversal1(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if(root == null) return res;
        helper(root, res);
        return res;
    }

    public void helper(TreeNode root, List<Integer> res) {
        if (root.left != null) helper(root.left, res);
        if (root.right != null) helper(root.right, res);
        res.add(root.val);
    }

    //Iterative
    public List<Integer> postorderTraversal2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            /**
             * different from Preorder iterative version, add at index 0.
             */
            res.add(0, cur.val);
            /**
             * It's a Stack, push in left first, then it will be popped out after right,
             * so output sequence is postorder
             */
            if (cur.left != null) stack.push(cur.left);
            if (cur.right != null) stack.push(cur.right);
        }

        return res;
    }
}
