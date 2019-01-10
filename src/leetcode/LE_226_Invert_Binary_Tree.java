package leetcode;

import common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yuank on 3/30/18.
 */
public class LE_226_Invert_Binary_Tree {
    /*
        Invert a binary tree.

             4
           /   \
          2     7
         / \   / \
        1   3 6   9
        to

             4
           /   \
          7     2
         / \   / \
        9   6 3   1

     */

    //Time and Space : O(n)

    //DFS
    public TreeNode invertTreeDFS(TreeNode root) {
        if (root == null) return root;

        /*
            !!!
            错误的写法：
                root.right = invertTree(root.right);
                root.left = invertTree(root.left);

            这样直接覆盖了root.left引用，那root.right去做的就是就是修改过后的。结果是错误的。
        */
        TreeNode left = invertTreeDFS(root.right);
        TreeNode right = invertTreeDFS(root.left);

        root.right = left;
        root.left = right;

        return root;
    }

    public TreeNode invertTreeBFS(TreeNode root) {
        if (root == null) return root;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();

                TreeNode temp = cur.right;
                cur.right = cur.left;
                cur.left = temp;

                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            }
        }

        return root;
    }
}
