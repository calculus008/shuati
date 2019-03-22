package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LE_515_Find_Largest_Value_In_Each_Tree_Row {
    /**
     * You need to find the largest value in each row of a binary tree.
     *
     * Example:
     * Input:
     *
     *           1
     *          / \
     *         3   2
     *        / \   \
     *       5   3   9
     *
     * Output: [1, 3, 9]
     */
    class Solution {
        public List<Integer> largestValues(TreeNode root) {
            List<Integer> res = new ArrayList<>();
            if (root == null) {
                return res;
            }

            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);

            while (!q.isEmpty()) {
                int size = q.size();
                int max = Integer.MIN_VALUE;

                for (int i = 0; i < size; i++) {
                    TreeNode cur = q.poll();
                    max = Math.max(max, cur.val);

                    if (cur.left != null) {
                        q.offer(cur.left);
                    }
                    if(cur.right != null) {
                        q.offer(cur.right);
                    }
                }

                res.add(max);
            }

            return res;
        }
    }
}
