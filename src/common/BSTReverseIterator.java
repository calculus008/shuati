package common;

import java.util.ArrayDeque;
import java.util.Deque;

public class BSTReverseIterator {
    private Deque<TreeNode> stack;
    private TreeNode cur;

    public BSTReverseIterator(TreeNode root) {
        stack = new ArrayDeque<>();
        cur = root;
    }

    public boolean hasNext() {
        if (!stack.isEmpty() || cur != null) return true;
        return false;
    }

    public int next() {
        while (cur != null) {
            stack.push(cur);
            cur = cur.right;
        }

        cur = stack.pop();
        int val = cur.val;
        cur = cur.left;
        return val;
    }
}
