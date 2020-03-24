package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LE_637_Average_Of_Levels_In_Binary_Tree {
    /**
     * Given a non-empty binary tree, return the average value of
     * the nodes on each level in the form of an array.
     * Example 1:
     * Input:
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * Output: [3, 14.5, 11]
     * Explanation:
     * The average value of nodes on level 0 is 3,  on level 1 is 14.5,
     * and on level 2 is 11. Hence return [3, 14.5, 11].
     *
     * Note:
     * The range of node's value is in the range of 32-bit signed integer.
     *
     * Easy
     */
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            /**
             * !!!
             * both size and sum must be long
             */
            long size = q.size();
            long sum = 0;

            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();
                sum += cur.val;

                if (cur.left != null) q.offer(cur.left);
                if (cur.right != null)  q.offer(cur.right);
            }

            /**
             * !!!
             * convert to double before doing division
             */
            res.add((double)sum / (double)size);
        }

        return res;
    }
}
