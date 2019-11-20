package leetcode;

import common.TreeNode;

import java.util.Stack;

/**
 * Created by yuank on 3/9/18.
 */
public class LE_98_Validate_BST {
    /**
        Assume a BST is defined as follows:

        The left subtree of a node contains only nodes with keys less than the node's key.
        The right subtree of a node contains only nodes with keys greater than the node's key.
        Both the left and right subtrees must also be binary search trees.
        Example 1:
            2
           / \
          1   3
        Binary tree [2,1,3], return true.
        Example 2:
            1
           / \
          2   3
        Binary tree [1,2,3], return false.
     */

    /**
     * Solution
     * Divide and Conquer
     * Time and Space : O(n)
     */
    public static boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        return helper(root, null, null);
    }

    //!!! pass min and max as object Integer
    public static boolean helper(TreeNode root, Integer min, Integer max) {
        /**
         * !!!
         */
        if (root == null) {
            return true;
        }

        /**
         * !!!
         * root.val <= min, "<="
         */
        if (min != null && root.val <= min) {
            return false;
        }

        /**
         * !!!
         * root.val >= max, ">="
         */
        if (max != null && root.val >= max) {
            return false;
        }

        return (helper(root.left, min, root.val) && helper(root.right, root.val, max));
    }

    /**
     * Solution 2
     * Same as Solution 1, use long type to prevent overflow
     */
    public boolean isValidBST2_JiuZhang(TreeNode root) {
        /**
         * !!!
         * Long.MIN_VALUE, Long.MAX_VALUE
         */
        return divConq(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean divConq(TreeNode root, long min, long max){
        if (root == null){
            return true;
        }

        if (root.val <= min || root.val >= max){
            return false;
        }

        return divConq(root.left, min, root.val) && divConq(root.right, root.val, max);
    }

    /**
     * Solution 3
     * 采用非递归（Non-recursion / Iteration）版本的遍历法, 时间复杂度O(n)
     *
     * Explicitly use Stack to simulate recursion, use BST property that inorder output is sorted.
     *
     * This is the iterative version of inorder traversal.
     */
    public boolean isValidBST3_JiuZhang(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();

        while (root != null) {
            stack.push(root);
            root = root.left;
        }

        TreeNode lastNode = null;
        while (!stack.isEmpty()) {
            // compare to last node
            TreeNode node = stack.peek();
            if (lastNode != null && lastNode.val >= node.val) {
                return false;
            }
            lastNode = node;

            // move to next
            if (node.right == null) {
                node = stack.pop();
                while (!stack.isEmpty() && stack.peek().right == node) {
                    node = stack.pop();
                }
            } else {
                node = node.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
        }

        return true;
    }

}
