package leetcode;

import common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by yuank on 3/11/18.
 */
public class LE_103_Binary_Tree_Zigzag_Level_Order_Traversal {
    /**
        Given a binary tree, return the zigzag level order traversal of its nodes' values.
        (ie, from left to right, then right to left for the next level and alternate between).

        For example:
        Given binary tree [3,9,20,null,null,15,7],
            3
           / \
          9  20
            /  \
           15   7
        return its zigzag level order traversal as:
        [
          [3],
          [20,9],
          [15,7]
        ]
     */

    /**
     * copy from LE_102 and only make a few changes
     *
     * Time and Space : O(n)
     */
    class Solution_BFS {
        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            if (root == null) return res;

            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);

            //boolean value, alternate in loop to control output direction
            boolean x = true;

            while (!q.isEmpty()) {
                int size = q.size();
                List<Integer> list = new ArrayList<>();

                for (int i = 0; i < size; i++) {
                    TreeNode cur = q.poll();

                    /**
                     * !!!
                     * Doing zig zag by changing the sequence of adding it to list based on flag x (change for each level)
                     * Note : The sequence of adding node to queue is always left first , then right. Zig Zag output has
                     *        nothing to do with the logic here. In other words, the logic form of the level in queue
                     *        is always left to right, we only switch sequence when we output it to list.
                     *
                     *        If we change the sequence of adding node to queue, the whole order of the nodes will be
                     *        scrambled and can not be recovered.
                     *
                     */
                    if (x) {
                        list.add(cur.val);
                    } else {
                        list.add(0, cur.val);
                    }

                    /**
                     * !!!
                     * Don't forget!!!
                     */
                    if (cur.left != null) q.offer(cur.left);
                    if (cur.right != null) q.offer(cur.right);
                }

                /**!!!
                 * !Altenate true/false, outside for loop
                 **/
                x = x ? false : true;
                res.add(list);
            }

            return res;
        }
    }

    class Solution_DFS {
        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            if (root == null) return res;

            helper(root, 0, res);
            return res;
        }

        private void helper(TreeNode root, int level, List<List<Integer>> res) {
            if (root == null) return;

            if (level == res.size()) {
                res.add(new ArrayList<>());
            }

            /**
             * Only difference from DFS solution for LE_102_Binary_Tree_Level_Order_Traversal
             */
            if (level % 2 == 0) {
                res.get(level).add(root.val);
            } else {
                res.get(level).add(0, root.val);
            }

            helper(root.left, level + 1, res);
            helper(root.right, level + 1, res);
        }
    }
}
