package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by yuank on 3/11/18.
 */
public class LE_102_Binary_Tree_Level_Order_Traversal {
    /**
        Given a binary tree, return the level order traversal of its nodes' values.
        (ie, from left to right, level by level).

        For example:
        Given binary tree [3,9,20,null,null,15,7],
            3
           / \
          9  20
            /  \
           15   7
        return its level order traversal as:
        [
          [3],
          [9,20],
          [15,7]
        ]
     */

    //Time and Space : O(n)

    //Solution 1 : use Queue and recursion
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
            res.add(list);
        }

        return res;
    }


    //Solution 2 : Use Pre-order traversal
    public static List<List<Integer>> levelOrderBottom2(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;

        helper(res, root, 0);
        return res;
    }

    public static void helper(List<List<Integer>> res, TreeNode root, int level) {
        if (root == null) return;

        //!!!Difference from 102
        if (level >= res.size()) res.add(0, new ArrayList<>());
        //res.size() - level - 1
        res.get(res.size() - level - 1).add(root.val);

        helper(res, root.left, level + 1);
        helper(res, root.right, level + 1);
    }
}
