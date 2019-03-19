package common;

import java.util.ArrayDeque;
import java.util.Deque;

public class BSTIterator {
    private Deque<TreeNode> stack;
    private TreeNode cur;

    public BSTIterator(TreeNode root) {
        stack = new ArrayDeque<>();
        cur = root;
    }

    /**
     * return whether we have a next smallest number
     */
    public boolean hasNext() {
        /**
         * This is actually the outer while loop condition in LE_94_Binary_Tree_Inorder_Traversal
         */
        if (!stack.isEmpty() || cur != null) return true;
        return false;
    }

    /** @return the next smallest number
     * This is the steps inside the while loop in LE_94_Binary_Tree_Inorder_Traversal,
     * each call to this method will just move 1 step in BST.
     */
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
