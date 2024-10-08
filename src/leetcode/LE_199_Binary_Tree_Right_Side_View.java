package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by yuank on 3/24/18.
 */
public class LE_199_Binary_Tree_Right_Side_View {
    /**
        Given a binary tree, imagine yourself standing on the right side of it,
        return the values of the nodes you can see ordered from top to bottom.

        For example:
        Given the following binary tree,
           1            <---
         /   \
        2     3         <---
         \     \
          5     4       <---
        You should return [1, 3, 4].
     */

    //BFS
    public List<Integer> rightSideViewBFS(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }

                /**
                 * !!!
                 * add the last element of each level
                 */
                if (i == (size - 1)) {
                    res.add(cur.val);
                }
            }
        }

        return res;
    }

    /**
     * !!!
     * BFS by DFS
     */
    public List<Integer> rightSideViewDFS(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        helper(root, res, 0);
        return res;
    }

    private void helper(TreeNode root, List<Integer> res, int level) {//!!! level
        if (root == null) {
            return;
        }
        if (res.size() == level) {//!!!
            res.add(root.val);
        }

        /**
         * !!!
         * right first, only by going right first, we encounter the right most element of the level
         * first, only then "res.size() == level" is true, we add the element, then res.size() plus 1,
         * all the rest of the element in the same level (in later recursion) will not be added
         */
        helper(root.right, res, level + 1); //!!!right
        helper(root.left, res, level + 1);
    }
}
