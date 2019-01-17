package leetcode;

import common.TreeNode;

import java.util.Stack;

/**
 * Created by yuank on 3/23/18.
 */
public class    LE_173_Binary_Search_Tree_Iterator {
    /**
        Implement an iterator over a binary search tree (BST). Your iterator will be initialized with the root node of a BST.

        Calling next() will return the next smallest number in the BST.

        Note: next() and hasNext() should run in average O(1) time and uses O(h) memory, where h is the height of the tree.
     **/

    /**
     * !!!
     * This basically is a variation of iterative version of inorder traversal
     * Refer to LE_94 inorder iterative traversal
     */
    public class BSTIterator {
        private Stack<TreeNode> stack;
        private TreeNode cur;

        public BSTIterator(TreeNode root) {
            stack = new Stack<>();
            cur = root;
        }

        /**
         * return whether we have a next smallest number
         */
        public boolean hasNext() {
            //!!!
            if (!stack.isEmpty() || cur != null) return true;
            return false;
        }

        /** @return the next smallest number */
        //Refer to LE_94 for inorder iterative traversal
        public int next() {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            int val = cur.val;
            cur = cur.right;
            return val;
        }
    }

}
