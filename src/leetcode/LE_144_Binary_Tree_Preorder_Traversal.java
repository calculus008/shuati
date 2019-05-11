package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by yuank on 3/18/18.
 */
public class LE_144_Binary_Tree_Preorder_Traversal {
    /**
        Given a binary tree, return the preorder traversal of its nodes' values.

        For example:
        Given binary tree [1,null,2,3],
           1
            \
             2
            /
           3
        return [1,2,3].

        Note: Recursive solution is trivial, could you do it iteratively?
     */


    //LE_94  Inorder
    //LE_144 Preorder
    //LE_145 Postorder
    //LE_102 Levelorder
    //LE_107 Levelorder II
    //LE_103 Levelorder Zigzag
    //LE_314 Verticalorder

    //Recursion
    public List<Integer> preorderTraversal1(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        helper(root, res);
        return res;
    }

    public void helper(TreeNode root, List<Integer> res) {
        res.add(root.val);
        if (root.left != null) helper(root.left, res);
        if (root.right != null) helper(root.right, res);
    }

    //Iterative with stack
    public List<Integer> preorderTraversal2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);    //PUSH!!!

        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            res.add(cur.val);

            if (cur.right != null) stack.push(cur.right);//!!! right, it's a Stack
            if (cur.left != null) stack.push(cur.left);
        }
        return res;
    }
}
