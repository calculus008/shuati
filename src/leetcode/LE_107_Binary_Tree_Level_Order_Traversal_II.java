package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by yuank on 3/11/18.
 */
public class LE_107_Binary_Tree_Level_Order_Traversal_II {
    /**
        Given a binary tree, return the bottom-up level order traversal of its nodes' values.
        (ie, from left to right, level by level from leaf to root).

        For example:
        Given binary tree [3,9,20,null,null,15,7],
            3
           / \
          9  20
            /  \
           15   7
        return its bottom-up level order traversal as:
        [
          [15,7],
          [9,20],
          [3]
        ]
     */

    //Follow up question from LE_102
    public static List<List<Integer>> levelOrderBottom1(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> list = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();
                list.add(cur.val);
                if (cur.left != null) q.offer(cur.left);
                if (cur.right != null) q.offer(cur.right);
            }

            //Only difference from LE_102
            res.add(0, list);
        }

        return res;
    }
}
